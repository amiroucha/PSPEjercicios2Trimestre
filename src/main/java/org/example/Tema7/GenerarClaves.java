/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.Tema7;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author alumno
 */
public class GenerarClaves {

    //Programa que crea una pareja de claves (pública y privada) y las muestra
    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();
        
        //GENERAR CLAVES PÚBLICA y PRIVADA 
        try {
            //Asigna al objeto claves de tipo keyPair el par de claves generadas
            KeyPairGenerator genera = KeyPairGenerator.getInstance("RSA");
            genera.initialize(512); //asigna tamaño de la clave
            KeyPair claves = genera.generateKeyPair(); //genera la pareja de claves
            
            //Imprime el valor de las claves generadas en diferentes formatos
            System.out.println("CLAVE PRIVADA");
            System.out.println("Algoritmo: " + claves.getPrivate().getAlgorithm());
            System.out.println("Codificación: " + claves.getPrivate().getFormat());
            System.out.println("Clave: " + claves.getPrivate().getEncoded().toString());
            //Se suele guardar la clave en B64
            System.out.println("Clave B64: " + encoder.encodeToString(claves.getPrivate().getEncoded()));

            System.out.println("CLAVE PÚBLICA");
            System.out.println("Algoritmo: " + claves.getPublic().getAlgorithm());
            System.out.println("Codificación: " + claves.getPublic().getFormat());
            System.out.println("Clave: " + claves.getPublic().getEncoded().toString());
            //Se suele guardar la clave en B64
            System.out.println("Clave B64: " + encoder.encodeToString(claves.getPublic().getEncoded()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GenerarClaves.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //GENERAR CLAVE SECRETA
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56); //se indica el tamaño de la clave
            SecretKey claveSecreta = keyGen.generateKey(); //genera la clave privada
            System.out.println("CLAVE SECRETA");
            System.out.println("Algoritmo: " + claveSecreta.getAlgorithm());
            System.out.println("Codificación: " + claveSecreta.getFormat());
            System.out.println("Clave: " + claveSecreta.getEncoded().toString());
            //Se suele guardar la clave en B64
            System.out.println("Clave B64: " + encoder.encodeToString(claveSecreta.getEncoded()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GenerarClaves.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
