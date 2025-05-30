package tp2;

import java.util.*;

public class GrafoDin implements GrafoTDA{
    public Map<Integer, List<Arista>> adyacentes;

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

}
