package org.example.utils;

import org.example.domain.Inventory;
import org.example.domain.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    final String fileName;
    final Inventory inventory;

    public DataLoader(final String fileName, final Inventory inventory) {
        this.fileName = fileName;
        this.inventory = inventory;
    }

    public void readFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                Long price = Long.parseLong(parts[1]);
                Integer quantity = Integer.parseInt(parts[2]);

                Product product = new Product(name, price);
                inventory.add(product, quantity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
