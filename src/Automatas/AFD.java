/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class AFD implements Cloneable, Proceso {

    private HashSet<String> estadosFinales;
    private String estadoIni = "";
    private HashSet<AFDTransicion> transiciones;
    private HashSet<String> estados;

    public AFD() {
        this.estadosFinales = new HashSet();
        this.transiciones = new HashSet();
        this.estados = new HashSet();

    }

    public void agregarTransicion(String e1, char simbolo, String e2) throws Exception {

        AFDTransicion t = new AFDTransicion(e1, simbolo, e2);

        agregarTransicion(t);
    }

    public void agregarTransicion(int e1, char simbolo, int e2) throws Exception {
        String se1 = "" + e1;
        String se2 = "" + e2;
        agregarTransicion(se1, simbolo, se2);
    }

    public void agregarTransicion(AFDTransicion trans) throws Exception {
        if (existeTransicion(trans)) {
            throw new Exception("La transacion ya existe");
        }

        AFDTransicion antigua = transicionUnica(trans);
        if (antigua != null) {
            //Es decir ya hay alguna Trnasicion con origen y simbolo modificamos el destino
            eliminarTransicion(antigua);
        }

        this.transiciones.add(trans);

    }

    public String getTransicion(String estado, char simbolo) {
        if (estado.equals("-")) {
            return "estadoMuerto";
        }
        for (AFDTransicion t : this.transiciones) {
            if (t.getEstadoOrigen().equals(estado) && t.getSimbolo() == simbolo) {

                return t.getEstadoDestino();
            }
        }

        return "null";
    }

    private boolean existeTransicion(AFDTransicion trans) {
        for (AFDTransicion t : this.getTransiciones()) {
            if (trans.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private AFDTransicion transicionUnica(AFDTransicion trans) {
        AFDTransicion res = null;
        for (AFDTransicion t : this.getTransiciones()) {
            if (trans.getEstadoOrigen().equals(t.getEstadoOrigen()) && (trans.getSimbolo() == t.getSimbolo())) {
                return t;
            }
        }
        return res;
    }

    public HashSet<Character> getSimbolos() {
        HashSet<Character> res = new HashSet();
        for (AFDTransicion t : this.getTransiciones()) {
            res.add(t.getSimbolo());
        }
        return res;
    }

    public String getEstadoInicial() {
        return estadoIni;
    }

    public void setEstadoInicial(String estadoIni) {
        this.estadoIni = estadoIni;
    }

    public void setEstadosFinales(HashSet<String> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadosFinales.add(estadoFinal);
    }

    public HashSet<String> getEstadosFinales() {
        return this.estadosFinales;
    }

    public void setEstados(HashSet<String> estados) {
        this.estados = estados;
    }

    public void setEstado(String estado) {
        this.estados.add(estado);
    }

    public HashSet<String> getEstados() {
        return this.estados;
    }

    /**
     *
     * @return devuelve las transiciones del automata
     */
    public HashSet<AFDTransicion> getTransiciones() {
        return this.transiciones;
    }

    public void agregarEstadoFinal(String estadoFinal) {
        this.estadosFinales.add(estadoFinal);
    }

    public void agregarEstadoFinalUnico(String estadoFinal) {
        this.estadosFinales.clear();
        this.estadosFinales.add(estadoFinal);
    }

    @Override
    public boolean esFinal(String estadoFinal) {
        return this.estadosFinales.contains(estadoFinal);
    }

    /**
     * Elimina las transiciones que tiene el simbolo a
     *
     * @param a simbolo
     */
    public void eliminarSimbolo(char a) {
        for (AFDTransicion t : this.transiciones) {
            if (t.getSimbolo() == a) {
                this.transiciones.remove(t);
            }
        }
    }

    /**
     * Elimina las transiciones que usan el estado
     *
     * @param estado
     */
    public void eliminarEstado(String estado) {
        //Creo nuevo HashSet donde meto las transiciones a borrar
        HashSet<AFDTransicion> borrar = new HashSet();

        for (AFDTransicion t : this.transiciones) {
            if (t.getEstadoOrigen().equals(estado) || t.getEstadoDestino().equals(estado)) {
                borrar.add(t);
            }
        }
        this.transiciones.removeAll(borrar);
    }

    public void eliminarTransicion(AFDTransicion t) {
        this.transiciones.remove(t);
    }

    @Override
    public boolean reconocer(String cadena) throws Exception {
        //Comprobamos la entrada de cadena y los estados
        if (estadoIni.equals("")) {
            throw new Exception("ERROR: estado inicial no iniciado");
        }

        if (getEstadosFinales().isEmpty()) {
            throw new Exception("ERROR: no hay estados finales inciados");
        }

        char[] simbolos = cadena.toCharArray();
        String estado = this.getEstadoInicial();
        //Estado inicial ya esta comprobado
        //Desde estado inicial+1 vamos recorriendo todos los estado 
        for (int i = 0; i < simbolos.length; i++) {
            estado = getTransicion(estado, simbolos[i]);

            if (estado.equals("-")) {
                throw new Exception("Estado Muerto");
            }
        }
        return esFinal(estado);
    }

    @Override
    public String toString() {
        String res = "";
        HashSet<String> estados = new HashSet();

        res += "Estados -> ";

        for (String s : this.estados) {
            if (!s.equals("-")) {
                res += s + " ";
            }
        }

        for (AFDTransicion t : this.transiciones) {
            this.estados.add(t.getEstadoOrigen());
            this.estados.add(t.getEstadoDestino());
        }

        res += "\nEstado Inicial -> " + this.estadoIni + "\n";
        res += "Estados Finales -> ";
        for (String s : estadosFinales) {
            res += s + " ";
        }

        res += "\nTransiciones:\n";
        for (AFDTransicion t : this.transiciones) {
            res += t + "\n";
        }

        return res;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        AFD nuevo = null;

        try {
            nuevo = (AFD) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }

        nuevo.estadosFinales = new HashSet();
        for (String s : estadosFinales) {
            nuevo.estadosFinales.add(s);
        }
        nuevo.transiciones = new HashSet();
        for (AFDTransicion a : transiciones) {
            nuevo.transiciones.add(a);
        }

        return nuevo;

    }

}
