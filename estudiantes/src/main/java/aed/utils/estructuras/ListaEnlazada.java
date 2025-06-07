package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo (T v) {
            valor = v;
        }
    }   

    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.longitud = 0;
    }

    public int longitud() {        
        return this.longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        }
        else {
        nuevo.sig = primero;
        nuevo.ant = null;
        primero = nuevo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        }
        else {
            nuevo.ant = ultimo;
            nuevo.sig = null;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        longitud++;
    }

    public T obtener(int i) {
        Nodo actual = primero;
        if (i == 0) {
            return primero.valor;
        }
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        if (this.longitud() == 1) {
            primero = null;
            ultimo = null;
        }
        else {
            // Primer elemento pero no único
            if (i == 0) {
                actual.sig.ant = null;
                primero = actual.sig;
            }
            // i es el último elemento
            else if (i == this.longitud() - 1) {
                for (int j = 0; j < i; j++) {
                    actual = actual.sig;
                }
                actual.ant.sig = null;
            }
            else {
                for (int j = 0; j < i; j++) {
                    actual.sig.ant = actual;
                    actual = actual.sig;
                }
                actual.ant.sig = actual.sig;
                actual.sig.ant = actual.ant;
            }
        }
        longitud--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo actual = lista.primero;
        this.primero = new Nodo(actual.valor);
        this.ultimo = primero;
        for (int i = 0; i < lista.longitud() - 1; i++) {
            this.agregarAtras(actual.sig.valor);
            actual = actual.sig;
        }
        this.longitud = lista.longitud();
    }
    
    @Override
    public String toString() {
        String texto = "[";
        Nodo actual = primero;
        for (int i = 0; i < longitud(); i++) {
            if (actual != ultimo) {
                texto = texto.concat(String.format("%s, ", this.obtener(i)));
                actual = actual.sig;
            }
            // El último elemento no lleva coma
            else if (actual == ultimo) {
                texto = texto.concat(String.format("%s", this.obtener(i)));
            }
        }
        texto = texto.concat("]");
        return texto;
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo punteroSig = primero;
        private Nodo punteroAnt = null;
        private Nodo actual = null;

        public boolean haySiguiente() {
            if (actual == null && punteroSig == null)
                return false;
            return punteroSig != null;
        }

        public boolean hayAnterior() {
            if (actual == null && punteroAnt == null)
                return false;
            return punteroAnt != null;
        }

        public T siguiente() {
            T valor = punteroSig.valor;
            actual = punteroSig;
            punteroSig = actual.sig;
            punteroAnt = actual;
            return valor;
        }

        public T anterior() {
            T valor = punteroAnt.valor;
            actual = punteroAnt;
            punteroAnt = actual.ant;
            punteroSig = actual;
            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}