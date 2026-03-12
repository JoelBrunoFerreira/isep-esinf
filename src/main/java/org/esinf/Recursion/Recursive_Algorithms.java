package org.esinf.Recursion;

public class Recursive_Algorithms {
    static void main() {
        System.out.println("=== TESTES DOS ALGORITMOS RECURSIVOS ===\n");

        // Teste a) - Inverter string
        System.out.println("a) Inverter string:");
        System.out.println("reverse('hello') = '" + reverse("hello") + "'");
        System.out.println("reverse('java') = '" + reverse("java") + "'");
        System.out.println("reverse('arara') = '" + reverse("arara") + "'");
        System.out.println();

        // Teste b) - Produto
        System.out.println("b) Produto de dois inteiros:");
        System.out.println("product(5, 3) = " + product(5, 3));
        System.out.println("product(7, 4) = " + product(7, 4));
        System.out.println("product(12, 0) = " + product(12, 0));
        System.out.println();

        // Teste c) - MDC
        System.out.println("c) Máximo divisor comum:");
        System.out.println("gcd(48, 30) = " + gcd(48, 30));
        System.out.println("gcd(54, 24) = " + gcd(54, 24));
        System.out.println("gcd(100, 35) = " + gcd(100, 35));
        System.out.println();

        // Teste d) - String para inteiro
        System.out.println("d) Converter string de dígitos em inteiro:");
        System.out.println("stringToInt('13531') = " + stringToInt("13531"));
        System.out.println("stringToInt('2024') = " + stringToInt("2024"));
        System.out.println("stringToInt('999') = " + stringToInt("999"));
        System.out.println();

        // Teste e) - Número palíndromo
        System.out.println("e) Verificar se número é palíndromo:");
        int[] numbers = {99, 101, 111, 121, 1221, 21112, 10001, 123, 456};
        for (int num : numbers) {
            System.out.println(num + " é palíndromo? " + isPalindrome(num));
        }
        System.out.println();

        // Teste f) - Soma array 2D
        System.out.println("f) Soma de array 2D:");
        int[][] arr2D = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        System.out.println("Array 4x4:");
        for (int[] row : arr2D) {
            System.out.print("  ");
            for (int val : row) {
                System.out.printf("%3d ", val);
            }
            System.out.println();
        }
        System.out.println("Soma total: " + sum2D(arr2D));
    }

    // a) Inverter string
    public static String reverse(String str) {
        if (str.isEmpty()) return str;
        return reverse(str.substring(1)) + str.charAt(0);
    }

    // b) Produto de dois inteiros usando apenas soma e subtração
    public static int product(int m, int n) {
        if (n == 0) return 0;
        if (n == 1) return m;
        return m + product(m, n - 1);
    }

    // c) Máximo divisor comum (m.d.c) - Algoritmo de Euclides
    public static int gcd(int m, int n) {
        if (n == 0) return m;
        return gcd(n, m % n);
    }

    // d) Converter string de dígitos em inteiro
    public static int stringToInt(String str) {
        if (str.isEmpty()) return 0;
        int lastDigit = str.charAt(str.length() - 1) - '0';
        return stringToInt(str.substring(0, str.length() - 1)) * 10 + lastDigit;
    }

    // e) Verificar se número é palíndromo
    public static boolean isPalindrome(int num) {
        String str = String.valueOf(num);
        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int start, int end) {
        if (start >= end) return true;
        if (str.charAt(start) != str.charAt(end)) return false;
        return isPalindromeHelper(str, start + 1, end - 1);
    }

    // f) Soma de array 2D n×n
    public static int sum2D(int[][] arr, int row, int col) {
        if (row >= arr.length) return 0;
        if (col >= arr[row].length) return sum2D(arr, row + 1, 0);
        return arr[row][col] + sum2D(arr, row, col + 1);
    }

    public static int sum2D(int[][] arr) {
        return sum2D(arr, 0, 0);
    }

}
