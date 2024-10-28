package org.example.domain;

import org.example.utils.BillGenerator;

import java.util.*;

public class Supermarket {
    private final static int NR_OF_THREADS = 2;

    private final List<Bill> bills;
    private final List<Bill> listOfSales;
    private final Inventory inventory;
    private long profit;

    public Supermarket(Inventory inventory) {
        this.inventory = inventory;
        bills = BillGenerator.generateListOfBills(1000);
        listOfSales = new ArrayList<>();
        profit = 0L;
    }

    public int nrOfProducts() {
        return inventory.size();
    }

    private void computeBill(Bill bill) throws Exception {
        Map<Product, Integer> productsAndQuantities = bill.getProducts();
        Bill sale = new Bill();
        for(Product product : productsAndQuantities.keySet()) {
            try {
                inventory.updateInventoryForProduct(product, productsAndQuantities.get(product));
                sale.addProduct(product, productsAndQuantities.get(product));
                profit += productsAndQuantities.get(product) * product.getPrice();
            } catch (Exception e) {
                if(!Objects.equals(e.getMessage(), "NOT ENOUGH INVENTORY")) {
                  throw e;
                }
            }
        }
        listOfSales.add(sale);
    }

    public void execute() throws Exception {
        //add threads
        for(Bill bill : bills) {
            computeBill(bill);
        }
        if(checkValidity()) {
            System.out.println("Valid");
        }
        else {
            System.out.println("Invalid");
        }
    }

    boolean checkValidity() {
        long computedProfit = 0;
        for(Bill sale : listOfSales) {
            computedProfit += sale.getTotalPrice();
        }
        System.out.println("Computed profit: " + computedProfit + "\nReal profit: " + profit);
        return computedProfit == profit;
    }

}
