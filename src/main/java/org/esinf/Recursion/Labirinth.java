package org.esinf.Recursion;

public class Labirinth {
    static void main() {
        System.out.println("=== RESOLUÇÃO DO LABIRINTO ===\n");

        // Labirinto do enunciado (7x13)
        int[][] labyrinth = {
                {1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1},
                {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0},
                {1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                {1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        System.out.println("Labirinto inicial:");
        printLabyrinth(labyrinth);

        System.out.println("\nProcurando caminho de (0,0) para (6,12)...\n");

        // Cria cópia para não modificar o original
        int[][] copy = new int[labyrinth.length][];
        for (int i = 0; i < labyrinth.length; i++) {
            copy[i] = labyrinth[i].clone();
        }

        int[][] result = check(copy, 0, 0);

        if (result != null) {
            System.out.println("Caminho encontrado!");
            System.out.println("\nLabirinto com solução:");
            System.out.println("(9 = caminho, 2 = tentativa sem sucesso, 1 = corredor, 0 = parede)\n");
            printLabyrinth(result);
        } else {
            System.out.println("Não existe caminho!");
        }

        // Teste com labirinto mais simples
        System.out.println("\n\n=== TESTE COM LABIRINTO SIMPLES 5x5 ===\n");
        int[][] simpleLab = {
                {1, 1, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 1, 1}
        };

        System.out.println("Labirinto inicial:");
        printLabyrinth(simpleLab);

        int[][] simpleResult = check(simpleLab, 0, 0);

        if (simpleResult != null) {
            System.out.println("\nCaminho encontrado!");
            printLabyrinth(simpleResult);
        } else {
            System.out.println("\nNão existe caminho!");
        }
    }



    /**
     *
     * @param actual the labyrinth in its actual (marked) form
     * @param y coordinate y in the labyrinth
     * @param x coordinate x in the labyrinth
     * @return the marked labyrinth or null if there is no way
     */
    public static int [][] check(int [][] actual, int y, int x) {
        // Verifica se está fora dos limites
        if (y < 0 || y >= actual.length || x < 0 || x >= actual[0].length) {
            return null;
        }

        // Verifica se é parede (0) ou já visitado (2 ou 9)
        if (actual[y][x] != 1) {
            return null;
        }

        // Marca a posição atual como parte do caminho
        actual[y][x] = 9;

        // Verifica se chegou ao destino
        if (y == actual.length - 1 && x == actual[0].length - 1) {
            return actual;
        }

        // Tenta mover em cada direção: Norte, Este, Sul, Oeste
        int[][] result;

        // Norte (↑)
        result = check(actual, y - 1, x);
        if (result != null) return result;

        // Este (→)
        result = check(actual, y, x + 1);
        if (result != null) return result;

        // Sul (↓)
        result = check(actual, y + 1, x);
        if (result != null) return result;

        // Oeste (←)
        result = check(actual, y, x - 1);
        if (result != null) return result;

        // Backtracking: marca como visitado mas sem solução
        actual[y][x] = 2;

        return null;
    }

    // Auxiliar Method
    public static void printLabyrinth(int[][] lab) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                System.out.print(lab[i][j] + " ");
            }
            System.out.println();
        }
    }
}

