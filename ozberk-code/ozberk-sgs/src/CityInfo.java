public class CityInfo {
    int XAxis;
    int YAxis;
    String cityName;
    int population;
    float minGroupAffiliation;

    public CityInfo(int XAxis, int YAxis, String cityName, int population, float minGroupAffiliation) {
        this.XAxis = XAxis;
        this.YAxis = YAxis;
        this.cityName = cityName;
        this.population = population;
        this.minGroupAffiliation = minGroupAffiliation;
    }

    public String CitytoString() {
        return "CityInfo{" +
                "XAxis=" + XAxis +
                ", YAxis=" + YAxis +
                ", cityName='" + cityName + '\'' +
                ", population=" + population +
                ", minGroupAffiliation=" + minGroupAffiliation +
                '}';
    }
}

