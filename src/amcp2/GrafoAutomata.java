/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amcp2;

import Automatas.*;
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

        //AJUSTES ESTÉTICOS EN EL GRAFO
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGrafo);
        layout.setInterRankCellSpacing(50.0);
        layout.setIntraCellSpacing(50.0);
        layout.setDisableEdgeStyle(false);
        layout.execute(mxGrafo.getDefaultParent());

        mxGrafo.getModel().endUpdate();

        dibujoCompleto = true;
        return new mxGraphComponent(mxGrafo);
    }

    public mxGraphComponent pintarPasoAFD(AFD automata, HashSet<String> estadosP, String estadoActual) {

        //        Object cellsActiva[] = null;
//        cellsActiva[0] = contenedorEstados.get(estados.indexOf(estadoActual));
//        mxGrafo.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", cellsActiva);
        //PINTAR EL ESTADO ACTIVO
//        for(Object s : contenedorEstados){
//            mxCell mx = (mxCell) s;
//            System.out.println(mx.getValue());
//        }
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

        try {
            mxCell mxx = (mxCell) contenedorEstados.get(estados.indexOf(estadoActual));
            mxx.setStyle("estiloEstadoActivo");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Caracter no valido en la cadena", "Error en reconocimiento", JOptionPane.WARNING_MESSAGE);
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

        //AJUSTES ESTÉTICOS EN EL GRAFO
        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGrafo);
        layout.setInterRankCellSpacing(50.0);
        layout.setIntraCellSpacing(50.0);
        layout.setDisableEdgeStyle(false);
        layout.execute(mxGrafo.getDefaultParent());

        mxGrafo.getModel().endUpdate();

        dibujoCompleto = true;
        return new mxGraphComponent(mxGrafo);

    }

    public boolean isDibujoCompleto() {
        return dibujoCompleto;
    }
    
    

}
