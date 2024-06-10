public class TurnData {
    // total şehirlerin grup başına toplanması
    //
    int[] groupPopulation;
    CityData[] cityDatas;

    public TurnData(int[] groupPopulation, CityData[] cityDatas) {
        this.groupPopulation = groupPopulation;
        this.cityDatas = cityDatas;
    }

    public static TurnData getTurnDataFromSimulation(Simulation simulation) {
        CityData[] cityDatas = CityData.cityDatasFromSimulationTurn(simulation);
        int[] groupPopulation = new int[simulation.numInterests];
        for(int i = 0; i < simulation.cities.size(); i++) {
            for(int j = 0; j < simulation.numInterests; j++) {
                groupPopulation[j] += simulation.cities.get(i).groups[j].members.size();
            }
        }
        return new TurnData(groupPopulation,cityDatas);
    }
}

class CityData {
    int population;
    int[] groupPopulation;

    CityData(int population, int[] groupPopulation) {
        this.population = population;
        this.groupPopulation = groupPopulation;
    }

    public static CityData[] cityDatasFromSimulationTurn(Simulation simulation) {
        CityData[] cityDatas = new CityData[simulation.cities.size()];
        for(int i = 0; i < simulation.cities.size(); i++) {
            City city = simulation.cities.get(i);
            System.out.println();
            int[] groupPopulation = new int[city.numInterests];
            for(int j = 0; j < city.numInterests; j++) {
                groupPopulation[j] = city.groups[j].members.size();
            }
            CityData cityData = new CityData(city.numPeople,groupPopulation);
            cityDatas[i] = cityData;
        }
        return cityDatas;
    }
}