package main.com.aoc.four;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    private static final String INPUT_FILE = "main/com/aoc/four/input.txt";
    private static final Pattern heightFormat = Pattern.compile("^(\\d+)(cm|in)$");
    private static final Pattern validHairFormat = Pattern.compile("^#\\w{6}$");
    private static final Pattern validPassportID = Pattern.compile("^\\d{9}$");
    private static final HashSet<String> validEyeColours = new HashSet<>(
            Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));

    public static boolean isValidHeight(String height) {
        if (!heightFormat.matcher(height).matches()) {
            return false;
        } else {
            Matcher m = heightFormat.matcher(height);
            m.find();
            switch ((String) m.group(2)) {
                case "cm":
                    return ((Integer.parseInt(m.group(1)) >= 150) && ((Integer.parseInt(m.group(1)) <= 193)));
                case "in":
                    return ((Integer.parseInt(m.group(1)) >= 59) && ((Integer.parseInt(m.group(1)) <= 76)));
                default:
                    // should be unreachable
                    throw new IllegalArgumentException("Bad Height recieved " + height);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        String allPassportsInput = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> passportAttributes = new ArrayList<>(Arrays.asList(allPassportsInput.split("\n\n")));
        ArrayList<HashMap<String, String>> passports = new ArrayList<>();

        int validPassportsPt1 = 0;
        int validPassportsPt2 = 0;
        for (String string : passportAttributes) {
            // parse each attribute group into a HashMap
            string = string.replaceAll("\n", " ");
            String[] attributes = string.split(" ");

            HashMap<String, String> passport = new HashMap<>();
            for (String attribute : attributes) {
                String[] kvPair = attribute.split(":");
                passport.put(kvPair[0], kvPair[1]);
            }

            passports.add(passport);

            // slight hack to check passport validity pt1 - should really validate each
            // field
            if (passport.size() == 8) {
                validPassportsPt1++;
            } else if (passport.size() == 7 && !passport.containsKey("cid")) {
                validPassportsPt1++;
            } else {
                // no need to check for part 2 validation
                continue;
            }

            // Part Two Validation rules
            if ((Integer.parseInt(passport.get("byr")) >= 1920) && (Integer.parseInt(passport.get("byr")) <= 2002)
                    && (Integer.parseInt(passport.get("iyr")) >= 2010)
                    && (Integer.parseInt(passport.get("iyr")) <= 2020)
                    && (Integer.parseInt(passport.get("eyr")) >= 2020)
                    && (Integer.parseInt(passport.get("eyr")) <= 2030) && isValidHeight(passport.get("hgt"))
                    && validEyeColours.contains(passport.get("ecl"))
                    && validHairFormat.matcher(passport.get("hcl")).matches()
                    && validPassportID.matcher(passport.get("pid")).matches()) {
                validPassportsPt2++;
            }

        }

        System.err.println("Got " + passports.size() + " passports from " + INPUT_FILE);
        System.err.println("Part One | There were " + validPassportsPt1 + " valid passports.");
        System.err.println("Part Two | There were " + validPassportsPt2 + " valid passports.");
    }
}