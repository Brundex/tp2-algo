package aed;

import java.util.ArrayList;

import aed.utils.estructuras.Iterador;
import aed.utils.estructuras.ListaEnlazada;
import aed.utils.estructuras.MaxHeap;

public class Bloque {
  private ListaEnlazada<Transaccion> transaccionesLE;
  private MaxHeap<ListaEnlazada<Transaccion>.Handle> transaccionesHeap;
  private int cantidadTransacciones = 0;
  private int sumaTransacciones = 0;

  public Bloque(Transaccion[] transacciones) {
    // Implementar
    transaccionesLE = new ListaEnlazada<>(); // Faltaba inicializar LE
    cantidadTransacciones = transacciones.length - 1; // -1 porque no considera la transacción de creación. 

    ArrayList<ListaEnlazada<Transaccion>.Handle> handles = new ArrayList<>();

    for (int i = 0; i < transacciones.length; i++) {
      Transaccion tx = new Transaccion(transacciones[i]);
      ListaEnlazada<Transaccion>.Handle h = transaccionesLE.agregarAtras(tx);
      handles.add(h);
      if (transacciones[i].id_comprador() != 0) {
        sumaTransacciones += tx.monto();
      }
    }
    
    transaccionesHeap = new MaxHeap<>(handles);
  }

  public Transaccion txMayorValor() {
    ListaEnlazada<Transaccion>.Handle handleTransaccion = transaccionesHeap.maximo();
    return handleTransaccion.obtener().valor;
  }

  public Transaccion[] transacciones() {
    Transaccion[] listaTransacciones = new Transaccion[transaccionesLE.longitud()];
    Iterador<Transaccion> iterador = transaccionesLE.iterador();
    int i = 0;
    while (iterador.haySiguiente()) {
      listaTransacciones[i] = new Transaccion(iterador.siguiente());
      i++;
    }
    return listaTransacciones;
  }

  public int montoMedio() {
    if (cantidadTransacciones <= 1) return 0;
    return sumaTransacciones / cantidadTransacciones;
  }

  // O(log n)
  public Transaccion extraerMayorTransaccion() { //Que devuelva la transacción para revertir los saldos en Berretacoin
    ListaEnlazada<Transaccion>.Handle mayor = transaccionesHeap.maximo();
    ListaEnlazada<Transaccion>.Nodo mayorEnLE = mayor.obtener();
    mayor.eliminar(mayorEnLE);
    transaccionesHeap.sacarMaximo();
    return mayorEnLE.valor;
  }
}
