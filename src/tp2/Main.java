package tp2;

public class Main {
    public static void main(String[] args) {
        GrafoTDA grafo = new GrafoDin();
        grafo.inicializarGrafo();

        // Agregar vértices
        for (int i = 1; i <= 6; i++) {
            grafo.agregarVertice(i);
        }

        // Agregar aristas dirigidas con pesos
        grafo.agregarArista(1, 2, 2);
        grafo.agregarArista(1, 3, 1);
        grafo.agregarArista(3, 5, 2);
        grafo.agregarArista(5, 4, 2);
        grafo.agregarArista(3, 6, 3);

        // Aplicar Dijkstra. Solo costos mínimos
        GrafoTDA grafoCostos = grafo.dijkstra(1, false);
        System.out.println("Costos mínimos desde el nodo 1:");
        mostrarGrafo(grafoCostos);

        // Aplicar Dijkstra. Caminos reales
        GrafoTDA grafoCaminos = grafo.dijkstra(1, true);
        System.out.println("\nCaminos mínimos desde el nodo 1:");
        mostrarGrafo(grafoCaminos);
    }

    public static void mostrarGrafo(GrafoTDA grafo) {
        ConjuntoTDA vertices = grafo.vertices();
        ConjuntoTDA auxVertices = new Conjunto();
        auxVertices.inicializarConjunto();

        // Copiar vértices para no modificar el original
        while (!vertices.conjuntoVacio()) {
            int x = vertices.elegir();
            auxVertices.agregar(x);
            vertices.sacar(x);
        }

        while (!auxVertices.conjuntoVacio()) {
            int origen = auxVertices.elegir();
            auxVertices.sacar(origen);

            ConjuntoTDA destinos = grafo.vertices();
            ConjuntoTDA auxDestinos = new Conjunto();
            auxDestinos.inicializarConjunto();

            while (!destinos.conjuntoVacio()) {
                int d = destinos.elegir();
                auxDestinos.agregar(d);
                destinos.sacar(d);
            }

            while (!auxDestinos.conjuntoVacio()) {
                int destino = auxDestinos.elegir();
                auxDestinos.sacar(destino);

                if (grafo.existeArista(origen, destino)) {
                    int peso = grafo.pesoArista(origen, destino);
                    System.out.println(origen + " -> " + destino + " (peso: " + peso + ")");
                }
            }
        }
    }
}
