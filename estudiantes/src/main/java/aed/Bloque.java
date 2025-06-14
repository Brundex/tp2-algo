package aed;

import java.util.ArrayList;

import aed.utils.estructuras.ListaEnlazada;
import aed.utils.estructuras.MaxHeap;
import aed.utils.estructuras.interfaces.Iterador;

public class Bloque {
  private ListaEnlazada<Transaccion> transaccionesLE;
  private MaxHeap<ListaEnlazada<Transaccion>.Handle> transaccionesHeap;
  private int cantidadTransacciones;
  private int sumaTransacciones;

  public Bloque(Transaccion[] transacciones) {
    // Implementar
    cantidadTransacciones = 0;
    sumaTransacciones = 0;

    transaccionesLE = new ListaEnlazada<>();
    if (transacciones.length > 0 && transacciones[0].id_comprador() == 0) {
      sumaTransacciones -= transacciones[0].monto();
      cantidadTransacciones--;
    }

    ArrayList<ListaEnlazada<Transaccion>.Handle> handles = new ArrayList<>();

    for (int i = 0; i < transacciones.length; i++) {
      Transaccion tx = new Transaccion(transacciones[i]);
      ListaEnlazada<Transaccion>.Handle h = transaccionesLE.agregarAtras(tx);
      handles.add(h);
      sumaTransacciones += transacciones[i].monto();
      cantidadTransacciones++;
    }
    
    transaccionesHeap = new MaxHeap<>(handles);
  }

  public Transaccion txMayorValor() {
    ListaEnlazada<Transaccion>.Handle handleTransaccion = transaccionesHeap.maximo();
    return handleTransaccion.obtenerValorNodo();
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
    if (cantidadTransacciones == 0) return 0;
    return sumaTransacciones / cantidadTransacciones;
  }

  // O(log n)
  public Transaccion extraerMayorTransaccion() { //Que devuelva la transacción para revertir los saldos en Berretacoin
    ListaEnlazada<Transaccion>.Handle mayor = transaccionesHeap.maximo();
    mayor.eliminar(mayor.obtenerNodo());
    transaccionesHeap.sacarMaximo();
    if (mayor.obtenerValorNodo().id_comprador() != 0) {
      cantidadTransacciones--;
      sumaTransacciones -= mayor.obtenerValorNodo().monto();
    }
    return mayor.obtenerValorNodo();
  }
}
