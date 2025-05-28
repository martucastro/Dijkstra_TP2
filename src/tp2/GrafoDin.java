package tp2;

import java.util.*;

public class GrafoDin implements GrafoTDA{
    private Map<Integer, List<Arista>> adyacentes;

    public void inicializarGrafo() {
        adyacentes = new HashMap<>();
    }

    public void agregarVertice(int x) {
        adyacentes.putIfAbsent(x, new ArrayList<>());
    }

    public void eliminarVertice(int x) {
        adyacentes.remove(x);
        for (List<Arista> lista : adyacentes.values()) {
            lista.removeIf(arista -> arista.destino == x);
        }
    }

    public void agregarArista(int origen, int destino, int peso) {
        if (!adyacentes.containsKey(origen)) agregarVertice(origen);
        if (!adyacentes.containsKey(destino)) agregarVertice(destino);
        adyacentes.get(origen).add(new Arista(destino, peso));
    }

    public void eliminarArista(int origen, int destino) {
        if (adyacentes.containsKey(origen)) {
            adyacentes.get(origen).removeIf(a -> a.destino == destino);
        }
    }

    public boolean existeArista(int origen, int destino) {
        if (!adyacentes.containsKey(origen)) return false;
        return adyacentes.get(origen).stream().anyMatch(a -> a.destino == destino);
    }

    public int pesoArista(int origen, int destino) {
        if (!adyacentes.containsKey(origen)) return -1;
        for (Arista a : adyacentes.get(origen)) {
            if (a.destino == destino) return a.peso;
        }
        return -1;
    }

    public ConjuntoTDA vertices() {
        ConjuntoTDA c = new Conjunto();
        c.inicializarConjunto();
        for (int v : adyacentes.keySet()) {
            c.agregar(v);
        }
        return c;
    }

    public GrafoTDA dijkstra(int origen, boolean devolverCaminos) {
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> anterior = new HashMap<>();
        Set<Integer> visitados = new HashSet<>();

        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (int v : adyacentes.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }
        dist.put(origen, 0);
        heap.add(new int[]{origen, 0});

        while (!heap.isEmpty()) {
            int[] actual = heap.poll();
            int nodo = actual[0];

            if (visitados.contains(nodo)) continue;
            visitados.add(nodo);

            for (Arista arista : adyacentes.getOrDefault(nodo, new ArrayList<>())) {
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
        for (int v : adyacentes.keySet()) {
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
                    int peso = pesoArista(desde, hasta);
                    resultado.agregarArista(desde, hasta, peso);
                }
            }
        }

        return resultado;
    }
}
