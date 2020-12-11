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
        // and collect into List (and then ArrayList).
        ArrayList<Integer> adapters = new ArrayList<>(Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt)
                .sorted().boxed().collect(Collectors.toList()));

        System.err.println("Got " + adapters.size() + " adapters from " + INPUT_FILE);

        HashMap<Integer, Integer> differences = new HashMap<>();
        Integer currentRating = 0;

        for (int adapter : adapters) {
            int diff = adapter - currentRating;
            differences.compute(diff, (k, v) -> (v == null) ? 1 : v + 1);
            currentRating = adapter;
        }

        // extra 3 diff for last adapter -> device
        differences.compute(3, (k, v) -> v + 1);

        System.err.println("Part One | Differences: " + differences + ", " + differences.get(1) + "*"
                + differences.get(3) + "=" + differences.get(1) * differences.get(3));

        /* 
            I got stuck writing part two!
        
            I'd thought about recursion but given the trillions (!) of possible valid solutions,
            I ruled it out (or at least I did in it's most naive form). I was thinking about
            finding substrings that could lead to multiple paths and then multiplying all those together 
            (e.g. [4,5,6,7] has the paths [4,5,6,7], [4,5,7], [4,6,7], [4,7]) but then I was aware you'd need to track overlap 
            between them (for an overall string of [4,5,6,7,8] you can't reduce to [4,5,7] and [6,8] at the same time) and it got late
            and my bed beckoned (to be able to function in my day job the next day).

            Thankfully though I did learn about memoization, recursive functions and dynamic programming!
            https://medium.com/swlh/understanding-recursion-memoization-and-dynamic-programming-3-sides-of-the-same-coin-8c1f57ee5604
            
            Thanks to the AoC Reddit community and the repo of repos that is https://github.com/Bogdanp/awesome-advent-of-code, i can confirm
            that the answer to part 2 is 37024595836928 (37 trillion 24 billion 595 million 836 thousand 928). I ended up reading a few solutions 
            so it seemed unfair to publish my own, given that it would be very heavily influenced by/ inevitably copying everyone else's.
        */

    }

}