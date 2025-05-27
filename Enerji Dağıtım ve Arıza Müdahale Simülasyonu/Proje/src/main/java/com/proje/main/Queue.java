package com.proje.main;

public class Queue {
    private static class Node {
        EnerjiPaketi paket;
        Node next;

        Node(EnerjiPaketi paket) {
            this.paket = paket;
            this.next = null;
        }
    }

    private Node front, rear;

    public Queue() {
        this.front = this.rear = null;
    }

    public void enqueue(EnerjiPaketi paket) {
        Node newNode = new Node(paket);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public EnerjiPaketi dequeue() {
        if (isEmpty()) return null;
        EnerjiPaketi paket = front.paket;
        front = front.next;
        if (front == null) rear = null;
        return paket;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void printQueue() {
        Node temp = front;
        System.out.print("Kuyruk: ");
        while (temp != null) {
            System.out.print(temp.paket + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}