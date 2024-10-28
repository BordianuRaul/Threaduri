package org.example.utils;

import org.example.domain.Inventory;

public class InventoryUtils {

    private static final int NR_OF_PRODUCTS = 4000;

    public static Inventory createInventory() {
        //ProductGenerator.generateProductsToFile("src/main/java/org/example/data/data.txt",NR_OF_PRODUCTS);
        Inventory inventory = new Inventory();
        DataLoader dataLoader = new DataLoader("src/main/java/org/example/data/data.txt", inventory);
        dataLoader.readFile();

        return inventory;
    }
}
