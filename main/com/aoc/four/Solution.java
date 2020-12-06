package main.com.aoc.four;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/four/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        ArrayList<HashMap<String, String>> passports = new ArrayList<>();
        ArrayList<String> passportAttributes = new ArrayList<>();
        
        String input = "";
        while (reader.hasNextLine()) {
            // load file, split attribute groups into ArrayList
            String line = reader.nextLine();
            // apparently split() won't neccessarily return matches in the order
            //   they appear in the string, hence more complicated split logic..
            if (line.isEmpty()){
                passportAttributes.add(input.trim());
                input = "";
                continue;
            }
            input = input + " " + line;
        }
        reader.close();
        passportAttributes.add(input.trim()); // add last passport for end of file

        int validPassportsPt1 = 0;
        for (String string : passportAttributes) {
            // parse each attribute group into a HashMap
            String[] attributes = string.split(" ");

            HashMap<String, String> passport = new HashMap<>();
            for (String attribute : attributes) {
                String[] kvPair = attribute.split(":");
                passport.put(kvPair[0], kvPair[1]);
            }

            passports.add(passport);

            if(passport.size() == 8) {
                validPassports++;
            } else if (passport.size() == 7 && !passport.containsKey("cid")) {
                validPassports++;
            }

        }

        System.err.println("Got " + passports.size() + " passports from " + INPUT_FILE);
        System.err.println("There were " + validPassports + " valid passports.");
    }
}