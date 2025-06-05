
package model;

import java.util.Arrays;
import java.util.List;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public void sort(int[] array, List<String> pasos, List<SortingStep> animsteps) {
        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "Bubble Sort:\n"));

        int n = array.length;
        boolean sorted = false;
        for (int i = 0; i < n - 1 && !sorted; i++) {
            boolean ordenado = true;
            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "Iteración " + (i + 1) + ":"));

            for (int j = 0; j < n - 1; j++) {
                boolean comparar = array[j] > array[j + 1];
                animsteps.add(new SortingStep(SortingStep.Type.COMPARE, j, j + 1, array,
                        "¿" + array[j] + " > " + array[j + 1] + "? " + comparar));

                if (comparar) {
                    animsteps.add(new SortingStep(SortingStep.Type.SWAP, j, j + 1, array,
                            "Intercambia " + array[j] + " y " + array[j + 1]));
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    ordenado = false;
                }

                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                        "Estado actual: " + Arrays.toString(array)));

                // Check if array is sorted after each comparison
                if (isSorted(array)) {
                    sorted = true;
                }
            }

            if (sorted) {
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                        "Completado anticipado: el arreglo ya está ordenado."));
                break;
            }
        }

        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                "Completado: todas las iteraciones fueron realizadas."));

        for (int i = 0; i < array.length - 1; i++) {
            animsteps.add(new SortingStep(SortingStep.Type.DONE, i, i + 1, array, ""));
        }
    }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) return false;
        }
        return true;
    }
}