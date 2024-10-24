package org.example.domain;

public class Product {
    private final String name;
    private final Long price;

    public Product(final String name, final Long price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    Long getPrice() {
        return price;
    }
}
