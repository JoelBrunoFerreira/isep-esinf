package org.esinf.DoublyLinkedList;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("╔═══════════════════════════════════════════════════════╗");
            System.out.println("║     TESTES DA DOUBLY LINKED LIST COM SENTINELS        ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝\n");

            // ========================================
            // PARTE 1: TESTES BÁSICOS
            // ========================================
            testeParte1();

            // ========================================
            // PARTE 2: TESTES DO ITERATOR
            // ========================================
            testeParte2();

            // ========================================
            // PARTE 3: TESTES EQUALS E CLONE
            // ========================================
            testeParte3();

            // ========================================
            // TESTES EXTRAS
            // ========================================
            testesExtras();

            System.out.println("\n✓✓✓ TODOS OS TESTES EXECUTADOS COM SUCESSO! ✓✓✓");

        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========================================
    // PARTE 1: MÉTODOS BÁSICOS
    // ========================================
    private static void testeParte1() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  PARTE 1: TESTES DOS MÉTODOS BÁSICOS");
        System.out.println("═══════════════════════════════════════════════════════\n");

        DoublyLinkedList<String> list = new DoublyLinkedList<>();

        // Teste 1: Lista vazia
        System.out.println("--- Teste 1: Lista Vazia ---");
        System.out.println("isEmpty(): " + list.isEmpty());
        System.out.println("size(): " + list.size());
        System.out.println("first(): " + list.first());
        System.out.println("last(): " + list.last());
        System.out.println("✓ Lista vazia testada\n");

        // Teste 2: Adicionar elementos no início
        System.out.println("--- Teste 2: Adicionar no Início ---");
        list.addFirst("JFK");
        list.addFirst("PVD");
        list.addFirst("SFO");
        System.out.println("Adicionados: SFO, PVD, JFK (nesta ordem)");
        System.out.println("size(): " + list.size());
        System.out.println("first(): " + list.first());
        System.out.println("last(): " + list.last());
        imprimirLista("Lista atual", list);
        System.out.println("✓ Adição no início testada\n");

        // Teste 3: Adicionar elementos no final
        System.out.println("--- Teste 3: Adicionar no Final ---");
        DoublyLinkedList<String> list2 = new DoublyLinkedList<>();
        list2.addLast("A");
        list2.addLast("B");
        list2.addLast("C");
        System.out.println("Adicionados: A, B, C");
        System.out.println("size(): " + list2.size());
        System.out.println("first(): " + list2.first());
        System.out.println("last(): " + list2.last());
        imprimirLista("Lista atual", list2);
        System.out.println("✓ Adição no final testada\n");

        // Teste 4: Remover do início
        System.out.println("--- Teste 4: Remover do Início ---");
        String removed = list.removeFirst();
        System.out.println("Removido: " + removed);
        System.out.println("size(): " + list.size());
        System.out.println("first(): " + list.first());
        imprimirLista("Lista atual", list);
        System.out.println("✓ Remoção do início testada\n");

        // Teste 5: Remover do final
        System.out.println("--- Teste 5: Remover do Final ---");
        removed = list.removeLast();
        System.out.println("Removido: " + removed);
        System.out.println("size(): " + list.size());
        System.out.println("last(): " + list.last());
        imprimirLista("Lista atual", list);
        System.out.println("✓ Remoção do final testada\n");

        // Teste 6: Adicionar e remover combinados
        System.out.println("--- Teste 6: Operações Combinadas ---");
        list.addFirst("INÍCIO");
        list.addLast("FIM");
        imprimirLista("Após adicionar INÍCIO e FIM", list);
        list.removeFirst();
        list.removeLast();
        imprimirLista("Após remover primeiro e último", list);
        System.out.println("✓ Operações combinadas testadas\n");
    }

    // ========================================
    // PARTE 2: ITERATOR
    // ========================================
    private static void testeParte2() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  PARTE 2: TESTES DO ITERATOR");
        System.out.println("═══════════════════════════════════════════════════════\n");

        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addLast(40);
        list.addLast(50);

        // Teste 1: hasNext e next
        System.out.println("--- Teste 1: Iteração para Frente (next) ---");
        ListIterator<Integer> iter = list.listIterator();
        System.out.print("Elementos: ");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();
        System.out.println("✓ Iteração para frente testada\n");

        // Teste 2: hasPrevious e previous
        System.out.println("--- Teste 2: Iteração para Trás (previous) ---");
        System.out.print("Elementos (reverso): ");
        while (iter.hasPrevious()) {
            System.out.print(iter.previous() + " ");
        }
        System.out.println();
        System.out.println("✓ Iteração para trás testada\n");

        // Teste 3: nextIndex e previousIndex
        System.out.println("--- Teste 3: Índices do Iterator ---");
        iter = list.listIterator();
        while (iter.hasNext()) {
            System.out.println("nextIndex: " + iter.nextIndex() +
                    ", previousIndex: " + iter.previousIndex() +
                    ", elemento: " + iter.next());
        }
        System.out.println("✓ Índices testados\n");

        // Teste 4: add() do iterator
        System.out.println("--- Teste 4: Adicionar via Iterator ---");
        iter = list.listIterator();
        iter.next(); // 10
        iter.next(); // 20
        iter.add(25); // Adiciona 25 entre 20 e 30
        imprimirLista("Após adicionar 25", list);
        System.out.println("✓ Adição via iterator testada\n");

        // Teste 5: remove() do iterator
        System.out.println("--- Teste 5: Remover via Iterator ---");
        iter = list.listIterator();
        iter.next(); // 10
        iter.next(); // 20
        iter.remove(); // Remove 20
        imprimirLista("Após remover 20", list);
        System.out.println("✓ Remoção via iterator testada\n");

        // Teste 6: set() do iterator
        System.out.println("--- Teste 6: Modificar via Iterator (set) ---");
        iter = list.listIterator();
        iter.next(); // 10
        iter.set(15); // Modifica 10 para 15
        imprimirLista("Após modificar 10 para 15", list);
        System.out.println("✓ Modificação via iterator testada\n");

        // Teste 7: Navegação bidirecional
        System.out.println("--- Teste 7: Navegação Bidirecional ---");
        iter = list.listIterator();
        System.out.println("Avança 2: " + iter.next() + ", " + iter.next());
        System.out.println("Retrocede 1: " + iter.previous());
        System.out.println("Avança 1: " + iter.next());
        System.out.println("Avança 1: " + iter.next());
        System.out.println("Retrocede 2: " + iter.previous() + ", " + iter.previous());
        System.out.println("✓ Navegação bidirecional testada\n");

        // Teste 8: Exceções
        System.out.println("--- Teste 8: Testes de Exceções ---");
        iter = list.listIterator();
        try {
            iter.remove(); // Deve falhar: nenhum next/previous chamado
            System.out.println("❌ Deveria ter lançado exceção!");
        } catch (NoSuchElementException e) {
            System.out.println("✓ Exceção correta ao remover sem next/previous");
        }

        while (iter.hasNext()) iter.next(); // Vai até o fim
        try {
            iter.next(); // Deve falhar: fim da lista
            System.out.println("❌ Deveria ter lançado exceção!");
        } catch (NoSuchElementException e) {
            System.out.println("✓ Exceção correta ao ultrapassar o fim");
        }

        iter = list.listIterator();
        try {
            iter.previous(); // Deve falhar: início da lista
            System.out.println("❌ Deveria ter lançado exceção!");
        } catch (NoSuchElementException e) {
            System.out.println("✓ Exceção correta ao ultrapassar o início");
        }
        System.out.println();
    }

    // ========================================
    // PARTE 3: EQUALS E CLONE
    // ========================================
    private static void testeParte3() throws CloneNotSupportedException {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  PARTE 3: TESTES DE EQUALS E CLONE");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Teste 1: Equals - Listas iguais
        System.out.println("--- Teste 1: Equals - Listas Iguais ---");
        DoublyLinkedList<String> list1 = new DoublyLinkedList<>();
        list1.addLast("A");
        list1.addLast("B");
        list1.addLast("C");

        DoublyLinkedList<String> list2 = new DoublyLinkedList<>();
        list2.addLast("A");
        list2.addLast("B");
        list2.addLast("C");

        System.out.println("list1.equals(list2): " + list1.equals(list2));
        System.out.println("list2.equals(list1): " + list2.equals(list1));
        System.out.println("✓ Listas iguais testadas\n");

        // Teste 2: Equals - Listas diferentes
        System.out.println("--- Teste 2: Equals - Listas Diferentes ---");
        DoublyLinkedList<String> list3 = new DoublyLinkedList<>();
        list3.addLast("A");
        list3.addLast("B");
        list3.addLast("X"); // Diferente

        System.out.println("list1.equals(list3): " + list1.equals(list3));
        System.out.println("✓ Listas diferentes testadas\n");

        // Teste 3: Equals - Tamanhos diferentes
        System.out.println("--- Teste 3: Equals - Tamanhos Diferentes ---");
        DoublyLinkedList<String> list4 = new DoublyLinkedList<>();
        list4.addLast("A");
        list4.addLast("B");

        System.out.println("list1.equals(list4): " + list1.equals(list4));
        System.out.println("✓ Tamanhos diferentes testados\n");

        // Teste 4: Clone - Cópia profunda
        System.out.println("--- Teste 4: Clone - Cópia Profunda ---");
        DoublyLinkedList<String> original = new DoublyLinkedList<>();
        original.addLast("X");
        original.addLast("Y");
        original.addLast("Z");

        DoublyLinkedList<String> cloned = (DoublyLinkedList<String>) original.clone();

        System.out.println("Original == Clone (mesma referência): " + (original == cloned));
        System.out.println("Original.equals(Clone): " + original.equals(cloned));
        imprimirLista("Original", original);
        imprimirLista("Clone", cloned);
        System.out.println("✓ Clone inicial testado\n");

        // Teste 5: Clone - Independência
        System.out.println("--- Teste 5: Clone - Teste de Independência ---");
        cloned.addLast("W");
        original.removeFirst();

        System.out.println("Após modificações independentes:");
        imprimirLista("Original (removeu primeiro)", original);
        imprimirLista("Clone (adicionou W)", cloned);
        System.out.println("Original.equals(Clone): " + original.equals(cloned));
        System.out.println("✓ Independência do clone testada\n");

        // Teste 6: Equals com null
        System.out.println("--- Teste 6: Equals com Null e Outros Tipos ---");
        System.out.println("list1.equals(null): " + list1.equals(null));
        System.out.println("list1.equals(\"string\"): " + list1.equals("string"));
        System.out.println("✓ Equals com null testado\n");
    }

    // ========================================
    // TESTES EXTRAS
    // ========================================
    private static void testesExtras() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  TESTES EXTRAS");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Teste 1: Lista com tipos diferentes
        System.out.println("--- Teste 1: Lista de Inteiros ---");
        DoublyLinkedList<Integer> intList = new DoublyLinkedList<>();
        for (int i = 1; i <= 5; i++) {
            intList.addLast(i);
        }
        imprimirLista("Lista de inteiros", intList);

        int soma = 0;
        for (int num : intList) {
            soma += num;
        }
        System.out.println("Soma dos elementos: " + soma);
        System.out.println("✓ Lista de inteiros testada\n");

        // Teste 2: Enhanced for loop (foreach)
        System.out.println("--- Teste 2: Enhanced For Loop ---");
        DoublyLinkedList<String> cities = new DoublyLinkedList<>();
        cities.addLast("Porto");
        cities.addLast("Lisboa");
        cities.addLast("Coimbra");

        System.out.print("Cidades: ");
        for (String city : cities) {
            System.out.print(city + " ");
        }
        System.out.println();
        System.out.println("✓ Enhanced for loop testado\n");

        // Teste 3: Lista grande
        System.out.println("--- Teste 3: Performance com Lista Grande ---");
        DoublyLinkedList<Integer> bigList = new DoublyLinkedList<>();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            bigList.addLast(i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Adicionados 10000 elementos em " + (endTime - startTime) + "ms");
        System.out.println("Tamanho final: " + bigList.size());

        startTime = System.currentTimeMillis();
        int count = 0;
        for (Integer num : bigList) {
            count++;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Iterados " + count + " elementos em " + (endTime - startTime) + "ms");
        System.out.println("✓ Performance testada\n");

        // Teste 4: Operações mistas com iterator
        System.out.println("--- Teste 4: Operações Mistas com Iterator ---");
        DoublyLinkedList<String> mixed = new DoublyLinkedList<>();
        mixed.addLast("1");
        mixed.addLast("2");
        mixed.addLast("3");
        mixed.addLast("4");
        mixed.addLast("5");

        ListIterator<String> it = mixed.listIterator();
        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("2") || val.equals("4")) {
                it.remove();
            }
        }

        imprimirLista("Após remover 2 e 4 via iterator", mixed);

        it = mixed.listIterator();
        while (it.hasNext()) {
            String val = it.next();
            if (val.equals("3")) {
                it.add("2.5");
            }
        }

        imprimirLista("Após adicionar 2.5 após o 3", mixed);
        System.out.println("✓ Operações mistas testadas\n");
    }

    // ========================================
    // MÉTODO AUXILIAR PARA IMPRIMIR LISTAS
    // ========================================
    private static <E> void imprimirLista(String titulo, DoublyLinkedList<E> list) {
        System.out.print(titulo + ": [");
        Iterator<E> iter = list.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next());
            if (iter.hasNext()) {
                System.out.print(", ");
            }
        }
        System.out.println("] (size=" + list.size() + ")");
    }
}

/*

        ## **Output Esperado:**
        ```
        ╔═══════════════════════════════════════════════════════╗
        ║     TESTES DA DOUBLY LINKED LIST COM SENTINELS        ║
        ╚═══════════════════════════════════════════════════════╝

        ═══════════════════════════════════════════════════════
PARTE 1: TESTES DOS MÉTODOS BÁSICOS
═══════════════════════════════════════════════════════

        --- Teste 1: Lista Vazia ---
isEmpty(): true
size(): 0
first(): null
last(): null
        ✓ Lista vazia testada

--- Teste 2: Adicionar no Início ---
Adicionados: SFO, PVD, JFK (nesta ordem)
size(): 3
first(): SFO
last(): JFK
Lista atual: [SFO, PVD, JFK] (size=3)
        ✓ Adição no início testada

--- Teste 3: Adicionar no Final ---
Adicionados: A, B, C
size(): 3
first(): A
last(): C
Lista atual: [A, B, C] (size=3)
        ✓ Adição no final testada

[... continua com todos os testes ...]

        ✓✓✓ TODOS OS TESTES EXECUTADOS COM SUCESSO! ✓✓✓

*/