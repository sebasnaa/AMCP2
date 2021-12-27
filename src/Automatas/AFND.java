/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebas
 */
public class AFND implements Cloneable, Proceso {

    private String estadoInicial = "";
    private HashSet<String> estadosFinales;
    private HashSet<AFNDTransicion> transiciones;
    private HashSet<TransicionLambda> transicionesLambda;

    public AFND() {
        this.estadosFinales = new HashSet();
        this.transiciones = new HashSet();
        this.transicionesLambda = new HashSet();

    }

    public String getEstadoInicial() {
        return this.estadoInicial;
    }

    public HashSet<String> getEstadosFinales() {
        return this.estadosFinales;
    }

    public HashSet<AFNDTransicion> getTransiciones() {
        return this.transiciones;
    }

    public HashSet<TransicionLambda> getTransicionesLambda() {
        return this.transicionesLambda;
    }

    public HashSet<String> getTransicion(String estado, char simbolo) {
        for (AFNDTransicion t : this.transiciones) {
            if (t.getSimbolo() == simbolo && t.getInicio().equals(estado)) {
                return t.getDestinos();
            }
        }
        //Caso donde no se encuetre nada
        return new HashSet();
    }

    /**
     * Retorna el conjunto de estados destinos dado unos estados de inicio
     *
     * @param macroestado
     * @param simbolo
     * @return
     */
    public HashSet<String> getTransicion(HashSet<String> macroestado, char simbolo) {
        HashSet<String> res = new HashSet();
        for (String estado : macroestado) {
            for (String estadoInt : this.getTransicion(estado, simbolo)) {
                res.add(estadoInt);
            }
        }
        return res;
    }

    /**
     * Retorna los estados que se llegan desde el estado lambda
     *
     * @param estado
     * @return
     */
    public HashSet<String> getTransicionLambda(String estado) {
        for (TransicionLambda t : this.transicionesLambda) {
            if (t.getInicio().equals(estado)) {
                return t.getDestinos();
            }
        }
        return new HashSet();
    }

    public void setEstadoInicial(String estado) {
        this.estadoInicial = estado;
    }

