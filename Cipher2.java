package app;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cipher2 {
    /*
     * Description
     * 
     * ElsieFour (LC4) is a low-tech authenticated encryption algorithm that can be
     * computed by hand. Rather than operating on octets, the cipher operates on
     * this 36-character alphabet:
     * 
     * #_23456789abcdefghijklmnopqrstuvwxyz
     * 
     * Each of these characters is assigned an integer 0–35. The cipher uses a 6x6
     * tile substitution-box (s-box) where each tile is one of these characters. A
     * key is any random permutation of the alphabet arranged in this 6x6 s-box.
     * Additionally a marker is initially placed on the tile in the upper-left
     * corner. The s-box is permuted and the marked moves during encryption and
     * decryption.
     * 
     * See the illustrations from the paper (album).
     * 
     * Each tile has a positive "vector" derived from its value: (N % 6, N / 6),
     * referring to horizontal and vertical movement respectively. All vector
     * movement wraps around, modulo-style.
     * 
     * To encrypt a single character, locate its tile in the s-box, then starting
     * from that tile, move along the vector of the tile under the marker. This will
     * be the ciphertext character (the output).
     * 
     * Next, the s-box is permuted. Right-rotate the row containing the plaintext
     * character. Then down-rotate the column containing the ciphertext character.
     * If the tile on which the marker is sitting gets rotated, marker goes with it.
     * 
     * Finally, move the marker according to the vector on the ciphertext tile.
     * 
     * Repeat this process for each character in the message.
     * 
     * Decryption is the same, but it (obviously) starts from the ciphertext
     * character, and the plaintext is computed by moving along the negated vector
     * (left and up) of the tile under the marker. Rotation and marker movement
     * remains the same (right-rotate on plaintext tile, down-rotate on ciphertext
     * tile).
     * 
     * If that doesn't make sense, have a look at the paper itself. It has
     * pseudo-code and a detailed step-by-step example. Input Description
     * 
     * Your program will be fed two lines. The first line is the encryption key. The
     * second line is a message to be decrypted. Output Description
     * 
     * Print the decrypted message.
     */

    private final String ALPHA = "#_23456789abcdefghijklmnopqrstuvwxyz";
    private final String key;
    private final String input;

    class Tile {
        char value;
        int x;
        int y;
        final int vx;
        final int vy;

        Tile(char value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
            vx = ALPHA.indexOf(value) % 6;
            vy = ALPHA.indexOf(value) / 6;
        }

        void moveRight() {
            y = (y + 1) % 6;
        }

        void moveDown() {
            x = (x + 1) % 6;
        }
    }

    private Map<Character, Tile> tiles = new HashMap<>();

    private char[][] board = new char[6][6];
    private Tile marker;
    private boolean encrypt = false;

    public Cipher2(String key, String input) {
        this.key = key;
        if (input.startsWith("%")) {
            this.input = input.substring(1);
            encrypt = true;
        } else {
            this.input = input;
        }
    }

    public String process() {
        loadKey(key);
        marker = tiles.get(board[0][0]);
        return encrypt ? encrypt() : decrypt();
    }

    private char encryptChar(char start) {
        Tile plain = tiles.get(start);
        Tile cypher = getMove(plain.x, plain.y, marker.vx, marker.vy);
        rotateRow(plain.x);
        rotateColumn(cypher.y);
        marker = getMove(marker.x, marker.y, cypher.vx, cypher.vy);
        return cypher.value;
    }

    private char decryptChar(char start) {
        Tile cypher = tiles.get(start);
        Tile plain = getMove(cypher.x, cypher.y, -marker.vx, -marker.vy);
        rotateRow(plain.x);
        rotateColumn(cypher.y);
        marker = getMove(marker.x, marker.y, cypher.vx, cypher.vy);
        return plain.value;
    }

    private String encrypt() {
        return input.chars().map(x -> encryptChar((char) x)).mapToObj(x -> Character.toString((char) x))
                .collect(Collectors.joining());

    }

    private String decrypt() {
        return input.chars().map(x -> decryptChar((char) x)).mapToObj(x -> Character.toString((char) x))
                .collect(Collectors.joining());
    }

    private void rotateRow(int row) {
        char temp = board[row][5];
        for (int i = 5; i > 0; i--) {
            board[row][i] = board[row][i - 1];
            tiles.get(board[row][i]).moveRight();
        }
        board[row][0] = temp;
        tiles.get(temp).moveRight();
    }

    private void rotateColumn(int col) {
        char temp = board[5][col];
        for (int i = 5; i > 0; i--) {
            board[i][col] = board[i - 1][col];
            tiles.get(board[i][col]).moveDown();
        }
        board[0][col] = temp;
        tiles.get(temp).moveDown();
    }

    private void loadKey(String key) {
        for (int i = 0; i < key.length(); i++) {
            board[i / 6][i % 6] = key.charAt(i);
            tiles.put(key.charAt(i), new Tile(key.charAt(i), i / 6, i % 6));
        }
    }

    private Tile getMove(int row, int col, int dx, int dy) {
        return tiles.get(board[Math.floorMod(row + dy, 6)][Math.floorMod(col + dx, 6)]);
    }

    public static void main(String[] args) {
        Cipher2 lc4 = new Cipher2(args[0], args[1]);
        System.out.println(lc4.process());
    }
}