package view;

    import javafx.animation.ScaleTransition;
    import javafx.scene.control.Label;
    import javafx.scene.layout.StackPane;
    import javafx.util.Duration;

    public class VisualBlock extends StackPane {
        private final Label label;
        private int value; // Remove final

        public VisualBlock(int value) {
            this.value = value;

            label = new Label(String.valueOf(value));
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");

            getChildren().add(label);
            this.getStyleClass().add("neon-block");
        }

        // Add this method
        public void setValue(int newValue) {
            this.value = newValue;
            label.setText(String.valueOf(newValue));
        }

        public void highlight(String neonClass) {
            getStyleClass().removeIf(s -> s.startsWith("neon-block"));
            getStyleClass().add(neonClass);
        }

        public void resetColor() {
            getStyleClass().removeIf(s -> s.startsWith("neon-block"));
            getStyleClass().add("neon-block");
        }

        public void animateComparison() {
            ScaleTransition st = new ScaleTransition(Duration.millis(300), this);
            st.setToX(1.3);
            st.setToY(1.3);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        }

        public int getValue() {
            return value;
        }
    }