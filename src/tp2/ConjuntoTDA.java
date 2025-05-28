package tp2;

public interface ConjuntoTDA {
    void inicializarConjunto();      // Inicializa el conjunto vacío
    void agregar(int x);             // Agrega un elemento al conjunto
    void sacar(int x);               // Elimina un elemento
    int elegir();                    // Devuelve un elemento
    boolean pertenece(int x);        // Verifica si el elemento está en el conjunto
    boolean conjuntoVacio();         // Verifica si el conjunto está vacío
}
