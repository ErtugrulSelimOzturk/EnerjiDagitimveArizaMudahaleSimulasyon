package com.proje.main;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ArizaMudahale {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";

    Map<Integer, Node[]> bolgeHashTablolari = new HashMap<>();

    public ArizaMudahale() {
        for (int bolgeID = 0; bolgeID < 4; bolgeID++) {
            bolgeHashTablolari.put(bolgeID, new Node[10]);
        }
    }

    private int hash(int ID) {
        return ID % 10;
    }

    public void insert(int ID, int bolgeID, String region, boolean hataliMi) {
        Node[] table = bolgeHashTablolari.get(bolgeID);
        int index = hash(ID);
        Node newNode = new Node(ID, bolgeID, region, hataliMi);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node temp = table[index];
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }

        String color = hataliMi ? RED : GREEN;
        System.out.println(color + ID + " eklendi. (Bölge: " + bolgeID + ", index: " + index + ")" +
                (hataliMi ? " [HATALI]" : "") + RESET);
    }

    public void update(int ID, int bolgeID, String newRegion, boolean yeniHataliMi) {
        Node[] table = bolgeHashTablolari.get(bolgeID);
        if (table == null) {
            System.out.println(RED + "Hata: Geçersiz bölge ID: " + bolgeID + RESET);
            return;
        }

        int index = hash(ID);
        Node current = table[index];

        while (current != null) {
            if (current.ID == ID && current.bolgeID == bolgeID) {
                current.region = newRegion;
                current.hataliMi = yeniHataliMi;
                String color = yeniHataliMi ? RED : GREEN;
                System.out.println(color + "ID " + ID + " güncellendi. Bölge: " + bolgeID + ", İlçe: " +
                        newRegion + ", Hatalı: " + yeniHataliMi + RESET);
                return;
            }
            current = current.next;
        }

        System.out.println(RED + "Hata: ID " + ID + ", Bölge " + bolgeID + " bulunamadı." + RESET);
    }

    public Map<Integer, Node[]> getBolgeHashTablolari() {
        return bolgeHashTablolari;
    }

    public void print() {
        for (Map.Entry<Integer, Node[]> entry : bolgeHashTablolari.entrySet()) {
            int bolgeID = entry.getKey();
            System.out.println(PURPLE + "=== Bölge " + bolgeID + " ===" + RESET);
            Node[] table = entry.getValue();
            for (int i = 0; i < 10; i++) {
                System.out.print("Index[" + i + "] -> ");
                Node temp = table[i];
                while (temp != null) {
                    String color = temp.hataliMi ? RED : RESET;
                    System.out.print(color + "(ID: " + temp.ID + ", İlçe: " + temp.region + ") -> " + RESET);
                    temp = temp.next;
                }
                System.out.println("null");
            }
        }
    }
}