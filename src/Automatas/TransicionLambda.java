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
public class TransicionLambda {

    private String inicio;
    private HashSet<String> destinos = new HashSet();

    public TransicionLambda(String inicio, HashSet<String> destinos) {
        this.inicio = inicio;
        this.destinos = destinos;
    }

    public TransicionLambda(String inicio, String destino) {
        this.inicio = inicio;
        this.destinos.add(destino);
    }
    
    public String getInicio() {
        return this.inicio;
    }

    public HashSet<String> getDestinos() {
        return this.destinos;
    }

    @Override
    public String toString() {
        String res = "";
        res += this.inicio;

        for (String s : this.destinos) {
            res += " "+s;
        }

        return res;

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TransicionLambda ts = (TransicionLambda) o;
        if (!this.inicio.equals(ts.inicio)) {
            return false;
        }

        if (!this.destinos.equals(ts.destinos)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.inicio);
        hash = 59 * hash + Objects.hashCode(this.destinos);
        return hash;
    }

}
