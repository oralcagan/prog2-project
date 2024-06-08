import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Simulation {
    Random random = new Random();
    int numInterests = 5;
    float minGroupInterest = 0.6F;
    List<City> cities = new ArrayList<>();
    ArrayList<Person> populus = new ArrayList<>();
    HashSet<Integer> populusIndex = new HashSet<>();
    int counter = 0;
    PersonGenerator personGenerator = new PersonGenerator();
    String[] cityNames;
    int[][] lonelyPeopleMatrix;

    Simulation(String[] cityNames,int numInterests,float minGroupInterest,int[] numberOfPeopleInCities) {
        this.numInterests = numInterests;
        this.minGroupInterest = minGroupInterest;
        this.cityNames = cityNames;
        for (int i = 0; i < cityNames.length; i++){
            int numberOfPeopleInCity = numberOfPeopleInCities[i];
            counter = personGenerator.generatePopulus(numberOfPeopleInCity, populusIndex,populus, counter);
            HashSet<Integer> peopleIndexSet = (HashSet<Integer>) populusIndex.clone();
            City city = new City(i,numInterests,numberOfPeopleInCity,minGroupInterest,populus,peopleIndexSet);
            cities.add(city);
        }
    }

    public void runTurn() {
        for(City city : this.cities) {
            city.runTurn();
        }
        this.moveLonelyPeople();
    }

    private int getActualIndexFromSplitIndex(int bound, int removedIndex,int index) {
        if(removedIndex > index) {
            return index;
        }
        return index+1;
    }

    private void moveLonelyPeople() {
        int numCities = cities.size();
        lonelyPeopleMatrix = new int[numCities][numCities];
        for(int i = 0; i < numCities; i++) {
            City city = cities.get(i);
            for(int personIndex : city.lonelyPeople) {
                //this.changePerson(personIndex);
                city.removePerson(personIndex);

                int temp = random.nextInt(numCities-1);
                int newCityIndex = this.getActualIndexFromSplitIndex(numCities-1,i,temp);
                City newCity = cities.get(newCityIndex);

                int groupIndex = random.nextInt(numInterests);
                newCity.addPerson(personIndex,groupIndex);
                lonelyPeopleMatrix[i][newCityIndex] += 1;
            }
            city.cleanLonelyPeople();
        }
    }

    public int[][] getLonelyPeopleMatrix() {
        return lonelyPeopleMatrix;
    }

    public String[] getCityNames() {
        return cityNames;
    }

    private void changePerson(int index){
        Person changing = this.populus.get(index);
        changing.charisma *= 0.90F;
        float[] changing_interests = this.populus.get(index).interests;
        for (int i = 0; i < changing.interests.length; i ++){
            float sign = random.nextFloat();
            float amount = random.nextFloat(0.1F);
            if (sign >= 0.5F){
                changing_interests[i] *= 1.0F + amount;
            }
            else {
                changing_interests[i] *= 1.0F - amount;
            }
        }
    }
}
