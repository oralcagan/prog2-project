import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class CitiesList {

    private List<City> cities;

    public int size() {
        return this.cities.size();
    }

    public City get(int index) {
        return this.cities.get(index);
    }

    public CitiesList() {
        cities = new ArrayList<>();

        cities.add(new City(0, "Bologna", 500, 400, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(1, "Milan", 350, 250, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(2, "Napoli", 750, 850, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(3, "Turin", 200, 300, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(4, "Roma", 550, 700, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(5, "Venice", 600, 260, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(6, "Palermo", 750, 1200, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(7, "Florence", 450, 470, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(8, "Bari", 930, 800, 5, 100, 0.5F, new ArrayList<Person>(), new HashSet<Integer>()));
        cities.add(new City(9, "Genova", 250, 400, 5, 100, 0.5F, new ArrayList<>(), new HashSet<>()));
    }

    public List<City> getCities() {
        return cities;
    }

    public City findCityByName(String cityName) {
        for (City city : cities) {
            if (city.cityName.equals(cityName)) {
                return city;
            }
        }
        return null;
    }
}


