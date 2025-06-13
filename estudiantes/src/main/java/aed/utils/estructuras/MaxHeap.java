package aed.utils.estructuras;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements ColaPrioridad<T> {
    private ArrayList<Nodo> heap;

    private class Nodo {
        T valor;
        int indice;
    
        Nodo(T valor, int indice) {
            this.valor = valor;
            this.indice = indice;
        }
    }

    public class Handle {
        private Nodo nodo;

        public T obtener() {
            return nodo.valor;
        }

        public void subirPrioridad() {
            heapifyUp(nodo.indice);
        }
        public void bajarPrioridad() {
            heapifyDown(nodo.indice);
        }
    }

    // Constructores
    public MaxHeap() {
        heap = new ArrayList<>();
    }
    
    public MaxHeap(T[] elems) {
        heap = new ArrayList<>();
    
        // Primero agregamos todos los elementos
        for (int i = 0; i < elems.length; i++) {
            Nodo nuevoNodo = new Nodo(elems[i], i);
            heap.add(nuevoNodo);
        }
    
        // Heapify desde la mitad hacia arriba
        for (int i = (elems.length / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    public MaxHeap(ArrayList<T> elems) {
        heap = new ArrayList<>();
    
        // Primero agregamos todos los elementos
        for (int i = 0; i < elems.size(); i++) {
            Nodo nuevoNodo = new Nodo(elems.get(i), i);
            heap.add(nuevoNodo);
        }
    
        // Heapify desde la mitad hacia arriba
        for (int i = (elems.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // O(1)
    public T maximo() { 
        if (heap.isEmpty()) return null;
        return heap.get(0).valor;
    }

    // O(log n)
    public void agregar(T elem) {
        Nodo nuevoNodo = new Nodo(elem, heap.size());
        heap.add(nuevoNodo);

        int ultimoIndice = heap.size() - 1;
        heapifyUp(ultimoIndice);
    }

    // O(log n)
    public void sacarMaximo() {
        if (heap.isEmpty()) {
            return;
        }

        // Intercambiar el máximo con el último elemento
        Nodo ultimo = heap.get(heap.size() - 1);
        heap.set(0, ultimo);
        heap.remove(heap.size() - 1); // Eliminar el último elemento tiene complejidad O(1)
      
        heapifyDown(0);
    }

    // O(1)
    public int longitud() {
        return heap.size();
    }

    // Métodos auxiliares
    private void heapifyDown(int i) {
        int indice = i;
        int maxIndice = i;
        int hijoIzq = obtenerIzq(i);
        int hijoDer = obtenerDer(i);

        // Chequea si alguno de los hijos es mayor
        if (hijoIzq < heap.size() && heap.get(hijoIzq).valor.compareTo(heap.get(maxIndice).valor) > 0) {
                maxIndice = obtenerIzq(indice);
            }

        if (hijoDer < heap.size() && heap.get(hijoDer).valor.compareTo(heap.get(maxIndice).valor) > 0) {
                maxIndice = obtenerDer(indice);
            }
        
        // Reemplaza por hijo mayor
        if (maxIndice != indice) {
            Nodo temp = heap.get(indice);
            heap.set(indice, heap.get(maxIndice));
            heap.set(maxIndice, temp);
            heap.get(indice).indice = maxIndice;
            heap.get(maxIndice).indice = indice;

            // Continua en el subárbol afectado
            heapifyDown(maxIndice);
        }
    }

    private void heapifyUp(int i) {
        int indice = i;
        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            Nodo actual = heap.get(indice);
            Nodo padre = heap.get(padreIndice);

            if (actual.valor.compareTo(padre.valor) <= 0) {
                break;
            }

            // Intercambiar elementos
            Nodo temp = heap.get(indice);
            heap.set(indice, heap.get(padreIndice));
            heap.set(padreIndice, temp);
            heap.get(indice).indice = padreIndice;
            heap.get(padreIndice).indice = indice;

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
}
