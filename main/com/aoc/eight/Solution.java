package main.com.aoc.eight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Solution {

    public static int processInstructions(List<Instruction> instructions){
        // process instructions in order until a loop is found.
        //   then return the current accumulator value.
        int accumulator = 0;
        HashSet<Instruction> seenInstructions = new HashSet<>();
        Instruction currentInstruction = instructions.get(0);

        while (!seenInstructions.contains(currentInstruction) && (seenInstructions.size() < instructions.size())) {
            seenInstructions.add(currentInstruction);
            switch (currentInstruction.opComponent) {
                case "nop":
                    currentInstruction = instructions.get(currentInstruction.index + 1);
                    break;
                case "jmp":
                    currentInstruction = instructions.get(currentInstruction.index + currentInstruction.numberComponent);
                    break;
                case "acc":
                    accumulator = accumulator + currentInstruction.numberComponent;
                    currentInstruction = instructions.get(currentInstruction.index + 1);
                    break;
                default:
                    throw new IllegalArgumentException("Bad operation found: "+ currentInstruction.opComponent);
            }

        }
        if (seenInstructions.size() < instructions.size()) {
            return accumulator;
        } else {
            throw new IllegalArgumentException("No loop found!");
        }
    }

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/eight/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));
        ArrayList<Instruction> instructions = new ArrayList<>(lines.size());

        System.err.println("Got " + lines.size() + " instructions from " + INPUT_FILE);

        for(int i = 0; i < lines.size(); i++) {
            instructions.add(new Instruction(i, lines.get(i)));
        }
        
        int accumlatorPt1 = processInstructions(instructions);

        System.err.println("Part One | Accumulator value: "+accumlatorPt1);
    }
}