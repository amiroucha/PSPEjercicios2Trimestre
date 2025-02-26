/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example.Tema6.Ejemplos.Log;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author alumno
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    private static final Logger log = LogManager.getLogger(NewMain.class);

    public static void main(String[] args) {

        log.trace("mensaje de trace");
        log.debug("mensaje de debug");
        log.info("mensaje de info");
        log.warn("mensaje de warn");
        log.error("mensaje de error");
        log.fatal("mensaje de fatal");
        System.out.println("Un mensaje en el programa");
        
        
        Prueba prueba = new Prueba("hola");
        prueba.getPruebasLog();
    }

}
