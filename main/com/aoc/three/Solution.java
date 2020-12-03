package main.com.aoc.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/three/input.txt";
        final int MAP_HEIGHT = 323;
        final int MAP_WIDTH = 31;
        final char TREE_SYMBOL = '#';

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        ArrayList<char[]> map = new ArrayList<>(MAP_HEIGHT);

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            char[] row = line.toCharArray();
            map.add(row);
        }
        reader.close();

        System.err.println("Got " + map.size() + " rows from " + INPUT_FILE);

        class DeltaPair {
            public int deltaX;
            public int deltaY;
            public int treeCounter = 0;

            public DeltaPair(int deltaX, int deltaY) {
                this.deltaX = deltaX;
                this.deltaY = deltaY;
            }
        }

        ArrayList<DeltaPair> pairs = new ArrayList<>();
        pairs.add(new DeltaPair(1, 1));
        pairs.add(new DeltaPair(3, 1));
        pairs.add(new DeltaPair(5, 1));
        pairs.add(new DeltaPair(7, 1));
        pairs.add(new DeltaPair(1, 2));

        int treeProduct = 1; // start with one so we don't get multiply by 0 issue.

        for (DeltaPair deltaPair : pairs) {
            // set start position
            int positionX = 0;
            int positionY = 0;

            while (positionY < map.size()) {
                if (map.get(positionY)[positionX] == TREE_SYMBOL) {
                    // check whether we've landed on a tree
                    deltaPair.treeCounter++;
                }

                // move to next position on map
                positionX += deltaPair.deltaX;
                positionY += deltaPair.deltaY;

                // 'wrap around' positionX if the co-ord has gone off the map
                if (positionX > (MAP_WIDTH - 1)) {
                    positionX = positionX - MAP_WIDTH;
                }
            }

            treeProduct = treeProduct * deltaPair.treeCounter;
            System.err.println("deltaX=" + deltaPair.deltaX + ", deltaY=" + deltaPair.deltaY + " | There were " + deltaPair.treeCounter + " trees encountered along the route.");
        }

        System.err.println("Final Product of each pair's treeCounter: "+ treeProduct);
    }
}