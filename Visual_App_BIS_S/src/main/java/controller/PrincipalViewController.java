package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import model.*;
import view.SortingAnimator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrincipalViewController {
    @FXML private Button btn_Bubble;
    @FXML private Button btn_Comenzar;
    @FXML private Button btn_Insertion;
    @FXML private Button btn_Selection;
    @FXML private TextArea txtBox_Datos;
    @FXML private TextArea txtBox_Ordenar;
    @FXML private Pane paneVisual;

    private Button selectedButton = null;
    private SortingAnimator animator;
    private String algoritmoSeleccionado = "";
    private int[] datosPrevios = null;

    @FXML
    private void initialize() {
        btn_Bubble.setOnAction(e -> {
            selectSortButton(btn_Bubble);
            algoritmoSeleccionado = "BubbleSort";
        });
        btn_Selection.setOnAction(e -> {
            selectSortButton(btn_Selection);
            algoritmoSeleccionado = "SelectionSort";
        });
        btn_Insertion.setOnAction(e -> {
            selectSortButton(btn_Insertion);
            algoritmoSeleccionado = "InsertionSort";
        });
        btn_Comenzar.setOnAction(e -> ejecutarOrdenamiento());
    }

    public void setAnimator(SortingAnimator animator) {
        this.animator = animator;
    }

    @FXML private void onStopClicked() {
        if (animator != null) animator.stop();
    }

    @FXML private void onBackClicked() {
        if (animator != null) animator.previousStep();
    }

    @FXML private void onNextClicked() {
        if (animator != null) animator.nextStep();
    }

    @FXML private void onPlayClicked() {
        if (animator != null) animator.play();
    }

    private void selectSortButton(Button btn) {
        if (selectedButton != null) selectedButton.getStyleClass().remove("selected");
        if (!btn.getStyleClass().contains("selected")) btn.getStyleClass().add("selected");
        selectedButton = btn;
    }

    private void ejecutarOrdenamiento() {
        txtBox_Ordenar.clear();
        String entrada = txtBox_Datos.getText();
        if (entrada == null || entrada.isBlank()) {
            txtBox_Ordenar.setText("Por favor, ingrese datos para ordenar.");
            return;
        }

        int[] datos;
        try {
            datos = Arrays.stream(entrada.trim().split("[,\\s]+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            txtBox_Ordenar.setText("Error: Ingrese solo números separados por comas o espacios.");
            return;
        }

        boolean mismosDatos = Arrays.equals(datos, datosPrevios);
        if (!mismosDatos) {
            datosPrevios = Arrays.copyOf(datos, datos.length);
            int[] datosOriginales = Arrays.copyOf(datos, datos.length);
            List<String> pasos = new ArrayList<>();
            List<SortingStep> animsteps = new ArrayList<>();

            SortingAlgorithm algoritmo = switch (algoritmoSeleccionado) {
                case "BubbleSort" -> new BubbleSort();
                case "InsertionSort" -> new InsertionSort();
                case "SelectionSort" -> new SelectionSort();
                default -> null;
            };

            if (algoritmo == null) {
                txtBox_Ordenar.setText("Por favor, seleccione un algoritmo de ordenamiento.");
                return;
            }

            algoritmo.sort(datos, pasos, animsteps);
            this.animator = new SortingAnimator(paneVisual, datosOriginales, txtBox_Ordenar);
            animator.setSteps(animsteps);
            animator.play();
        } else if (animator != null) {
            // Si los datos son los mismos y el animator ya existe, simplemente reanudar
            animator.play();
        }
    }
}
