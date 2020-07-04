package shortestjobfirst;

public class ColaListos {

    Nodo inicio;
    Nodo fin;

    public ColaListos() {
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

    public void eliminarNodo() {
        inicio = inicio.siguiente;
        inicio.anterior=null;
    }
    
    public void setCalculado(int proceso){
        Nodo recorrido=inicio;
        while(recorrido!=null){
            if(recorrido.proceso==proceso){
                recorrido.calculado=true;
            }
            recorrido=recorrido.siguiente;
        }
    }
    
/*
    public void eliminarNodo(int proceso) {
        Nodo recorrido = inicio;
        while (recorrido!= null) {

            if (proceso == recorrido.proceso) {

                if (proceso == 1) {
                    eliminarNodo();
                    break;
                }else if(inicio==fin){
                    inicio=null;
                    fin=null;
                    break;
                } 
                else {
                    Nodo aux=inicio;
                    
                    inicio=recorrido;
                    inicio.anterior.siguiente=inicio.siguiente;
                    inicio.siguiente.anterior=inicio.anterior;
                    inicio.anterior=null;
                    inicio.siguiente=null;
                    
                    inicio=aux;
                    
                    
                    
                    //recorrido.anterior.siguiente = recorrido.siguiente;

                    break;
                }
            }

            recorrido = recorrido.siguiente;
        }
        
    }
*/
    public void mostrarLista() {
        Nodo recorrido = inicio;
        while (recorrido != null) {
            System.out.println(recorrido.proceso + " : " + recorrido.rafaga);
            recorrido = recorrido.siguiente;
        }
    }
}
