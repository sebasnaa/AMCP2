/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import Automatas.AFD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author sebas
 */
public class FicherosAutomatas {

    public static AFD lecturaAFD(String archivo) throws FileNotFoundException, IOException, Exception {

        AFD res = new AFD();
        HashSet<String> estadosPermitidos = new HashSet();
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        boolean encontrado = false;
        String linea;

        while ((linea = lector.readLine()) != null) {

            if (!linea.equals("FIN") && !linea.equals("")) {
                if (linea.contains("ESTADOS:")) {
                    String partes[] = linea.split(":");

                    String componentes[] = partes[1].trim().split(" ");
                    for (String c : componentes) {
                        estadosPermitidos.add(c.trim());
                    }
                    estadosPermitidos.add("-");
                    res.setEstados(estadosPermitidos);
                }
                if (linea.contains("INICIAL:")) {
                    String partes[] = linea.split(":");
                    String inicial = partes[1].trim();
                    res.setEstadoInicial(inicial);
                }
                if (linea.contains("FINALES:")) {
                    String partes[] = linea.split(":");
                    res.setEstadoFinal(partes[1].trim());
                }

                //Encuentra las transiciones
                if (encontrado) {
                    String partes[] = linea.split(" ");
                    char simbolo = partes[2].charAt(1);
                    String estado1 = partes[1];
                    String estado2 = partes[3];
                    if (estadosPermitidos.contains(estado1) && estadosPermitidos.contains(estado2)) {
                        res.agregarTransicion(estado1, simbolo, estado2);
                    } else {
                        throw new Exception("Estados desconocidos en las transaciones");
                    }
                } else {
                    if (linea.equals("TRANSICIONES:")) {
                        encontrado = true;
                    }
                }

            }

        }

        return res;
    }

}
