import java.util.ArrayList;
import java.util.List;

public class CitiesList {
    private List<City> cities;

    public CitiesList() {
        cities = new ArrayList<>();

        cities.add(new City("Bologna", 500, 400, 30, 60, 70, 2873000));
        cities.add(new City("Milan", 350, 250, 35, 70, 65, 1399860));
        cities.add(new City("Napoli", 750, 850, 40, 50, 60, 962003));
        cities.add(new City("Turin", 200, 300, 30, 60, 70, 875698));
        cities.add(new City("Roma", 550, 700, 35, 55, 65, 663173));
        cities.add(new City("Venice", 600, 260, 30, 60, 70, 583601));
        cities.add(new City("Palermo", 750, 1200, 25, 65, 75, 390636));
        cities.add(new City("Florence", 450, 470, 20, 70, 80, 382258));
        cities.add(new City("Bari", 930, 800, 30, 55, 65, 322751));
        cities.add(new City("Genoa", 250, 400, 35, 50, 60, 311584));
    }

    public List<City> getCities() {
        return cities;
    }

}
