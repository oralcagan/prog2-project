import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SimpleUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the image
        Image image = new Image("file:C:\\Users\\OZBERK\\IdeaProjects\\ozberk-sgs\\ital.png");
        ImageView imageView = new ImageView(image);

        // Create a Pane and add the ImageView to it
        Pane pane = new Pane();
        pane.getChildren().add(imageView);

        // Create some cities
        List<City> cities = new ArrayList<>();
        cities.add(new City("City1", 400,200, 1000000, 50000,32,900));
        cities.add(new City("City2", 300, 200, 2000000, 100000,100,123123));
        // Add more cities as needed...

        // Create a Circle for each city and add it to the Pane
        for (City city : cities) {
            Circle circle = new Circle(10); // Adjust the size as needed
            circle.setOnMouseClicked((MouseEvent event) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(city.name);
                alert.setHeaderText(null);
                alert.setContentText("Population: " + city.population + "\nCostOfLiving: " + city.CostOfLiving + "\nHappinessIndex: " + city.HappinessIndex + "\nCrimeRate: " + city.CrimeRate);                alert.showAndWait();
            });
            pane.getChildren().add(circle);
            // Set the position of the circle based on the city's location
            circle.setCenterX(city.Xaxis);
            circle.setCenterY(city.Yaxis);
        }

        // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);

        // Add a zoom handler
        imageView.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                imageView.setScaleX(imageView.getScaleX() * 1.1);
                imageView.setScaleY(imageView.getScaleY() * 1.1);
            } else if (event.getDeltaY() < 0) {
                imageView.setScaleX(imageView.getScaleX() / 1.1);
                imageView.setScaleY(imageView.getScaleY() / 1.1);
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