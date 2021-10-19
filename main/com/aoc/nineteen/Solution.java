package main.com.aoc.nineteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {

    public static ArrayList<String> expandRule(Integer targetRuleNo, HashMap<Integer, String> rules, HashMap<Integer, ArrayList<String>> expandedRules) {
        if(expandedRules.containsKey(targetRuleNo)) {
            return expandedRules.get(targetRuleNo);
        } else if(rules.get(targetRuleNo).contains("\"")){
            return new ArrayList<>();
        }

        return new ArrayList<>(); //temp
    }

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/nineteen/input.txt";
        final Pattern RULE_PATTERN = Pattern.compile("^(\\d+): (.*)$");

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));
        HashMap<Integer, String> rules = new HashMap<>();
        ArrayList<String> messages = new ArrayList<>();

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);

        for (String el : lines) {
            Matcher match = RULE_PATTERN.matcher(el);
            match.find();

            if(match.matches()) {
                try {
                    rules.put(Integer.parseInt(match.group(1)), match.group(2));
                } catch (IllegalStateException e) {
                    System.err.printf("Couldn't parse %s\n", el);
                }
            } else {
                messages.add(el);
            }
        }

        System.err.printf("Found %d rules, and %d messages.", rules.size(), messages.size());



    }
}