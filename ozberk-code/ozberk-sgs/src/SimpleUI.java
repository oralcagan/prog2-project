import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

import java.util.List;
import java.util.Random;

public class SimpleUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the image
        Image image = new Image("file:C:\\Users\\OZBERK\\IdeaProjects\\sgs-prog2\\ozberk-code\\ozberk-sgs\\ital.png");
        ImageView imageView = new ImageView(image);

        // Create a Pane and add the ImageView to it
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Create a CitiesList and get the list of cities
        CitiesList citiesList = new CitiesList();
        List<City> cities = citiesList.getCities();

        // Create a Circle for each city and add it to the Pane
        for (City city : cities) {
            Circle circle = new Circle(10); // Adjust the size as needed
            circle.setOnMouseClicked((MouseEvent event) -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(city.name);
                alert.setHeaderText(null);
                alert.setContentText("Population: " + city.population + "\nCostOfLiving: " + city.CostOfLiving + "\nHappinessIndex: " + city.HappinessIndex + "\nCrimeRate: " + city.CrimeRate);

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}