package org.example;

import org.example.domain.Inventory;
import org.example.domain.Supermarket;
import org.example.utils.DataLoader;
import org.example.utils.InventoryUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        Supermarket supermarket = new Supermarket(InventoryUtils.createInventory());
        try {
            supermarket.execute();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}