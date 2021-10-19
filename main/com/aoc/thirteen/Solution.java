package main.com.aoc.thirteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

class Solution {

    public static long part2Attempt2(String schedules) {
        ArrayList<String> schedulesPt2 = new ArrayList<>();
        schedulesPt2.addAll(new ArrayList<>(Arrays.stream(schedules.split(",")).collect(Collectors.toList())));

        // increment each element by it's index 
        for (int i = 0; i < schedulesPt2.size(); i++) {
            String el = schedulesPt2.get(i);
            if (!el.equals("x")) {
                Integer sum = (Integer.parseInt(el) + i);
                el = String.valueOf(sum);
                schedulesPt2.set(i, el);
            }
        }

        // strip out x's and sort in reverse order. 
        ArrayList<Integer> sortedScheduleValues = new ArrayList<>(
            schedulesPt2.stream().filter((el) -> (!el.equals("x")))
            .mapToInt(Integer::parseInt).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));

        long tTime = 1;

        for (Integer el : sortedScheduleValues) {
            if (tTime % el > 0) {
                tTime = tTime*el;
            }
        }

        return tTime;

        // .reduce(0, (subTotal, element) -> (subTotal % element > 0) ? subTotal*element : subTotal;
    }

    public static long part2Attempt3(String schedules) {
        ArrayList<String> schedulesPt2 = new ArrayList<>();
        schedulesPt2.addAll(new ArrayList<>(Arrays.stream(schedules.split(",")).collect(Collectors.toList())));

        long tTime = 0;
        long multiplier = 0;

        for (String el : schedulesPt2) {
            
            // skip x's
            if (el.equals("x")) {
                continue;
            }

            if (multiplier == 0) {
                multiplier = Integer.parseInt(el);
            }

            do {
                tTime = tTime + multiplier;
            } while ((tTime == 0) || (tTime + schedulesPt2.indexOf(el)) % Integer.parseInt(el) > 0);

            // add el to multiplier
            multiplier = multiplier * Integer.parseInt(el);

        }

        return tTime;
    }

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/thirteen/input.txt";

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

        /*

        // Part Two
        ArrayList<ArrayList<String>> schedulesPt2 = new ArrayList<>();
        schedulesPt2.add(new ArrayList<>(Arrays.stream(lines.get(1).split(",")).collect(Collectors.toList())));

        // find the max bus ID - we'll iterate around it's timetable to reduce overall number of iterations.
        int maxBusID = schedules.get(1).stream().max((i, j) -> i.compareTo(j)).get();
        int maxBusIDIndex = schedulesPt2.get(0).indexOf(String.valueOf(maxBusID));

        long minTTime = 100000000000000L;
        //slightly cheeky hack - set the initial multiplier so that starting tTime >= 100000000000000, as hinted at in challenge
        long multiplierPt2 = (long) Math.floor(minTTime / maxBusID); 
        long tTime = 0;
        boolean solutionFound;
        do {
            multiplierPt2++;
            solutionFound = true;
            // ArrayList<String> newTimes = new ArrayList<>(schedulesPt2.get(0).size());
            tTime = (maxBusID*multiplierPt2) - maxBusIDIndex;

            for (int i = 0; i < schedulesPt2.get(0).size(); i++) {
                String el = schedulesPt2.get(0).get(i);
                if(el.equals("x")) {
                    continue;
                } else if ((tTime + i) % Integer.parseInt(el) > 0) {
                    solutionFound = false;
                    break;
                }
            }
        } while (!solutionFound);

        // Uh-oh! My tTime has got to 59684871347196 and it's well and truly time for bed..
        // Something tells me I need to find a more efficient way to do this..
        // TO BE CONTINUED. */

        System.err.println("Part Two | Answer is: "+ part2Attempt3(lines.get(1)));

    }
}