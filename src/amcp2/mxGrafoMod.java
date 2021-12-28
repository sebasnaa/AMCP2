/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amcp2;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.util.Hashtable;
import java.util.Map;

/**
 * Sobre escribimos las clase mxGraph del view para modificar el aspecto visual
 *
 * @author sebas
 */
public class mxGrafoMod extends mxGraph {

    public mxGrafoMod() {

        super();

        Hashtable<String, Object> estiloEstado = new Hashtable<>();
        estiloEstado.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        estiloEstado.put(mxConstants.STYLE_FONTSIZE, 20);
        estiloEstado.put(mxConstants.STYLE_FILLCOLOR, "FFFFFF");
        estiloEstado.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        Hashtable<String, Object> estiloEstadoFinal = new Hashtable<String, Object>();
        estiloEstadoFinal.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
        estiloEstadoFinal.put(mxConstants.STYLE_FONTSIZE, 20);
        estiloEstadoFinal.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        estiloEstadoFinal.put(mxConstants.STYLE_FONTCOLOR, "#FFFFFF");

        Hashtable<String, Object> estiloEstadoInicial = new Hashtable<String, Object>();
        estiloEstadoInicial.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
        estiloEstadoInicial.put(mxConstants.STYLE_FONTSIZE, 20);
        estiloEstadoInicial.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        Hashtable<String, Object> estiloEstadoMuerto = new Hashtable<String, Object>();
        estiloEstadoMuerto.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        estiloEstadoMuerto.put(mxConstants.STYLE_FONTSIZE, 20);
        estiloEstadoMuerto.put(mxConstants.STYLE_FILLCOLOR, "#FFFB00");
        estiloEstadoMuerto.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        Hashtable<String, Object> estiloEstadoActivo = new Hashtable<>();
        estiloEstadoActivo.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        estiloEstadoActivo.put(mxConstants.STYLE_FONTSIZE, 20);
        estiloEstadoActivo.put(mxConstants.STYLE_FILLCOLOR, "#D081FF");
        estiloEstadoActivo.put(mxConstants.STYLE_FONTCOLOR, "#000000");

        Map<String, Object> edgeStyle = new Hashtable<String, Object>();
        edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.setDefaultEdgeStyle(edgeStyle);

        stylesheet.putCellStyle("estiloEstadoFinal", estiloEstadoFinal);
        stylesheet.putCellStyle("estiloEstado", estiloEstado);
        stylesheet.putCellStyle("estiloEstadoInicial", estiloEstadoInicial);
        stylesheet.putCellStyle("estiloEstadoMuerto", estiloEstadoMuerto);
        stylesheet.putCellStyle("estiloEstadoActivo", estiloEstadoActivo);

        setStylesheet(stylesheet);

        this.getModel().beginUpdate();
        this.setCellsLocked(true);
        this.setVertexLabelsMovable(false);
        this.setEdgeLabelsMovable(false);

    }

}
