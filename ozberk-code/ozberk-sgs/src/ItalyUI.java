import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.*;

public class ItalyUI extends Application {
    private Map<String, Circle> cityCircles = new HashMap<>();
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
        List<TurnData> turnDataList = runSimulation(userInfoResults);
        for (TurnData turnData : turnDataList) {
            System.out.println(Arrays.toString(turnData.groupPopulation));
            System.out.println("-----------------");
        }
        //Start the UI with 5s intervals initializeUI(primaryStage, cityInfoList);
        initializeUI(primaryStage, turnDataList, userInfoResults);

    }

    private List<UserInfoResult> AskForUserInput() {

        CityInfo[] cityInfoList = new CityInfo[]{new CityInfo(500, 400, "Bologna", 100, 0.7F), new CityInfo(350, 250, "Milan", 100, 0.7f), new CityInfo(750, 850, "Napoli", 100, 0.7F), new CityInfo(200, 300, "Turin", 100, 0.7F), new CityInfo(550, 700, "Roma", 100, 0.7F), new CityInfo(600, 260, "Venezia", 100, 0.7F), new CityInfo(750, 1200, "Palermo", 100, 0.7F), new CityInfo(450, 470, "Firenze", 100, 0.7F), new CityInfo(930, 800, "Bari", 100, 0.7F), new CityInfo(250, 400, "Genova", 100, 0.7F),};
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

    private List<TurnData> runSimulation(List<UserInfoResult> userInfoResult) {
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
        List<TurnData> turnDataList = new ArrayList<>();
        while (n > 0) {
            simulation.runTurn();
            turnDataList.add(TurnData.getTurnDataFromSimulation(simulation));
            n--;
        }
        return turnDataList;
    }

    private void initializeUI(Stage primaryStage, List<TurnData> turnDataList, List<UserInfoResult> userInfoResult) {
        Image image = new Image("ital.png");
        ImageView imageView = new ImageView(image);

        int GroupLength = turnDataList.get(0).groupPopulation.length;
        // Create a Pane and add the ImageView to it
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Create a CitiesList and get the list of cities
        CityInfo[] cityInfoList = userInfoResult.get(0).cityInfoList;
        Map<String, Label> cityLabels = new HashMap<>();


        // VBox to hold city population labels
        VBox cityInfoVBox = new VBox();
        cityInfoVBox.setSpacing(30);
        cityInfoVBox.setLayoutX(1150);
        cityInfoVBox.setLayoutY(250);
        cityInfoVBox.setStyle("-fx-background-color: white;");

        pane.getChildren().add(cityInfoVBox);
        cityInfoVBox.toFront();

        Label turnNumberLabel = new Label("Turn: 0");
        turnNumberLabel.setTextFill(Color.GREEN);
        turnNumberLabel.setFont(new javafx.scene.text.Font("Arial", 50));
        cityInfoVBox.getChildren().add(turnNumberLabel);

        for (CityInfo city : cityInfoList) {
            Circle circle = new Circle(10); // Adjust the size as needed
            Label cityCircle = new Label();
            cityCircle.setLayoutX(city.Xaxis);
            cityCircle.setLayoutY(city.Yaxis);
            pane.getChildren().add(cityCircle);

            Label cityLabel = new Label(city.cityName + ": " + city.population);
            cityLabels.put(city.cityName, cityLabel); // Add label to the map
            cityLabel.setTextFill(Color.BLACK); // Set text color
            cityLabel.setFont(new javafx.scene.text.Font("Arial", 40)); // Set font size
            cityInfoVBox.getChildren().add(cityLabel); // Add label to the VBox

            // City stats are shown when the circle is clicked

            cityCircles.put(city.cityName, circle);
            circle.setOnMouseClicked((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(city.cityName);
                alert.setHeaderText(null);
                alert.setContentText("Starting Population: " + city.population + "\n" + "Minimum Group Affiliation: " + city.minGroupAffiliation + "\n" + "Groups: " + GroupLength + "\n");

                // Show the dialog in a non-modal way
                alert.show();

                // Estimate the size of the window decorations
                double titleBarHeight = 30.0; // This is an estimate, adjust as needed
                double windowBorderWidth = 30.0; // This is an estimate, adjust as needed

                // Position the dialog at the top right corner of the application, inside the window decorations
                alert.getDialogPane().getScene().getWindow().setX(primaryStage.getX() + primaryStage.getWidth() - alert.getDialogPane().getWidth() - windowBorderWidth);
                alert.getDialogPane().getScene().getWindow().setY(primaryStage.getY() + titleBarHeight);

                // Bring the dialog to front
                alert.getDialogPane().getScene().getWindow().requestFocus();
            });
            pane.getChildren().add(circle);
            // Set the position of the circle based on the city's location
            circle.setCenterX(city.Xaxis);
            circle.setCenterY(city.Yaxis);
        }
        // Create a Label to display the information
        Label infoLabel = new Label();
        infoLabel.setTextFill(Color.BLACK);
        infoLabel.setFont(new javafx.scene.text.Font("Arial", 40));
        infoLabel.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        pane.getChildren().add(infoLabel);


// Create an iterator for each list
        Iterator<TurnData> turnDataIterator = turnDataList.iterator();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (turnDataIterator.hasNext()) {
                // for each group
                TurnData turnData = turnDataIterator.next();
                int[] groupPopulation = turnData.groupPopulation;

                    // Update the Label
                    infoLabel.setText("Population by Group: " + Arrays.toString(groupPopulation));
                    infoLabel.toFront();
                    CityData[] cityDatas = turnData.cityDatas;
                    for (int i = 0; i < cityDatas.length; i++) {
                        int currentTurn = i;
                        CityData cityData = cityDatas[i];
                        CityInfo cityInfo = cityInfoList[i];
                        Label cityLabel = cityLabels.get(cityInfo.cityName);
                        cityLabel.setText(cityInfo.cityName + ": " + cityData.population);

                        // We need to add the city turn to vbox
                        Circle circle = cityCircles.get(cityInfo.cityName); // Get the circle for the city
                        double radius = calculateRadius(cityData.population);
                        circle.setRadius(radius);

                        int currentPopulation = cityData.population;
                        int startingPopulation = cityInfo.population;


                        circle.setOnMouseClicked((MouseEvent ClickEvent) -> {
                            showCityStats(primaryStage,cityInfo, currentPopulation, startingPopulation, GroupLength);
                        });
                    }
                int currentTurnNumber = turnDataList.indexOf(turnData);
                turnNumberLabel.setText("Turn: " + currentTurnNumber);
                }
            }));

