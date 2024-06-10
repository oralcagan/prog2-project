import javafx.application.Application;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CitiesList citiesList = new CitiesList();
        int[] numberOfPeopleInCities = citiesList.getCities().stream().mapToInt(city -> city.numPeople).toArray();
        int[] peopleInCities = Arrays.copyOf(numberOfPeopleInCities, numberOfPeopleInCities.length);
        float minGroupInterest = citiesList.getCities().get(0).minGroupAffiliation;
        int numInterests = citiesList.getCities().get(0).numInterests;
        Simulation simulation = new Simulation(citiesList, numInterests, minGroupInterest, numberOfPeopleInCities);
        List<int[][]> lonelyPeopleMatrixList = simulation.runSimulation(citiesList);
        // Calculate and save the number of people in each city after the turn
        List<int[]> populationHistory = simulation.populationHistory(lonelyPeopleMatrixList);

        System.out.println("Population History: " + populationHistory);
        System.out.println("Lonely People Matrix List: " + lonelyPeopleMatrixList);


//        Application.launch(italyUI.class, args);
    }
}

