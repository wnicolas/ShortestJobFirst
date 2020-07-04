package shortestjobfirst;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.ui.RefineryUtilities;

public class Vista extends JFrame implements ActionListener, Runnable {

    int tiempo = 0;
    int procesoAnterior;

    ColaListos listos = new ColaListos();
    ColaBloqueo bloqueo = new ColaBloqueo();

    JButton btnIniciar;
    JButton btnAñadir;
    JButton btnBloquear;
    JButton btnGraficar;
    JTextField txtProcesosInicio;
    JPanel panelSuperior;
    JPanel panelCentral = new JPanel(null);
    JPanel panelInferior = new JPanel(null);

    DefaultTableModel modeloC;
    DefaultTableModel modeloSC;
    DefaultTableModel modeloB;

    JScrollPane scrollC;
    JScrollPane scrollSC;
    JScrollPane scrollB;

    Thread hilo;

    public Vista() {

        setTitle("Shortest Job First");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(50, 100, 1215, 450);
        setResizable(false);
        setLayout(new BorderLayout());

        //PANEL SUPERIOR
        panelSuperior = new JPanel();
        add(panelSuperior, BorderLayout.NORTH);
        txtProcesosInicio = new JTextField("Procesos");
        btnAñadir=new JButton("Añadir proceso");
        btnAñadir.addActionListener(this);
        btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(this);
        btnBloquear = new JButton("Bloquear");
        btnBloquear.addActionListener(this);
        panelSuperior.add(txtProcesosInicio);
        panelSuperior.add(btnAñadir);
        panelSuperior.add(btnIniciar);
        panelSuperior.add(btnBloquear);

        //PANEL CENTRAL
        add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(null);

        modeloC = new DefaultTableModel();
        modeloC.addColumn("Proceso");
        modeloC.addColumn("T. llegada");
        modeloC.addColumn("Ráfaga");
        modeloC.addColumn("T. comienzo");
        modeloC.addColumn("T. final");
        modeloC.addColumn("T. Retorno");
        modeloC.addColumn("T. Espera");

        modeloSC = new DefaultTableModel();
        modeloSC.addColumn("Proceso");
        modeloSC.addColumn("Ráfaga");
        modeloSC.addColumn("T. restante S.C");

        modeloB = new DefaultTableModel();
        modeloB.addColumn("Proceso");
        modeloB.addColumn("Ráfaga");
        modeloB.addColumn("T. restante C.B");

        JTable tablaC = new JTable(modeloC);
        JTable tablaL = new JTable(modeloSC);
        JTable tablaB = new JTable(modeloB);

        scrollC = new JScrollPane(tablaC);
        scrollSC = new JScrollPane(tablaL);
        scrollB = new JScrollPane(tablaB);

        panelCentral.add(scrollC);
        scrollC.setBounds(10, 10, 600, 300);
        panelCentral.add(scrollSC);
        scrollSC.setBounds(625, 10, 275, 39);
        panelCentral.add(scrollB);
        scrollB.setBounds(925, 10, 275, 300);

        //PANEL INFERIOR
        add(panelInferior, BorderLayout.SOUTH);

        hilo = new Thread(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAñadir) {

            int rafaga = Integer.parseInt(txtProcesosInicio.getText());
            Nodo nuevo = new Nodo(rafaga, tiempo);
            listos.insertarNodo(nuevo);
            Object p[] = {nuevo.proceso, nuevo.tLlegada, nuevo.rafaga};
            modeloC.addRow(p);

        } else if (e.getSource() == btnIniciar) {
            hilo.start();
            entraSC();
        }
         else if (e.getSource() == btnBloquear) {
            entraSC();
        }

    }

    public void entraSC() {
        Nodo recorrido = listos.inicio;

        int menor = 1000000;
        int referenciaNodo = 0;

        while (recorrido != null) {
            if (recorrido.rafaga < menor && recorrido.calculado == false) {
                menor = recorrido.rafaga;
                referenciaNodo = recorrido.proceso;
            }
            recorrido = recorrido.siguiente;

        }
        System.out.println("Este es el menor: " + menor + "//Este es el proceso: " + referenciaNodo);

        recorrido = listos.inicio;
        while (recorrido != null) {
            if (recorrido.proceso == referenciaNodo) {
                Object p[] = {recorrido.proceso, recorrido.rafaga, recorrido.rafaga};
                modeloSC.addRow(p);
            }
            recorrido = recorrido.siguiente;

        }

    }

    public void actualizarSC() {

        Object p[] = {modeloSC.getValueAt(0, 0), modeloSC.getValueAt(0, 1), (int) modeloSC.getValueAt(0, 2) - 1};
        modeloSC.addRow(p);
        modeloSC.removeRow(0);

    }

    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(500);
                tiempo += 1;

                if ((int) (modeloSC.getValueAt(0, 2)) == 0) {

                    int procesoActual = (int) modeloSC.getValueAt(0, 0) - 1;

                    listos.setCalculado((int) modeloSC.getValueAt(0, 0));

                    if (tiempo - 1 == (int) modeloSC.getValueAt(0, 1)) {
                        int proceso = (int) modeloC.getValueAt(procesoActual, 0);
                        int tLlegada = (int) modeloC.getValueAt(procesoActual, 1);
                        int tFinal = (int) modeloC.getValueAt(procesoActual, 2);
                        int retorno = tFinal - (int) modeloC.getValueAt(procesoActual, 1);
                        int rafaga = (int) modeloC.getValueAt(procesoActual, 2);
                        int espera = retorno - rafaga;
                        Object p[] = {proceso, tLlegada, rafaga, 0, tFinal, retorno, espera};

                        modeloC.insertRow((int) modeloSC.getValueAt(0, 0) - 1, p);
                        modeloC.removeRow((int) modeloSC.getValueAt(0, 0));
                        entraSC();
                        procesoAnterior = proceso;
                    } else {

                        int proceso = (int) modeloC.getValueAt(procesoActual, 0);
                        int tLlegada = (int) modeloC.getValueAt(procesoActual, 1);
                        int rafaga = (int) modeloC.getValueAt(procesoActual, 2);
                        
                        int tComienzo=(int)modeloC.getValueAt(procesoAnterior-1, 4);
                        int tFinal = (int) modeloC.getValueAt(procesoActual, 2)+tComienzo;
                        int retorno = tFinal - (int) modeloC.getValueAt(procesoActual, 1);
                        int espera = retorno - rafaga;
                        Object p[] = {proceso, tLlegada, rafaga, tComienzo, tFinal, retorno, espera};

                        modeloC.insertRow((int) modeloSC.getValueAt(0, 0) - 1, p);
                        modeloC.removeRow((int) modeloSC.getValueAt(0, 0));
                        
                        entraSC();
                        procesoAnterior = proceso;

                        System.out.println("El anterior proceso es " + procesoAnterior);
                        int nuevoProceso = (int) modeloSC.getValueAt(0, 0);
                        System.out.println("******Calcular " + nuevoProceso);

                    }

                    modeloSC.removeRow(0);
                    System.out.println("Sección crítica terminada");
                    //entraSC();
                } else {
                    actualizarSC();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(tiempo);
        }
    }

}
