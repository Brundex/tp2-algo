package aed.utils.estructuras;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements ColaPrioridad<T> {
    private ArrayList<Nodo> heap;

    private class Nodo {
        T valor;
        int indice;

        // O(1)
        Nodo(T valor, int indice) {
            this.valor = valor;
            this.indice = indice;
        }

        // O(1)
        private T obtenerValor() {
            return valor;
        }
    }

    public class Handle {
        private Nodo nodo;

        // O(1)
        public Handle(Nodo n) {
            nodo = n;
        }

        // O(1)
        public T obtener() {
            return nodo.obtenerValor();
        }

        // O(log n)
        public void subirPrioridad() {
            heapifyUp(nodo.indice);
        }

        // O(log n)
        public void bajarPrioridad() {
            heapifyDown(nodo.indice);
        }
    }

    // Constructores

    // O(1)
    public MaxHeap() {
        heap = new ArrayList<>();
    }

    // O(n)
    public MaxHeap(T[] elems) {
        heap = new ArrayList<>();

        // Primero agregamos todos los elementos
        // O(n)
        for (int i = 0; i < elems.length; i++) {
            Nodo nuevoNodo = new Nodo(elems[i], i);
            heap.add(nuevoNodo);
        }

        // Heapify desde la mitad hacia arriba
        // O(n) Por el algoritmo de Floyd
        for (int i = (elems.length / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // O(n)
    public MaxHeap(ArrayList<T> elems) {
        heap = new ArrayList<>();

        // Primero agregamos todos los elementos
        // O(n)
        for (int i = 0; i < elems.size(); i++) {
            Nodo nuevoNodo = new Nodo(elems.get(i), i);
            heap.add(nuevoNodo);
        }

        // Heapify desde la mitad hacia arriba
        // Por el algoritmo de Floyd esto es O(n)
        for (int i = (elems.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // O(n)
    public ArrayList<Handle> constructorHandles(ArrayList<T> elems) {
        heap = new ArrayList<>();
        ArrayList<Handle> handles = new ArrayList<>();

        // Primero agregamos todos los elementos
        // O(n)
        for (int i = 0; i < elems.size(); i++) {
            Nodo nuevoNodo = new Nodo(elems.get(i), i);
            heap.add(nuevoNodo);
            Handle h = new Handle(nuevoNodo);
            handles.add(h);
        }

        // Heapify desde la mitad hacia arriba
        // Por el algoritmo de Floyd esto es O(n)
        for (int i = (elems.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }

        return handles;
    }

    // O(1)
    public T maximo() {
        if (heap.isEmpty()) return null;
        return heap.get(0).valor;
    }

    // O(log n)
    public void agregar(T elem) {
        Nodo nuevoNodo = new Nodo(elem, heap.size());
        // O(1)
        heap.add(nuevoNodo);

        int ultimoIndice = heap.size() - 1;
        // O(log n)
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
    // O(log n)
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

        // Reemplaza por hijo mayor (en caso de haberlo)
        if (maxIndice != indice) {
            // O(1)
            swap(indice, maxIndice);

            // Continua recursivamente en el subárbol afectado
            // La cantidad máxima de iteraciones va ser la altura del árbol
            heapifyDown(maxIndice);
        }
    }

    // O(log n)
    private void heapifyUp(int i) {
        int indice = i; // O(1)

        // O(log n)
        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            Nodo actual = heap.get(indice);
            Nodo padre = heap.get(padreIndice);

            // Si el actual es menor al padre corta la heapificación
            if (actual.valor.compareTo(padre.valor) <= 0) {
                break;
            }

            // Intercambiar elementos
            // O(1)
            swap(indice, padreIndice);

            indice = padreIndice;
        }
    }

    // O(1)
    private int obtenerIzq(int i) {
        return 2 * i + 1;
    }

    // O(1)
    private int obtenerDer(int i) {
        return 2 * i + 2;
    }

    // O(1)
    private int obtenerPadre(int i) {
        return (i - 1) / 2;
    }

    // O(1): set y get tienen complejidad O(1)
    private void swap(int i, int j) {
        Nodo temp = heap.get(i);

        heap.set(i, heap.get(j));
        heap.set(j, temp);

        heap.get(i).indice = i;
        heap.get(j).indice = j;
    }
}
