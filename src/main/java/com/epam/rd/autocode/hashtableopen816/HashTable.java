package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;

public class HashTable implements HashtableOpen8to16 {
    private int capacity;
    private int size;
    private static final int MAX_SIZE = 16;
    private HashNode[] hashNode;

    public HashTable() {
        super();
        this.capacity = 8;
        this.size = 0;
        this.hashNode = new HashNode[this.capacity];
    }

    @Override
    public void insert(int key, Object value) {
        if (search(key) != null) {
            return;
        }

        if (this.size >= MAX_SIZE) throw new IllegalStateException();
        if (size == capacity) {
            capacity *= 2;
            resize(capacity / 2, capacity);
        }

        HashNode temp = new HashNode(key, value);

        int hashIndex = hashCode(key);

        while (hashNode[hashIndex] != null && hashNode[hashIndex].getKey() != key) {
            hashIndex++;
            hashIndex %= capacity;
        }

        if (hashNode[hashIndex] == null) {
            size++;
        }

        this.hashNode[hashIndex] = temp;
    }

    @Override
    public Object search(int key) {
        int hashIndex = hashCode(key);

        int counter = 0;
        while (this.hashNode[hashIndex] != null) {
            if (counter > this.capacity) {
                return null;
            }
            if (hashNode[hashIndex].getKey() == key){
                return hashNode[hashIndex].getValue();
            }
            hashIndex++;
            hashIndex %= this.capacity;
            counter++;
        }
        if (hashNode[hashIndex] == null){
            for (HashNode node : hashNode){
                if (node != null && (node.getKey() == key)){
                    return node.getValue();

                }
            }
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int hashIndex = hashCode(key);
        int index = hashIndex;

        while (hashNode[hashIndex] == null || hashNode[hashIndex].getKey() != key) {

            hashIndex++;
            hashIndex %= this.capacity;

            if (hashIndex == capacity) hashIndex = 0;

            if (hashIndex == index) return;
        }

        hashNode[hashIndex] = null;
        this.size--;

        if (size * 4 <= capacity && capacity != 2) {
            capacity = capacity / 2;
            resize(capacity * 2, capacity);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int[] keys() {
        int[] array = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            if (hashNode[i] != null) {
                array[i] = hashNode[i].getKey();
            }
        }
        return array;
    }

    public int hashCode(int key) {
        if (key < 0) key = -key;
        return key % this.capacity;
    }

    public void resize(int capacity, int newCapacity) {
        HashNode[] tempCapacity = Arrays.copyOf(this.hashNode, capacity);
        this.hashNode = new HashNode[newCapacity];

        size = 0;//push against
        for (int i = 0; i < capacity; i++)
            if (tempCapacity[i] != null) insert(tempCapacity[i].getKey(), tempCapacity[i].getValue());
    }

}