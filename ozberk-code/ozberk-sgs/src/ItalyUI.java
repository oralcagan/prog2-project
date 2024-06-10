import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItalyUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Ask for user input +++
        List<UserInfoResult> userInfoResults = AskForUserInput();
        for (UserInfoResult result : userInfoResults) {
            System.out.println("Number of Interests: " + result.numInterests);
            System.out.println("Turns: " + result.turns);
            System.out.println("City Info List: ");
            for (CityInfo cityInfo : result.cityInfoList) {
                System.out.println(cityInfo.CitytoString());
            }
        }

        // Run the simulation
        List<int[][]> lonelyPeopleMatrix = runSimulation(userInfoResults);
        for (int[][] matrix : lonelyPeopleMatrix) {
            for (int i = 0; i < matrix.length; i++) {
                System.out.println(Arrays.toString(matrix[i]));
            }
            System.out.println("------------");
        }

        //Start the UI with 5s intervals initializeUI(primaryStage, cityInfoList);


    }

    private List<UserInfoResult> AskForUserInput() {

        CityInfo[] cityInfoList = new CityInfo[]{
                new CityInfo(500, 400, "Bologna", 100, 0.7F),
                new CityInfo(350, 250, "Milan", 100, 0.7f),
                new CityInfo(750, 850, "Napoli", 100, 0.7F),
                new CityInfo(200, 300, "Turin", 100, 0.7F),
                new CityInfo(550, 700, "Roma", 100, 0.7F),
                new CityInfo(600, 260, "Venezia", 100, 0.7F),
                new CityInfo(750, 1200, "Palermo", 100, 0.7F),
                new CityInfo(450, 470, "Firenze", 100, 0.7F),
                new CityInfo(930, 800, "Bari", 100, 0.7F),
                new CityInfo(250, 400, "Genova", 100, 0.7F),
        };
        // Create a Dialog for user choice
        List<UserInfoResult> userInfoResult = new ArrayList<>();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText("Do you want to change the parameters?");
        ButtonType yesButtonType = new ButtonType("Yes");
        ButtonType noButtonType = new ButtonType("No");
        dialog.getDialogPane().getButtonTypes().add(yesButtonType);
        dialog.getDialogPane().getButtonTypes().add(noButtonType);

        // Create a TextInputDialog for user input
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Input Dialog");


        dialog.showAndWait().ifPresent(response -> {

            UserInfoResult result = new UserInfoResult();
            inputDialog.setHeaderText("Enter the number of turns for this simulation: ");
            String TurnsStr = inputDialog.showAndWait().orElse("200");
            int turns = Integer.parseInt(TurnsStr);
            result.turns = turns;

            inputDialog.setHeaderText("Enter the number of interests for this simulation: ");
            String numInterestsStr = inputDialog.showAndWait().orElse("");
            int numInterests = Integer.parseInt(numInterestsStr);
            result.numInterests = numInterests;


            if (response == yesButtonType) {
                for (CityInfo city : cityInfoList) {
                    inputDialog.setHeaderText("Population for " + city.cityName + ":");
                    inputDialog.getEditor().setText(String.valueOf(city.population)); // Set the current value
                    String populationStr = inputDialog.showAndWait().orElse("100");
                    city.population = Integer.parseInt(populationStr);

                    inputDialog.setHeaderText("Enter the minimum group affiliation for " + city.cityName + ":");
                    inputDialog.getEditor().setText(String.valueOf(city.minGroupAffiliation)); // Set the current value
                    String minGroupAffiliationStr = inputDialog.showAndWait().orElse("0.7");
                    city.minGroupAffiliation = Float.parseFloat(minGroupAffiliationStr);
                }
                result.cityInfoList = cityInfoList;
                userInfoResult.add(result);
            }
            if (response == noButtonType) {
                result.cityInfoList = cityInfoList;
                userInfoResult.add(result);

            }
        });
        return userInfoResult;
    }

    private List<int[][]> runSimulation(List<UserInfoResult> userInfoResult) {
        int numberOfInterests = userInfoResult.get(0).numInterests;
        int numberOfTurns = userInfoResult.get(0).turns;
        CityInfo[] cityInfoList = userInfoResult.get(0).cityInfoList;
        //    CityInfo[] cityInfoList = new CityInfo[]{
        //            new CityInfo(500, 400, "Bologna", 100, 0.7F),
        //            new CityInfo(350, 250, "Milan", 100, 0.7f),
        //            new CityInfo(750, 850, "Napoli", 100, 0.7F),
        //            new CityInfo(200, 300, "Turin", 100, 0.7F),
        //            new CityInfo(550, 700, "Roma", 100, 0.7F),
        //            new CityInfo(600, 260, "Venezia", 100, 0.7F),
        //            new CityInfo(750, 1200, "Palermo", 100, 0.7F),
        //            new CityInfo(450, 470, "Firenze", 100, 0.7F),
        //            new CityInfo(930, 800, "Bari", 100, 0.7F),
        //            new CityInfo(250, 400, "Genova", 100, 0.7F),
        //    };
        List<int[][]> lonelyPeopleMatrixList = new ArrayList<>();
        Simulation simulation = new Simulation(cityInfoList, numberOfInterests);
        int n = numberOfTurns;
        int a = 0;
        while (n > 0) {
            simulation.runTurn();
            if (n % 10 == 0) {
                // Lonely people movement across cities
                // From/To Roma Milan Napoli
                // Roma      0    5     3
                // Milan     2    0     7
                // Napoli    3    2     0
                int[][] lonelyPeopleMatrix = simulation.getLonelyPeopleMatrix();
                for (int i = 0; i < cityInfoList.length; i++) {
                    //System.out.println(Arrays.toString(lonelyPeopleMatrix[i]));
                    lonelyPeopleMatrixList.add(lonelyPeopleMatrix);
                }
                System.out.println("------------");
                a++;
            }
            // Ex.
            // Number of people in a group
            // simulation.cities.get((0)).groups[0].members.size();
            n--;
        }
        System.out.println("Number of times the matrix is printed: " + a);
        return lonelyPeopleMatrixList;
    }


    public static void main(String[] args) {
        launch(args);
    }
}


