package org.example.utils;

import org.example.domain.Bill;
import org.example.domain.Inventory;
import org.example.domain.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BillGenerator {

    private static final int MAX_NR_OF_PRODUCTS = 9;
    private static final int MAX_QUANTITY_FOR_PRODUCT = 99;
    private static final String FILE_PATH = "src/main/java/org/example/data/bills.txt";

    public static List<Bill> generateListOfBills(int nrOfBills) {

        List<Bill> bills = new ArrayList();
        Inventory inventory = InventoryUtils.createInventory();
        Random random = new Random();

        for(int currentBill = 0; currentBill < nrOfBills; currentBill++) {
            Bill bill = new Bill();
            int nrOfProductsInBill = random.nextInt(MAX_NR_OF_PRODUCTS) + 1;
            for(int currentProduct = 0; currentProduct < nrOfProductsInBill; currentProduct++) {
                int randomProduct = random.nextInt(inventory.size() - 2) + 1;
                Product product = inventory.getProductAtIndex(randomProduct);
                int randomQuantity = random.nextInt(MAX_QUANTITY_FOR_PRODUCT) + 1;
                bill.addProduct(product, randomQuantity);
            }

            bills.add(bill);
        }

        return bills;

    }

    public static void generateBillsToFile(int nrOfBills) {
        List<Bill> bills = generateListOfBills(nrOfBills);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Bill bill : bills) {
                for (Product product : bill.getProducts().keySet()) {
                    int quantity = bill.getProducts().get(product);
                    writer.write(product.getName() + "," + product.getPrice() + "," + quantity);
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Bill> readBillsFromFile() {
        List<Bill> bills = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Bill bill = new Bill();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    if (!bill.getProducts().isEmpty()) {
                        bills.add(bill);
                        bill = new Bill();
                    }
                    continue;
                }

                String[] parts = line.split(",");
                String productName = parts[0];
                long productPrice = Long.parseLong(parts[1]);
                int quantity = Integer.parseInt(parts[2]);

                Product product = new Product(productName, productPrice);
                bill.addProduct(product, quantity);
            }

            if (!bill.getProducts().isEmpty()) {
                bills.add(bill);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bills;
    }
}
