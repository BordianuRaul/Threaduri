package org.example;

import org.example.domain.Inventory;
import org.example.domain.Supermarket;
import org.example.utils.DataLoader;
import org.example.utils.ProductGenerator;

public class Main {
    public static void main(String[] args) {
        //ProductGenerator.generateProductsToFile("src/main/java/org/example/data/data.txt",4000);
        Inventory inventory = new Inventory();
        DataLoader dataLoader = new DataLoader("src/main/java/org/example/data/data.txt", inventory);
        dataLoader.readFile();
        Supermarket supermarket = new Supermarket(inventory);

        System.out.println(supermarket.nrOfProducts());
    }
}