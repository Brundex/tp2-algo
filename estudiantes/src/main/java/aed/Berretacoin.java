/* TODO:
 * Asegurarse de que sea correcto el indice de usuarios.
 * Limpiar comentarios
 * Dejar código limpio y asegurarse que los nombres sean consistentes
 * Justificar complejidades
 * Escribir más tests si hace falta. Los de ListaEnlazada los dejamos así? Casi que ni usamos algunas cosas.
 * Limpiar ListaEnlazada (tiene cosas que no hacen falta)
 * Corroborar que se sigan los principios de abstraccion y encapsulamiento.
 * Hay que validar los saldos en las transacciones del bloque? Si es así agregar un test 
 */
package aed;

import java.util.ArrayList;

import aed.utils.estructuras.MaxHeap;

public class Berretacoin {
    private ArrayList<Bloque> bloques;
    private MaxHeap<GestionUsuario.Usuario> usuariosHeap;
    private ArrayList<GestionUsuario> indiceUsuarios;

    public Berretacoin(int n_usuarios){
        bloques = new ArrayList<>();
        indiceUsuarios = new ArrayList<>();
        usuariosHeap = new MaxHeap<>();
        ArrayList<GestionUsuario.Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < n_usuarios; i++) {
            indiceUsuarios.add(new GestionUsuario(i + 1, 0));
            listaUsuarios.add(indiceUsuarios.get(i).usuario()); //indiceUsuarios[0] = (1, 0) indiceUsuarios[1] = (2, 0) indiceUsuarios[2] = (3, 0) 
        }
        ArrayList<MaxHeap<GestionUsuario.Usuario>.Handle> handles = usuariosHeap.constructorHandles(listaUsuarios);

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
