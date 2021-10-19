package main.com.aoc.twenty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Tile {
    private static final int TILE_WIDTH = 10;
    private static final int TILE_HEIGHT = TILE_WIDTH; // tiles are square
    public static final ArrayList<Character> DIRECTIONS = new ArrayList<>(Arrays.asList('N', 'E', 'S', 'W'));
    private static final ArrayList<Integer> ROTATIONS = new ArrayList<>(Arrays.asList(0, 90, 180, 270));
    private static HashMap<Character, Character> RECIPROCAL_DIRECTION = new HashMap<>();
    RECIPROCAL_DIRECTION.put('N', 'S');
    private ArrayList<ArrayList<Character>> grid;
    private final Integer randomID;
    private HashMap<Character, Tile> neighbours = new HashMap<>(DIRECTIONS.size());
    private int rotation = 0;
    private boolean flippedVertical = false;
    private boolean flippedHorizontal = false;
    private boolean lockOrientation = false;

    public Tile(ArrayList<String> grid, Integer randomID) {
        this.randomID = randomID;

        ArrayList<ArrayList<Character>> rows = new ArrayList<>(TILE_HEIGHT);

        for (String row : grid) {
            ArrayList<Character> column = new ArrayList<>(
                    row.chars().mapToObj((c) -> Character.valueOf((char) c)).collect(Collectors.toList()));
            rows.add(column);
        }

        this.grid = rows;
    }

    public boolean matchNeighbour(Tile neighbour) {

        if (neighbour.lockOrientation) {
            Character dir = this.matchEdge(neighbour);

            if (dir != null) {
                this.lockOrientation = true;
                neighbour.lockOrientation = true;
                this.neighbours.put(dir, neighbour);
                return true;
            }
        }

        for (int i = 0; i < 2; i++) {
            neighbour.flipVertical((i == 1));

            for (int j = 0; j < 2; j++) {
                neighbour.flipHorizontal((j == 1));

                for (Integer rotation : ROTATIONS) {
                    neighbour.rotate(rotation);
                    Character dir = this.matchEdge(neighbour);

                    if (dir != null) {
                        this.lockOrientation = true;
                        neighbour.lockOrientation = true;
                        this.neighbours.put(dir, neighbour);
                        return true;
                    }

                }

            }
        }

        return false;
    }

    private static ArrayList<Character> colToArrayList(int col, ArrayList<ArrayList<Character>> grid) {
        ArrayList<Character> newRow = new ArrayList<>(TILE_WIDTH);
        for (int y = (TILE_HEIGHT -1); y > -1; y--) {
            newRow.add(grid.get(col).get(y));
        }

        return newRow;
    }

    private Character matchEdge(Tile neighbour) {
        boolean match = false;
        for(Character direction: DIRECTIONS) {
            if(this.neighbours.containsKey(direction)) {
                continue;
            } else {
                switch (direction.charValue()) {
                    case 'N':
                        match = matchLine(this.grid.get(0),neighbour.grid.get(TILE_HEIGHT-1));
                        break;
                    case 'S':
                        match = matchLine(this.grid.get(TILE_HEIGHT-1),neighbour.grid.get(0));
                        break;
                    case 'E':
                        match = matchLine(colToArrayList(TILE_WIDTH-1, this.grid), colToArrayList(0, neighbour.grid));
                        break;
                    case 'W':
                        match = matchLine(colToArrayList(0, this.grid), colToArrayList(TILE_WIDTH-1, neighbour.grid));
                        break;

                }

                if (match) {
                    return direction.charValue();
                }
            }
        }
        return null; // no matches or all sides taken
    }

    private static boolean matchLine(ArrayList<Character> a, ArrayList<Character> b) {
        return a.equals(b);
    }

    private void flipHorizontal(boolean flip) {
        if (this.flippedHorizontal == flip) {
            return;
        } else {
            Collections.reverse(this.grid);
            return;
        }
    }

    private void flipVertical(boolean flip) {
        if (this.flippedVertical == flip) {
            return;
        } else {
            for (ArrayList<Character> row : this.grid) {
                Collections.reverse(row);
            }
            return;
        }
    }

    public void rotate(Integer rotation) {
        ArrayList<ArrayList<Character>> newRows = new ArrayList<>(TILE_HEIGHT);

        while (this.rotation != rotation.intValue()) {
            for (int x = 0; x < TILE_WIDTH; x++) {
                newRows.add(colToArrayList(x, this.grid));
            }

            // System.err.println("Old Grid:\n" + this.grid);
            // System.err.println("Rotated Grid:\n" + newRows);
            this.grid = newRows;

            this.rotation = this.rotation + 90;
            if(this.rotation == 360) {
                this.rotation = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Tile [flippedHorizontal=" + flippedHorizontal + ", flippedVertical=" + flippedVertical + ", neighbours="
                + neighbours + ", randomID=" + randomID + ", rotation=" + rotation + "]";
    }

}
