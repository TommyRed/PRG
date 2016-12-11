package pkg.resources;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Rechtig on 10.12.2016.
 */
public class Map {

    //    private final int size;
//    private final int mines;
    private final int[][] map;

    public Map(int size, int mines) {
//        this.size = size;
//        this.mines = mines;
        this.map = generate(size, mines);
    }

    private int[][] generate(int size, int mines) {
        return generateNumbers(generateMines(createMap(size), mines));
    }

    public int[][] createMap(int size) {
        return IntStream.range(0, size)
                        .mapToObj(x -> IntStream.range(0, size)
                                                .map(y -> 0)
                                                .toArray())
                        .toArray(int[][]::new);
    }

    // Mines
    private int[][] generateMines(int[][] map, final int minesToPlace) {
        return (minesToPlace == 0) ? map : generateMine(map, minesToPlace);
    }

    private int[][] generateMine(int[][] map, final int minesToPlace) {
        final int x = getIndex(map.length);
        final int y = getIndex(map.length);

        return (map[x][y] == 9)
                ? generateMine(map, minesToPlace)
                : generateMines(placeMine(map, x, y), minesToPlace - 1);
    }

    private int[][] placeMine(int[][] map, int x, int y) {
        map[x][y] = 9;
        return map;
    }


    // Numbers
    private int[][] generateNumbers(int[][] map) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = (map[i][j] == 9) ? 9 : generateNumber(i, j, map);
            }
        }

        return map;
    }

    private int generateNumber(int x, int y, int[][] map) {

        int i = 0;

        for (int j = (x == 0 ? 0 : x - 1); j <= ((x + 1) < map.length ? x + 1 : x); j++) {
            for (int k = (y == 0 ? 0 : y - 1); k <= ((y + 1) < map.length ? y + 1 : y); k++) {
                i += map[j][k] == 9 ? 1 : 0;
            }
        }

        return i;
    }


//    public int getSize() {
//        return size;
//    }

//    public int getMines() {
//        return mines;
//    }

    private int getIndex(int bound) {
        return new Random().nextInt(bound);
    }

    public int[][] getMap() {
        return map;
    }
}
