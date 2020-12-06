package main.com.aoc.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/template/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        ArrayList<char[]> map = new ArrayList<>();

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            char[] row = line.toCharArray();
            map.add(row);
        }
        reader.close();

        System.err.println("Got " + map.size() + " rows from " + INPUT_FILE);
    }
}