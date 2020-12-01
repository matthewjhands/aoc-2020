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

        while(reader.hasNextLine()){
            int expense = Integer.parseInt(reader.nextLine());
            expenses.add(expense);
        }
        reader.close();

        System.err.println("Got " + expenses.size() + " expenses from " + INPUT_FILE);
        
        expenses.sort(null);

        for (Integer a : expenses) {
            for (Integer b : expenses) {
                if(a + b == 2020){
                   System.err.println(a + " + " + b + " = 2020"); 
                   System.err.println(a + " * " + b + " = " + (a*b));
                   System.exit(0); // Stop execution as soon as we find a valid pair.
                }
            }
        }
    }
}