package main.com.aoc.five;

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
        this.seatID = (this.row * 8) + this.column;
        this.seatCode = seatCode;

        // Part One answer
        BoardingPass.maxSeatID = Math.max(BoardingPass.maxSeatID, this.seatID);
    }
    
}
