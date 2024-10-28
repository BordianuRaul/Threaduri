package org.example.utils;

import org.example.domain.Bill;
import org.example.domain.Inventory;
import org.example.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BillGenerator {

    private static final int MAX_NR_OF_PRODUCTS = 9;
    private static final int MAX_QUANTITY_FOR_PRODUCT = 99;

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

}
