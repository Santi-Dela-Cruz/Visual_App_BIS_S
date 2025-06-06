package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Path;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Path.FXML_PRINCIPAL_VIEW)));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        // Add this line to load your CSS
        scene.getStylesheets().add(getClass().getResource(Path.FXML_STYLES).toExternalForm());

        stage.setTitle("Visualizador de Algoritmos de Ordenamiento");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
