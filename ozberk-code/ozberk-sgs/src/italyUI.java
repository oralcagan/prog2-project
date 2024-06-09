import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.transform.Scale;

import java.util.Arrays;
import java.util.List;

public class italyUI extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Load the image
        Image image = new Image("ital.png");
        ImageView imageView = new ImageView(image);

        // Create a Pane and add the ImageView to it
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Create a CitiesList and get the list of cities
        CitiesList citiesList = new CitiesList();
        List<City> cities = citiesList.getCities();

        // if and else for the user to choose between manual inputs and otomatic inputs by the system
// if the user chooses manual inputs, the user will be asked to enter the number of people, the minimum group affiliation, the number of interests, and the number of turns

        // Create a Dialog for user choice
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
            if (response == yesButtonType) {
                inputDialog.setHeaderText("Enter the number of interests for this simulation: ");
                String numInterestsStr = inputDialog.showAndWait().orElse("");
                int numInterests = Integer.parseInt(numInterestsStr);

                // All the stats that need to be saved and same for each city
                // Set the number of interests for each city
                for (int i = 0; i < citiesList.size(); i++) {
                    City city = citiesList.get(i);
                    city.numInterests = numInterests;

                    inputDialog.setHeaderText("Population for " + city.cityName + ":");
                    inputDialog.getEditor().setText(String.valueOf(city.numPeople)); // Set the current value
                    String numPeopleStr = inputDialog.showAndWait().orElse("");
                    int numPeople = Integer.parseInt(numPeopleStr);

                    inputDialog.setHeaderText("Enter the minimum group affiliation for " + city.cityName + ":");
                    inputDialog.getEditor().setText(String.valueOf(city.minGroupAffiliation)); // Set the current value
                    String minGroupAffiliationStr = inputDialog.showAndWait().orElse("");
                    float minGroupAffiliation = Float.parseFloat(minGroupAffiliationStr);

                    city.numInterests = numInterests;
                    city.numPeople = numPeople;
                    city.minGroupAffiliation = minGroupAffiliation;
                }
            }

            // Create a Circle for each city and add it to the Pane
            for (City city : cities) {
                Circle circle = new Circle(10); // Adjust the size as needed
                Label label = new Label();
                label.setLayoutX(city.Xaxis);
                label.setLayoutY(city.Yaxis);
                pane.getChildren().add(label);

                // City stats are shown when the circle is clicked
                circle.setOnMouseClicked((MouseEvent event) -> {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(city.cityName);
                    alert.setHeaderText(null);
                    alert.setContentText("Population: " + city.numPeople + "\n" + "Number of Interests: " + city.numInterests + "\n" + "Minimum Group Affiliation: " + city.minGroupAffiliation + "\n" + "Lonely People: " + city.lonelyPeople.size() + "\n" + "Groups: " + city.numInterests + "\n");

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
            Scene scene = new Scene(scrollPane, 800, 600);

            // Set the Scene to the Stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Zoomable Image App");
            primaryStage.show();

            runSimulation(citiesList);
        });
    }

    private void runSimulation(CitiesList citiesList) {
        String[] cityNames = new String[]{"Bologna", "Milan", "Napoli", "Roma", "Turin", "Venice", "Palermo", "Florence", "Bari", "Genova"};
        int[] numberOfPeopleInCities = new int[cityNames.length];

        for (int i = 0; i < cityNames.length; i++) {
            City city = citiesList.findCityByName(cityNames[i]);
            if (city != null) {
                numberOfPeopleInCities[i] = city.numPeople;
            }
        }
        Simulation simulation = new Simulation(citiesList, 5, 0.7F, numberOfPeopleInCities);
        int n = 200;
        while (n > 0) {
            simulation.runTurn();
            if (n % 10 == 0) {
                // Lonely people movement across cities
                // From/To Roma Milan Napoli
                // Roma      0    5     3
                // Milan     2    0     7
                // Napoli    3    2     0
                // simulation.getLonelyPeopleMatrix()
                System.out.println(Arrays.deepToString(simulation.getLonelyPeopleMatrix()));
            }
            // Ex.
            // Number of people in a group
            // simulation.cities.get((0)).groups[0].members.size();
            n--;
        }
    }

    // here we need to add the final result excel alike table to show the results of the simulation
    // from bologna to milan, napoli to roma etc. the number of people moved from one city to another
    // Launch the application
    public static void main(String[] args) {
        launch(args);
    }
}