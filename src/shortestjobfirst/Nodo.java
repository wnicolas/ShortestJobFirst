package shortestjobfirst;

public class Nodo {

    Nodo siguiente;
    Nodo anterior;
    static int turn = 0;
    int proceso = 0;
    int tLlegada;
    int rafaga;
    int tComienzo;
    int tFinal;
    int tRetorno;
    int tEspera;
    boolean calculado=false;
    
    boolean bloqueado=false;
    int tRestante;

    public Nodo(int rafaga,int tLlegada) {
        turn += 1;
        proceso = turn;
        this.rafaga = rafaga;
        this.tLlegada=tLlegada;
        siguiente = null;
        anterior=null;

    }
    
    public int calcularFinal(){
        return tComienzo+rafaga;
    }
    public int calcularRetorno(){
        return tFinal-tLlegada;
    }
    public int calcularEspera(){
        return tRetorno-rafaga;
    }
}
