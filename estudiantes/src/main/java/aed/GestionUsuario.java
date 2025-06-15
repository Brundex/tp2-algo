package aed;

import aed.utils.estructuras.MaxHeap;

public class GestionUsuario {
    private MaxHeap<Usuario>.Handle handle;
    private Usuario usuario;

    // O(1)
    public GestionUsuario(int i, int m) {
        usuario = new Usuario(i, m);
    }

    public class Usuario implements Comparable<Usuario> {
        private int id;
        private int monto;

        // O(1)
        public Usuario(int i, int m) {
            id = i;
            monto = m;
        }

        // O(1)
        @Override
        public int compareTo(Usuario otro) {
            if (this.monto != otro.monto) {
                return this.monto - otro.monto;
            } else {
                return otro.id - this.id;
            }
        }

        // O(1)
        @Override
        public boolean equals(Object otro) {
            if (this == otro) return true;
            if (otro == null || getClass() != otro.getClass()) return false;
            Usuario otroUsuario = (Usuario) otro;
            return this.id == otroUsuario.id && this.monto == otroUsuario.monto;
        }

        // O(1)
        public Usuario usuario() {
            return usuario;
        }

        // O(1)
        public int monto() {
            return monto;
        }

        // O(1)
        public int id() {
            return id;
        }

        // O(1)
        public void sumarMonto(int x) {
            monto += x;
        }

        // O(1)
        public void bajarMonto(int x) {
            monto -= x;
        }
    }

    // O(1)
    public void crearHandle(MaxHeap<Usuario>.Handle h) {
        handle = h;
    }

    // O(log p)
    public void sumarSaldo(int x) {
        usuario.sumarMonto(x);
        handle.subirPrioridad();
    }

    // O(log p)
    public void restarSaldo(int x) {
        usuario.bajarMonto(x);
        handle.bajarPrioridad();
    }

    // O(1)
    public Usuario usuario() {
        return usuario;
    }

    // O(1)
    public int monto() {
        return usuario.monto();
    }

    // O(1)
    public int id() {
        return usuario.id();
    }
}


