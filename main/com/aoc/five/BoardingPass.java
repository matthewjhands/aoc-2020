package main.com.aoc.five;

import java.util.HashSet;

public class BoardingPass {
    public int row;
    public int column;
    public int seatID;
    public String seatCode;
    private static final char ROW_LOWER_CHAR = 'F';
    private static final char ROW_UPPER_CHAR = 'B';
    private static final int ROW_MAX_UPPER_LIMIT = 127;
    private static final char COLUMN_LOWER_CHAR = 'L';
    private static final char COLUMN_UPPER_CHAR = 'R';
    private static final int COLUMN_MAX_UPPER_LIMIT = 7;
    public static int maxSeatID = 0;
    public static HashSet<Integer> assignedSeatIDs = new HashSet<>();
    public static HashSet<Integer> unassignedSeatIDs = populateSeatIDs();

    public static Integer findMySeat() throws Exception {
		for (Integer sid : BoardingPass.unassignedSeatIDs) {
            if (assignedSeatIDs.contains(sid - 1) && assignedSeatIDs.contains(sid + 1)) {
                return sid;
            }
        }
        throw new Exception("An unassigned seatID with neighbouring assigned SeatIDs couldn't be found.");
    }

    private static HashSet<Integer> populateSeatIDs() {
        HashSet<Integer> seatIDs = new HashSet<>(); 
        // Build static set of unique SeatIDs
        for (int row = 0; row <= ROW_MAX_UPPER_LIMIT; row++) {
            for (int col = 0; col <= COLUMN_MAX_UPPER_LIMIT; col++) {
                seatIDs.add(calculateSeatID(row, col));
            }
        }
        return seatIDs;
    }

    private static int calculateSeatID(int row, int column) {
        return (row * 8) + column;
    }

    private int binarySearch(char[] code, int currentIndex, int lowerLimit, int upperLimit, char lowerChar, char upperChar) {
        Character current = Character.valueOf(code[currentIndex]);
        if(currentIndex == code.length - 1) {
            return (current.equals(lowerChar)) ? lowerLimit : upperLimit;
        } else {
            int delta = (int) Math.pow(2, code.length - currentIndex - 1); // 64,32,16,8,4,2
            lowerLimit = (current.equals(upperChar)) ? lowerLimit + delta : lowerLimit;
            upperLimit = (current.equals(lowerChar)) ? upperLimit - delta : upperLimit;
            return binarySearch(code, currentIndex+1, lowerLimit, upperLimit, lowerChar, upperChar);
        }
    }

    public BoardingPass(String seatCode){
        String rowCode = seatCode.substring(0, 7);
        String columnCode = seatCode.substring(7);

        this.row = binarySearch(rowCode.toCharArray(), 0, 0, ROW_MAX_UPPER_LIMIT, ROW_LOWER_CHAR, ROW_UPPER_CHAR);
        this.column = binarySearch(columnCode.toCharArray(), 0, 0, COLUMN_MAX_UPPER_LIMIT, COLUMN_LOWER_CHAR, COLUMN_UPPER_CHAR);
        this.seatID = calculateSeatID(this.row, this.column);
        this.seatCode = seatCode;

        // Part One answer
        BoardingPass.maxSeatID = Math.max(BoardingPass.maxSeatID, this.seatID);

        // towards Part Two
        unassignedSeatIDs.remove(this.seatID);
        assignedSeatIDs.add(this.seatID);
    }
    
}
