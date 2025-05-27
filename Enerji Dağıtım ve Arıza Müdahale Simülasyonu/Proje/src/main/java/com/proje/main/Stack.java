package com.proje.main;

public class Stack {
    private static class Node {
        EnerjiPaketi paket;
        Node next;

        Node(EnerjiPaketi paket) {
            this.paket = paket;
            this.next = null;
        }
    }

    private Node top;

    public Stack() {
        this.top = null;
    }

    public void push(EnerjiPaketi paket) {
        Node newNode = new Node(paket);
        newNode.next = top;
        top = newNode;
    }

    public EnerjiPaketi pop() {
        if (isEmpty()) return null;
        EnerjiPaketi paket = top.paket;
        top = top.next;
        return paket;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void transferToQueue(Queue queue) {
        while (!isEmpty()) {
            queue.enqueue(pop());
        }
    }
}