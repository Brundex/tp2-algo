package aed.utils.estructuras;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements ColaPrioridad<T> {
    private ArrayList<T> heap;

    // Constructores
    public MaxHeap() {
        heap = new ArrayList<T>();
    }
    
    public MaxHeap(T[] elems) {
        heap = new ArrayList<T>();
    
        // Primero agregamos todos los elementos
        for (int i = 0; i < elems.length; i++) {
            T elem = elems[i];
            heap.add(elem);
        }
    
        // Heapify desde la mitad hacia arriba
        for (int i = (elems.length / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    public MaxHeap(ArrayList<T> elems) {
        heap = new ArrayList<T>();
        // Primero agregamos todos los elementos
        for (int i = 0; i < elems.size(); i++) {
            T elem = elems.get(i);
            heap.add(elem);
        }

        // Heapify desde la mitad hacia arriba
        for (int i = (elems.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    // O(1)
    @Override
    public T maximo() { 
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    // O(log n)
    @Override
    public void agregar(T elem) {
        heap.add(elem);

        int ultimoIndice = heap.size() - 1;
        heapifyUp(ultimoIndice);
    }

    // O(log n)
    @Override
    public void sacarMaximo() {
        if (heap.isEmpty()) {
            return;
        }

        // Intercambiar el máximo con el último elemento
        T ultimo = heap.get(heap.size() - 1);
        heap.set(0, ultimo);
        heap.remove(heap.size() - 1); // Eliminar el último elemento tiene complejidad O(1)
      
        heapifyDown(0);
    }

    // O(1)
    @Override
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
        if (hijoIzq < heap.size() && heap.get(hijoIzq).compareTo(heap.get(maxIndice)) > 0) {
                maxIndice = obtenerIzq(indice);
            }

        if (hijoDer < heap.size() && heap.get(hijoDer).compareTo(heap.get(maxIndice)) > 0) {
                maxIndice = obtenerDer(indice);
            }
        
        // Reemplaza por hijo mayor
        if (maxIndice != indice) {
            T temp = heap.get(indice);
            heap.set(indice, heap.get(maxIndice));
            heap.set(maxIndice, temp);

            // Continua en el subárbol afectado
            heapifyDown(maxIndice);
        }
    }

    private void heapifyUp(int i) {
        int indice = i;
        while (indice > 0) {
            int padreIndice = obtenerPadre(indice);
            T actual = heap.get(indice);
            T padre = heap.get(padreIndice);

            if (actual.compareTo(padre) <= 0) {
                break;
            }

            // Intercambiar elementos
            T temp = heap.get(indice);
            heap.set(indice, heap.get(padreIndice));
            heap.set(padreIndice, temp);

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
