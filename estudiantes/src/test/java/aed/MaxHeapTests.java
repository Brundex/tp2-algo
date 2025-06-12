package aed;

import aed.utils.estructuras.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MaxHeapTests {
  @Test
  public void obtMaxElemsDesordenados() {
    MaxHeap<Integer> h = new MaxHeap<Integer>(new Integer[] { 123, 1, 999, 888, 5, 2, 3 });
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

    MaxHeap<Transaccion> h = new MaxHeap<Transaccion>(transacciones.toArray(new Transaccion[0]));

    assertTrue(h.maximo().equals(max));
  }

  @Test
  public void borrarMaxVariasVeces() {
    ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
    transacciones.add(new Transaccion(0, 1, 0, 1));
    transacciones.add(new Transaccion(1, 2, 1, 1));
    transacciones.add(new Transaccion(2, 3, 2, 1));
    Transaccion max = new Transaccion(3, 1, 3, 1);
    transacciones.add(max);

    MaxHeap<Transaccion> h = new MaxHeap<Transaccion>(transacciones.toArray(new Transaccion[0]));

    assertEquals(4, h.longitud());

    h.sacarMaximo();

    assertEquals(3, h.longitud());
    assertTrue(new Transaccion(2, 3, 2, 1).equals(h.maximo()));

    h.sacarMaximo();
    assertTrue(new Transaccion(1, 2, 1, 1).equals(h.maximo()));
    assertEquals(2, h.longitud());

    h.sacarMaximo();
    assertTrue(new Transaccion(0, 1, 0, 1).equals(h.maximo()));
    assertEquals(1, h.longitud());

    h.sacarMaximo();
    assertEquals(null, h.maximo());
    assertEquals(0, h.longitud());
  }

  @Test
  public void borrarVariasVecesMontoDistinto() {
    ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
    transacciones.add(new Transaccion(0, 1, 0, 1));
    transacciones.add(new Transaccion(1, 2, 1, 100));
    transacciones.add(new Transaccion(2, 3, 2, 3));
    Transaccion max = new Transaccion(3, 1, 3, 1);
    transacciones.add(max);

    MaxHeap<Transaccion> h = new MaxHeap<Transaccion>(transacciones.toArray(new Transaccion[0]));

    assertEquals(4, h.longitud());

    h.sacarMaximo();

    assertEquals(3, h.longitud());
    assertTrue(new Transaccion(2, 3, 2, 3).equals(h.maximo()));

    h.sacarMaximo();
    assertTrue(new Transaccion(3, 1, 3, 1).equals(h.maximo()));
    assertEquals(2, h.longitud());

    h.sacarMaximo();
    assertTrue(new Transaccion(0, 1, 0, 1).equals(h.maximo()));
    assertEquals(1, h.longitud());

    h.sacarMaximo();
    assertEquals(null, h.maximo());
    assertEquals(0, h.longitud());
  }

  @Test
  public void obtMaxHeapVacio() {
    MaxHeap<Transaccion> h = new MaxHeap<>();
    assertEquals(null, h.maximo());
  }

  @Test
  public void operarSobreListaOrdenada() {
    MaxHeap<Integer> h = new MaxHeap<Integer>(new Integer[] { 1, 2, 3 });

    assertEquals(3, h.maximo());
    assertEquals(3, h.longitud());

    h.sacarMaximo();
    assertEquals(2, h.maximo());
    assertEquals(2, h.longitud());

    h.sacarMaximo();
    assertEquals(1, h.maximo());
    assertEquals(1, h.longitud());

    h.sacarMaximo();
    assertEquals(null, h.maximo());
    assertEquals(0, h.longitud());
  }
}
