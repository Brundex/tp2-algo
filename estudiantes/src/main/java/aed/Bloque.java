package aed;

import aed.utils.estructuras.Iterador;
import aed.utils.estructuras.ListaEnlazada;
import aed.utils.estructuras.MaxHeap;

public class Bloque {
  private ListaEnlazada<Transaccion> transaccionesLE;
  private MaxHeap<Transaccion> transaccionesHeap;
  private int cantidadTransacciones = 0;
  private int sumaTransacciones = 0;

  public Bloque(Transaccion[] transacciones) {
    // Implementar
    Transaccion[] txArray = new Transaccion[transacciones.length];
    for (int i = 0; i < transacciones.length; i++) {
      Transaccion tx = new Transaccion(transacciones[i]);
      transaccionesLE.agregarAtras(tx);
      txArray[i] = tx;
      sumaTransacciones += tx.monto();
    }
    cantidadTransacciones = transacciones.length;
    transaccionesHeap.conjuntoACola(txArray);
  }

  public Transaccion txMayorValor() {
    return transaccionesHeap.maximo();
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
    return sumaTransacciones / cantidadTransacciones;
  }

  // O(log n)
  public void extraerMayorTransaccion() {
    Transaccion mayor = transaccionesHeap.maximo();
    // Borrar transacciones del bloque

    // Ajustar monto de los usuarios
  }
}
