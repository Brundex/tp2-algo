package aed;

import aed.utils.estructuras.MaxHeap;

public class GestionUsuario {
    private MaxHeap<Usuario>.Handle handle;
    private Usuario usuario;

    public GestionUsuario(int i, int m) {
        usuario = new Usuario(i, m);
    }

    public class Usuario implements Comparable<Usuario>{
        private int id;
        private int monto;
        
        public Usuario(int i, int m) {
            id = i;
            monto = m;
            }

            @Override
            public int compareTo(Usuario otro) {
                if (this.monto != otro.monto) {
                    return this.monto - otro.monto; 
                } else {
                    return otro.id - this.id;
                }
            }

            @Override
            public boolean equals(Object otro) {
                if (this == otro) return true; 
                if (otro == null || getClass() != otro.getClass()) return false; 
                Usuario otroUsuario = (Usuario) otro; 
                return this.id == otroUsuario.id &&
                    this.monto == otroUsuario.monto;
            }

            public Usuario usuario() {
                return usuario;
            }

            public int monto() {
                return monto;
            }

            public int id() {
                return id;
            }

            public void sumarMonto(int x) {
                monto += x;
            }
            public void bajarMonto(int x) {
                monto -= x;
            }
        }

        public void crearHandle(MaxHeap<Usuario>.Handle h) {
            handle = h;
        }
    
        public void sumarSaldo(int x) {
            usuario.sumarMonto(x);
            handle.subirPrioridad();
        }
    
        public void restarSaldo(int x) {
            usuario.bajarMonto(x);
            handle.bajarPrioridad();
        }
        
        public Usuario usuario() {
            return usuario;
        }
        public int monto() {
            return usuario.monto();
        }

        public int id() {
            return usuario.id();
        }
        
    }
    

    