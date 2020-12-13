package main.com.aoc.eleven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/eleven/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);

        // parse earliest departure time and bus numbers/schedules into schedules[0]
        Integer earliestDepart = Integer.parseInt(lines.get(0));
        ArrayList<ArrayList<Integer>> schedules = new ArrayList<>();
        schedules.add(null);
        schedules.add(new ArrayList<>(Arrays.stream(lines.get(1).replace(",x", "").split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList())));
        
        int multiplier = 1;

        do {
            multiplier++;
            ArrayList<Integer> nextTimes = new ArrayList<>(schedules.get(1).size());
            
            for (int i = 0; i < schedules.get(1).size(); i++) {
                nextTimes.add(i, schedules.get(1).get(i) * multiplier);
            }

            schedules.add(nextTimes);

        } while (schedules.get(multiplier).stream().max((i, j) -> i.compareTo(j)).get() < earliestDepart);

        int earliestBusTime = schedules.get(multiplier).stream().filter((i) -> (i >= earliestDepart)).min((i, j) -> i.compareTo(j)).get();
        int busID = earliestBusTime/multiplier;
        int waitTime = earliestBusTime - earliestDepart;

        System.err.println("Part One | Answer is: "+busID*waitTime);

    }
}