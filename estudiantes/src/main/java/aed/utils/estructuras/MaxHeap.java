package aed.utils.estructuras;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<Handle> listaHandles;

    private class Handle {
        public int indice;
        public T valor;

        public Handle(int i, T v) {
            valor = v;
            indice = i;
        }
    }

    // Para debugear
    public String print() {
        String sListaHandles = "";
        for (int i = 0; i < listaHandles.size(); i++) {
            sListaHandles += String.valueOf(listaHandles.get(i).valor) + ", ";
        }
        System.out.println("lista handlers: " + sListaHandles);
        return "lista handlers: " + sListaHandles;
    }

    private Handle obtenerIzq(Handle elem) {
        int indiceIzq = 2 * elem.indice + 1;
        return indiceIzq < listaHandles.size() ? listaHandles.get(indiceIzq) : null;
    }

    private Handle obtenerDer(Handle elem) {
        int indiceDer = 2 * elem.indice + 2;
        return indiceDer < listaHandles.size() ? listaHandles.get(indiceDer) : null;
    }

    private void heapifyDown(Handle elem) {
        Handle mayor = elem;
        Handle izq = obtenerIzq(elem);
        Handle der = obtenerDer(elem);

        if (izq != null && izq.valor.compareTo(mayor.valor) > 0) {
            mayor = izq;
        }

        if (der != null && der.valor.compareTo(mayor.valor) > 0) {
            mayor = der;
        }

        if (mayor != elem) {
            // Intercambiar valores
            T temp = elem.valor;
            elem.valor = mayor.valor;
            mayor.valor = temp;

            // Continuar heapify en el subárbol afectado
            heapifyDown(mayor);
        }
    }

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
        for (int i = elems.length / 2 - 1; i >= 0; i--) {
            heapifyDown(listaHandles.get(i));
        }
    }

    public T maximo() {
        return listaHandles.get(0).valor;
    }

    public int longitud() {
        return listaHandles.size();
    }

    public void agregar(T elem) {
        Handle nuevoHandle = new Handle(listaHandles.size(), elem);
        listaHandles.add(nuevoHandle);

        heapifyUp(nuevoHandle);
    }

    private void heapifyUp(Handle elem) {
        int indice = elem.indice;
        while (indice > 0) {
            int padreIndice = (indice - 1) / 2;
            Handle padre = listaHandles.get(padreIndice);

            if (elem.valor.compareTo(padre.valor) <= 0) {
                break;
            }

            // Intercambiar elementos
            T temp = elem.valor;
            elem.valor = padre.valor;
            padre.valor = temp;

            indice = padreIndice;
            elem = padre;
        }
    }

    public void sacarMaximo() {
        if (listaHandles.isEmpty()) {
            return;
        }

        // Intercambiar el máximo con el último elemento
        Handle ultimo = listaHandles.get(listaHandles.size() - 1);
        listaHandles.set(0, ultimo);

        // Eliminar el último elemento tiene complejidad O(1)
        listaHandles.remove(listaHandles.size() - 1);

        heapifyDown(ultimo);
    }

    public void conjuntoACola(T[] conj) {
        // Limpiar el heap actual
        listaHandles.clear();

        // Construir nuevo heap con los elementos del conjunto
        for (T elem : conj) {
            agregar(elem);
        }
    }
}
