package org.example.domain;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Supermarket {
    private static final int NR_OF_THREADS = 12;
    private static final int CHECK_THRESHOLD = 1000;

    private final List<Bill> bills;
    private final List<Bill> listOfSales;
    private final Inventory inventory;
    private long profit;

    private final Object saleLock = new Object();
    private final Object profitLock = new Object();

    public Supermarket(Inventory inventory, List<Bill> bills) {
        this.inventory = inventory;
        this.bills = bills;
        listOfSales = new ArrayList<>();
        profit = 0L;
    }

    private void computeBill(Bill bill) throws Exception {
        Map<Product, Integer> productsAndQuantities = bill.getProducts();
        Bill sale = new Bill();

        for(Product product : productsAndQuantities.keySet()) {
            try {
                inventory.updateInventoryForProduct(product, productsAndQuantities.get(product));
                sale.addProduct(product, productsAndQuantities.get(product));

            } catch (Exception e) {
                if(!Objects.equals(e.getMessage(), "NOT ENOUGH INVENTORY")) {
                  throw e;
                }
            }
        }
        synchronized (listOfSales) {
            listOfSales.add(sale);

        }

        synchronized (profitLock) {
            profit += sale.getTotalPrice();
        }


    }

    public void execute() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(NR_OF_THREADS);
        List<Callable<Void>> tasks = new ArrayList<>();

        int chunkSize = bills.size() / NR_OF_THREADS;

        for(int i = 0; i < NR_OF_THREADS; i++) {
            List<Bill> threadBills = bills.subList(i * chunkSize, i == NR_OF_THREADS - 1 ? bills.size() : (i + 1) * chunkSize);
            tasks.add(() -> {
                int processedBillsCount = 0;
                for(Bill bill : threadBills) {
                    computeBill(bill);
                    processedBillsCount++;

                    if(processedBillsCount == CHECK_THRESHOLD) {
                        List<Bill> snapshotSales;
                        long snapshotProfit;
                        synchronized (saleLock) {
                            snapshotSales = new ArrayList<>(listOfSales);
                        }
                        synchronized (profitLock) {
                            snapshotProfit = profit;
                        }
                        printValidityCheckResult(snapshotSales, snapshotProfit);
                        processedBillsCount = 0;
                    }
                }
                return null;
            });
        }

        List<Future<Void>> results = executor.invokeAll(tasks);
        for(Future<Void> future : results) {
            future.get();
        }

        executor.shutdown();

        printValidityCheckResult(listOfSales, profit);
    }

    boolean checkValidity(List<Bill> snapshotListOfSales, long snapshotProfit) {
        long computedProfit = 0;
        for (Bill sale : snapshotListOfSales) {
            computedProfit += sale.getTotalPrice();
        }

        System.out.println("Computed profit: " + stringifyProfit(computedProfit) + "\nReal profit: " + stringifyProfit(snapshotProfit));
        return computedProfit == snapshotProfit;
    }

    void printValidityCheckResult(List<Bill> snapshotListOfSales, long snapshotProfit) {
        System.out.println("Validity check result:\n");
        if(checkValidity(snapshotListOfSales, snapshotProfit)) {
            System.out.println("Valid\n");
        }
        else {
            System.out.println("Invalid\n");
        }
    }

    private String stringifyProfit(long profit) {
        final String decimal = String.valueOf(profit % 100);
        final String whole = String.valueOf(profit / 100);
        return whole + "." + decimal + "$";
    }

}
