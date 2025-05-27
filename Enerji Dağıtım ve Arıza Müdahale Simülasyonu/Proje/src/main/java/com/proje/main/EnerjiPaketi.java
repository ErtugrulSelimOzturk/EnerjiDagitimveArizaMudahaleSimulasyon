package com.proje.main;

public class EnerjiPaketi {
    private int id;

    public EnerjiPaketi(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Enerji Paketi ID=" + id ;
    }
}