package main.com.aoc.fourteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {

    public final static int MASK_WIDTH = 36;

    public static String padLeftZero(String inputString, int stringLength){
        // https://www.baeldung.com/java-pad-string#2-using-substring
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            sb.append('0');
        }

        return sb.substring(inputString.length()) + inputString;
    }

    public static String applyBitmask(String inputBinary, String binaryMask){
        if (inputBinary.length() != MASK_WIDTH && binaryMask.length() != MASK_WIDTH){
            throw new InvalidParameterException("Binary String and Mask lengths not equal!");
        }

        char[] result = new char[MASK_WIDTH];
        for (int i = 0; i < MASK_WIDTH; i++) {
            result[i] = (binaryMask.charAt(i) == 'X') ? inputBinary.charAt(i) : binaryMask.charAt(i);
        }
        return String.valueOf(result);
    }

    private static String[] applyBitmaskPartTwo(String[] binaryStrings, String mask){
        if (!mask.contains("X") && !mask.contains("1")) {
            // no more floating points or 1's - nothing else to do.
            return binaryStrings;
        } 

        if (mask.contains("1")) {
            // process all the 1's ASAP - then update the mask to avoid re-processing
            for (int i = 0; i < binaryStrings.length; i++) {
                // for every binary string - should just be 1 
                char[] result = new char[MASK_WIDTH];
                for (int j = 0; j < MASK_WIDTH; j++) {
                    // for every char in string, if char at same index in mask is 1, replace with 1
                    result[j] = (mask.charAt(j) == '1') ? '1' : binaryStrings[i].charAt(j);
                }
                binaryStrings[i] = String.valueOf(result);
            }
            // disable any further processing of 1's
            mask = mask.replace("1", "0");
        }

        // process X's 

        // identify the first X from L to R in mask, record index
        int xIndex = mask.indexOf("X");

        ArrayList<String> newBinaryStrings = new ArrayList<>();
        for (int i = 0; i < binaryStrings.length; i++) {
            // for every binary string
            
            // copy the string, replacing the char at xIndex with 0, then save it
            StringBuilder xAsZeroBinary = new StringBuilder(binaryStrings[i]);
            xAsZeroBinary.replace(xIndex, xIndex+1, "0");
            newBinaryStrings.add(xAsZeroBinary.toString());

            // the same as above but replacing char with 1
            StringBuilder xAsOneBinary = new StringBuilder(binaryStrings[i]);
            xAsOneBinary.replace(xIndex, xIndex+1, "1");
            newBinaryStrings.add(xAsOneBinary.toString());
        }
        // replace this X so that we don't reprocess it
        StringBuilder maskSB = new StringBuilder(mask);
        maskSB.replace(xIndex, xIndex+1, "0");
        mask = maskSB.toString();

        // then call this method again with updated mask and binaryStrings
        String[] arr = new String[newBinaryStrings.size()];
        arr = newBinaryStrings.toArray(arr);
        return applyBitmaskPartTwo(arr, mask);

    }

    private static void partOne(ArrayList<String> lines) {
        Long sumTotal = 0L;
        String currentMask = "";
        HashMap<Integer,Long> mem = new HashMap<>();
        Pattern addressRegex = Pattern.compile("[0-9]+");
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts[0].startsWith("mask")){
                currentMask = parts[2];
            } else {
                Matcher match = addressRegex.matcher(parts[0]);
                match.find();
                String binaryRepresentation = Long.toBinaryString(Long.parseLong(parts[2]));
                binaryRepresentation = padLeftZero(binaryRepresentation, MASK_WIDTH);
                binaryRepresentation = applyBitmask(binaryRepresentation, currentMask);
                mem.put(Integer.parseInt(match.group()), Long.parseLong(binaryRepresentation, 2));
            }
        }
        
        for (Integer key : mem.keySet()) {
            sumTotal += mem.get(key);
        }
        System.err.printf("Part One | Answer is: %d\n", sumTotal);
    }

    private static void partTwo(ArrayList<String> lines) {
        Long sumTotal = 0L;
        String currentMask = "";
        HashMap<Long,Long> mem = new HashMap<>();
        Pattern addressRegex = Pattern.compile("[0-9]+");
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts[0].startsWith("mask")){
                currentMask = parts[2];
            } else {
                // get the address
                Matcher match = addressRegex.matcher(parts[0]);
                match.find();

                // convert to binary, pad to MASK_WIDTH
                String binaryRepresentation = Long.toBinaryString(Long.parseLong(match.group()));
                binaryRepresentation = padLeftZero(binaryRepresentation, MASK_WIDTH);

                // stick binaryAddress in a String[] and work out all possible address permutations
                String[] initialBinaryStrings = new String[1];
                initialBinaryStrings[0] = binaryRepresentation;
                String[] binaryStrings = applyBitmaskPartTwo(initialBinaryStrings, currentMask);

                // for each address permutation save the value (parts[2]) to memory
                for(String binaryAddress : binaryStrings) {
                    Long longAddress = Long.parseLong(binaryAddress, 2);
                    mem.put(longAddress, Long.parseLong(parts[2]));
                }
            }
        }
        
        // finally, calculate the sum of the value of every address in memory
        for (Long key : mem.keySet()) {
            sumTotal += mem.get(key);
        }
        System.err.printf("Part Two | Answer is: %d", sumTotal);
    }

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/fourteen/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);

        partOne(lines);
        partTwo(lines);
    }

}