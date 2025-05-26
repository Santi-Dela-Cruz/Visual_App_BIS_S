package model;

import java.util.List;

public class SelectionSort implements SortingAlgorithm{

    @Override
    public void sort(int[] array, List<String> pasos) {
        pasos.add("Selection Sort:\n");
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            boolean swapped = false;
            pasos.add("Iteración " + (i + 1) + ": busca el mínimo desde el índice " + i);
            for (int j = i + 1; j < array.length; j++) {
                pasos.add("¿" + array[j] + " < " + array[minIndex] + "? " + (array[j] < array[minIndex]));
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                    pasos.add("Nuevo mínimo encontrado: " + array[minIndex] + " en posición " + minIndex);
                }
            }
            if (minIndex != i) {
                pasos.add("Intercambia " + array[i] + " y " + array[minIndex]);
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                swapped = true;
            } else {
                pasos.add("No se requiere intercambio");
            }
            pasos.add("Estado actual: " + java.util.Arrays.toString(array));
            if (!swapped) {
                pasos.add("Completado: el arreglo ya está ordenado.");
                break;
            }
        }
    }
}
