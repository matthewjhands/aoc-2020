package main.com.aoc.eight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public static ProcessingResult processInstructions(List<Instruction> instructions) throws IndexOutOfBoundsException {
        int accumulator = 0;
        ArrayList<Instruction> seenInstructions = new ArrayList<>();
        Instruction currentInstruction = instructions.get(0);
        boolean looped = true;

        while (!seenInstructions.contains(currentInstruction)) {
            if (!Instruction.permittedOps.contains(currentInstruction.opComponent)) {
                throw new IllegalArgumentException("Bad operation found: "+ currentInstruction.opComponent);
            }

            int nextIndex;
            seenInstructions.add(currentInstruction);

            accumulator = (currentInstruction.opComponent.equals("acc")) ? accumulator + currentInstruction.numberComponent : accumulator;
            nextIndex = (currentInstruction.opComponent.equals("jmp")) ? currentInstruction.index + currentInstruction.numberComponent : currentInstruction.index + 1; 

            if(nextIndex == instructions.size()){
                // we've reached the end of the instructions! (nextIndex == lastIndex + 1)
                looped = false;
                break;
            } else {
                currentInstruction = instructions.get(nextIndex);
            }
        }

        ProcessingResult result = new ProcessingResult(accumulator, seenInstructions, looped);

        return result;
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
        
        ProcessingResult resultPt1 = processInstructions(instructions);
        int noSeenInstructionsPt1 = resultPt1.instructionSequence.size();
        System.err.println("Part One | Accumulator value: "+resultPt1.accumulator+" after " +
            noSeenInstructionsPt1 + " instructions processed, loop started with: " +
            resultPt1.instructionSequence.get(noSeenInstructionsPt1-1));


        // Part Two - bad instruction must already have been encountered,
        //  back track through sequence and try switching out jmp/nop instructions.

        for(int i = noSeenInstructionsPt1 - 2; i >= 0; i--) {

            Instruction badInstruction = resultPt1.instructionSequence.get(i);
            if(badInstruction.opComponent.equals("acc")) {
                continue; // we know bad instruction isn't an acc
            }

            ArrayList<Instruction> patchedInstructions = (ArrayList<Instruction>) instructions.clone();

            String newOpComponent = (badInstruction.opComponent.equals("jmp")) ? "nop" : "jmp";
            Instruction replacementInstruction = new Instruction(newOpComponent, badInstruction.numberComponent, badInstruction.index);
            patchedInstructions.set(replacementInstruction.index, replacementInstruction);

            ProcessingResult result = processInstructions(patchedInstructions);

            if (!result.looped) {
                System.err.println("Part Two | Found accumulator value: "+result.accumulator+" after "+
                (noSeenInstructionsPt1 - 2 - i) + " swap-outs, bad instruction was: "+badInstruction);
                break;
            } else {
                // System.err.println("Part Two | Still looping, " + (i + 1) + " instructions still to test.");
            }
        }
    }
}