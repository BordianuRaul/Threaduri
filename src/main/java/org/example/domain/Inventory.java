package org.example.domain;

import java.util.ArrayList;
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

    public Product getProductAtIndex(int index) {
        ArrayList<Product> products = new ArrayList<>(inventory.keySet());

        if (index >= 0 && index < products.size()) {
            return products.get(index);
        }

        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + inventory.size());
    }

    public void updateInventoryForProduct(Product product, int quantityToBeSubstracted) throws Exception {
        if(inventory.containsKey(product)) {
            int currentQuantity = inventory.get(product);

            if(currentQuantity >= quantityToBeSubstracted) {
                inventory.put(product, currentQuantity - quantityToBeSubstracted);
            } else {
                throw new Exception("NOT ENOUGH INVENTORY");
            }
        } else {
            throw new Exception("????");
        }
    }
}
