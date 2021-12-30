/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author sebas
 */
public class AFNDTransicion {

    private String inicio;
    private char simbolo;
    private HashSet<String> destinos = new HashSet();

    public AFNDTransicion(String inicio, char simbolo, HashSet<String> destinos) {
        this.inicio = inicio;
        this.simbolo = simbolo;
        this.destinos = destinos;
    }

    public AFNDTransicion(String inicio, char simbolo, String destino) {
        this.inicio = inicio;
        this.simbolo = simbolo;
        this.destinos.add(destino);
    }
    
    public String getInicio() {
        return this.inicio;
    }

    public HashSet<String> getDestinos() {
        return this.destinos;
    }
    
    public String getDestino(){
        if(this.destinos.size()==1){
            for(String s : destinos){
                return s;
            }
        }
        return "";
    }

    public char getSimbolo() {
        return this.simbolo;
    }

    @Override
    public String toString() {
        String res = "";
        res += this.inicio + " " + this.simbolo + " ";
        for (String s : this.destinos) {
            res += s + " ";
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AFNDTransicion afnd = (AFNDTransicion) o;
        if (this.simbolo != afnd.simbolo) {
            return false;
        }
        if (!this.inicio.equals(afnd.inicio)) {
            return false;
        }

        if (!this.destinos.equals(afnd.destinos)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.inicio);
        hash = 29 * hash + this.simbolo;
        hash = 29 * hash + Objects.hashCode(this.destinos);
        return hash;
    }

}
