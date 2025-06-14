package aed.utils.estructuras.interfaces;

public interface ColaPrioridad<T> {
    public T maximo();
    public void agregar(T elem);
    public void sacarMaximo();
    public int longitud();
}