package org.esinf.Generics;

import java.time.LocalDate;
import java.util.*;

public class Supermarket {
    Map <Invoice, Set<Product>> sup;

    Supermarket() {
        sup = new HashMap<>();
    }

    // Reads invoices from a list of String
    void getInvoices(List<String> l) throws Exception {
        Invoice currentInvoice = null;
        Set<Product> products = null;

        for (String line : l) {
            String[] parts = line.split(",");

            if (parts[0].equals("I")) {
                // Se já existe uma invoice anterior, adiciona ao map
                if (currentInvoice != null) {
                    sup.put(currentInvoice, products);
                }
                // Cria nova invoice
                currentInvoice = new Invoice(parts[1], parts[2]);
                products = new HashSet<>();

            } else if (parts[0].equals("P")) {
                // Adiciona produto à invoice atual
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                long price = Long.parseLong(parts[3]);
                products.add(new Product(id, quantity, price));
            }
        }

        // Adiciona a última invoice
        if (currentInvoice != null) {
            sup.put(currentInvoice, products);
        }
    }

    // returns a set in which each number is the number of products in the invoice
    Map<Invoice, Integer> numberOfProductsPerInvoice() {
        Map<Invoice, Integer> result = new HashMap<>();

        for (Map.Entry<Invoice, Set<Product>> entry : sup.entrySet()) {
            result.put(entry.getKey(), entry.getValue().size());
        }

        return result;
    }

    // returns a Set of invoices in which each date is >d1 and <d2
    Set<Invoice> betweenDates(LocalDate d1, LocalDate d2) {
        Set<Invoice> result = new HashSet<>();

        for (Invoice invoice : sup.keySet()) {
            LocalDate invoiceDate = invoice.getDate();
            if (invoiceDate.isAfter(d1) && invoiceDate.isBefore(d2)) {
                result.add(invoice);
            }
        }

        return result;
    }

    // returns the sum of the price of the product in all the invoices
    long totalOfProduct(String productId) {
        long total = 0;

        for (Set<Product> products : sup.values()) {
            for (Product product : products) {
                if (product.getIdentification().equals(productId)) {
                    total += product.getPrice() * product.getQuantity();
                }
            }
        }

        return total;
    }

    // converts a map of invoices and troducts to a map which key is a product
    // identification and the values are a set of the invoices in which it appears
    Map<String, Set<Invoice>> convertInvoices() {
        Map<String, Set<Invoice>> result = new HashMap<>();

        // Para cada invoice e seus produtos
        for (Map.Entry<Invoice, Set<Product>> entry : sup.entrySet()) {
            Invoice invoice = entry.getKey();
            Set<Product> products = entry.getValue();

            // Para cada produto
            for (Product product : products) {
                String productId = product.getIdentification();

                // Se o productId ainda não existe no map, cria um novo Set
                if (!result.containsKey(productId)) {
                    result.put(productId, new HashSet<>());
                }

                // Adiciona a invoice ao Set desse produto
                result.get(productId).add(invoice);
            }
        }

        return result;
    }
}