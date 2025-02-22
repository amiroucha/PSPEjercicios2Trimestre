/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example.Tema7;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class MessageDigest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA1");
            String texto = "Texto para el mensaje ejemplo SHA1";
            md.update(texto.getBytes());
            byte[] resumen = md.digest();
            System.out.println(resumen.toString());
            //Para imprimir en Base64, o para guardarlo, es mejor en este formato
            Base64.Encoder encoder = Base64.getEncoder();
            String cadena = encoder.encodeToString(resumen);
            System.out.println("En Base64: "+cadena);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MessageDigest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