// Create a Circle for each city and add it to the Pane

//    private List<int[][]> runSimulation(CityInfo[] cityInfoList, int numberOfInterests) {
//        this.cityInfoList = cityInfoList;
//        Simulation simulation = new Simulation(cityInfoList, numberOfInterests);
//        int n = 200;
//        while (n > 0) {
//            simulation.runTurn();
//            if (n % 10 == 0) {
//                // Lonely people movement across cities
//                // From/To Roma Milan Napoli
//                // Roma      0    5     3
//                // Milan     2    0     7
//                // Napoli    3    2     0
//                int[][] lonelyPeopleMatrix = simulation.getLonelyPeopleMatrix();
//                for (int i = 0; i < cityInfoList.length; i++) {
//                    System.out.println(Arrays.toString(lonelyPeopleMatrix[i]));
//                }
//                System.out.println("------------");
//            }
//            // Ex.
//            // Number of people in a group
//            // simulation.cities.get((0)).groups[0].members.size();
//            n--;
//        }
//    }
//}
//    }


//private void initializeUI(Stage primaryStage, CitiesList citiesList, List<int[][]> lonelyPeopleMatrixList, List<int[]> populationHistory) {
//    Image image = new Image("ital.png");
//    ImageView imageView = new ImageView(image);
//
//    // Create a Pane and add the ImageView to it
//    Pane pane = new Pane();
//    pane.getChildren().add(imageView);
//
//    // Create a CitiesList and get the list of cities
//    List<City> cities = citiesList.getCities();
//
//
//    for (City city : cities) {
//        Circle circle = new Circle(10); // Adjust the size as needed
//        Label label = new Label();
//        label.setLayoutX(city.Xaxis);
//        label.setLayoutY(city.Yaxis);
//        pane.getChildren().add(label);
//
//        // City stats are shown when the circle is clicked
//        circle.setOnMouseClicked((MouseEvent event) -> {
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle(city.cityName);
//            alert.setHeaderText(null);
//            alert.setContentText("Starting Population: " + city.numPeople +
//                    "\n" + "Minimum Group Affiliation: "
//                    + city.minGroupAffiliation + "\n" + "Lonely People: " + city.lonelyPeople.size() + "\n" + "Groups: " + city.numInterests + "\n");
//
//            // Show the dialog in a non-modal way
//            alert.show();
//
//            // Estimate the size of the window decorations
//            double titleBarHeight = 30.0; // This is an estimate, adjust as needed
//            double windowBorderWidth = 30.0; // This is an estimate, adjust as needed
//
//            // Position the dialog at the top right corner of the application, inside the window decorations
//            alert.getDialogPane().getScene().getWindow().setX(primaryStage.getX() + primaryStage.getWidth() - alert.getDialogPane().getWidth() - windowBorderWidth);
//            alert.getDialogPane().getScene().getWindow().setY(primaryStage.getY() + titleBarHeight);
//
//            // Bring the dialog to front
//            alert.getDialogPane().getScene().getWindow().requestFocus();
//        });
//        pane.getChildren().add(circle);
//        // Set the position of the circle based on the city's location
//        circle.setCenterX(city.Xaxis);
//        circle.setCenterY(city.Yaxis);
//    }
//
//    // Create an iterator for each list
//    Iterator<int[][]> matrixIterator = lonelyPeopleMatrixList.iterator();
//    Iterator<int[]> populationIterator = populationHistory.iterator();
//
//    // Create a Timeline to update the TextAreas every 5 seconds
//    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
//        if (matrixIterator.hasNext() && populationIterator.hasNext()) {
//            int[][] matrix = matrixIterator.next();
//            int[] population = populationIterator.next();
//
//
//            // Update the Alert
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Simulation Data");
//            alert.setHeaderText(null);
//            alert.setContentText("Matrix: " + Arrays.deepToString(matrix) +
//                    "\n" + "Population: " + Arrays.toString(population));
//            alert.show();
//        }
//
//    }));
//
//    // Set the cycle count to the size of the lists
//    timeline.setCycleCount(Math.max(lonelyPeopleMatrixList.size(), populationHistory.size()));
//
//    // Start the Timeline
//    timeline.play();
//
//
//    // Create a ScrollPane
//    ScrollPane scrollPane = new ScrollPane();
//    scrollPane.setContent(pane);
//
//    // Create a Scale transform for zooming
//    Scale scale = new Scale(0.4, 0.4);
//    pane.getTransforms().add(scale);
//
//    // Add a zoom handler
//    scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
//        if (event.getDeltaY() > 0) {
//            scale.setX(scale.getX() * 1.1);
//            scale.setY(scale.getY() * 1.1);
//        } else if (event.getDeltaY() < 0) {
//            scale.setX(scale.getX() / 1.1);
//            scale.setY(scale.getY() / 1.1);
//        }
//    });
//
//    // Create the Scene
//    Scene scene = new Scene(scrollPane, 800, 600);
//
//    // Set the Scene to the Stage
//    primaryStage.setScene(scene);
//    primaryStage.setTitle("Zoomable Image App");
//    primaryStage.show();
//
//
//}


// here we need to add the final result excel alike table to show the results of the simulation
// from bologna to milan, napoli to roma etc. the number of people moved from one city to another
// Launch the application
