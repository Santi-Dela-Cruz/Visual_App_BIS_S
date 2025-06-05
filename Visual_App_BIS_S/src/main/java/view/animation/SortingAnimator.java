package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.SortingStep;
import view.components.VisualBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingAnimator {

    private final List<VisualBlock> blocks = new ArrayList<>();
    private final Pane container;
    private final TextArea textArea;
    private static final double BLOCK_WIDTH = 60;
    private static final double GAP = 10;

    private Timeline timeline;
    private List<SortingStep> steps;
    private int currentStep = 0;
    private int[] initialValues;
    private boolean isPlaying = false;
    private boolean firstPlay = true;
    private boolean interrupted = false;
    private final StringBuilder textLog = new StringBuilder();

    public SortingAnimator(Pane container, int[] values, TextArea textArea) {
        this.container = container;
        this.textArea = textArea;
        this.container.getChildren().clear();
        blocks.clear();
        this.initialValues = values.clone();
        for (int value : values) {
            VisualBlock block = new VisualBlock(value);
            blocks.add(block);
            container.getChildren().add(block);
        }
        updateBlockPositions();
    }

    public void setSteps(List<SortingStep> steps) {
        this.steps = steps;
        currentStep = 0;
        firstPlay = true;
        isPlaying = false;
        interrupted = false;
        textLog.setLength(0);
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    public void play() {
        if (steps == null || steps.isEmpty()) return;

        if (isPlaying) return;
        isPlaying = true;

        if (firstPlay) {
            resetBlocks();
            textLog.setLength(0);
            textArea.clear();
            currentStep = 0;
            firstPlay = false;
        }

        playStep();
    }

    public void resume() {
        play();
    }

    private void playStep() {
        if (!isPlaying || currentStep >= steps.size()) {
            isPlaying = false;
            if (timeline != null) {
                timeline.stop();
                timeline = null;
            }
            if (currentStep >= steps.size()) ShowCelebration.show(container);
            return;
        }

        SortingStep step = steps.get(currentStep);
        applyStepWithCallback(step, () -> {
            currentStep++;
            if (isPlaying) {
                timeline = new Timeline(new KeyFrame(Duration.millis(700), e -> playStep()));
                timeline.setOnFinished(ev -> timeline = null);
                timeline.play();
            }
        });
    }

    private void applyStepWithCallback(SortingStep step, Runnable onFinish) {
        int indexA = step.getIndexA();
        int indexB = step.getIndexB();
        String mensaje = step.getMensaje();
        if (mensaje != null && !mensaje.isEmpty()) {
            textLog.append(mensaje).append("\n");
            textArea.setText(textLog.toString());
            textArea.setScrollTop(Double.MAX_VALUE);
        }
        switch (step.getType()) {
            case COMPARE -> {
                if (indexA >= 0 && indexB >= 0) {
                    VisualBlock a = blocks.get(indexA);
                    VisualBlock b = blocks.get(indexB);
                    a.highlight("neon-block-orange");
                    b.highlight("neon-block-orange");
                    a.animateComparison();
                    b.animateComparison();
                    Timeline t = new Timeline(new KeyFrame(Duration.millis(300), ev -> {
                        a.resetColor();
                        b.resetColor();
                        if (onFinish != null) onFinish.run();
                    }));
                    t.play();
                } else if (onFinish != null) {
                    onFinish.run();
                }
            }
            case SWAP -> {
                if (indexA >= 0 && indexB >= 0) {
                    swapBlocks(indexA, indexB, onFinish);
                } else if (onFinish != null) {
                    onFinish.run();
                }
            }
            case DONE -> {
                if (indexA >= 0 && indexB >= 0) {
                    blocks.get(indexA).highlight("neon-block-done");
                    blocks.get(indexB).highlight("neon-block-done");
                }
                if (onFinish != null) onFinish.run();
            }
            case STATE -> {
                updateBlockPositions();
                if (onFinish != null) onFinish.run();
            }
            default -> {
                if (onFinish != null) onFinish.run();
            }
        }
    }

    public void stop() {
        isPlaying = false;
        interrupted = true;
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    public void previousStep() {
        stop();
        if (steps == null || currentStep <= 0) return;
        currentStep--;
        resetBlocks();
        textLog.setLength(0);
        textArea.clear();
        for (int i = 0; i < currentStep; i++) {
            String mensaje = steps.get(i).getMensaje();
            if (mensaje != null && !mensaje.isEmpty()) textLog.append(mensaje).append("\n");
            applyStep(steps.get(i));
        }
        textArea.setText(textLog.toString());
    }

    public void nextStep() {
        stop();
        if (steps == null || currentStep >= steps.size()) return;
        applyStep(steps.get(currentStep));
        currentStep++;
    }

    private void resetBlocks() {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).setValue(initialValues[i]);
            blocks.get(i).resetColor();
        }
        updateBlockPositions();
    }

    private void applyStep(SortingStep step) {
        applyStepWithCallback(step, null);
    }

    private void swapBlocks(int i, int j, Runnable onFinish) {
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
            Collections.swap(blocks, i, j);
            updateBlockPositions();
            if (onFinish != null) onFinish.run();
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
        double paneHeight = container.getHeight();
        int total = blocks.size();

        double blockSize = BLOCK_WIDTH;
        double spacing = GAP;
        int maxCols = Math.max(1, (int) ((paneWidth + spacing) / (blockSize + spacing)));
        int rows = (int) Math.ceil((double) total / maxCols);

        double totalHeight = rows * blockSize + (rows - 1) * spacing;
        double startY = (paneHeight - totalHeight) / 2.0;

        for (int i = 0; i < total; i++) {
            int col = i % maxCols;
            int row = i / maxCols;

            int blocksInThisRow = (row == rows - 1) ? (total - row * maxCols) : maxCols;
            double rowWidth = blocksInThisRow * blockSize + (blocksInThisRow - 1) * spacing;
            double startX = (paneWidth - rowWidth) / 2.0;

            VisualBlock block = blocks.get(i);
            block.setPrefWidth(blockSize);
            block.setPrefHeight(blockSize);
            block.setLayoutX(startX + col * (blockSize + spacing));
            block.setLayoutY(startY + row * (blockSize + spacing));
        }
    }
}