// Set the cycle count to the size of the lists
        timeline.setCycleCount(turnDataList.size());

// Start the Timeline
        timeline.play();
        // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);

        // Create a Scale transform for zooming
        Scale scale = new Scale(0.4, 0.4);
        pane.getTransforms().add(scale);

        // Add a zoom handler
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                scale.setX(scale.getX() * 1.1);
                scale.setY(scale.getY() * 1.1);
            } else if (event.getDeltaY() < 0) {
                scale.setX(scale.getX() / 1.1);
                scale.setY(scale.getY() / 1.1);
            }
        });

        // Create the Scene
        Scene scene = new Scene(scrollPane, 1000, 650);

        // Set the initial position of the label

        infoLabel.setLayoutX(1150);
        infoLabel.setLayoutY(1100);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Crazy");
        primaryStage.show();


    }

    private double calculateRadius(int population) {
        // Adjust this factor as needed to control the scaling of the circle based on population
        double scaleFactor = 1.5;
        // Calculate radius based on population
        return Math.sqrt(population) * scaleFactor;
    }

    private void showCityStats(Stage primaryStage,CityInfo cityInfo, int currentPopulation, int startingPopulation, int GroupLength) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(cityInfo.cityName);
        alert.setHeaderText(null);
        alert.setContentText("Starting Population: " + startingPopulation + "\n" +
                "Current Population: " + currentPopulation + "\n" +
                "Minimum Group Affiliation: " + cityInfo.minGroupAffiliation + "\n" +
                "Groups: " + GroupLength + "\n");

        // Show the dialog in a non-modal way
        alert.show();

        // Estimate the size of the window decorations
        double titleBarHeight = 39.0; // This is an estimate, adjust as needed
        double windowBorderWidth = 40.0; // This is an estimate, adjust as needed

        // Position the dialog at the top right corner of the application, inside the window decorations
        // Position the dialog at the top right corner of the application, inside the window borders
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        titleBarHeight = primaryStage.getHeight() - primaryStage.getScene().getHeight();
        windowBorderWidth = primaryStage.getWidth() - primaryStage.getScene().getWidth();

        // Positioning calculations
        alertStage.setX(primaryStage.getX() + primaryStage.getWidth() - alertStage.getWidth() - windowBorderWidth);
        alertStage.setY(primaryStage.getY() + titleBarHeight);

        // Bring the dialog to front
        alertStage.requestFocus();
        alert.getDialogPane().getScene().getWindow().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


// here we need to add the final result excel alike table to show the results of the simulation
// from bologna to milan, napoli to roma etc. the number of people moved from one city to another
// Launch the application
