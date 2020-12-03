package main.com.aoc.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        final String INPUT_FILE = "main/com/aoc/three/input.txt";

        File inputFile = new File(INPUT_FILE);
        Scanner reader;
        reader = new Scanner(inputFile);

        ArrayList<char[]> map = new ArrayList<>();

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            char[] row = line.toCharArray();
            map.add(row);
        }
        reader.close();

        System.err.println("Got " + map.size() + " rows from " + INPUT_FILE);
        System.err.println("Rows are "+ map.get(0).length + " elements wide.");

        // set start position
        int positionX = 0;
        int positionY = 0;
        
        final int ROW_WIDTH =  map.get(0).length;
        final char TREE_SYMBOL = '#';
        int treeCounter = 0;

        while (positionY < map.size()) {
            if(map.get(positionY)[positionX] == TREE_SYMBOL){
                // check whether we've landed on a tree
                treeCounter++;
            }

            // move to next position on map
            positionX = positionX + 3;
            positionY++;

            // 'wrap around' positionX if the co-ord has gone off the map
            if(positionX > (ROW_WIDTH - 1)){
                positionX = positionX - ROW_WIDTH;
            }
        }

        System.err.println("There were " + treeCounter + " trees encountered along the route.");

    }
}