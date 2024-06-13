import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Simulation {
    Random random = new Random();
    int numInterests;
    List<City> cities = new ArrayList<>();
    ArrayList<Person> populus = new ArrayList<>();
    HashSet<Integer> populusIndex = new HashSet<>();
    int counter = 0;
    PersonGenerator personGenerator = new PersonGenerator();
    int[][] lonelyPeopleMatrix;
    CityInfo[] cityInfoList;
    CityInfo[] getCityInfoList() {
        return this.cityInfoList;
    }

    Simulation(CityInfo[] cityInfoList, int numberOfInterests) {
        this.numInterests = numberOfInterests;
        this.cityInfoList = cityInfoList;
        for (int i = 0; i < cityInfoList.length; i++){
            int numberOfPeopleInCity = cityInfoList[i].population;
            counter = personGenerator.generatePopulus(numberOfPeopleInCity, populusIndex,populus, counter,numberOfInterests);
            HashSet<Integer> peopleIndexSet = new HashSet<>(populusIndex);
            City city = new City(i,numInterests,numberOfPeopleInCity,cityInfoList[i].minGroupAffiliation,populus,peopleIndexSet);
            cities.add(city);
        }
    }

    public void runTurn() {
        for(City city : this.cities) {
            city.runTurn();
        }
        this.moveLonelyPeople();
    }

    private int getActualIndexFromSplitIndex(int removedIndex,int index) {
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
                this.changePerson(personIndex);
                city.removePerson(personIndex);

                int temp = random.nextInt(numCities-1);
                int newCityIndex = this.getActualIndexFromSplitIndex(i,temp);
                City newCity = cities.get(newCityIndex);

                int groupIndex = random.nextInt(numInterests);
                newCity.addPerson(personIndex,groupIndex);
                lonelyPeopleMatrix[i][newCityIndex] += 1;
                newCity.numPeople += 1;
            }
            city.numPeople -= city.lonelyPeople.size();
            city.cleanLonelyPeople();
        }
    }

    public int[][] getLonelyPeopleMatrix() {
        return lonelyPeopleMatrix;
    }

    private void changePerson(int index){
        Person changing = this.populus.get(index);
        changing.charisma *= 0.95F;
    }
}
