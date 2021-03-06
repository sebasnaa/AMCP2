/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficheros;

import Automatas.AFD;
import Automatas.AFDTransicion;
import Automatas.AFND;
import Automatas.TransicionLambda;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        ArrayList<Character> simbolosUsados = new ArrayList<>();
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

                    if (!simbolosUsados.contains(simbolo)) {
                        simbolosUsados.add(simbolo);
                    }

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

        //comprobar si todos los estados tiene transicion destino
        HashSet<AFDTransicion> trasicionesExistentes = res.getTransiciones();
        int numeroTransicionesMinimas = simbolosUsados.size();
        HashSet<String> estadosPermitodosMuerto = new HashSet(estadosPermitidos);
        estadosPermitodosMuerto.remove("-");
        for (String estado : estadosPermitidos) {
            int contador = 0;
            
            for(AFDTransicion t : res.getTransiciones()){
                if(t.getEstadoOrigen().equals(estado)){
                    contador++;
                }
            }
            
            if(contador == numeroTransicionesMinimas){
                System.out.println("Nodo completo "+ estado);
            }else{
                System.out.println("Necesita estado muerto " + estado);
                //Compruebo que simbolos no utiliza, si no exsite devuelve el String "null"
                for(Character sim : simbolosUsados){
                    if(res.getTransicion(estado, sim).equals("null")){
                        res.agregarTransicion(estado, sim, "-");
                    }
                }
            }

        }
        
        

        return res;
    }

    public static AFND lecturaAFND(String archivo) throws FileNotFoundException, IOException, Exception {

        AFND res = new AFND();
        HashSet<String> estadosPermitidos = new HashSet();
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        boolean encontrado = false;
        boolean encontradoLambda = false;

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
                    HashSet<String> estadosFinales = new HashSet();
                    String estadosF = partes[1].trim();
                    estadosFinales.add(estadosF);
                    res.setEstadosFinales(estadosFinales);
                }

                if (linea.contains("TRANSICIONES_L:")) {
                    encontrado = false;
                }

                //Encuentra las transiciones
                if (encontrado) {
                    String partes[] = linea.split(" ");
                    char simbolo = partes[2].charAt(1);
                    String estado1 = partes[1].trim();
                    String estado2 = partes[3].trim();
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

                if (encontradoLambda) {
                    String partes[] = linea.split(" ");
                    String estado1 = partes[1].trim();
                    String estado2 = partes[2].trim();
                    if (estadosPermitidos.contains(estado1) && estadosPermitidos.contains(estado2)) {

                        res.agregarTransicionLambda(new TransicionLambda(estado1, estado2));
                    } else {
                        throw new Exception("Estados desconocidos en las transaciones");
                    }
                } else {
                    if (linea.equals("TRANSICIONES_L:")) {
                        encontradoLambda = true;
                    }
                }

            }

        }

        return res;
    }

}
