package main.com.aoc.six;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/six/input.txt";

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> groups = new ArrayList<>(Arrays.asList(input.split("\n\n")));

        System.err.println("Got " + groups.size() + " groups from " + INPUT_FILE);

        int sumOfdistinctQuestions = 0;
        Pattern questionRegex = Pattern.compile("[a-z]");
        for (String group : groups){
            Matcher matches = questionRegex.matcher(group);
            List<String> distinctQuestions = matches.results() // stream MatchResults (the matches)
                .map(MatchResult::group) // get the actual character match
                .sorted() // sort them, required to get distinct
                .distinct() // get rid of dupes
                .collect(Collectors.toList()); // stick 'em in a list

            sumOfdistinctQuestions += distinctQuestions.size();
        }

        System.err.printf("Part One | There were %d questions answered by any one member of a given group.", sumOfdistinctQuestions);

        int allGroupMembersQuestionCount = 0;
        for (String group : groups){
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(group.split("\n")));

            HashMap<String, Integer> questionCount = new HashMap<>();

            lines.forEach(line -> {
                Matcher matches = questionRegex.matcher(line);
                matches.results() // stream MatchResults (the matches)
                .map(MatchResult::group) // get the actual character match
                .sorted() // sort them, required to get distinct
                .distinct() // get rid of dupes
                .forEach(question -> {
                    questionCount.compute(question, (k, v) -> (v == null) ? 1 : v+1);
                });
            });

            for(String key : questionCount.keySet()) {
                if (questionCount.get(key) == lines.size()) {
                    allGroupMembersQuestionCount++;
                }
            }
        }

        System.err.printf("\nPart Two | There were %d questions answered by every member of a given group.", allGroupMembersQuestionCount);
    }
}