    public void setEstadosFinales(HashSet<String> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public void setTransiciones(HashSet<AFNDTransicion> transiciones) {
        this.transiciones = transiciones;
    }

    public void setTransicionesLambda(HashSet<TransicionLambda> transiciones) {
        this.transicionesLambda = transiciones;
    }

    /**
     * @param e1 estado inicio
     * @param simbolo simbolo de entrada
     * @param ef estado destino
     */
    public void agregarTransicion(String e1, char simbolo, HashSet ef) {
        this.transiciones.add(new AFNDTransicion(e1, simbolo, ef));
    }

    public void agregarTransicion(AFNDTransicion t) {
        this.transiciones.add(t);
    }

    /**
     * @param e1 estado incio
     * @param ef estados finales
     */
    public void agregarTransicionLambda(String e1, HashSet ef) {
        this.transicionesLambda.add(new TransicionLambda(e1, ef));
    }

    public void agregarTransicionLambda(TransicionLambda t) {
        this.transicionesLambda.add(t);
    }

    @Override
    public boolean esFinal(String estado) {
        return this.estadosFinales.contains(estado);
    }

    /**
     * Comprueba si alguno de estado del macroestado es final
     *
     * @param macroestado
     * @return
     */
    public boolean esFinal(HashSet<String> macroestado) {
        for (String s : macroestado) {
            if (this.esFinal(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna los estados destino que se alcanzan mediante el estado via
     * transaciones Lambda
     *
     * @param estado
     * @return
     */
    public HashSet<String> clausuraLambda(String estado) {
        HashSet<String> res = new HashSet();
        res.add(estado);

        for (TransicionLambda tl : transicionesLambda) {
            if (tl.getInicio().equals(estado)) {
                tl.getDestinos().forEach((estadoDestino) -> {
                    if (!estadoDestino.equals(tl.getInicio())) {
                        res.addAll(clausuraLambda(estadoDestino));
                    }
                });
            }
        }
        return res;
    }

    /**
     * Retorna los estados destino que se alcanzan mediante los estados via
     * transaciones Lambda
     *
     * @param estados
     * @return
     */
    public HashSet clausuraLambda(HashSet<String> estados) {
        HashSet<String> res = new HashSet();

        for (String s : estados) {
            HashSet<String> tmp = clausuraLambda(s);

            for (String ss : tmp) {
                res.add(ss);
            }

        }

        return res;

    }

    /**
     * recorre los estados segun la cadena si al leer el ultimo llega a un
     * estado final acepta el la cadena
     *
     * @param cadena
     * @return
     * @throws Exception
     */
    @Override
    public boolean reconocer(String cadena) throws Exception {
        //Comprobamos la entrada de cadena y los estados
        if (estadoInicial.equals("")) {
            throw new Exception("ERROR: estado inicial no iniciado");
        }

        if (getEstadosFinales().isEmpty()) {
            throw new Exception("ERROR: no hay estados finales inciados");
        }

        char[] simbolos = cadena.toCharArray();
        HashSet<String> estado = new HashSet();
        //agrego el estado inicial del automata
        estado.add(getEstadoInicial());

        //obtengo las transiciones Lambda
        estado = clausuraLambda(estado);

        for (int j = 0; j < simbolos.length; j++) {

            //compruebo el paso de estado consumiendo un caracter
            estado = getTransicion(estado, simbolos[j]);

            HashSet<String> lambdas = clausuraLambda(estado);

            for (String le : lambdas) {
                estado.add(le);
            }

            if (estado.isEmpty()) {
                throw new Exception("ERROR: no existe transicion posible con el caracter " + simbolos[j]);
            }

        }
        return esFinal(estado);

    }

    /**
     * Borrar las transiciones que contienen el simbolo
     *
     * @param c simbolo
     */
    public void eliminarTransacionSimbolo(char c) {

        for (AFNDTransicion t : this.transiciones) {
            if (t.getSimbolo() == c) {
                this.transiciones.remove(t);
            }
        }

//        transiciones.stream().filter(t -> (t.getSimbolo() == c)).forEachOrdered(t -> {
//            transiciones.remove(t);
//        });
    }

    /**
     * Eliminar las transiciones que usan el estado
     *
     * @param estado estado
     */
    public void eliminarEstado(String estado) {

        //comprobar inicio y los destinos de dicho inicio
        //de AFND y transicion normales y Lambdas
        HashSet<AFNDTransicion> borrarInicios = new HashSet();
        for (AFNDTransicion t : transiciones) {
            if (t.getInicio().equals(estado)) {
                borrarInicios.add(t);
            }
            HashSet<String> borrarDestinos = new HashSet();
            for (String e : t.getDestinos()) {
                if (e.equals(estado)) {
                    borrarDestinos.add(e);
                }
            }
            t.getDestinos().removeAll(borrarDestinos);
        }

        transiciones.removeAll(borrarInicios);

        HashSet<TransicionLambda> borrarInicioL = new HashSet();

        for (TransicionLambda tl : transicionesLambda) {
            if (tl.getInicio().equals(estado)) {
                borrarInicioL.add(tl);
            }
            HashSet<String> borrarDestinosL = new HashSet();

            for (String edl : tl.getDestinos()) {
                if (edl.equals(estado)) {
                    borrarDestinosL.add(edl);
                }
            }
            tl.getDestinos().removeAll(borrarDestinosL);
        }
        transicionesLambda.removeAll(borrarInicioL);
    }
    
    /**
     * Borra la transicino pasada
     * @param t transicion Lambda
     */
    public void eliminarTransicionLambda(TransicionLambda t){
        transicionesLambda.remove(t);
    }
    
    /**
     * Borra la transicion pasada
     * @param t transicion
     */
    public void eliminarTransicion(AFNDTransicion t){
        transiciones.remove(t);
    }

    @Override
    public String toString(){
        String res = "";
        
        res+= "Estados -> ";
        
        HashSet<String> estados = new HashSet();
        
        for(AFNDTransicion s : transiciones){
            estados.add(s.getInicio());
        }
        
        for(String e : estados){
            res+= e + "\n";
        }
        
        res += "\nEstado Inicial -> " + estadoInicial;
        res += "\nEstados Finales -> ";
        for(String e : estadosFinales){
            res += e + " ";
        }
        
        res += "\nTransiciones\n ";
        for(AFNDTransicion t : transiciones){
            res += t + "\n";
        }
        
        
        res += "\nTransiciones Lambda\n ";
        for(TransicionLambda t : transicionesLambda){
            res += t + "\n";
        }
        
        return res;
    }
    
    public Object clone(){
        AFND nuevo = null;
        
        try {
            nuevo = (AFND) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(ex.getMessage());
        }
        
        nuevo.transiciones = new HashSet();
        nuevo.setTransiciones(this.transiciones);
        
        nuevo.transicionesLambda = new HashSet();
        nuevo.setTransicionesLambda(this.transicionesLambda);
        
        nuevo.estadosFinales = new HashSet();
        nuevo.setEstadosFinales(this.estadosFinales);
        
        return nuevo;
    }
    
}
