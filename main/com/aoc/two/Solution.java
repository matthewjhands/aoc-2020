package main.com.aoc.two;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/two/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        LinkedList<Password> passwords = new LinkedList<>();
        int validPasswordsPartOne = 0;
        int validPasswordsPartTwo = 0;

        while (reader.hasNextLine()) {
            // example line: 3-4 t: ttnt
            String line = reader.nextLine();
            String[] parts = line.split(":");
            Password  newPassword = new Password();
            newPassword.value = parts[1].strip();

            String[] policyParts = parts[0].split(" ");
            newPassword.policyChar = policyParts[1].charAt(0);

            String[] range = policyParts[0].split("-");
            newPassword.policyMin = Integer.parseInt(range[0]);
            newPassword.policyMax = Integer.parseInt(range[1]);

            passwords.add(newPassword);

            if(newPassword.isValidPartOne()) {
                validPasswordsPartOne++;
            }

            if(newPassword.isValidPartTwo()) {
                validPasswordsPartTwo++;
            }

        }
        reader.close();

        System.err.println("Got " + passwords.size() + " passwords from " + INPUT_FILE);
        System.err.println("Password 0: " + passwords.get(0).toString());
        System.err.println("Part One: There are "+ validPasswordsPartOne + " valid passwords.");
        System.err.println("Part Two: There are "+ validPasswordsPartTwo + " valid passwords.");

    }
}