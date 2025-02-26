/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.Tema6.Ejemplos.Log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alumno
 */
public class Prueba {

    private String cadena;
    private static final Logger log = LogManager.getLogger(Prueba.class);

    public Prueba(String cadena) {
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public void getPruebasLog() {
        log.info("un info "+getCadena());
        log.warn("un warning");
        log.error("un error");
    }

}
