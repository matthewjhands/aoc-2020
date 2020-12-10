package main.com.aoc.ten;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/ten/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        
        // hellish stream to split the input into array, parse to int, sort, unbox,
        //   and collect into List (and then ArrayList).
        ArrayList<Integer> adapters = new ArrayList<>(
            Arrays.stream(input.split("\n"))
            .mapToInt(Integer::parseInt).sorted()
            .boxed().collect(Collectors.toList()));

        System.err.println("Got " + adapters.size() + " adapters from " + INPUT_FILE);

        HashMap<Integer, Integer> differences = new HashMap<>();
        Integer currentRating = 0;

        for (int adapter : adapters) {
            int diff = adapter - currentRating;
            differences.compute(diff, (k,v) -> (v == null) ? 1 : v + 1);
            currentRating = adapter;
        }
        
        // extra 3 diff for last adapter -> device
        differences.compute(3, (k,v) -> v + 1);

        System.err.println("Part One | Differences: " + differences + ", " 
            + differences.get(1) + "*" + differences.get(3) + "=" + differences.get(1) * differences.get(3));

    }
}