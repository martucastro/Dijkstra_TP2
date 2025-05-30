package tp2;

import java.util.*;

public class Main {

    public static GrafoTDA dijkstra(GrafoDin grafo, int origen, boolean devolverCaminos) {
        ;
    }

    public static void mostrarGrafo(GrafoTDA grafo) {
        ConjuntoTDA vertices = grafo.Vertices();
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

            ConjuntoTDA destinos = grafo.Vertices();
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

                if (grafo.ExisteArista(origen, destino)) {
                    int peso = grafo.PesoArista(origen, destino);
                    System.out.println(origen + " -> " + destino + " (peso: " + peso + ")");
                }
            }
        }
    }

    public static void main(String[] args) {
        GrafoDin grafo = new GrafoDin();
        grafo.InicializarGrafo();

        // Agregar vértices
        for (int i = 1; i <= 6; i++) {
            grafo.AgregarVertice(i);
        }

        // Agregar aristas dirigidas con pesos
        grafo.AgregarArista(1, 2, 2);
        grafo.AgregarArista(1, 3, 1);
        grafo.AgregarArista(3, 5, 2);
        grafo.AgregarArista(5, 4, 2);
        grafo.AgregarArista(3, 6, 3);

    }

}
