/* TODO:
 * Asegurarse de que sea correcto el indice de usuarios. No encontré otra forma de hacerlo
 * Limpiar comentarios
 * Dejar código limpio y asegurarse que los nombres sean consistentes
 * Justificar complejidades
 * Escribir más tests si hace falta. Los de ListaEnlazada los dejamos así? Casi que ni usamos algunas cosas.
 * Limpiar ListaEnlazada (tiene cosas que no hacen falta)
 * Corroborar que se sigan los principios de abstraccion y encapsulamiento. ListaEnlazada quedó rara
 * Hay que validar los saldos en las transacciones del bloque? Si es así agregar un test 
 */
package aed;

import java.util.ArrayList;

import aed.utils.estructuras.MaxHeap;

public class Berretacoin {
    private ArrayList<Bloque> bloques;
    private MaxHeap<Usuario> usuariosHeap;
    private ArrayList<Usuario> indiceUsuarios;

    // O(P)
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

    // O(nb * log P)
    public void agregarBloque(Transaccion[] transacciones) {
        bloques.add(new Bloque(transacciones));

        for (int i = 0; i < transacciones.length; i++) { // O(nb)
            int monto = transacciones[i].monto();
            int indiceComprador = transacciones[i].id_comprador() - 1;
            int indiceVendedor = transacciones[i].id_vendedor() - 1;

            if (transacciones[i].id_comprador() == 0) {
                indiceUsuarios.get(indiceVendedor).sumarSaldo(monto); // O(log P)

            }
            else {
                indiceUsuarios.get(indiceVendedor).sumarSaldo(monto); // O(log P) 
                indiceUsuarios.get(indiceComprador).restarSaldo(monto); // O(log P)
            }
        }
    }
    /*  Justificación de la complejidad: el for itera sobre cada transacción del bloque (nb). 
    Dentro de cada iteración y evaluando condiciones,  ya sea se cumpla una o la otra, se usa sumar/restarSaldo: 
    que actualizan los montos de los usuarios y hacen un subir/bajarPrioridad dependiendo de cada una. Dentro del handle, 
    subir/bajarPrioridad hace un heapifyUp/Down, que tiene O(log n) (en este caso O(log P)).
    */

    // O(1)
    public Transaccion txMayorValorUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.txMayorValor();
    }
    /*  Una vez que tenemos al último bloque (accediendo con el get en O(1), utilizamos la función de la clase Bloque 
    txMayorValor, que tiene complejidad O(1), trayendo el Handle del nodo máximo del MaxHeap, y con obtenerValorNodo 
    de la clase Handle podemos traer ese valor en O(1) (la tx).
    ) */

    // O(nb)
    public Transaccion[] txUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.transacciones();
    }
    /*  Traemos el último bloque en O(1) y luego usamos el método transacciones de la clase Bloque, para iterar sobre
     el mismo y traer transacción por transacción para agregar cada una en la secuencia que vamos a devolver como res.
     */

    // O(1)
    public int maximoTenedor(){
        return usuariosHeap.maximo().id();
    }
    /*  maximoTenedor es el máximo de maxHeap usuariosHeap, se obtiene en O(1). */

    // O(1)
    public int montoMedioUltimoBloque(){
        Bloque ultimoBloque = bloques.get(bloques.size() -1);
        return ultimoBloque.montoMedio();
    }
    /*  montoMedio es un método de la clase Bloque, que toma dos observadores y los divide entre sí. La complejidad
    termina siendo siempre O(1). */

    // O(log nb + log P)
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
    /*  El O(nb) se obtiene en el momento de utilizar el método extraerMayorTransacción del último bloque: utiliza
    la función heapifyDown que es O(log nb). Luego de esto, se actualizan los montos de los vendedores usando los métodos
    restar/sumarSaldo, y luego se reordenan en el heap a razón de O(log P)
     */
}

