package com.proje.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    private Map<String, Stack> stacks;
    private Queue queue;
    private RegionTree tree;
    private ArizaMudahale arizaMudahale;
    private List<Map<String, Object>> districts;
    private Random random;
    public int hataCounter = 0;

    public Main(List<Map<String, Object>> districts) {
        this.districts = districts;
        this.stacks = new HashMap<>();
        this.queue = new Queue();
        this.tree = new RegionTree();
        this.arizaMudahale = new ArizaMudahale();
        this.random = new Random();

        String[] regions = {"Beylikdüzü Merkez", "Beşiktaş Merkez", "Kadıköy Merkez", "Çekmeköy Merkez"};
        for (String region : regions) {
            stacks.put(region, new Stack());
        }
    }

    public void main() {
        this.arizaMudahale = new ArizaMudahale();
        this.hataCounter = 0;

        int packetId = 101;
        for (String region : stacks.keySet()) {
            Stack stack = stacks.get(region);
            for (int i = 0; i < 10; i++) {
                stack.push(new EnerjiPaketi(packetId++));
            }
        }

        for (Stack stack : stacks.values()) {
            stack.transferToQueue(queue);
        }

        int nodeId = 1;
        Node root = tree.getRoot();
        for (String region : stacks.keySet()) {
            tree.insert(nodeId++, region, "Istanbul");
        }

        Map<String, String> districtToRegion = new HashMap<>();
        for (Map<String, Object> district : districts) {
            String ilce = (String) district.get("ilce");
            String merkez = (String) ((Map<?, ?>) district.get("elektrikHatti")).get("merkez");
            districtToRegion.put(ilce, merkez);
            tree.insert(nodeId++, ilce, merkez);
        }

        Map<String, Integer> regionToBolgeID = Map.of(
            "Beylikdüzü Merkez", 0, "Beşiktaş Merkez", 1, "Kadıköy Merkez", 2, "Çekmeköy Merkez", 3
        );
        int id = 0;
        List<BolgeIlce> arizaListesi = new ArrayList<>();
        for (Map<String, Object> district : districts) {
            String ilce = (String) district.get("ilce");
            String merkez = (String) ((Map<?, ?>) district.get("elektrikHatti")).get("merkez");
            int bolgeID = regionToBolgeID.get(merkez);
            int ilceID = districts.indexOf(district) + 1;
            String region = getIlceById(ilceID);

            System.out.println("DEBUG: " + ilce + " -> " + merkez + " (bolgeID=" + bolgeID + ", ilceID=" + ilceID + ")");
            boolean arizali = random.nextDouble() < 0.3;
            BolgeIlce bolgeIlce = new BolgeIlce(bolgeID, ilceID);
            if (arizali && !arizaListesi.contains(bolgeIlce)) {
                arizaListesi.add(bolgeIlce);
                arizaMudahale.insert(id, bolgeID, region, true);
                hataCounter++;
                district.put("durum", "sorunlu");
                System.out.println("Arıza eklendi: " + ilce + ", ilceID: " + ilceID + ", durum: sorunlu");
            } else if (!arizaListesi.contains(bolgeIlce)) {
                arizaMudahale.insert(id, bolgeID, region, false);
                district.put("durum", "sorunsuz");
                System.out.println("Sorunsuz ilçe: " + ilce + ", ilceID: " + ilceID + ", durum: sorunsuz");
            }
            district.put("bolgeID", bolgeID);
            district.put("ilceID", ilceID);
            id++;
        }

        System.out.println("Başlangıç hataCounter: " + hataCounter);
        tree.printTree();
        arizaMudahale.print();
    }

    public List<Map<String, Object>> getDistrictData() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> district : districts) {
            String ilce = (String) district.get("ilce");
            String durum = (String) district.get("durum");
            result.add(Map.of(
                "id", district.get("id"),
                "region", district.get("region"),
                "ilce", ilce,
                "durum", durum,
                "lat", district.get("lat"),
                "lng", district.get("lng"),
                "elektrikHatti", district.get("elektrikHatti"),
                "bolgeID", district.get("bolgeID"),
                "ilceID", district.get("ilceID"),
                "hataCounter", hataCounter
            ));
        }
        return result;
    }

    private int getBolgeIDFromMerkez(String merkez) {
        return switch (merkez) {
            case "Beylikdüzü Merkez" -> 0;
            case "Beşiktaş Merkez" -> 1;
            case "Kadıköy Merkez" -> 2;
            case "Çekmeköy Merkez" -> 3;
            default -> 3;
        };
    }

    private boolean isAriza(int bolgeID, int ilceID) {
        Node[] table = arizaMudahale.getBolgeHashTablolari().get(bolgeID);
        int index = (ilceID - 1) % table.length;
        if (bolgeID == 0) {
            System.out.println("isAriza kontrolü: bolgeID=" + bolgeID + ", ilceID=" + ilceID + ", index=" + index);
        }
        Node node = table[index];
        int nodeCount = 0;
        while (node != null) {
            nodeCount++;
            if (bolgeID == 0) {
                System.out.println("isAriza düğüm " + nodeCount + ": BolgeID=" + node.bolgeID + ", İlce: " + node.region + ", Hatalı=" + node.hataliMi);
            }
            if (node.region.equals(getIlceById(ilceID)) && node.bolgeID == bolgeID) {
                return node.hataliMi;
            }
            node = node.next;
        }
        if (bolgeID == 0) {
            System.out.println("isAriza: Arıza bulunamadı, ilceID=" + ilceID + ", düğüm sayısı=" + nodeCount);
        }
        return false;
    }

    public void addAriza(int ilceID) {
        Map<String, Object> district = districts.get(ilceID - 1);
        int bolgeID = (int) district.get("bolgeID");
        int id = (int) district.get("id");
        String region = (String) district.get("ilce");
        district.put("durum", "sorunlu");

        arizaMudahale.update(id, bolgeID, region, true);
        hataCounter++;
        System.out.println("Yeni arıza eklendi: ilceID=" + ilceID + ", ilce: " + district.get("ilce") + ", bolgeID=" + bolgeID);
        arizaMudahale.print();
    }

    public void arizaDuzelt(int bolgeID, int ilceID) {
        System.out.println("arizaDuzelt çağrıldı: bolgeID=" + bolgeID + ", ilceID=" + ilceID);

        Map<String, Object> district;
        try {
            district = districts.get(ilceID - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Hata: Geçersiz ilceID: " + ilceID);
            return;
        }

        bolgeID = (int) district.get("bolgeID");
        int id = (int) district.get("id");
        String region = (String) district.get("ilce");
        System.out.println("Düzeltilmiş bolgeID: " + bolgeID + ", ilce: " + district.get("ilce"));

        arizaMudahale.update(id, bolgeID, region, false);
        district.put("durum", "sorunsuz");
        hataCounter--;
        System.out.println("Arıza düzeltildi: Bölge " + bolgeID + ", İlçe " + ilceID + ", İlce: " + district.get("ilce"));
        arizaMudahale.print();
    }

    public int getHataCounter() {
        return hataCounter;
    }

    public ArizaMudahale getArizaMudahale() {
        return arizaMudahale;
    }

    public String getIlceById(int ilceID) {
        try {
            Map<String, Object> district = districts.get(ilceID - 1);
            return (String) district.get("ilce");
        } catch (IndexOutOfBoundsException e) {
            return "Hata: Geçersiz ilceID: " + ilceID;
        }
    }
}