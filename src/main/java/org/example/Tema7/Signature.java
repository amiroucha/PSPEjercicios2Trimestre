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


public class Signature {

    public static void main(String[] args) {
        // Texto que se va a firmar
        String texto = "texto de prueba para ser firmado";

        // Genera un par de claves (pública y privada)
        KeyPair clave = Signature.generarClaves();

        // Realiza la firma del texto utilizando la clave privada
        byte[] textoFirmado = Signature.hacerFirma(texto.getBytes(), clave.getPrivate());

        // Verifica si la firma es válida utilizando la clave pública
        if (Signature.verificarFirma(texto.getBytes(), clave.getPublic(), textoFirmado)) {
            System.out.println("Firma realizada y verificada correctamente");
        } else {
            System.out.println("Firma incorrecta");
        }
    }

    // Método que genera una pareja de claves (pública y privada)
    public static KeyPair generarClaves() {
        // Inicializa el objeto claves, tipo KeyPair, a null
        KeyPair claves = null;
        try {
            // Indica el algoritmo a utilizar en la generación de claves (DSA)
            KeyPairGenerator generador = KeyPairGenerator.getInstance("DSA");
            // Inicializa el generador de claves con una longitud de 512 bits
            generador.initialize(512);
            // Asigna la pareja de claves generadas al objeto tipo KeyPair, claves
            claves = generador.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            // Maneja la excepción si el algoritmo no se encuentra
            System.out.println("No se ha podido generar el keypair");
        }
        // Retorna un objeto tipo KeyPair
        return claves;
    }

    // Método que realiza la firma digital del texto o datos y la devuelve
    public static byte[] hacerFirma(byte[] datos, PrivateKey clave) {
        byte[] firmado = null;
        try {
            // Crea el objeto tipo Signature con algoritmo DSA
            java.security.Signature firma = java.security.Signature.getInstance("DSA");
            // Inicializa la firma con la clave privada a utilizar
            firma.initSign(clave);
            // Actualiza el resumen del mensaje
            firma.update(datos);
            //---------------------------------------------------
            // Obtiene la firma digital
            firmado = firma.sign();
            // Codifica la firma en Base64 para imprimirla como cadena
            Base64.Encoder encoder = Base64.getEncoder();
            String cadena = encoder.encodeToString(firmado);
            System.out.println("Firma: " + cadena);
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir
            System.out.println("Error tratando de firmar: "+e.getMessage());
        }
        // Devuelve la firma digital
        return firmado;
    }

    // Método que verifica la firma digital
    public static boolean verificarFirma(byte[] texto, PublicKey clave, byte[] textoFirmado) {
        try {
            // Crea el objeto tipo Signature con algoritmo DSA
            java.security.Signature firma = java.security.Signature.getInstance("DSA");
            // Verifica la clave pública
            firma.initVerify(clave);
            // Actualiza el resumen de mensaje
            firma.update(texto);
            // Devuelve el resultado de la verificación
            return (firma.verify(textoFirmado));
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir
            System.out.println();
        }
        // Si ocurre un error o la firma no es válida, devuelve false
        return false;
    }
}
