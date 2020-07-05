package shortestjobfirst;

public class ColaBloqueo {

    Nodo inicio;
    Nodo fin;

    public ColaBloqueo() {
        inicio = null;
        fin = null;
    }

    public void insertarNodo(Nodo nuevo) {
        if (inicio == fin && inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        }
    }

    public void bloquear(int proceso) {
        Nodo recorrido = inicio;
        while (recorrido != null) {
            if (recorrido.proceso == proceso) {
                recorrido.bloqueado = true;
                System.out.println("Nodo con proceso: "+recorrido.proceso+" y tiene estado bloqueado: "+recorrido.bloqueado);
            }
            recorrido = recorrido.siguiente;
        }
    }

    public void eliminarNodo() {
        inicio = inicio.siguiente;
        inicio.anterior = null;
    }

    public void mostrarLista() {
        Nodo recorrido = inicio;
        while (recorrido != null) {
            System.out.println(recorrido.rafaga);
            recorrido = recorrido.siguiente;
        }
    }
}
