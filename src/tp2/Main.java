package tp2;

import java.util.*;

public class Main {

    public static GrafoTDA dijkstra(GrafoDin grafo, int origen, boolean devolverCaminos) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> anterior = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();

        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (int v : grafo.adyacentes.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        dist.put(origen, 0);
        heap.add(new int[]{origen, 0});

        while (!heap.isEmpty()) {
            int[] actual = heap.poll();
            int nodo = actual[0];

            if (visitados.contains(nodo)) continue;
            visitados.add(nodo);

            for (Arista arista : grafo.adyacentes.getOrDefault(nodo, new ArrayList<>())) {
                int vecino = arista.destino;
                int nuevoDist = dist.get(nodo) + arista.peso;

                if (nuevoDist < dist.getOrDefault(vecino, Integer.MAX_VALUE)) {
                    dist.put(vecino, nuevoDist);
                    anterior.put(vecino, nodo);
                    heap.add(new int[]{vecino, nuevoDist});
                }
            }
        }

        GrafoTDA resultado = new GrafoDin();
        resultado.inicializarGrafo();
        for (int v : grafo.adyacentes.keySet()) {
            resultado.agregarVertice(v);
        }

        for (int destino : dist.keySet()) {
            if (destino == origen || dist.get(destino) == Integer.MAX_VALUE) continue;

            if (!devolverCaminos) {
                resultado.agregarArista(origen, destino, dist.get(destino));
            } else {
                List<Integer> camino = new ArrayList<>();
                int actual = destino;
                while (anterior.containsKey(actual)) {
                    camino.add(actual);
                    actual = anterior.get(actual);
                }
                camino.add(origen);
                Collections.reverse(camino);
                for (int i = 0; i < camino.size() - 1; i++) {
                    int desde = camino.get(i);
                    int hasta = camino.get(i + 1);
                    int peso = grafo.pesoArista(desde, hasta);
                    resultado.agregarArista(desde, hasta, peso);
                }
            }
        }

        return resultado;
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

    public static void main(String[] args) {
        GrafoDin grafo = new GrafoDin();
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
        GrafoTDA grafoCostos = dijkstra(grafo,1, false);
        System.out.println("Costos mínimos desde el nodo 1:");
        mostrarGrafo(grafoCostos);

        // Aplicar Dijkstra. Caminos reales
        GrafoTDA grafoCaminos = dijkstra(grafo,1, true);
        System.out.println("\nCaminos mínimos desde el nodo 1:");
        mostrarGrafo(grafoCaminos);
    }

}
