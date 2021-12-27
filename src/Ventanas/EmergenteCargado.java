/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class EmergenteCargado {

    private String[] opcionesMuestras;
    private String opcion;

    public EmergenteCargado(menu m) {
        opcionesMuestras = obtenerNombres();
        opcion = (String) JOptionPane.showInputDialog(null, "Elige una muestra", "Muestras",
                JOptionPane.QUESTION_MESSAGE, null, opcionesMuestras, opcionesMuestras[0]);

        if (opcion != null) {
            //m.limpiarPantalla();
        }
    }

    public String getNombreElegido() {
        return opcion;
    }

    private String[] obtenerNombres() {

        File carpeta = new File("./datos/");
        //File carpeta = new File("../datos/");

        File[] listaCarpeta = carpeta.listFiles();
        String res[] = new String[listaCarpeta.length];
        for (int i = 0; i < listaCarpeta.length; i++) {
            if (listaCarpeta[i].isFile()) {
                res[i] = listaCarpeta[i].getName();
            }
        }
        return res;
    }
}
