package pkg;

import pkg.resources.Map;

import java.util.*;

/**
 * Created by Rechtig on 10.12.2016.
 */
public class Game {

    public void startGame() {
        Map map = new Map(10, 10, false);
        Map shadowMap = new Map(10, 10, true);

        System.out.println(play(map, shadowMap, true) ? "You won" : "You lost");
    }


    public boolean play(Map map, Map shadowMap, boolean flag) {
        if (!flag) {

            printMap(revealMines(map, shadowMap));

            return false;
        } else {

            if (checkForEnd(map)) {
                printMap(map);
                return true;
            }

//            System.out.println("#   MAP");
            printMap(map);

//            System.out.println("#   SHADOW MAP");
//            printMap(shadowMap);

            printOptions();

            int selectedOption = listenForNumber(1, 3);

            if (selectedOption == 3)
                return play(map, shadowMap, false);

            else if (selectedOption == 2)
                return play(placeFlag(map), shadowMap, true);

            else {
                int[] input = listenForInput(1, map.getMap().length);

                if (shadowMap.getMap()[input[0]][input[1]] == 9) return play(map, shadowMap, false);
                else return play(evaluate(map, shadowMap, input), shadowMap, true);
            }
        }
    }

    private Map evaluate(Map map, Map shadowMap, int[] input) {
        int x = input[0], y = input[1];

        if (shadowMap.getMap()[x][y] != 0) {
            return revealField(map, shadowMap, x, y);
        } else {
            map.getMap()[x][y] = shadowMap.getMap()[x][y];

            for (int j = (x == 0 ? 0 : x - 1); j <= ((x + 1) < map.getMap().length ? x + 1 : x); j++) {
                for (int k = (y == 0 ? 0 : y - 1); k <= ((y + 1) < map.getMap().length ? y + 1 : y); k++) {
                    if (!(j == x && k == y) && map.getMap()[j][k] == -2) map = evaluate(map, shadowMap, new int[]{j, k});
                }
            }
            return map;
        }
    }

    private Map placeFlag(Map map) {

        int x = listenForNumber(1, map.getMap().length) - 1;
        int y = listenForNumber(1, map.getMap().length) - 1;

        System.out.println("#   placing flag on x: " + x + " y: " + y);

        map.getMap()[x][y] = map.getMap()[x][y] == -1 ? 0 : -1;

        return map;

    }

    private Map revealField(Map map, Map shadowMap, int x, int y) {
        map.getMap()[x][y] = shadowMap.getMap()[x][y];
        return map;
    }

    private Map revealMines(Map map, Map shadowMap) {
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap().length; j++) {
                if (shadowMap.getMap()[i][j] == 9) map.getMap()[i][j] = 9;
            }
        }
        return map;
    }

    private void printMap(Map map) {
        Arrays.stream(map.getMap())
              .forEach(row -> {
                  Arrays.stream(row)
                        .forEach(item -> {
                            if (item == -1) System.out.print("F ");
                            else if (item == 9) System.out.print("M ");
                            else if (item == -2) System.out.print("X ");
                            else System.out.print(item + " ");
                        });
                  System.out.println();
              });
    }

    private boolean checkForEnd(Map map){
        int i = 0;
        for (int x = 0; x < map.getMap().length; x++) {
            for (int y = 0; y < map.getMap().length; y++) {
                if (map.getMap()[x][y] == -2 || map.getMap()[x][y] == -1) i++;
            }
        }
        return i == map.getMines();
    }

    private int[] listenForInput(int minBound, int maxBound) {
        return new int[]{listenForNumber(minBound, maxBound) - 1, listenForNumber(minBound, maxBound) - 1};
    }

    private int listenForNumber(int minBound, int maxBound) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number : ");

        int input;

        try {
            input = sc.nextInt();
        } catch (InputMismatchException err) {
            sc.next();

            return listenForNumber(minBound, maxBound);
        }

        if (input >= minBound && input <= maxBound) return input;
        else {
            System.out.println("Invalid number");
            return listenForNumber(minBound, maxBound);
        }
    }

    private void printOptions() {
        System.out.println("\n1) Reveal field");
        System.out.println("2) Flag field");
        System.out.println("3) Resign");
    }
}
