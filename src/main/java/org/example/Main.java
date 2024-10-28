package org.example;

import org.example.domain.Inventory;
import org.example.domain.Supermarket;
import org.example.utils.BillGenerator;
import org.example.utils.DataLoader;
import org.example.utils.InventoryUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        //BillGenerator.generateBillsToFile(40000);
        Supermarket supermarket = new Supermarket(InventoryUtils.createInventory(), BillGenerator.readBillsFromFile());

        long startTime = System.nanoTime();

        try {
            supermarket.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0;

        System.out.printf("Execution Time: %.2f ms%n", elapsedTime);

    }
}