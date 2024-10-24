package org.example.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ProductGenerator {
    private static final int PRODUCT_NAME_LENGTH = 10;
    private static final Random random = new Random();

    private static String generateRandomProductName() {
        StringBuilder productName = new StringBuilder(PRODUCT_NAME_LENGTH);
        for (int i = 0; i < PRODUCT_NAME_LENGTH; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            productName.append(randomChar);
        }
        return productName.toString();
    }

    private static int generateRandomQuantity() {
        return random.nextInt(1000) + 1;
    }

    private static int generateRandomPrice() {
        return random.nextInt(100) + 1;
    }

    public static void generateProductsToFile(String fileName, int nrOfProducts) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < nrOfProducts; i++) {
                String productName = generateRandomProductName();
                int quantity = generateRandomQuantity();
                int price = generateRandomPrice();

                writer.write(String.format("%s,%d,%d\n", productName, quantity, price));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
