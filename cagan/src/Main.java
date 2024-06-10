import java.util.*;

public class Main {
    public static void main(String[] args) {
        int numberOfInterests = 5;
        CityInfo[] cityInfoList = new CityInfo[]{
                new CityInfo(500,400,"Bologna",100,0.5F),
                new CityInfo(350,250,"Milan",100,0.5f),
                new CityInfo(750,850,"Napoli",100,0.5F),
                new CityInfo(200,300,"Turin",100,0.5F),
                new CityInfo(550,700,"Roma",100,0.5F),
                new CityInfo(600,260,"Venezia",100,0.5F),
                new CityInfo(750,1200,"Palermo",100,0.5F),
                new CityInfo(450,470,"Firenze",100,0.5F),
                new CityInfo(930,800,"Bari",100,0.5F),
                new CityInfo(250,400,"Genova",100,0.5F),
        };
        Simulation simulation = new Simulation(cityInfoList,numberOfInterests);
        int n = 200;
        while(n > 0) {
            simulation.runTurn();
            if(n % 10 == 0) {
                // Lonely people movement across cities
                // From/To Roma Milan Napoli
                // Roma      0    5     3
                // Milan     2    0     7
                // Napoli    3    2     0
                int[][] lonelyPeopleMatrix = simulation.getLonelyPeopleMatrix();
                for(int i = 0; i < cityInfoList.length; i++) {
                    System.out.println(Arrays.toString(lonelyPeopleMatrix[i]));
                }
                System.out.println("------------");
            }
            // Ex.
            // Number of people in a group
            // simulation.cities.get((0)).groups[0].members.size();
            n--;
        }
    }
}