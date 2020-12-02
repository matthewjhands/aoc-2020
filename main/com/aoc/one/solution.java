package main.com.aoc.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/one/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);
        LinkedList<Integer> expenses = new LinkedList<>();

        while (reader.hasNextLine()) {
            int expense = Integer.parseInt(reader.nextLine());
            expenses.add(expense);
        }
        reader.close();

        System.err.println("Got " + expenses.size() + " expenses from " + INPUT_FILE);

        expenses.sort(null);

        boolean foundPair = false;
        boolean foundTriple = false;

        for (Integer a : expenses) {
            for (Integer b : expenses) {
                if (!foundPair && (a + b == 2020)) {
                    System.err.println("Part One");
                    System.err.println(a + " + " + b + " = 2020");
                    System.err.println(a + " * " + b + " = " + (a * b));
                    foundPair = true;
                }
                if (!foundTriple) {
                    for (Integer c : expenses) {
                        if (foundPair && foundTriple) {
                            System.exit(0);
                        } else if (a + b + c == 2020) {
                            System.err.println("Part Two");
                            System.err.println(a + " + " + b + " + " + c + " = 2020");
                            System.err.println(a + " * " + b + " * " + c + " = " + (a * b * c));
                            foundTriple = true;
                            break;
                        }
                    }
                }
            }
        }
    }
}