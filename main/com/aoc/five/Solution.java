package main.com.aoc.five;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    private static final String INPUT_FILE = "main/com/aoc/five/input.txt";

    public static void main(String[] args) throws Exception {

        String allSeatsInput = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> seatCodes = new ArrayList<>(Arrays.asList(allSeatsInput.split("\n")));
        ArrayList<BoardingPass> boardingPasses = new ArrayList<>();

        seatCodes.forEach((sc) -> {
            BoardingPass newBP = new BoardingPass(sc);
            boardingPasses.add(newBP);
        });

        System.err.println("Got " + seatCodes.size() + " seat codes from " + INPUT_FILE);
        System.err.println("Part One | Maximum SeatID was: " + BoardingPass.maxSeatID);
        System.err.println("Part Two | Assigned SeatIDs: " + BoardingPass.assignedSeatIDs.size() + ", Unassigned SeatIDs: " + BoardingPass.unassignedSeatIDs.size() );
        System.err.println("Part Two | My SeatID is: " + BoardingPass.findMySeat());
    }

}