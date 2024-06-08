import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] cityNames = new String[]{"Roma", "Milan", "Napoli"};
        int[] numberOfPeopleInCities = new int[]{100,50,40};
        Simulation simulation = new Simulation(cityNames,5,0.7F,numberOfPeopleInCities);
        int n = 200;
        while(n > 0) {
            simulation.runTurn();
            if(n % 10 == 0) {
                // Lonely people movement across cities
                // From/To Roma Milan Napoli
                // Roma      0    5     3
                // Milan     2    0     7
                // Napoli    3    2     0
                // simulation.getLonelyPeopleMatrix()
                System.out.println(Arrays.deepToString(simulation.getLonelyPeopleMatrix()));
            }
            // Ex.
            // Number of people in a group
            // simulation.cities.get((0)).groups[0].members.size();
            n--;
        }
    }
}