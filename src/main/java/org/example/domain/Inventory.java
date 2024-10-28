package org.example.domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    private ConcurrentHashMap<Product, Integer> inventory;
    private Map<Product, Lock> locks;

    public Inventory() {
        inventory = new ConcurrentHashMap<>();
        locks = new ConcurrentHashMap<>();
    }

    public void add(Product product, int quantity) {
        inventory.put(product, quantity);
        locks.putIfAbsent(product, new ReentrantLock());
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
        Lock lock = locks.get(product);
        if(lock == null) {
            throw new Exception("Null lock for product:" + product.getName());
        }
        lock.lock();
        try {
            if (inventory.containsKey(product)) {
                int currentQuantity = inventory.get(product);

                if (currentQuantity >= quantityToBeSubstracted) {
                    inventory.put(product, currentQuantity - quantityToBeSubstracted);
                } else {
                    throw new Exception("NOT ENOUGH INVENTORY");
                }
            } else {
                throw new Exception("Product " + product.getName() + " does not exist");
            }
        } finally {
            lock.unlock();
        }
    }
}
