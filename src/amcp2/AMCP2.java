/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amcp2;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.text.html.StyleSheet;
import org.jgrapht.graph.DirectedWeightedPseudograph;

/**
 *
 * @author sebas
 */
public class AMCP2 extends mxGraph {

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Pruebas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        //DirectedWeightedPseudograph DefaultDirectedWeightedGraph
        DirectedWeightedPseudograph<String, MyEdge> g = buildGraph();
        JGraphXAdapter<String, MyEdge> graphAdapter = new JGraphXAdapter<>(g);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);

        layout.execute(graphAdapter.getDefaultParent());

        

        frame.add(new mxGraphComponent(graphAdapter));

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

    public static class MyEdge extends DefaultWeightedEdge {

        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    public static DirectedWeightedPseudograph<String, MyEdge> buildGraph() {
        DirectedWeightedPseudograph<String, MyEdge> g = new DirectedWeightedPseudograph<>(MyEdge.class);

        String q0 = "q0";
        String q1 = "q1";
        String q2 = "q2";

        g.addVertex(q0);
        g.addVertex(q1);
        g.addVertex(q2);
        
        
        

        MyEdge e = g.addEdge(q0, q2);
        g.setEdgeWeight(e, 0);
        e = g.addEdge(q2, q1);
        g.setEdgeWeight(e, 1);

        e = g.addEdge(q1, q1);
        g.setEdgeWeight(e, 0);
        g.setEdgeWeight(e, 1);

        return g;
    }

}
