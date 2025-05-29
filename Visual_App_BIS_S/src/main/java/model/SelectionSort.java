package model;

import java.util.Arrays;
import java.util.List;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public void sort(int[] array, List<String> pasos, List<SortingStep> animsteps) {
        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "Selection Sort:\n"));

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            boolean huboCambio = false;

            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                    "Iteración " + (i + 1) + ": busca el mínimo desde el índice " + i));

            for (int j = i + 1; j < array.length; j++) {
                boolean comparar = array[j] < array[minIndex];
                animsteps.add(new SortingStep(SortingStep.Type.COMPARE, j, minIndex, array,
                        "¿" + array[j] + " < " + array[minIndex] + "? " + comparar));

                if (comparar) {
                    minIndex = j;
                    animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                            "Nuevo mínimo encontrado: " + array[minIndex] + " en posición " + minIndex));
                }
            }

            if (minIndex != i) {
                animsteps.add(new SortingStep(SortingStep.Type.SWAP, i, minIndex, array,
                        "Intercambia " + array[i] + " y " + array[minIndex]));
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                huboCambio = true;
            } else {
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "No se requiere intercambio"));
            }

            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                    "Estado actual: " + Arrays.toString(array)));

            if (!huboCambio && estaOrdenado(array, i + 1)) {
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

    private boolean estaOrdenado(int[] array, int start) {
        for (int i = start; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) return false;
        }
        return true;
    }
}
