import java.util.*;

import static java.util.stream.Gatherers.scan;

public class Main {
    public static void main(String[] args) {
        City ankara = new City(5,100, 0.6F);
        for(int i = 1; i < 100; i++) {
            ankara.runTurn();
        }
    }
}