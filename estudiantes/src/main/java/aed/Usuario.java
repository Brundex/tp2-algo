package aed;

import aed.utils.estructuras.MaxHeap;

public class Usuario implements Comparable<Usuario> {
    private int id;
    private int monto;
    private MaxHeap<Usuario>.Handle handle;


    public Usuario(int id, int monto) {
        this.id = id;
        this.monto = monto;
    }

    @Override
    public int compareTo(Usuario otro) {
        if (this.monto != otro.monto) {
            return this.monto - otro.monto; 
        } else {
            return otro.id - this.id;
        }
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true; 
        if (otro == null || getClass() != otro.getClass()) return false; 
        Usuario otroUsuario = (Usuario) otro; 
        return this.id == otroUsuario.id &&
            this.monto == otroUsuario.monto;
    }  

    public int monto() {
        return monto;
    }

    public int id() {
        return id;
    }

    public void crearHandle(MaxHeap<Usuario>.Handle h) {
        handle = h;
    }

    public void sumarSaldo(int x) {
        monto += x;
        handle.subirPrioridad();
    }

    public void restarSaldo(int x) {
        monto -= x;
        handle.bajarPrioridad();
    }
}