package main.com.aoc.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/template/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);
    }
}