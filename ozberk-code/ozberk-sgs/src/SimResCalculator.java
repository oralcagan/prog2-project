//import java.util.List;
//
//public class SimResCalculator {
//    List<int[][]> lonelyPeopleMatrixList;
//    List<UserInfoResult> userInfoResults;
//    // It tries to understand what is happening after each turn in the simulation
//    // It calculates the number of lonely people in each city after each turn
//    public SimResCalculator(List<int[][]> lonelyPeopleMatrixList, List<UserInfoResult> userInfoResults) {
//        this.lonelyPeopleMatrixList = lonelyPeopleMatrixList;
//        this.userInfoResults = userInfoResults;
//    }
//
//    public List<int[][]> calculate() {
//        CityInfo[] cityInfoList = userInfoResults.get(0).cityInfoList;
//        int[][] populationInfoByTurn; // populationInfoByTurn[i][j] = number of people in city j after turn i
//    // lonelyPeopleMatrixList[i][j][k] = number of lonely people in city j after turn i that want to move to city k
//        for (UserInfoResult userInfoResult : userInfoResults) {
//            int numCities = userInfoResult.cityInfoList.length;
//            int[][] lonelyPeopleMatrix = new int[numCities][numCities];
//            for (int i = 0; i < numCities; i++) {
//                for (int j = 0; j < numCities; j++) {
//                    lonelyPeopleMatrix[i][j] = lonelyPeopleMatrixList.get(i)[j];
//                }
//            }
//            lonelyPeopleMatrixList.add(lonelyPeopleMatrix);
//        }
//    }
//}