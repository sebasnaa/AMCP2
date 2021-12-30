/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amcp2;

import Automatas.*;
import Ventanas.menu;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class GrafoAutomata {

    private mxGrafoMod mxGrafo = new mxGrafoMod();
    Object parent; // para las referencias
    mxStylesheet hojaEstilo = mxGrafo.getStylesheet();
    ArrayList<String> estados = new ArrayList();
    ArrayList<Object> contenedorEstados = new ArrayList();
    private boolean dibujoCompleto = false;

    public mxGraphComponent pintarAFD(AFD automata, HashSet<String> estadosP) {
        estados.clear();
        contenedorEstados.clear();

        mxGrafo = new mxGrafoMod();

        estados = new ArrayList<>(estadosP);
        boolean estadoMuerto = false;

        estados.remove("-");
        for (String e : estados) {
            if (automata.getEstadoInicial().equals(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoInicial"));
            } else if (automata.getEstadosFinales().contains(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoFinal"));
            } else {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstado"));
            }

        }

        //comprobamos si alguna transicion con destino es estado muerto 
        for (AFDTransicion t : automata.getTransiciones()) {
            if (t.getEstadoDestino().equals("-")) {
                estadoMuerto = true;
            }
        }
        if (estadoMuerto) {
            contenedorEstados.add(mxGrafo.insertVertex(parent, null, "M", 100, 200, 50, 50, "estiloEstadoMuerto"));
            mxGrafo.insertEdge(parent, null, "          0,1 ", contenedorEstados.get(contenedorEstados.size() - 1), contenedorEstados.get(contenedorEstados.size() - 1));

        }

        for (AFDTransicion t : automata.getTransiciones()) {

            if (!t.getEstadoDestino().equals("-")) {
                mxGrafo.insertEdge(parent, null, "     " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getEstadoOrigen())), contenedorEstados.get(estados.indexOf(t.getEstadoDestino())));
            } else if (estadoMuerto) {
                mxGrafo.insertEdge(parent, null, "    " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getEstadoOrigen())), contenedorEstados.get(contenedorEstados.size() - 1));
            }

        }

        //Ajuste de espaciado entre las celulas(nodos) interno y externo
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGrafo);
        layout.setIntraCellSpacing(25.0);
        layout.setInterRankCellSpacing(35.0);
        layout.execute(mxGrafo.getDefaultParent());

        mxGrafo.getModel().endUpdate();

        dibujoCompleto = true;
        return new mxGraphComponent(mxGrafo);
    }

    public mxGraphComponent pintarPasoAFD(AFD automata, HashSet<String> estadosP, String estadoActual) {

        //comprobamos si alguna transicion con destino es estado muerto 
        estados.clear();
        contenedorEstados.clear();

        mxGrafo = new mxGrafoMod();

        estados = new ArrayList<>(estadosP);
        boolean estadoMuerto = false;

        estados.remove("-");
        for (String e : estados) {
            if (automata.getEstadoInicial().equals(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoInicial"));
            } else if (automata.getEstadosFinales().contains(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoFinal"));
            } else {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstado"));
            }

        }

        //comprobamos si alguna transicion con destino es estado muerto 
        for (AFDTransicion t : automata.getTransiciones()) {
            if (t.getEstadoDestino().equals("-")) {
                estadoMuerto = true;
            }
        }
        if (estadoMuerto) {
            contenedorEstados.add(mxGrafo.insertVertex(parent, null, "M", 100, 200, 50, 50, "estiloEstadoMuerto"));
            mxGrafo.insertEdge(parent, null, "          0,1 ", contenedorEstados.get(contenedorEstados.size() - 1), contenedorEstados.get(contenedorEstados.size() - 1));

        }

        //Pasos sobre estados no finales y no muerto
        if (!estadoActual.equals("-") && !estadoActual.equals("estadoMuerto")) {

            try {
                mxCell mxx = (mxCell) contenedorEstados.get(estados.indexOf(estadoActual));
                mxx.setStyle("estiloEstadoActivo");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Caracter no valido en la cadena", "Error en reconocimiento", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            for (Object s : contenedorEstados) {
                mxCell mx = (mxCell) s;
                if (mx.getValue().equals("M")) {
                    mx.setStyle("estiloEstadoActivo");
                }
            }
        }

        for (AFDTransicion t : automata.getTransiciones()) {

            if (!t.getEstadoDestino().equals("-")) {
                mxGrafo.insertEdge(parent, null, "     " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getEstadoOrigen())), contenedorEstados.get(estados.indexOf(t.getEstadoDestino())));
            } else if (estadoMuerto) {
                mxGrafo.insertEdge(parent, null, "    " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getEstadoOrigen())), contenedorEstados.get(contenedorEstados.size() - 1));
            }
        }

        //AJUSTES ESTÉTICOS EN EL GRAFO
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGrafo);
        layout.setIntraCellSpacing(25.0);
        layout.setInterRankCellSpacing(35.0);
        layout.execute(mxGrafo.getDefaultParent());

        mxGrafo.getModel().endUpdate();

        dibujoCompleto = true;
        return new mxGraphComponent(mxGrafo);

    }

    public mxGraphComponent pintarAFND(AFND automata, HashSet<String> estadosP) {
        estados.clear();
        contenedorEstados.clear();

        mxGrafo = new mxGrafoMod();

        estados = new ArrayList<>(estadosP);
        boolean estadoMuerto = false;
        boolean multiplesDestinos = false;

        estados.remove("-");
        for (String e : estados) {
            if (automata.getEstadoInicial().equals(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoInicial"));
            } else if (automata.getEstadosFinales().contains(e)) {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstadoFinal"));
            } else {
                contenedorEstados.add(mxGrafo.insertVertex(parent, null, e, 100, 200, 50, 50, "estiloEstado"));
            }

        }

        //comprobamos si alguna transicion con destino es estado muerto 
        for (AFNDTransicion t : automata.getTransiciones()) {
            if (t.getDestinos().contains("-")) {
                estadoMuerto = true;
                System.out.println("muerto");

            }
        }

        

        if (estadoMuerto) {
            contenedorEstados.add(mxGrafo.insertVertex(parent, null, "M", 100, 200, 50, 50, "estiloEstadoMuerto"));
            mxGrafo.insertEdge(parent, null, "          0,1 ", contenedorEstados.get(contenedorEstados.size() - 1), contenedorEstados.get(contenedorEstados.size() - 1));

        }

        for (AFNDTransicion t : automata.getTransiciones()) {
            if (!t.getDestino().equals("-")) {
                mxGrafo.insertEdge(parent, null, "     " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getInicio())), contenedorEstados.get(estados.indexOf(t.getDestino())));
            } else if (estadoMuerto) {
                mxGrafo.insertEdge(parent, null, "    " + t.getSimbolo(), contenedorEstados.get(estados.indexOf(t.getInicio())), contenedorEstados.get(contenedorEstados.size() - 1));
            }
        }

        //Ajuste de espaciado entre las celulas(nodos) interno y externo
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGrafo);
        layout.setIntraCellSpacing(25.0);
        layout.setInterRankCellSpacing(35.0);
        layout.execute(mxGrafo.getDefaultParent());

        mxGrafo.getModel().endUpdate();

        dibujoCompleto = true;
        return new mxGraphComponent(mxGrafo);
    }

    public boolean isDibujoCompleto() {
        return dibujoCompleto;
    }

}
