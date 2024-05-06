public class City {

    public String name;
    public int CrimeRate;
    public int CostOfLiving;
    public int HappinessIndex;

    public int Xaxis;
    public int Yaxis;
    public int population;

    public City(String name, int Xaxis, int Yaxis, int CrimeRate, int CostOfLiving, int HappinessIndex,  int population) {
        this.CrimeRate = CrimeRate;
        this.CostOfLiving = CostOfLiving;
        this.HappinessIndex = HappinessIndex;
        this.Xaxis = Xaxis;
        this.Yaxis = Yaxis;
        this.population = population;
        this.name = name;
    }
}