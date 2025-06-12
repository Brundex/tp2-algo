package aed.utils.estructuras;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements ColaPrioridad<T> {
    private ArrayList<Handle> listaHandles;

    private class Handle {
        public int indice;
        public T valor;

        public Handle(int i, T v) {
            valor = v;
            indice = i;
        }
    }

    // Constructores
    public MaxHeap() {
        listaHandles = new ArrayList<Handle>();
    }

    public MaxHeap(T[] elems) {
        listaHandles = new ArrayList<Handle>();

        // Primero agregamos todos los elementos
        for (int i = 0; i < elems.length; i++) {
            T elem = elems[i];
            Handle elemHandle = new Handle(i, elem);
            listaHandles.add(elemHandle);
        }

        // Heapify desde la mitad hacia arriba
        for (int i = (elems.length / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // Métodos de la interfaz

    // O(1)
    public T maximo() { 
        if (listaHandles.isEmpty())
            return null;
        return listaHandles.get(0).valor; // 
    }

    // O(log n)
    public void agregar(T elem) {
        Handle nuevoHandle = new Handle(listaHandles.size(), elem);
        listaHandles.add(nuevoHandle);

        int ultimoIndice = listaHandles.size() - 1;
        heapifyUp(ultimoIndice);
    }

    // O(log n)
    public void sacarMaximo() {
        if (listaHandles.isEmpty()) {
            return;
        }

        // Intercambiar el máximo con el último elemento
        Handle ultimo = listaHandles.get(listaHandles.size() - 1);
        listaHandles.set(0, ultimo);
        listaHandles.remove(listaHandles.size() - 1); // Eliminar el último elemento tiene complejidad O(1)

        heapifyDown(0);
    }

    public int longitud() {
        return listaHandles.size();
    }
    
    // Métodos auxiliares
    private void heapifyDown(int i) {
        int indice = i;
        int maxIndice = i;
        int hijoIzq = obtenerIzq(i);
        int hijoDer = obtenerDer(i);

        // Chequea si alguno de los hijos es mayor
        if (hijoIzq < listaHandles.size() && listaHandles.get(hijoIzq).valor.compareTo(listaHandles.get(maxIndice).valor) > 0) {
                maxIndice = obtenerIzq(indice);
            }

        if (hijoDer < listaHandles.size() && listaHandles.get(hijoDer).valor.compareTo(listaHandles.get(maxIndice).valor) > 0) {
                maxIndice = obtenerDer(indice);
            }
        
        // Reemplaza por hijo mayor
        if (maxIndice != indice) {
            Handle temp = listaHandles.get(indice);
            listaHandles.set(indice, listaHandles.get(maxIndice));
            listaHandles.set(maxIndice, temp);

            // Continua en el subárbol afectado
            heapifyDown(maxIndice);
        }
    }

    private void heapifyUp(int i) {
        int indice = i;
        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            Handle actual = listaHandles.get(indice);
            Handle padre = listaHandles.get(padreIndice);

            if (actual.valor.compareTo(padre.valor) <= 0) {
                break;
            }

            // Intercambiar elementos
            T temp = actual.valor;
            actual.valor = padre.valor;
            padre.valor = temp;

            indice = padreIndice;
        }
    }

    private int obtenerIzq(int i) {
        return 2 * i + 1;
    }

    private int obtenerDer(int i) {
        return 2 * i + 2;
    }

    private int obtenerPadre(int i) {
        return (i - 1) / 2;
    }

    public void conjuntoACola(ArrayList<T> conj) {
        // Limpiar el heap actual
        listaHandles.clear();

        // Construir nuevo heap con los elementos del conjunto
        for (T elem : conj) {
            agregar(elem);
        }
    }
}
