/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

import java.util.Objects;

/**
 *
 * @author sebas
 */
public class AFDTransicion {

    private String estadoOrigen;
    private String estadoDestino;
    private String estadoMuerto;
    private char simbolo;

    /**
     * Constructor de transisicon
     *
     * @param eO Estado de origen
     * @param simbolo Simbolo que entra
     * @param eD Estado de destino
     */
    public AFDTransicion(String eO, char simbolo, String eD) {
        this.simbolo = simbolo;
        this.estadoOrigen = eO;
        this.estadoDestino = eD;
    }

    public String getEstadoOrigen() {
        return estadoOrigen;
    }

    public String getEstadoDestino() {
        return estadoDestino;
    }

    public char getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return (" " + this.estadoOrigen + " " + this.simbolo + " " + this.estadoDestino);
    }

    //Necesario para utilizar equals
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.estadoOrigen);
        hash = 79 * hash + Objects.hashCode(this.estadoDestino);
        hash = 79 * hash + this.simbolo;
        return hash;
    }

    /**
     * Comprueba si el dos AFDTransion son iguales
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        AFDTransicion afdt = (AFDTransicion) o;
        if (this.simbolo != afdt.simbolo) {
            return false;
        }
        if (!this.estadoOrigen.equals(afdt.estadoOrigen)) {
            return false;
        }
        if (!this.estadoDestino.equals(afdt.estadoDestino)) {
            return false;
        }
        return true;

    }

}
