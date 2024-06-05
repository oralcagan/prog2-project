import java.util.*;

public class Main {
    public static void main(String[] args) {
        int numInterests = 5;
        float minGroupInterest = 0.6F;
        String[] cityNames = {"Milano", "Napoleon", "Roma"};
        List<City> cities = new ArrayList<>();
        ArrayList<Person> populus = new ArrayList<>();
        HashSet<Integer> populusIndex = new HashSet<>();
        int counter = 0;
        PersonGenerator personGenerator = new PersonGenerator();
        for (int i = 0; i < cityNames.length; i++){
            counter = personGenerator.generatePopulus(10, populusIndex,populus, counter);
            City city = new City(numInterests,10,minGroupInterest,populus);
            city.people = (HashSet<Integer>) populusIndex.clone();
            cities.add(city);
        }
        for (City city : cities) {
            city.changePerson(11, populus);
        }
    }
}