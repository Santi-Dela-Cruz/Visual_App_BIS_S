
package model;

import java.util.Arrays;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public void sort(int[] array, List<String> pasos, List<SortingStep> animsteps) {
        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "Insertion Sort:\n"));

        boolean sorted = false;
        for (int i = 1; i < array.length && !sorted; i++) {
            int key = array[i];
            int j = i - 1;

            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                                "Iteración " + i + ":"));
            animsteps.add(new SortingStep(SortingStep.Type.COMPARE, j, i, array,
                                "Selecciona key: " + key));

            boolean huboCambio = false;
            while (j >= 0) {
                boolean comparar = array[j] > key;
                animsteps.add(new SortingStep(SortingStep.Type.COMPARE, j, j + 1, array,
                                    "¿" + array[j] + " > " + key + "? " + comparar));
                if (!comparar) break;

                animsteps.add(new SortingStep(SortingStep.Type.SWAP, j, j + 1, array,
                                    "Desplaza " + array[j] + " a la derecha"));
                array[j + 1] = array[j];
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                                    "Estado actual: " + Arrays.toString(array)));
                j--;
                            huboCambio = true;
            }

            array[j + 1] = key;
            if (huboCambio)
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                                    "Inserta key en posición " + (j + 1) + ": " + Arrays.toString(array)));
            else
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                                    "No se realizaron cambios: " + Arrays.toString(array)));


            if (isSorted(array)) {
                animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                                    "Completado anticipado: el arreglo ya está ordenado."));
                sorted = true;
            }
        }

        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                            "Completado: todas las iteraciones fueron realizadas."));

        for (int i = 0; i < array.length - 1; i++) {
            animsteps.add(new SortingStep(SortingStep.Type.DONE, i, i + 1, array, ""));
        }
    }

    public boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) return false;
        }
        return true;
    }
}