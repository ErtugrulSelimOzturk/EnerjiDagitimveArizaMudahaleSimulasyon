package com.proje.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proje.main.Main;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class DistrictController {
    private List<Map<String, Object>> districts;
    private int hataCounter = 0;
    private Random random;
    private Main system;
    List<Map<String, Object>> sorunsuzDistricts = new ArrayList<>();

    private static final Map<String, Map<String, Double>> merkezler = Map.of(
        "Beylikdüzü Merkez", Map.of("lat", 40.985, "lng", 28.645),
        "Beşiktaş Merkez", Map.of("lat", 41.074, "lng", 29.034),
        "Kadıköy Merkez", Map.of("lat", 40.980, "lng", 29.054),
        "Çekmeköy Merkez", Map.of("lat", 41.0802, "lng", 29.277)
    );

    private static final Map<String, String> ilceMerkezEslesmesi;
    static {
        ilceMerkezEslesmesi = new HashMap<>();
        ilceMerkezEslesmesi.put("Beylikdüzü", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Avcılar", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Bağcılar", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Bahçelievler", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Başakşehir", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Büyükçekmece", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Çatalca", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Esenyurt", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Küçükçekmece", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Silivri", "Beylikdüzü Merkez");
        ilceMerkezEslesmesi.put("Beşiktaş", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Arnavutköy", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Bayrampaşa", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Esenler", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Eyüpsultan", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Gaziosmanpaşa", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Kağıthane", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Sarıyer", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Şişli", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Sultangazi", "Beşiktaş Merkez");
        ilceMerkezEslesmesi.put("Kadıköy", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Beyoğlu", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Adalar", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Bakırköy", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Güngören", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Kartal", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Maltepe", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Üsküdar", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Zeytinburnu", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Fatih", "Kadıköy Merkez");
        ilceMerkezEslesmesi.put("Çekmeköy", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Ataşehir", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Beykoz", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Pendik", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Sancaktepe", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Sultanbeyli", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Şile", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Tuzla", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Gebze", "Çekmeköy Merkez");
        ilceMerkezEslesmesi.put("Ümraniye", "Çekmeköy Merkez");
    }

    @PostConstruct
    public void init() {
        districts = new ArrayList<>();
        districts.add(createDistrict("Beylikdüzü", "sorunsuz", 40.970, 28.630));
        districts.add(createDistrict("Avcılar", "sorunsuz", 40.9818, 28.7223));
        districts.add(createDistrict("Bağcılar", "sorunsuz", 41.0390, 28.8367));
        districts.add(createDistrict("Bahçelievler", "sorunsuz", 41.0000, 28.8610));
        districts.add(createDistrict("Başakşehir", "sorunsuz", 41.0930, 28.7930));
        districts.add(createDistrict("Büyükçekmece", "sorunsuz", 41.0480, 28.5328));
        districts.add(createDistrict("Çatalca", "sorunsuz", 41.1455, 28.4600));
        districts.add(createDistrict("Esenyurt", "sorunsuz", 41.0430, 28.6630));
        districts.add(createDistrict("Küçükçekmece", "sorunsuz", 41.0020, 28.7860));
        districts.add(createDistrict("Silivri", "sorunsuz", 41.1290, 28.2380));
        districts.add(createDistrict("Beşiktaş", "sorunsuz", 41.056, 29.028));
        districts.add(createDistrict("Arnavutköy", "sorunsuz", 41.1850, 28.7400));
        districts.add(createDistrict("Bayrampaşa", "sorunsuz", 41.0400, 28.9100));
        districts.add(createDistrict("Esenler", "sorunsuz", 41.0631, 28.8647));
        districts.add(createDistrict("Eyüpsultan", "sorunsuz", 41.2110, 28.8930));
        districts.add(createDistrict("Gaziosmanpaşa", "sorunsuz", 41.0790, 28.9010));
        districts.add(createDistrict("Kağıthane", "sorunsuz", 41.0654, 28.9630));
        districts.add(createDistrict("Sarıyer", "sorunsuz", 41.1696, 28.9999));
        districts.add(createDistrict("Şişli", "sorunsuz", 41.0515, 28.9870));
        districts.add(createDistrict("Sultangazi", "sorunsuz", 41.1066, 28.8822));
        districts.add(createDistrict("Kadıköy", "sorunsuz", 40.982, 29.071));
        districts.add(createDistrict("Beyoğlu", "sorunsuz", 41.0369, 28.9670));
        districts.add(createDistrict("Adalar", "sorunsuz", 40.8676, 29.1231));
        districts.add(createDistrict("Bakırköy", "sorunsuz", 40.9805, 28.8565));
        districts.add(createDistrict("Güngören", "sorunsuz", 41.0220, 28.8790));
        districts.add(createDistrict("Kartal", "sorunsuz", 40.9020, 29.1890));
        districts.add(createDistrict("Maltepe", "sorunsuz", 40.9370, 29.1430));
        districts.add(createDistrict("Üsküdar", "sorunsuz", 41.0270, 29.0330));
        districts.add(createDistrict("Zeytinburnu", "sorunsuz", 41.0026, 28.9090));
        districts.add(createDistrict("Fatih", "sorunsuz", 41.0080, 28.9580));
        districts.add(createDistrict("Çekmeköy", "sorunsuz", 41.0701, 29.307));
        districts.add(createDistrict("Ataşehir", "sorunsuz", 40.9816, 29.1272));
        districts.add(createDistrict("Beykoz", "sorunsuz", 41.1370, 29.1635));
        districts.add(createDistrict("Pendik", "sorunsuz", 40.939, 29.315));
        districts.add(createDistrict("Sancaktepe", "sorunsuz", 40.9910, 29.2310));
        districts.add(createDistrict("Sultanbeyli", "sorunsuz", 40.9610, 29.2650));
        districts.add(createDistrict("Şile", "sorunsuz", 41.1219, 29.6126));
        districts.add(createDistrict("Tuzla", "sorunsuz", 40.8760, 29.3450));
        districts.add(createDistrict("Gebze", "sorunsuz", 40.7941, 29.421));
        districts.add(createDistrict("Ümraniye", "sorunsuz", 41.0330, 29.1221));

        districts = districts.stream().map(district -> {
            String ilce = (String) district.get("ilce");
            String closestCenter = ilceMerkezEslesmesi.getOrDefault(ilce, "Çekmeköy Merkez");
            Map<String, Double> merkez = merkezler.get(closestCenter);
            int index = districts.indexOf(district);

            Map<String, Object> newDistrict = new HashMap<>(district);
            newDistrict.put("elektrikHatti", Map.of(
                    "merkez", closestCenter,
                    "merkezKoordinat", merkez
            ));
            newDistrict.put("bolgeID", getBolgeIDFromMerkez(closestCenter));
            newDistrict.put("ilceID", index + 1);
            newDistrict.put("id", index);
            newDistrict.put("region", ilce);

            return newDistrict;
        }).toList();

        sorunsuzDistricts = new ArrayList<>(districts);

        random = new Random();

        this.system = new Main(districts);
        this.system.main();
        this.hataCounter = system.getHataCounter();
    }

    private Map<String, Object> createDistrict(String ilce, String durum, double lat, double lng) {
        Map<String, Object> district = new HashMap<>();
        district.put("ilce", ilce);
        district.put("durum", durum);
        district.put("lat", lat);
        district.put("lng", lng);
        return district;
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

    @GetMapping("/district-data")
    public List<Map<String, Object>> getDistrictData() {
        List<Map<String, Object>> result = system.getDistrictData();
        this.hataCounter = system.getHataCounter();
        return result;
    }

    @PostMapping("/fix-issue")
    public Map<String, Object> fixIssue(@RequestBody Map<String, Integer> request) {
        try {
            int ilceID = request.get("ilceID");
            Map<String, Object> district = districts.get(ilceID - 1);

            if ("sorunsuz".equals(district.get("durum"))) {
                return Map.of(
                    "message", "İlçe zaten sorunsuz: " + district.get("ilce"),
                    "durum", "sorunsuz",
                    "hataCounter", hataCounter,
                    "reset", false
                );
            }

            int bolgeID = (int) district.get("bolgeID");
            system.arizaDuzelt(bolgeID, ilceID);
            hataCounter = system.getHataCounter();
            district.put("durum", "sorunsuz");

            boolean isReset = hataCounter <= 0;
            if (isReset) {
                System.out.println("Tüm arızalar düzeltildi, sistem sıfırlandı");
            }

            return Map.of(
                "message", "Arıza düzeltildi: " + district.get("ilce"),
                "durum", "sorunsuz",
                "hataCounter", hataCounter,
                "reset", isReset
            );
        } catch (Exception e) {
            System.out.println("fixIssue hata: " + e.getMessage());
            return Map.of(
                "message", "Arıza düzeltme başarısız: " + e.getMessage(),
                "durum", "sorunlu",
                "hataCounter", hataCounter,
                "reset", false
            );
        }
    }

    @PostMapping("/add-random-issue")
    public Map<String, Object> addRandomIssue() {
        try {
            List<Map<String, Object>> sorunsuzDistricts = system.getDistrictData().stream()
                .filter(d -> "sorunsuz".equals(d.get("durum")))
                .toList();

            if (sorunsuzDistricts.isEmpty()) {
                return Map.of("message", "Tüm ilçeler arızalı", "hataCounter", hataCounter, "ilceID", -1);
            }

            Map<String, Object> district = sorunsuzDistricts.get(random.nextInt(sorunsuzDistricts.size()));
            int ilceID = (int) district.get("ilceID");
            int bolgeID = (int) district.get("bolgeID");
            String ilce = (String) district.get("ilce");

            system.addAriza(ilceID);
            hataCounter = system.getHataCounter();

            System.out.println("Yeni arıza: ilceID=" + ilceID + ", ilce=" + ilce);
            system.getArizaMudahale().print();

            return Map.of(
                "message", "Yeni arıza eklendi: " + ilce,
                "ilceID", ilceID,
                "durum", "sorunlu",
                "hataCounter", hataCounter
            );
        } catch (Exception e) {
            System.out.println("addRandomIssue hata: " + e.getMessage());
            return Map.of("message", "Arıza ekleme başarısız: " + e.getMessage(), "hataCounter", hataCounter, "ilceID", -1);
        }
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