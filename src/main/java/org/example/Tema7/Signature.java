/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example.Tema7;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * @author alumno
 */
public class Signature {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String texto = "texto de prueba para ser firmado";
        KeyPair clave = Signature.generarClaves();
        byte[] textoFirmado = Signature.hacerFirma(texto.getBytes(),clave.getPrivate());
        if (Signature.verificarFirma(texto.getBytes(), clave.getPublic(), textoFirmado)) {
            System.out.println("Firma realizada y verificada correctamente");
        } else {
            System.out.println("Firma incorrecta");
        }
    }
    //método que genera una pareja de claves (pública y privada)
    //que se utilizarán en la firma digital

    public static KeyPair generarClaves() {
        //inicializa el objeto claves, tipo KeyPair, a null
        KeyPair claves = null;
        try {
            //Indica el algoritmo a utilizar en la generación de claves
            KeyPairGenerator generador = KeyPairGenerator.getInstance("DSA");
            generador.initialize(512);
            //asigna la pareja de claves generadas al objeto tipo KeyPair, claves
            claves = generador.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //retorna un objeto tipo KeyPair
        return claves;
    }
//método que realiza la firma digital del texto o datos y la devuelve

    public static byte[] hacerFirma(byte[] datos, PrivateKey clave) {
        byte[] firmado = null;
        try {
            //crea el objeto tipo Signature con algoritmo DSA
            java.security.Signature firma = java.security.Signature.getInstance("DSA");
            //inicializa la firma con la clave privada a utilizar
            firma.initSign(clave);
            //obtine el resumen del mensaje
            firma.update(datos);
            //obtien la firma digital
            firmado = firma.sign();
            Base64.Encoder encoder = Base64.getEncoder();
            String cadena = encoder.encodeToString(firmado);
            System.out.println("Firma: "+cadena);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //devuleve la firma digital
        return firmado;
 
 }
//Método que verifica la firma digital, devolviendo:
//false, si la firma no es correcta o se produce una excepción
//verdadero, si la firma es correcta
 public static boolean verificarFirma(byte[] texto, PublicKey clave,
            byte[] textoFirmado) {
        try {
            //crea el objeto tipo Signature con algoritmo DSA
            java.security.Signature firma = java.security.Signature.getInstance("DSA");
            //verifica la clave pública
            firma.initVerify(clave);
            //actualiza el resumen de mensaje
            firma.update(texto);
            //devuelve el resultado de la verificación
            return (firma.verify(textoFirmado));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
