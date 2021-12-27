/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatas;

/**
 *
 * @author sebas
 */
public interface Proceso {
    public abstract boolean esFinal(String estado); //Si un estado es final
    public abstract boolean reconocer(String cadena) throws Exception;
    public abstract String toString();
}
