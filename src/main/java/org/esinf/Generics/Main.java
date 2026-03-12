package org.esinf.Generics;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Criar um supermercado
            Supermarket supermarket = new Supermarket();

            // ========================================
            // 1. TESTAR getInvoices()
            // ========================================
            System.out.println("=== TESTE 1: Carregar Faturas ===");
            List<String> invoiceData = Arrays.asList(
                    "I,INV001,2016/09/10",
                    "P,EGG,12,200",
                    "P,APPLE,2,140",
                    "P,BUTTER,1,100",
                    "I,INV002,2016/09/11",
                    "P,PEAR,3,230",
                    "P,CHIPS,3,320",
                    "I,INV003,2016/09/15",
                    "P,EGG,5,200",
                    "P,CHIPS,2,320"
            );

            supermarket.getInvoices(invoiceData);
            System.out.println("✓ Faturas carregadas com sucesso!");
            System.out.println("Total de faturas: " + supermarket.sup.size());
            System.out.println();

            // ========================================
            // 2. TESTAR numberOfProductsPerInvoice()
            // ========================================
            System.out.println("=== TESTE 2: Número de Produtos por Fatura ===");
            Map<Invoice, Integer> productsCount = supermarket.numberOfProductsPerInvoice();

            for (Map.Entry<Invoice, Integer> entry : productsCount.entrySet()) {
                System.out.println("Fatura " + entry.getKey().getReference() +
                        " tem " + entry.getValue() + " produtos");
            }
            System.out.println();

            // ========================================
            // 3. TESTAR betweenDates()
            // ========================================
            System.out.println("=== TESTE 3: Faturas entre Datas ===");
            LocalDate data1 = LocalDate.of(2016, 9, 9);
            LocalDate data2 = LocalDate.of(2016, 9, 12);

            Set<Invoice> invoicesBetween = supermarket.betweenDates(data1, data2);
            System.out.println("Faturas entre " + data1 + " e " + data2 + ":");
            for (Invoice inv : invoicesBetween) {
                System.out.println("  - " + inv.getReference() + " (" + inv.getDate() + ")");
            }
            System.out.println();

            // ========================================
            // 4. TESTAR totalOfProduct()
            // ========================================
            System.out.println("=== TESTE 4: Total de um Produto ===");
            String productId = "EGG";
            long total = supermarket.totalOfProduct(productId);
            System.out.println("Total de " + productId + ": " + total);
            System.out.println("  (Cálculo: INV001: 12*200=2400 + INV003: 5*200=1000 = 3400)");
            System.out.println();

            // ========================================
            // 5. TESTAR convertInvoices()
            // ========================================
            System.out.println("=== TESTE 5: Converter Invoices (Produto -> Faturas) ===");
            Map<String, Set<Invoice>> converted = supermarket.convertInvoices();

            for (Map.Entry<String, Set<Invoice>> entry : converted.entrySet()) {
                System.out.println("Produto: " + entry.getKey());
                System.out.print("  Aparece nas faturas: ");
                for (Invoice inv : entry.getValue()) {
                    System.out.print(inv.getReference() + " ");
                }
                System.out.println();
            }
            System.out.println();

            // ========================================
            // 6. TESTES ADICIONAIS
            // ========================================
            System.out.println("=== TESTES ADICIONAIS ===");

            // Testar equals e hashCode de Product
            Product p1 = new Product("EGG", 10, 200);
            Product p2 = new Product("EGG", 5, 150);
            System.out.println("Produtos com mesmo ID são iguais? " + p1.equals(p2));
            System.out.println("HashCodes iguais? " + (p1.hashCode() == p2.hashCode()));

            // Testar equals e hashCode de Invoice
            Invoice i1 = new Invoice("INV001", "2016/09/10");
            Invoice i2 = new Invoice("INV001", "2020/01/01");
            System.out.println("Faturas com mesma referência são iguais? " + i1.equals(i2));
            System.out.println("HashCodes iguais? " + (i1.hashCode() == i2.hashCode()));

            // Testar compareTo
            Product p3 = new Product("APPLE");
            Product p4 = new Product("ZEBRA");
            System.out.println("Comparação APPLE vs ZEBRA: " + p3.compareTo(p4));

            System.out.println("\n✓ Todos os testes executados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
/*

        ## Output esperado:
        ```
        === TESTE 1: Carregar Faturas ===
        ✓ Faturas carregadas com sucesso!
Total de faturas: 3

        === TESTE 2: Número de Produtos por Fatura ===
Fatura INV001 tem 3 produtos
Fatura INV002 tem 2 produtos
Fatura INV003 tem 2 produtos

=== TESTE 3: Faturas entre Datas ===
Faturas entre 2016-09-09 e 2016-09-12:
        - INV001 (2016-09-10)
  - INV002 (2016-09-11)

=== TESTE 4: Total de um Produto ===
Total de EGG: 3400
        (Cálculo: INV001: 12*200=2400 + INV003: 5*200=1000 = 3400)

        === TESTE 5: Converter Invoices (Produto -> Faturas) ===
Produto: EGG
Aparece nas faturas: INV001 INV003
Produto: APPLE
Aparece nas faturas: INV001
Produto: BUTTER
Aparece nas faturas: INV001
Produto: PEAR
Aparece nas faturas: INV002
Produto: CHIPS
Aparece nas faturas: INV002 INV003

=== TESTES ADICIONAIS ===
Produtos com mesmo ID são iguais? true
HashCodes iguais? true
Faturas com mesma referência são iguais? true
HashCodes iguais? true
Comparação APPLE vs ZEBRA: -25

        ✓ Todos os testes executados com sucesso!
 */