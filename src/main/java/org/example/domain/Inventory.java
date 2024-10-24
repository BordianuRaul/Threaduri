package org.example.domain;

import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private ConcurrentHashMap<Product, Integer> inventory;

    public Inventory() {
        inventory = new ConcurrentHashMap<>();
    }

    public void add(Product product, int quantity) {
        inventory.put(product, quantity);
    }

    public int get(Product product) {
        return inventory.get(product);
    }

    public void update(Product product, int quantity) {
        inventory.put(product, quantity);
    }

    public int size() {
        return inventory.size();
    }
}
