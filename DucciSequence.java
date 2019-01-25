package app;

import java.util.ArrayList;
import java.util.Arrays;

public class DucciSequence {
    /*
     * A Ducci sequence is a sequence of n-tuples of integers, sometimes known as
     * "the Diffy game", because it is based on sequences. Given an n-tuple of
     * integers (a_1, a_2, ... a_n) the next n-tuple in the sequence is formed by
     * taking the absolute differences of neighboring integers. Ducci sequences are
     * named after Enrico Ducci (1864-1940), the Italian mathematician credited with
     * their discovery.
     * 
     * Some Ducci sequences descend to all zeroes or a repeating sequence. An
     * example is (1,2,1,2,1,0) -> (1,1,1,1,1,1) -> (0,0,0,0,0,0).
     * 
     * Additional information about the Ducci sequence can be found in this writeup
     * from Greg Brockman, a mathematics student.
     * 
     * It's kind of fun to play with the code once you get it working and to try and
     * find sequences that never collapse and repeat. One I found was (2, 4126087,
     * 4126085), it just goes on and on.
     * 
     * It's also kind of fun to plot these in 3 dimensions. Here is an example of
     * the sequence "(129,12,155,772,63,4)" turned into 2 sets of lines (x1, y1, z1,
     * x2, y2, z2).
     */

    public static int count;
    public static ArrayList<int[]> tempMatrix;

    public static void main(String... args) {

        int[][] sets = { { 1, 5, 7, 9, 9 }, { 1, 2, 1, 2, 1, 0 }, { 10, 12, 41, 62, 31, 50 }, { 0, 653, 1854, 4063 },
                { 10, 12, 41, 62, 31 } };

        for (int[] set : sets) {
            tempMatrix = new ArrayList<>();
            tempMatrix.add(set);
            System.out.print(Arrays.toString(tempMatrix.get(0)) + "\n");
            count = 1;
            calc(set);
            System.out.println(count + " cycles to reach full 0's or to find repeat");
        }

    }

    static int calc(int[] arr) {
        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i + 1]) {
                temp[i] = arr[i] - arr[i + 1];
            } else {
                temp[i] = arr[i + 1] - arr[i];
            }
        }
        if (arr[arr.length - 1] <= arr[0]) {
            temp[arr.length - 1] = arr[0] - arr[arr.length - 1];
        } else {
            temp[arr.length - 1] = arr[arr.length - 1] - arr[0];
        }

        count++;

        // System.out.print(Arrays.toString(temp) + "\n");
        for (int[] m : tempMatrix) {
            if (Arrays.equals(m, temp)) {
                return count;
            }
        }

        if (!isZero(temp)) {
            tempMatrix.add(temp);
            calc(temp);
        }

        return count;

    }

    static boolean isZero(int[] arr) {
        for (int n : arr) {
            if (n != 0) {
                return false;
            }

        }
        return true;
    }

}
