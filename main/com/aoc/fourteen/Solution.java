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

    public static Integer convertDecimalToBinary(Integer decimalNumber) {
        // https://www.baeldung.com/java-binary-numbers#1-decimal-to-a-binary-number

        if (decimalNumber == 0) {
            return decimalNumber;
        }
    
        StringBuilder binaryNumber = new StringBuilder();
        Integer quotient = decimalNumber;
    
        while (quotient > 0) {
            int remainder = quotient % 2;
            binaryNumber.append(remainder);
            quotient /= 2;
        }
    
        binaryNumber = binaryNumber.reverse();
        return Integer.valueOf(binaryNumber.toString());
    }

    public static Integer convertBinaryToDecimal(Integer binaryNumber) {
        // https://www.baeldung.com/java-binary-numbers#2-binary-to-a-decimal-number

        Integer decimalNumber = 0;
        Integer base = 1;
    
        while (binaryNumber > 0) {
            int lastDigit = binaryNumber % 10;
            binaryNumber = binaryNumber / 10;
            decimalNumber += lastDigit * base;
            base = base * 2;
        }
        return decimalNumber;
    }

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

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/fourteen/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(input.split("\n")));

        System.err.println("Got " + lines.size() + " rows from " + INPUT_FILE);

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
        System.err.printf("Part One | Answer is: %d", sumTotal);
    }
}