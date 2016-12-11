package pkg;

import pkg.resources.Map;

import java.util.Arrays;

/**
 * Created by Rechtig on 10.12.2016.
 */
public class Main {
    public static void main(String[] args) {

        Map mapa = new Map(10, 10);

        Arrays.stream(mapa.getMap()).forEach(row -> {
            Arrays.stream(row).forEach(item -> System.out.print(item + " "));
            System.out.println();
        });
    }
}
