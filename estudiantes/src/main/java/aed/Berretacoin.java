package aed;

import java.util.ArrayList;

import aed.utils.estructuras.MaxHeap;

public class Berretacoin {
    private ArrayList<Bloque> bloques;
    private MaxHeap<Usuario> usuariosHeap;
    private ArrayList<Usuario> indiceUsuarios;

    public Berretacoin(int n_usuarios){
        bloques = new ArrayList<>();
        indiceUsuarios = new ArrayList<>();
        usuariosHeap = new MaxHeap<>(indiceUsuarios);
        for (int i = 0; i < n_usuarios; i++) {
            indiceUsuarios.add(new Usuario(i + 1, 0)); //indiceUsuarios[0] = (1, 0) indiceUsuarios[1] = (2, 0) indiceUsuarios[2] = (3, 0)
        }

        ArrayList<MaxHeap<Usuario>.Handle> handles = usuariosHeap.constructorHandles(indiceUsuarios);
        for (int i = 0; i < indiceUsuarios.size(); i++) {
            indiceUsuarios.get(i).crearHandle(handles.get(i));
        }
    }

    public void agregarBloque(Transaccion[] transacciones) {
        bloques.add(new Bloque(transacciones));

        for (int i = 0; i < transacciones.length; i++) {
            int monto = transacciones[i].monto();
            int indiceComprador = transacciones[i].id_comprador() - 1;
            int indiceVendedor = transacciones[i].id_vendedor() - 1;

            if (transacciones[i].id_comprador() == 0) {
                indiceUsuarios.get(indiceVendedor).sumarSaldo(monto);

            }
            else {
                indiceUsuarios.get(indiceVendedor).sumarSaldo(monto);
                indiceUsuarios.get(indiceComprador).restarSaldo(monto);
            }
        }
    }

    public Transaccion txMayorValorUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.txMayorValor();
    }

    public Transaccion[] txUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.transacciones();
    }

    public int maximoTenedor(){
        return usuariosHeap.maximo().id();
    }

    public int montoMedioUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.montoMedio();
    }

    public void hackearTx(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        Transaccion extraida = ultimoBloque.extraerMayorTransaccion();

        int monto = extraida.monto();
        int indiceComprador = extraida.id_comprador() - 1;
        int indiceVendedor = extraida.id_vendedor() - 1;

        if (extraida.id_comprador() == 0) {
            indiceUsuarios.get(indiceVendedor).restarSaldo(monto);
        } else {
            indiceUsuarios.get(indiceVendedor).restarSaldo(monto);
            indiceUsuarios.get(indiceComprador).sumarSaldo(monto);
        }
    }
}
