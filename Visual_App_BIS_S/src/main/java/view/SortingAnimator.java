package view;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.SortingStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingAnimator {

    private final List<VisualBlock> blocks = new ArrayList<>();
    private final Pane container;

    private static final double BLOCK_WIDTH = 60;
    private static final double BLOCK_HEIGHT = 60;
    private static final double GAP = 10;
    private static final double START_X = 20;
    private static final double START_Y = 40;
    private final TextArea textArea;

    public SortingAnimator(Pane container, int[] values, TextArea textArea) {
        this.container = container;
        this.textArea = textArea;
        this.container.getChildren().clear();
        blocks.clear();

        for (int value : values) {
            VisualBlock block = new VisualBlock(value);
            blocks.add(block);
            container.getChildren().add(block);
        }

        updateBlockPositions();
    }

    /*private void updateBlockPositions() {
        for (int i = 0; i < blocks.size(); i++) {
            VisualBlock block = blocks.get(i);
            block.setLayoutX(START_X + i * (BLOCK_WIDTH + GAP));
            block.setLayoutY(START_Y);
        }
    }*/

    public void playSteps(List<SortingStep> steps, TextArea txtConsola) {
        Timeline timeline = new Timeline();
        int delay = 1000; // milisegundos entre cada paso

        for (int i = 0; i < steps.size(); i++) {
            final int index = i;
            KeyFrame frame = new KeyFrame(Duration.millis(delay * i), e -> {
                SortingStep step = steps.get(index);
                int indexA = step.getIndexA();
                int indexB = step.getIndexB();

                // Mostrar mensaje en consola sincronizado
                String mensaje = step.getMensaje();
                if (mensaje != null && !mensaje.isEmpty()) {
                    txtConsola.appendText(mensaje + "\n");
                    txtConsola.setScrollTop(Double.MAX_VALUE);
                }

                // Aplicar animaciones seguras
                switch (step.getType()) {
                    case COMPARE -> {
                        if (indexA >= 0 && indexB >= 0) {
                            VisualBlock a = blocks.get(indexA);
                            VisualBlock b = blocks.get(indexB);
                            a.highlight(Color.ORANGE);
                            b.highlight(Color.ORANGE);
                            a.animateComparison();
                            b.animateComparison();

                            new Timeline(new KeyFrame(Duration.millis(300), ev -> {
                                a.resetColor();
                                b.resetColor();
                            })).play();
                        }
                    }

                    case SWAP -> {
                        if (indexA >= 0 && indexB >= 0) {
                            swapBlocks(indexA, indexB);
                        }
                    }

                    case DONE -> {
                        if (indexA >= 0 && indexB >= 0) {
                            blocks.get(indexA).highlight(Color.LIMEGREEN);
                            blocks.get(indexB).highlight(Color.LIMEGREEN);
                        }
                    }

                    case STATE -> {
                        updateBlockPositions();
                    }
                }
            });

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }



    private void swapBlocks(int i, int j) {
        VisualBlock blockA = blocks.get(i);
        VisualBlock blockB = blocks.get(j);

        double xA = blockA.getLayoutX();
        double xB = blockB.getLayoutX();

        TranslateTransition t1 = new TranslateTransition(Duration.millis(300), blockA);
        TranslateTransition t2 = new TranslateTransition(Duration.millis(300), blockB);
        t1.setByX(xB - xA);
        t2.setByX(xA - xB);

        final int[] finished = {0};

        Runnable finalizarSwap = () -> {
            blockA.setTranslateX(0);
            blockB.setTranslateX(0);

            // ⚠️ INTERCAMBIO LÓGICO
            Collections.swap(blocks, i, j);

            // ⚠️ ACTUALIZAR LAYOUT FÍSICO
            for (int k = 0; k < blocks.size(); k++) {
                VisualBlock block = blocks.get(k);
                block.setLayoutX(START_X + k * (BLOCK_WIDTH + GAP));
            }
        };

        t1.setOnFinished(e -> {
            finished[0]++;
            if (finished[0] == 2) finalizarSwap.run();
        });

        t2.setOnFinished(e -> {
            finished[0]++;
            if (finished[0] == 2) finalizarSwap.run();
        });

        t1.play();
        t2.play();
    }




    private void updateBlockPositions() {
        double paneWidth = container.getWidth();
        int total = blocks.size();

        double spacing = 10;
        double totalSpacing = spacing * (total - 1);
        double dynamicWidth = (paneWidth - totalSpacing - 40) / total; // 40 = margen extra
        double width = Math.min(dynamicWidth, 60); // límite superior

        for (int i = 0; i < total; i++) {
            VisualBlock block = blocks.get(i);
            block.setPrefWidth(width);
            block.setLayoutX(20 + i * (width + spacing)); // márgenes dinámicos
            block.setLayoutY(START_Y);
        }
    }


}
