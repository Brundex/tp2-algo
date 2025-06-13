package aed.utils.estructuras;

public class ListaEnlazada<T extends Comparable<T>> {
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    // O(1)
    private class Nodo {
        private T valor;
        private Nodo sig;
        private Nodo ant;

        Nodo(T v) {
            valor = v;
        }
    }


    public class Handle implements Comparable<Handle> {
        private Nodo nodo;

        // O(1)
        public Handle(Nodo nuevoNodo) {
            nodo = nuevoNodo;
        }

        // O(1)
        public Nodo obtenerNodo() {
            return nodo;
        }

        // O(1)
        public T obtenerValorNodo() {
            return nodo.valor;
        }

        // O(1)
        public void eliminar(Nodo n) {
            if (n.ant == null && n.sig == null) {   // O(1)
                primero = null;
                ultimo = null;
            } else {    // O(1)
                if (n == primero) { // Primer elemento pero no único
                    primero = n.sig;
                    primero.ant = null;
                } else if (n == ultimo) { // Es el último elemento
                    ultimo = n.ant;
                    ultimo.sig = null;
                } else {
                    n.ant.sig = n.sig;
                    n.sig.ant = n.ant;
                }
            }
            longitud--;
        }

        // O(1)
        @Override
        public int compareTo(Handle otro) {
            return nodo.valor.compareTo(otro.nodo.valor);
        }
    }

    // Constructores
    // O(1)
    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    // O(n)
    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo actual = lista.primero;
        primero = new Nodo(actual.valor);
        ultimo = primero;
        for (int i = 0; i < lista.longitud() - 1; i++) {
            agregarAtras(actual.sig.valor);
            actual = actual.sig;
        }
        longitud = lista.longitud();
    }

    // O(1)
    public int longitud() {
        return longitud;
    }

    // O(1)
    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.sig = primero;
            nuevo.ant = null;
            primero = nuevo;
        }
        longitud++;
    }

    // O(1)
    public Handle agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (primero == null) {  // O(1)
            primero = nuevo;
            ultimo = nuevo;
        } else {    // O(1)
            nuevo.ant = ultimo;
            nuevo.sig = null;
            ultimo.sig = nuevo;
            ultimo = nuevo;
        }
        longitud++;
        return new Handle(nuevo);
    }

    // O(n)
    public T obtener(int i) {
        Nodo actual = primero;
        if (i == 0) {   // O(1)
            return primero.valor;
        }
        // O(n)
        for (int j = 0; j < i; j++) {
            actual = actual.sig;
        }
        return actual.valor;
    }

    // O(n)
    public void eliminar(int i) {
        Nodo actual = primero;
        if (longitud() == 1) {  // O(1)
            primero = null;
            ultimo = null;
        } else {
            // Primer elemento pero no único
            if (i == 0) {
                actual.sig.ant = null;
                primero = actual.sig;
            }
            // i es el último elemento
            else if (i == longitud() - 1) {
                // O(n)
                for (int j = 0; j < i; j++) {
                    actual = actual.sig;
                }
                actual.ant.sig = null;
            } else {
                // O(n)
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

    // O(n)
    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        // O(1)
        for (int i = 0; i < indice; i++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    // O(n)
    @Override
    public String toString() {
        String texto = "[";
        Nodo actual = primero;
        // O(n)
        for (int i = 0; i < longitud(); i++) {
            if (actual != ultimo) { // O(1)
                texto = texto.concat(String.format("%s, ", obtener(i)));
                actual = actual.sig;
            }
            // El último elemento no lleva coma
            else if (actual == ultimo) { // O(1)
                texto = texto.concat(String.format("%s", obtener(i)));
            }
        }
        texto = texto.concat("]");
        return texto;
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo punteroSig = primero;
        private Nodo punteroAnt = null;
        private Nodo actual = null;

        // O(1)
        public boolean haySiguiente() {
            if (actual == null && punteroSig == null)
                return false;
            return punteroSig != null;
        }

        // O(1)
        public boolean hayAnterior() {
            if (actual == null && punteroAnt == null)
                return false;
            return punteroAnt != null;
        }

        // O(1)
        public T siguiente() {
            T valor = punteroSig.valor;
            actual = punteroSig;
            punteroSig = actual.sig;
            punteroAnt = actual;
            return valor;
        }

        // O(1)
        public T anterior() {
            T valor = punteroAnt.valor;
            actual = punteroAnt;
            punteroAnt = actual.ant;
            punteroSig = actual;
            return valor;
        }
    }

    // O(1)
    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}