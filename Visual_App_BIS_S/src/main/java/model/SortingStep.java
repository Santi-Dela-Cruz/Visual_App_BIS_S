package model;

public class SortingStep {
    public enum Type {COMPARE, SWAP, DONE, STATE}
    private final Type type;
    private final int indexA;
    private final int indexB;
    private final int[] arrayActual;
    private final String mensaje;

    public SortingStep(Type type, int indexA, int indexB, int[] arrayActual, String mensaje) {
        this.type = type;
        this.indexA = indexA;
        this.indexB = indexB;
        this.arrayActual = arrayActual.clone(); // Clonar el array para preservar el estado actual
        this.mensaje = mensaje; // Descripción opcional, se puede usar si es necesario
    }

    public Type getType() {
        return type;
    }
    public int getIndexA() {
        return indexA;
    }
    public int getIndexB() {
        return indexB;
    }
    public int[] getArrayActual() {
        return arrayActual.clone(); // Clonar para evitar modificaciones externas
    }

    public String getMensaje() {
        return mensaje;
    }
}
