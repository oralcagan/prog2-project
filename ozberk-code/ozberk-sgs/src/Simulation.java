import java.util.*;

public class Simulation {
    Random random = new Random();
    int numInterests = 5;
    float minGroupInterest = 0.5F;
    CitiesList cities ;
    ArrayList<Person> populus = new ArrayList<>();
    HashSet<Integer> populusIndex = new HashSet<>();
    int counter = 0;
    PersonGenerator personGenerator = new PersonGenerator();
    int[][] lonelyPeopleMatrix;

    Simulation(CitiesList cities,int numInterests,float minGroupInterest,int[] numberOfPeopleInCities) {
        this.numInterests = numInterests;
        this.cities = cities;

        for (int i = 0; i < cities.size(); i++){
            int numberOfPeopleInCity = numberOfPeopleInCities[i];
            counter = personGenerator.generatePopulus(numberOfPeopleInCity, populusIndex,populus, counter);
            HashSet<Integer> peopleIndexSet = (HashSet<Integer>) populusIndex.clone();
            City city = cities.getCities().get(i);
            city.addPeople(peopleIndexSet);
        }
    }

    public void runTurn() {
        for(City city : cities.getCities()) {
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
        return cities.getCities().stream().map(city -> city.cityName).toArray(String[]::new);
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
    public List<int[][]> runSimulation(CitiesList citiesList) {
        String[] cityNames = citiesList.getCities().stream().map(city -> city.cityName).toArray(String[]::new);
        int[] numberOfPeopleInCities = citiesList.getCities().stream().mapToInt(city -> city.numPeople).toArray();
        int[] peopleInCities = Arrays.copyOf(numberOfPeopleInCities, numberOfPeopleInCities.length);
        float minGroupInterest = citiesList.getCities().get(0).minGroupAffiliation;
        int numInterests = citiesList.getCities().get(0).numInterests;
        List<int[][]> lonelyPeopleMatrixList = new ArrayList<>();
        List<int[]> populationHistory = new ArrayList<>();
        Simulation simulation = new Simulation(citiesList,numInterests,minGroupInterest , numberOfPeopleInCities);
        int n = 200;
        while (n > 0) {
            simulation.runTurn();
            if (n % 10 == 0) {

                int[][] lonelyPeopleMatrix = simulation.getLonelyPeopleMatrix();
                System.out.println(Arrays.deepToString(lonelyPeopleMatrix));
                // Calculate the number of people in each city after the turn
                lonelyPeopleMatrixList.add(lonelyPeopleMatrix);
                for (int i = 0; i < cityNames.length; i++) {
                    for (int j = 0; j < cityNames.length; j++) {
                        if (i != j) {
                            peopleInCities[i] -= lonelyPeopleMatrix[i][j]; // subtract people who left city i
                            peopleInCities[i] += lonelyPeopleMatrix[j][i]; // add people who moved to city i from city j
                        }
                    }
                }
                populationHistory.add(peopleInCities.clone());
                // Print the number of people in each city after the turn
                System.out.println(Arrays.toString(peopleInCities));

            }
            n--;
        }
        for (int[] population : populationHistory) {
            System.out.println(Arrays.toString(population));

        }
        return lonelyPeopleMatrixList;
    }
    public List<int[]> populationHistory(List<int[][]> lonelyPeopleMatrixList){
        List<int[]> populationHistory = new ArrayList<>();
        int lengthCities = lonelyPeopleMatrixList.get(0).length;
        int[] peopleInCities = new int[lengthCities];

        for (int[][] lonelyPeopleMatrix : lonelyPeopleMatrixList) {
            for (int i = 0; i < lengthCities; i++) {
                for (int j = 0; j < lengthCities; j++) {
                    if (i != j) {
                        peopleInCities[i] -= lonelyPeopleMatrix[i][j]; // subtract people who left city i
                        peopleInCities[i] += lonelyPeopleMatrix[j][i]; // add people who moved to city i from city j
                    }
                }
            }
            populationHistory.add(peopleInCities.clone());
        }

        return populationHistory;
    }
}
