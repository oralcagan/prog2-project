public class City {
    private String name;
    private int CrimeRate;
    private int CostOfLiving;
    private int HappinessIndex;

    private int Xaxis;
    private int Yaxis;
    private int population;
    private int resources;

    private int gdp;
    private int perCapitaIncome;
    private int unemploymentRate;
    private int costOfLivingIndex;
    private int incomeInequality;
    private int povertyRate;
    private int healthcareAccessIndex;
    private int lifeExpectancy;
    private int educationLevel;
    private int crimeRate;
    private int airQualityIndex;
    private int greenSpacePercentage;
    private int publicTransportAccessibility;
    private int trafficCongestionIndex;
    private int happinessIndex;
    private int culturalDiversityIndex;
    private int startupDensity;
    private int environmentalSustainability;
    private int housingAffordabilityIndex;
    private int infrastructureQuality;

    public City(String name, int CrimeRate, int CostOfLiving, int HappinessIndex, int Xaxis, int Yaxis, int population, int resources, int gdp, int perCapitaIncome, int unemploymentRate, int costOfLivingIndex, int incomeInequality, int povertyRate, int healthcareAccessIndex, int lifeExpectancy, int educationLevel, int crimeRate, int airQualityIndex, int greenSpacePercentage, int publicTransportAccessibility, int trafficCongestionIndex, int happinessIndex, int culturalDiversityIndex, int startupDensity, int environmentalSustainability, int housingAffordabilityIndex, int infrastructureQuality) {
        this.name = name;
        this.CrimeRate = CrimeRate;
        this.CostOfLiving = CostOfLiving;
        this.HappinessIndex = HappinessIndex;
        this.Xaxis = Xaxis;
        this.Yaxis = Yaxis;
        this.population = population;
        this.resources = resources;
        this.gdp = gdp;
        this.perCapitaIncome = perCapitaIncome;
        this.unemploymentRate = unemploymentRate;
        this.costOfLivingIndex = costOfLivingIndex;
        this.incomeInequality = incomeInequality;
        this.povertyRate = povertyRate;
        this.healthcareAccessIndex = healthcareAccessIndex;
        this.lifeExpectancy = lifeExpectancy;
        this.educationLevel = educationLevel;
        this.crimeRate = crimeRate;
        this.airQualityIndex = airQualityIndex;
        this.greenSpacePercentage = greenSpacePercentage;
        this.publicTransportAccessibility = publicTransportAccessibility;
        this.trafficCongestionIndex = trafficCongestionIndex;
        this.happinessIndex = happinessIndex;
        this.culturalDiversityIndex = culturalDiversityIndex;
        this.startupDensity = startupDensity;
        this.environmentalSustainability = environmentalSustainability;
        this.housingAffordabilityIndex = housingAffordabilityIndex;
        this.infrastructureQuality = infrastructureQuality;
    }
    public String getName() {
        return name;
    }
    public int getXaxis() {
        return Xaxis;
    }
    public int getYaxis() {
        return Yaxis;
    }
    public int getPopulation() {
        return population;
    }

    public int getResources() {
        return resources;
    }
}