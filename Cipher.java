package app;

import java.util.ArrayList;

public class Cipher {
    /*
     * You've been taking some classes at a local university. Unfortunately, your
     * theory-of-under-water-basket-weaving professor is really boring. He's also
     * very nosy. In order to pass the time during class, you like sharing notes
     * with your best friend sitting across the aisle. Just in case your professor
     * intercepts any of your notes, you've decided to encrypt them.
     * 
     * To make things easier for yourself, you're going to write a program which
     * will encrypt the notes for you. You've decided a transposition cipher is
     * probably the best suited method for your purposes.
     * 
     * A transposition cipher is
     * "a method of encryption by which the positions held by units of plaintext (which are commonly characters or groups of characters) are shifted according to a regular system, so that the ciphertext constitutes a permutation of the plaintext"
     * (En.wikipedia.org, 2018).
     * 
     * Specifically, we will be implementing a type of route cipher today. In a
     * route cipher the text you want to encrypt is written out in a grid, and then
     * arranged in a given pattern. The pattern can be as simple or complex as you'd
     * like to make it. Task
     * 
     * For our purposes today, your program should be able to accommodate two input
     * paramters: Grid Dimensions, and Clockwise or Counterclockwise Rotation. To
     * make things easier, your program need only support the Spiral route from
     * outside to inside.
     */
    private final char[][] charGrid;
    private final String rotation;
    private final int nRow, nCol;

    public Cipher(String s) {
        int quoteBegin = 1;
        int quoteEnd = s.lastIndexOf("\"");
        int braceBegin = s.lastIndexOf("(");
        int braceEnd = s.lastIndexOf(")");
        int i, j;
        char[] arr = s.substring(quoteBegin, quoteEnd).toCharArray();
        String grid = s.substring(braceBegin + 1, braceEnd);
        String[] gridSize = grid.split(", ");
        this.nRow = Integer.parseInt(gridSize[1]);
        this.nCol = Integer.parseInt(gridSize[0]);
        this.rotation = s.substring(braceEnd + 2);
        ArrayList<Character> charList = new ArrayList<>();
        for (i = 0; i < arr.length; i++) {
            j = (int) arr[i];
            if ((j > 64 && j < 91)) {
                charList.add(arr[i]);
            } else if ((j > 96 && j < 123)) {
                charList.add((char) (j - 32));
            }
        }
        while (charList.size() < (nCol * nRow))
            charList.add('X');
        this.charGrid = new char[nRow][nCol];
        i = 0;
        j = 0;
        for (char c : charList) {
            this.charGrid[i][j++] = c;
            if (j == nCol) {
                j = 0;
                i++;
            }
        }
        if (rotation.equals("clockwise")) {
            this.rotateClockwise(this.charGrid, 0, this.nCol - 1, 0, this.nRow - 1);
        } else {
            this.rotateCounter(this.charGrid, 0, this.nCol - 1, 0, this.nRow - 1);
        }
        System.out.print("\n");
    }

    private void rotateCounter(char[][] matrix, int rowStart, int colStart, int colLength, int rowLength) {
        for (int i = colStart; i >= colLength; i--) {
            System.out.print(matrix[rowStart][i]);
        }
        for (int i = rowStart + 1; i <= rowLength; i++) {
            System.out.print(matrix[i][colLength]);
        }
        if (colLength + 1 < colStart) {
            for (int i = colLength + 1; i <= colStart; i++) {
                System.out.print(matrix[rowLength][i]);
            }
            for (int i = rowLength - 1; i > rowStart; i--) {
                System.out.print(matrix[i][colStart]);
            }
        }
        if (rowStart + 1 <= rowLength - 1 && colLength + 1 <= colStart - 1) {
            rotateCounter(matrix, rowStart + 1, colStart - 1, colLength + 1, rowLength - 1);
        }
    }

    private void rotateClockwise(char[][] matrix, int rowStart, int colStart, int colEnd, int rowLength) {
        for (int i = rowStart; i <= rowLength; i++) {
            System.out.print(matrix[i][colStart]);
        }
        for (int i = colStart - 1; i >= colEnd; i--) {
            System.out.print(matrix[rowLength][i]);
        }
        if (rowStart + 1 <= rowLength) {
            for (int i = rowLength - 1; i >= rowStart; i--) {
                System.out.print(matrix[i][colEnd]);
            }
            for (int i = colEnd + 1; i < colStart; i++) {
                System.out.print(matrix[rowStart][i]);
            }
        }
        if (rowStart + 1 <= rowLength - 1 && colEnd + 1 < colStart - 1) {
            rotateClockwise(matrix, rowStart + 1, colStart - 1, colEnd + 1, rowLength - 1);
        }
    }

    public static void main(String[] args) {
        new Cipher("\"WE ARE DISCOVERED. FLEE AT ONCE\" (9, 3) clockwise");
        new Cipher("\"why is this professor so boring omg\" (6, 5) counter-clockwise");
        new Cipher("\"Solving challenges on r/dailyprogrammer is so much fun!!\" (8, 6) counter-clockwise");
        new Cipher("\"For lunch let's have peanut-butter and bologna sandwiches\" (4, 12) clockwise");
        new Cipher("\"I've even witnessed a grown man satisfy a camel\" (9, 5) clockwise");
        new Cipher("\"Why does it say paper jam when there is no paper jam?\" (3, 14) counter-clockwise");
    }
}