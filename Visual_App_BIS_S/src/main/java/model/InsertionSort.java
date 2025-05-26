package model;

import java.util.Arrays;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {


    @Override
    public void sort(int[] array, List<String> pasos) {
        pasos.add("Insertion Sort:\n");
        boolean sorted = true;
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            pasos.add("Selecciona key: " + key);
            boolean shifted = false;

            while (j >= 0) {
                pasos.add("¿" + array[j] + " > " + key + "? " + (array[j] > key));
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    pasos.add("Desplaza " + array[j] + " a la derecha: " + java.util.Arrays.toString(array));
                    shifted = true;
                    sorted = false;
                } else {
                    break;
                }
                j--;
            }

            array[j + 1] = key;
            pasos.add("Inserta key en posición " + (j + 1) + ": " + java.util.Arrays.toString(array));

            if (!shifted) {
                pasos.add("Completado: el arreglo ya está ordenado.");
                break;
            }
        }
    }
}

