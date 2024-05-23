import java.util.ArrayList;
import java.util.List;

public class CitiesList {
    private List<City> cities;

    public CitiesList() {
        cities = new ArrayList<>();

        cities.add(new City("Bologna", 500, 400, 5, 100, 0.5F));
        cities.add(new City("Milan", 350, 250, 5, 100, 0.5F));
        cities.add(new City("Napoli", 750, 850, 5,100, 0.5F));
        cities.add(new City("Turin", 200, 300, 5,100, 0.5F));
        cities.add(new City("Roma", 550, 700, 5,100, 0.5F));
        cities.add(new City("Venice", 600, 260, 5,100, 0.5F));
        cities.add(new City("Palermo", 750, 1200, 5,100, 0.5F));
        cities.add(new City("Florence", 450, 470, 5,100, 0.5F));
        cities.add(new City("Bari", 930, 800, 5,100, 0.5F));
        cities.add(new City("Genova", 250, 400, 5,100, 0.5F));
    }

    public List<City> getCities() {
        return cities;
    }

    public City findCityByName(String cityName) {
        for (City city : cities) {
            if (city.cityname.equals(cityName)) {
                return city;
            }
        }
        return null;
    }
}
