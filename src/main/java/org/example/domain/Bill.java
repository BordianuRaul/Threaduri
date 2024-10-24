package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Bill {
    private Map<Product, Integer> products;
    private long totalPrice;

    Bill() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
        totalPrice += quantity * product.getPrice();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public long getTotalPrice() {
        return totalPrice;
    }
}
