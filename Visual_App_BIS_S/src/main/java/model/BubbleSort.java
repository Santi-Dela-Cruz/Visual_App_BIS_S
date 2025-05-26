package model;

import java.util.Arrays;
import java.util.List;

public class BubbleSort implements SortingAlgorithm{

    @Override
    public void sort(int[] array, List<String> pasos) {
        pasos.add("Bubble Sort:\n");
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                pasos.add("¿" + array[j] + " > " + array[j + 1] + "? " + (array[j] > array[j + 1]));
                if (array[j] > array[j + 1]) {
                    pasos.add("Intercambia " + array[j] + " y " + array[j + 1]);
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
                pasos.add("Estado actual: " + Arrays.toString(array));
            }
            if (!swapped) {
                pasos.add("Completado: el arreglo ya está ordenado.");
                break;
            }
        }
    }
}
