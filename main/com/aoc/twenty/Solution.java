package main.com.aoc.twenty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {

    public static void main(String[] args) throws IOException {
        final String INPUT_FILE = "main/com/aoc/twenty/input.txt";
        final Pattern ID_PATTERN = Pattern.compile("Tile (\\d+)");

        String input = Files.readString(Path.of(INPUT_FILE));
        ArrayList<String> lineGroups = new ArrayList<>(Arrays.asList(input.split("\n\n")));

        System.err.println("Got " + lineGroups.size() + " tiles from " + INPUT_FILE);

        ArrayList<Tile> tiles = new ArrayList<>(lineGroups.size());

        for (String group : lineGroups) {
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(group.split("\n")));
            Matcher m = ID_PATTERN.matcher(lines.get(0));
            m.find();
            Integer id = Integer.parseInt(m.group(1));
            lines.remove(0); // remove header
            tiles.add(new Tile(lines, id));

        }

        System.err.printf("%d Tile Objects created. First is: %s.\n", tiles.size(), tiles.get(0));

        final int ALL_MATCHES = (tiles.size() * Tile.DIRECTIONS.size())
                - (((int) Math.sqrt(tiles.size())) - Tile.DIRECTIONS.size());
        int matches = 0;
        boolean change = true;

        while (change == true && matches < ALL_MATCHES) {
            change = false;
            for (Tile tile : tiles) {
                for (Tile tile2 : tiles) {
                    if (tile.matchNeighbour(tile2)) {
                        matches++;
                        change = true;
                    }
                }
            }
        }

        System.err.println("Jigsaw complete!");
    }
}