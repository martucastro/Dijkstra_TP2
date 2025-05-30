package tp2;

public interface GrafoTDA {
    void inicializarGrafo();                          // Inicializa la estructura del grafo
    void agregarVertice(int x);                       // Agrega un vértice
    void eliminarVertice(int x);                      // Elimina un vértice
    void agregarArista(int origen, int destino, int peso); // Agrega una arista con peso
    void eliminarArista(int origen, int destino);     // Elimina una arista
    boolean existeArista(int origen, int destino);    // Verifica si existe una arista
    int pesoArista(int origen, int destino);          // Devuelve el peso de una arista
    ConjuntoTDA vertices();                           // Devuelve el conjunto de vértices

}
