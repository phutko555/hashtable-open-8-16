package com.epam.rd.autocode.hashtableopen816;

public class HashtableOpen8to16Impl implements HashtableOpen8to16 {
    private Entry[] table;
    private int capacity;
    private int size;

    public HashtableOpen8to16Impl() {
        this.capacity = 8;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    @Override
    public void insert(int key, Object value) {
        if (size >= capacity) {  // Check if resizing is needed
            if (capacity == 16) {
                throw new IllegalStateException("Maximum capacity reached");
            }
            resize(capacity * 2);
        }
        int index = probe(key, table);
        if (table[index] == null) {
            table[index] = new Entry(key, value);
            size++;
        } else {
            table[index].value = value; // Update existing entry
        }
    }

    @Override
    public void remove(int key) {
        int index = probe(key, table);
        if (table[index] != null && table[index].key == key) {
            table[index] = null;  // Remove the entry
            size--;
            if (size <= capacity / 4 && capacity > 8) {
                resize(capacity / 2);  // Resize down if necessary
            }
        }
    }


    @Override
    public Object search(int key) {
        int index = probe(key);
        if (table[index] != null && table[index].key == key) {
            return table[index].value;
        }
        return null;
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        return java.util.Arrays.stream(table)
                .filter(e -> e != null)
                .mapToInt(e -> e.key)
                .toArray();
    }

    private void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        for (Entry entry : this.table) {
            if (entry != null) {
                int newIndex = Math.floorMod(entry.key, newCapacity);  // Recompute the index based on the new capacity
                while (newTable[newIndex] != null) {
                    newIndex = (newIndex + 1) % newCapacity;  // Resolve collisions if any
                }
                newTable[newIndex] = entry;
            }
        }
        this.table = newTable;  // Replace the old table with the new one
    }


    private int probe(int key) {
        return probe(key, table);
    }

    private int probe(int key, Entry[] table) {
        int index = Math.floorMod(key, table.length);  // Ensures the index is non-negative
        while (table[index] != null && table[index].key != key) {
            index = (index + 1) % table.length;  // Circular increment
        }
        return index;
    }


    private static class Entry {
        int key;
        Object value;

        Entry(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public static HashtableOpen8to16 getInstance() {
        return HashtableOpen8to16Impl.getInstance();
    }
}

