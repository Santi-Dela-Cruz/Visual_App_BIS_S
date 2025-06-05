package view;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ShowCelebration {

    public static void show(Pane parent) {
        Label completedLabel = new Label("¡Completado!");
        completedLabel.setFont(Font.font("Segoe UI", 36));
        completedLabel.setTextFill(Color.web("#65a30d"));
        completedLabel.setStyle("-fx-effect: dropshadow(gaussian, #65a30d, 5, 0.8, 0, 0);");
        completedLabel.setLayoutY(30);
        parent.getChildren().add(completedLabel);

        Platform.runLater(() ->
                completedLabel.setLayoutX((parent.getWidth() - completedLabel.getWidth()) / 2)
        );

        for (int i = 0; i < 40; i++) {
            Circle confetti = new Circle(6, Color.hsb(Math.random() * 360, 0.8, 1.0));
            confetti.setLayoutX(Math.random() * parent.getWidth());
            confetti.setLayoutY(-20);
            parent.getChildren().add(confetti);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2 + Math.random()), confetti);
            tt.setByY(parent.getHeight() + 40);
            tt.setByX((Math.random() - 0.5) * 200);
            tt.setOnFinished(e -> parent.getChildren().remove(confetti));
            tt.play();

            FadeTransition ft = new FadeTransition(Duration.seconds(2.5), confetti);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.play();
        }

        FadeTransition colorTransition = new FadeTransition(Duration.seconds(2.5), completedLabel);
        colorTransition.setOnFinished(e -> {
            completedLabel.setTextFill(Color.web("#52b69a"));
            completedLabel.setStyle("-fx-effect: dropshadow(gaussian, #52b69a, 10, 0.8, 0, 0);");
        });
        colorTransition.play();
    }
}
