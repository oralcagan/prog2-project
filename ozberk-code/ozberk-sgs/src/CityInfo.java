public class CityInfo {
    int Xaxis;
    int Yaxis;
    String cityName;
    int population;
    float minGroupAffiliation;

    public CityInfo(int Xaxis, int Yaxis, String cityName, int population, float minGroupAffiliation) {
        this.Xaxis = Xaxis;
        this.Yaxis = Yaxis;
        this.cityName = cityName;
        this.population = population;
        this.minGroupAffiliation = minGroupAffiliation;
    }

    public String CitytoString() {
        return "CityInfo{" +
                "XAxis=" + Xaxis +
                ", YAxis=" + Yaxis +
                ", cityName='" + cityName + '\'' +
                ", population=" + population +
                ", minGroupAffiliation=" + minGroupAffiliation +
                '}';
    }
}

