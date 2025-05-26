package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.BubbleSort;
import model.InsertionSort;
import model.SelectionSort;
import model.SortingAlgorithm;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
    private String algoritmoSeleccionado = "";
    private final List<String> pasos = new ArrayList<>();

    @FXML
    private void initialize() {
        btn_Bubble.setOnAction(e -> algoritmoSeleccionado = "BubbleSort");
        btn_Insertion.setOnAction(e -> algoritmoSeleccionado = "InsertionSort");
        btn_Selection.setOnAction(e -> algoritmoSeleccionado = "SelectionSort");
        btn_Comenzar.setOnAction(e -> ejecutarOrdenamiento());
    }

    private void ejecutarOrdenamiento() {
        System.out.println("Ejecutando Ordenamiento");
        String entrada = txtBox_Datos.getText();
        if (entrada == null || entrada.isEmpty()) {
            txtBox_Ordenar.setText("Por favor, ingrese datos para ordenar.");
            return;
        }

        int[] datos;
        try {
            datos = Arrays.stream(entrada.trim().split("[,\\s]+")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            txtBox_Ordenar.setText("Error: Ingrese solo números separados por comas o espacios.");
            return;
        }

        pasos.clear();
        SortingAlgorithm algoritmo = switch (algoritmoSeleccionado){
            case "BubbleSort" -> new BubbleSort();
            case "InsertionSort" -> new InsertionSort();
            case "SelectionSort" -> new SelectionSort();
            default -> {
                txtBox_Ordenar.setText("Por favor, seleccione un algoritmo de ordenamiento.");
                yield null;
            }
        };
        if(algoritmo == null){
            txtBox_Ordenar.setText("Por favor, seleccione un algoritmo de ordenamiento.");
            return;
        }

        algoritmo.sort(datos, pasos);
        reproducirPasos(pasos);
    }

    private void reproducirPasos(List<String> pasos) {
        txtBox_Ordenar.clear();
        Timeline timeline = new Timeline();
        int delay = 1;

        for (int i = 0; i < pasos.size(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay * i), e -> txtBox_Ordenar.appendText(pasos.get(index) + "\n"));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
}
