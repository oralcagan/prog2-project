import java.util.*;

public class Main {
    public static void main(String[] args) {
        City napoli = new City(5,100,0.6F);
        for (int i = 1; i <100; i ++){
            napoli.runTurn();
        }
    }
}