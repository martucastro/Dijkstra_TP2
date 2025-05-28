package tp2;

public class Conjunto implements ConjuntoTDA{
    private int[] elementos;
    private int cantidad;

    public void inicializarConjunto() {
        elementos = new int[100]; // tamaño fijo
        cantidad = 0;
    }

    public void agregar(int x) {
        if (!pertenece(x)) {
            elementos[cantidad++] = x;
        }
    }

    public void sacar(int x) {
        int i = 0;
        while (i < cantidad && elementos[i] != x) i++;
        if (i < cantidad) {
            elementos[i] = elementos[cantidad - 1];
            cantidad--;
        }
    }

    public int elegir() {
        return elementos[cantidad - 1];
    }

    public boolean pertenece(int x) {
        for (int i = 0; i < cantidad; i++) {
            if (elementos[i] == x) return true;
        }
        return false;
    }

    public boolean conjuntoVacio() {
        return cantidad == 0;
    }
}
