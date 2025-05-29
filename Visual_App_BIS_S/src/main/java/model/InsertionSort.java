package model;

import java.util.Arrays;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public void sort(int[] array, List<String> pasos, List<SortingStep> animsteps) {
        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array, "Insertion Sort:\n"));

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                    "Iteración " + i + ":"));
            animsteps.add(new SortingStep(SortingStep.Type.COMPARE, j, i, array,
                    "Selecciona key: " + key));

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
            }

            array[j + 1] = key;
            animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                    "Inserta key en posición " + (j + 1) + ": " + Arrays.toString(array)));
        }

        animsteps.add(new SortingStep(SortingStep.Type.STATE, -1, -1, array,
                "Completado: todas las iteraciones fueron realizadas."));

        for (int i = 0; i < array.length - 1; i++) {
            animsteps.add(new SortingStep(SortingStep.Type.DONE, i, i + 1, array, ""));
        }
    }
}
