package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Supermarket {
    private List<Bill> bills;
    private Inventory inventory;
    public Long profit;

    public Supermarket(Inventory inventory) {
        this.inventory = inventory;
        bills = new ArrayList<>();
        profit = 0L;
    }

    public int nrOfProducts() {
        return inventory.size();
    }

}
