package view;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class VisualBlock extends StackPane {
    private final Rectangle rectangulo;
    private final Label label;
    private final int value;

    public VisualBlock(int value) {
        this.value = value;

        rectangulo = new Rectangle(60, 60);
        rectangulo.setFill(Color.LIGHTGREEN);
        rectangulo.setArcWidth(10);
        rectangulo.setArcHeight(10);

        label = new Label(String.valueOf(value));
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        getChildren().addAll(rectangulo, label);
    }

    public void highlight(Color color) {
        rectangulo.setFill(color);
    }

    public void resetColor() {
        rectangulo.setFill(Color.LIGHTGREEN);
    }

    public void animateComparison() {
        ScaleTransition st = new ScaleTransition(Duration.millis(300), this);
        st.setToX(1.3);
        st.setToY(1.3);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    public void setBlockWidth(double width) {
        rectangulo.setWidth(width);
    }

    public int getValue() {
        return value;
    }
}
