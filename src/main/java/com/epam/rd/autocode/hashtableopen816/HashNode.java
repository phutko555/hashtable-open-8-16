package com.epam.rd.autocode.hashtableopen816;

public class HashNode {
    private final int key;
    private final Object value;

    public HashNode(int key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}