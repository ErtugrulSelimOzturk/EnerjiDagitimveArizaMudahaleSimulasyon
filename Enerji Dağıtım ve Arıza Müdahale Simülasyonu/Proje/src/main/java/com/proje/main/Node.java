package com.proje.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node 
{
    int ID;
    int bolgeID;
    int ilceID;
    boolean hataliMi;
    Node next;
    String region;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBolgeID() {
        return bolgeID;
    }

    public void setBolgeID(int bolgeID) {
        this.bolgeID = bolgeID;
    }
/* Her bir düğümün (Node) “istediğin kadar alt düğümü” (çocuğu) olabilmesi için
       dinamik boyutlu bir koleksiyona ihtiyacımız var. */

    List<Node> children = new ArrayList<>();

    public Node(int ID,int bolgeID, int ilceID,boolean hataliMi)
    {
        this.ID = ID;
        this.bolgeID = bolgeID;
        this.ilceID = ilceID;
        this.hataliMi = hataliMi;
        this.next = null;
    }

    public Node(int ID,int bolgeID, String region,boolean hataliMi)
    {
        this.ID = ID;
        this.bolgeID = bolgeID;
        this.region = region;
        this.hataliMi = hataliMi;
        this.next = null;
    }

    public Node(int id, String region) 
    {
        this.ID = id;
        this.region = region;
    }

    public void addChild(Node child) 
    {
        children.add(child);

        // Çocukları id'ye göre sıralı tutmak için yaptık!
        //Collections.sort(children, Comparator.comparingInt(n -> n.ID));
    }
}
