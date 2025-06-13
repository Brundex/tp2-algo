package aed;

import aed.utils.estructuras.MaxHeap;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int monto;
    private MaxHeap<Usuario>.Handle handle;

    // O(1)
    public Usuario(int id, int monto) {
        this.id = id;
        this.monto = monto;
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
        return this.id == otroUsuario.id &&
            this.monto == otroUsuario.monto;
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
    public void crearHandle(MaxHeap<Usuario>.Handle h) {
        handle = h;
    }

    // O(log p)
    public void sumarSaldo(int x) {
        monto += x;
        handle.subirPrioridad();
    }

    // O(log p)
    public void restarSaldo(int x) {
        monto -= x;
        handle.bajarPrioridad();
    }
}