package com.proje.main;

public class BolgeIlce {
    private int bolgeID;
    private int ilceID;

    public BolgeIlce(int bolgeID, int ilceID) {
        this.bolgeID = bolgeID;
        this.ilceID = ilceID;
    }

    public int getBolgeID() {
        return bolgeID;
    }

    public int getIlceID() {
        return ilceID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BolgeIlce other = (BolgeIlce) obj;
        return bolgeID == other.bolgeID && ilceID == other.ilceID;
    }

    @Override
    public int hashCode() {
        return 11 * bolgeID + ilceID;
    }
}
