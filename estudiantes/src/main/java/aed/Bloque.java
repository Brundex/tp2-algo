package aed;

import aed.utils.estructuras.Iterador;
import aed.utils.estructuras.ListaEnlazada;
import aed.utils.estructuras.MaxHeap;
import aed.utils.estructuras.ListaEnlazada.Nodo;

public class Bloque {
  private ListaEnlazada<Transaccion> transaccionesLE;
  private MaxHeap<ListaEnlazada<Transaccion>.Handle> transaccionesHeap;
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
    transaccionesHeap = new MaxHeap<ListaEnlazada<Transaccion>.Handle>(transaccionesLE.obtenerHandles());
  }

  public Transaccion txMayorValor() {
    ListaEnlazada<Transaccion>.Handle handleTransaccion = transaccionesHeap.maximo();
    return handleTransaccion.nodo.valor;
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
    ListaEnlazada<Transaccion>.Handle mayor = transaccionesHeap.maximo();
    Nodo mayorEnLE = mayor.nodo;
    if (mayorEnLE.ant != null)
      mayorEnLE.ant.sig = mayorEnLE.sig;
    if (mayorEnLE.sig != null)
      mayorEnLE.sig.ant = mayorEnLE.ant;
    transaccionesHeap.sacarMaximo();
  }
}
