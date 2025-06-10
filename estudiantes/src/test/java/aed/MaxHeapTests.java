package aed;

import aed.utils.estructuras.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MaxHeapTests {
  @Test
  public void obtMaxElemsDesordenados() {
    MaxHeap<Integer> h = new MaxHeap<Integer>();
    h.conjuntoACola(new Integer[] { 123, 1, 999, 888, 5, 2, 3 });
    // assertEquals("h", h.print());
    assertEquals(999, h.maximo());
  }

  @Test
  public void obtMaxMismosMontosTx() {
    ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
    transacciones.add(new Transaccion(0, 1, 0, 1));
    transacciones.add(new Transaccion(1, 2, 1, 1));
    transacciones.add(new Transaccion(2, 3, 2, 1));
    Transaccion max = new Transaccion(3, 1, 3, 1);
    transacciones.add(max);

    MaxHeap<Transaccion> h = new MaxHeap<Transaccion>();
    h.conjuntoACola(transacciones.toArray(new Transaccion[0]));

    assertTrue(max.equals(new Transaccion(3, 1, 3, 1)));
  }
}
