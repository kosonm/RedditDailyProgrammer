package app;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class Grid {
    /*
     * Given a grid size N, find an NxN layout of X's and O's such that no
     * axis-aligned square (2x2 or larger) within the grid has the same symbol at
     * each of its four corners. That is, if four cells of the grid form a square,
     * they must not be either all X's or all O's.
     * 
     * Posted byu/Cosmologicon 2 32 months ago [2018-11-21] Challenge #368
     * [Intermediate] Single-symbol squares Description
     * 
     * Given a grid size N, find an NxN layout of X's and O's such that no
     * axis-aligned square (2x2 or larger) within the grid has the same symbol at
     * each of its four corners. That is, if four cells of the grid form a square,
     * they must not be either all X's or all O's.
     * 
     * Run time
     * 
     * To qualify as a solution to this challenge, you must actually run your
     * program through to completion for N = 6. It's not enough to write a program
     * that will eventually complete. Post your solution along with your code.
     */
    private final int[] bitArray;
    private Map<Point, Integer> invalidCorners;
    private static Set<Grid> previousGrids = new HashSet<>();

    private Grid(int[] bitArray) {
        this.bitArray = bitArray;
    }

    private Grid(int[] bitArray, Map<Point, Integer> invalidCorners) {
        this.bitArray = bitArray;
        this.invalidCorners = invalidCorners;
    }

    public Grid withPointFlipped(Point point) {
        int[] newBitArray = Arrays.copyOf(bitArray, bitArray.length);
        newBitArray[point.y] = (1 << point.x) ^ bitArray[point.y];
        Grid grid = new Grid(newBitArray, new HashMap<>(invalidCorners));
        grid.computeDifference(point);
        return grid;
    }

    public Map<Point, Integer> getInvalidCorners() {
        int size = bitArray.length;
        if (invalidCorners == null) {
            invalidCorners = new HashMap<>();
            for (int i = 1; i < size; i++) {
                for (int j = 0; i + j < size; j++) {
                    for (int k = 0; k + i < size; k++) {
                        if (cornersAreSame(k, j, k + i, j + i))
                            computeCorners(k, j, k + i, j + i);
                    }
                }
            }
        }
        return invalidCorners;
    }

    private boolean cornersAreSame(int x1, int y1, int x2, int y2) {
        int fullSide = ((1 << x1) + (1 << (x2)));
        int topSide = fullSide & bitArray[y1];
        int bottomSide = fullSide & bitArray[y2];
        return (topSide == fullSide && bottomSide == fullSide) || (topSide == 0 && bottomSide == 0);
    }

    private void flipPoint(int x, int y) {
        bitArray[y] = (1 << x) ^ bitArray[y];
    }

    private void computeCorners(int x1, int y1, int x2, int y2) {
        invalidCorners.compute(new Point(x1, y1), (point, corners) -> corners == null ? 1 : corners + 1);
        invalidCorners.compute(new Point(x2, y1), (point, corners) -> corners == null ? 1 : corners + 1);
        invalidCorners.compute(new Point(x1, y2), (point, corners) -> corners == null ? 1 : corners + 1);
        invalidCorners.compute(new Point(x2, y2), (point, corners) -> corners == null ? 1 : corners + 1);
    }

    private void removeCorners(int x1, int y1, int x2, int y2) {
        performForEachCorner(this::removePointIfNotPartOfSquare, x1, y1, x2, y2);
    }

    private void removePointIfNotPartOfSquare(int x, int y) {
        Point point = new Point(x, y);
        int corners = invalidCorners.getOrDefault(point, 0);
        if (corners > 0) {
            if (corners == 1)
                invalidCorners.remove(point);
            else
                invalidCorners.put(point, corners - 1);
        }
    }

    private void computeDifference(int x1, int y1, int x2, int y2) {
        if (cornersAreSame(x1, y1, x2, y2))
            computeCorners(x1, y1, x2, y2);
        else {
            flipPoint(x1, y1);
            if (cornersAreSame(x1, y1, x2, y2)) {
                removeCorners(x1, y1, x2, y2);
            }
            flipPoint(x1, y1);
        }
    }

    public void computeDifference(Point point) {
        int maxIndex = bitArray.length - 1;
        for (int i = 1; i <= Math.min(maxIndex - point.x, maxIndex - point.y); i++) {
            computeDifference(point.x, point.y, point.x + i, point.y + i);
        }
        for (int i = 1; i <= Math.min(maxIndex - point.x, point.y); i++) {
            computeDifference(point.x, point.y, point.x + i, point.y - i);
        }
        for (int i = 1; i <= Math.min(point.x, maxIndex - point.y); i++) {
            computeDifference(point.x, point.y, point.x - i, point.y + i);
        }
        for (int i = 1; i <= Math.min(point.x, point.y); i++) {
            computeDifference(point.x, point.y, point.x - i, point.y - i);
        }
    }

    public boolean isValid() {
        return getScore() == 0;
    }

    public int getScore() {
        return getInvalidCorners().size();
    }

    public static Grid generate(int N) {
        int[] ints = new Random().ints(N, 0, 1 << (N + 1) - 1).toArray();
        return new Grid(ints);
    }

    private void performForEachCorner(BiConsumer<Integer, Integer> pointConsumer, int x1, int y1, int x2, int y2) {
        pointConsumer.accept(x1, y1);
        pointConsumer.accept(x1, y2);
        pointConsumer.accept(x2, y1);
        pointConsumer.accept(x2, y2);
    }

    public static void main(String[] args) {
        int N = 6;
        long timeBefore = System.currentTimeMillis();
        PriorityQueue<Grid> rankedGrids = new PriorityQueue<>(Comparator.comparingInt(Grid::getScore));
        Grid currentGrid = Grid.generate(N);
        for (int i = 0; i < 5; i++) {
            Grid tempGrid = Grid.generate(N);
            if (tempGrid.getScore() < currentGrid.getScore())
                currentGrid = tempGrid;
        }
        previousGrids.add(currentGrid);
        System.out.println(currentGrid);
        while (!currentGrid.isValid()) {
            for (Point point : currentGrid.getInvalidCorners().keySet()) {
                Grid mutated = currentGrid.withPointFlipped(point);
                if (mutated.getScore() < currentGrid.getScore() + 4 && !previousGrids.contains(mutated)) {
                    previousGrids.add(mutated);
                    rankedGrids.add(mutated);
                }
            }
            currentGrid = rankedGrids.poll();
        }
        System.out.println("------------");
        System.out.println(currentGrid);
        System.out.println("Time taken = " + (System.currentTimeMillis() - timeBefore));

    }

}