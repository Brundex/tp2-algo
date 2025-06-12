package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int idComprador;
    private int idVendedor;
    private int monto;

    public Transaccion(int id, int idComprador, int idVendedor, int monto) {
        this.id = id;
        this.idComprador = idComprador;
        this.idVendedor = idVendedor;
        this.monto = monto;
    }

    public Transaccion(Transaccion tx) {
        this.id = tx.id;
        this.idComprador = tx.idComprador;
        this.idVendedor = tx.idVendedor;
        this.monto = tx.monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto != otro.monto) {
            return this.monto - otro.monto;
        } else {
            return this.id - otro.id;
        }
    }

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

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return idComprador;
    }

    public int id_vendedor() {
        return idVendedor;
    }

    public int id() {
        return id;
    }
}