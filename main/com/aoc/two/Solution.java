package main.com.aoc.two;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/two/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        LinkedList<Password> passwords = new LinkedList<>();
        int validPasswordsPartOne = 0;
        int validPasswordsPartTwo = 0;
        Pattern fromLine = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");

        while (reader.hasNextLine()) {
            // example line: 3-4 t: ttnt
            String line = reader.nextLine();
            Password  newPassword = new Password();

            Matcher matcher = fromLine.matcher(line);
            matcher.matches();

            newPassword.policyMin = Integer.parseInt(matcher.group(1));
            newPassword.policyMax = Integer.parseInt(matcher.group(2));
            newPassword.policyChar = matcher.group(3).charAt(0);
            newPassword.value = matcher.group(4);

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
        System.err.println("passwords[91]: " + passwords.get(91).toString());
        System.err.println("Part One: There are "+ validPasswordsPartOne + " valid passwords.");
        System.err.println("Part Two: There are "+ validPasswordsPartTwo + " valid passwords.");

    }
}