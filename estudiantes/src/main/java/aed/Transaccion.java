package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int idComprador;
    private int idVendedor;
    private int monto;

    // O(1)
    public Transaccion(int id, int idComprador, int idVendedor, int monto) {
        this.id = id;
        this.idComprador = idComprador;
        this.idVendedor = idVendedor;
        this.monto = monto;
    }

    // O(1)
    public Transaccion(Transaccion tx) {
        this.id = tx.id;
        this.idComprador = tx.idComprador;
        this.idVendedor = tx.idVendedor;
        this.monto = tx.monto;
    }

    // O(1)
    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto != otro.monto) {
            return this.monto - otro.monto;
        } else {
            return this.id - otro.id;
        }
    }

    // O(1)
    @Override
    public boolean equals(Object otro) {
        if (this == otro)
            return true;
        if (otro == null || this.getClass() != otro.getClass())
            return false;
        Transaccion otraTx = (Transaccion) otro;
        return this.id == otraTx.id &&
                this.idComprador == otraTx.idComprador &&
                this.idVendedor == otraTx.idVendedor &&
                this.monto == otraTx.monto;
    }

    // O(1)
    public int monto() {
        return monto;
    }

    // O(1)
    public int id_comprador() {
        return idComprador;
    }

    // O(1)
    public int id_vendedor() {
        return idVendedor;
    }

    // O(1)
    public int id() {
        return id;
    }
}