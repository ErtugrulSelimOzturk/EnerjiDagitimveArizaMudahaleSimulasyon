package com.proje.main;

public class RegionTree
{
    private final Node root;

    public RegionTree()
    {
        // root her zaman İstanbul olmalı!
        this.root = new Node(0, "Istanbul");
    }

    public Node getRoot()
    {
        return root;
    }

    private Node findByRegion(Node node, String region)
    {
        if (node == null)
            return null;
        if (node.region.equals(region))
            return node;
        for (Node c : node.children)
        {
            Node r = findByRegion(c, region);
            if (r != null)
                return r;
        }
        return null;
    }

    private Node findById(Node node, int id)
    {
        if (node == null)
            return null;
        if (node.ID == id)
            return node;
        for (Node c : node.children)
        {
            Node r = findById(c, id);
            if (r != null)
                return r;
        }
        return null;
    }

    public void insert(int id, String region, String parentRegion)
    {
        if (findById(root, id) != null)
        {
            System.err.println("Hata: id=" + id + " zaten mevcut, ekleme iptal.");
            return;
        }
        if (findByRegion(root, region) != null)
        {
            System.err.println("Hata: \"" + region + "\" zaten var, ekleme iptal.");
            return;
        }
        Node parent = findByRegion(root, parentRegion);
        if (parent == null)
        {
            System.err.println("Hata: parent \"" + parentRegion + "\" bulunamadi.");
            return;
        }
        parent.addChild(new Node(id, region));
    }

    /** Post‑order: önce çocukları, sonra node’u kendisi için toplamEnergy hesapla */

    public void printTree()
    {
        // önce tüm toplam enerjileri güncelle
        // sonra çiz
        printSubtree(root, "", true);
    }
    private void printSubtree(Node node, String prefix, boolean isTail)
     {
        System.out.println(prefix
            + (isTail ? "└─ " : "├─ ")
            + node.region
            + " (id=" + node.ID);
        for (int i = 0; i < node.children.size(); i++)
        {
            boolean last = (i == node.children.size() - 1);
            printSubtree(node.children.get(i),
                         prefix + (isTail ? "   " : "│  "),
                         last);
        }
    }
}