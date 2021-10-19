package main.com.aoc.fourteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/fourteen/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);

        String currentMask;
        HashMap<Integer,Integer> mem = new HashMap<>();
        Pattern addressRegex = Pattern.compile("[0-9]+");
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts[0].startsWith("mask")){
                currentMask = parts[2];
                System.err.println(currentMask);
            } else {
                Matcher match = addressRegex.matcher(parts[0]);
                match.find();
                mem.put(Integer.parseInt(match.group()), Integer.parseInt(parts[2]));
            }
        }
    }
}