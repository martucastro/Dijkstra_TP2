package tp2;

import java.util.*;

public class Main {

        public static GrafoTDA[] dijkstra(GrafoDin grafo, int origen) {
            ConjuntoTDA vertices = grafo.Vertices();
            ConjuntoTDA auxVertices = new Conjunto();
            auxVertices.inicializarConjunto();

            int[] dist = new int[100];
            int[] visitado = new int[100];
            int[] anterior = new int[100];

            while (!vertices.conjuntoVacio()) {
                int x = vertices.elegir();
                auxVertices.agregar(x);
                vertices.sacar(x);
            }

            for (int i = 0; i < 100; i++) {
                dist[i] = Integer.MAX_VALUE;
                visitado[i] = 0;
                anterior[i] = -1;
            }
            dist[origen] = 0;

            ConjuntoTDA restantes = new Conjunto();
            restantes.inicializarConjunto();

            ConjuntoTDA tempVertices = auxVertices;
            while (!tempVertices.conjuntoVacio()) {
                int x = tempVertices.elegir();
                restantes.agregar(x);
                tempVertices.sacar(x);
            }

            while (!restantes.conjuntoVacio()) {
                int minDist = Integer.MAX_VALUE;
                int u = -1;

                ConjuntoTDA aux = new Conjunto();
                aux.inicializarConjunto();

                while (!restantes.conjuntoVacio()) {
                    int v = restantes.elegir();
                    aux.agregar(v);
                    restantes.sacar(v);

                    if (visitado[v] == 0 && dist[v] < minDist) {
                        minDist = dist[v];
                        u = v;
                    }
                }

                while (!aux.conjuntoVacio()) {
                    int x = aux.elegir();
                    restantes.agregar(x);
                    aux.sacar(x);
                }

                if (u == -1) {
                    break;
                }

                visitado[u] = 1;

                ConjuntoTDA ady = grafo.Vertices();
                ConjuntoTDA auxAdy = new Conjunto();
                auxAdy.inicializarConjunto();

                while (!ady.conjuntoVacio()) {
                    int w = ady.elegir();
                    auxAdy.agregar(w);
                    ady.sacar(w);
                }

                while (!auxAdy.conjuntoVacio()) {
                    int v = auxAdy.elegir();
                    auxAdy.sacar(v);

                    if (grafo.ExisteArista(u, v)) {
                        int peso = grafo.PesoArista(u, v);
                        if (dist[u] + peso < dist[v]) {
                            dist[v] = dist[u] + peso;
                            anterior[v] = u;
                        }
                    }
                }
            }

            GrafoDin grafoCostos = new GrafoDin();
            grafoCostos.InicializarGrafo();
            GrafoDin grafoCaminos = new GrafoDin();
            grafoCaminos.InicializarGrafo();

            ConjuntoTDA finalVertices = auxVertices;
            while (!finalVertices.conjuntoVacio()) {
                int v = finalVertices.elegir();
                grafoCostos.AgregarVertice(v);
                grafoCaminos.AgregarVertice(v);
                finalVertices.sacar(v);
            }

            for (int v = 0; v < 100; v++) {
                if (v != origen && dist[v] != Integer.MAX_VALUE) {
                    grafoCostos.AgregarArista(origen, v, dist[v]);

                    int actual = v;
                    int previo = anterior[actual];
                    while (previo != -1) {
                        grafoCaminos.AgregarArista(previo, actual, grafo.PesoArista(previo, actual));
                        actual = previo;
                        previo = anterior[actual];
                    }
                }
            }

            return new GrafoTDA[]{grafoCostos, grafoCaminos};
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

        for (int i = 1; i <= 6; i++) {
            grafo.AgregarVertice(i);
        }

        grafo.AgregarArista(1, 2, 2);
        grafo.AgregarArista(1, 3, 1);
        grafo.AgregarArista(3, 5, 2);
        grafo.AgregarArista(5, 4, 2);
        grafo.AgregarArista(3, 6, 3);

        GrafoTDA[] resultado = dijkstra(grafo, 1);

        System.out.println("Grafo de Costos Mínimos:");
        mostrarGrafo(resultado[0]);

        System.out.println("\nGrafo de Caminos Mínimos:");
        mostrarGrafo(resultado[1]);

    }

}
