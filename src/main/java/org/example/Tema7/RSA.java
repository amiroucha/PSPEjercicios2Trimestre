/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example.Tema7;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author alumno
 */
public class RSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("Crear clave pública y privada");
            //Crea e inicializa el generador de claves RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);//tamaño de la clave
            KeyPair clavesRSA = keyGen.generateKeyPair();
            PrivateKey clavePrivada = clavesRSA.getPrivate();//obtiene clave privada
            PublicKey clavePublica = clavesRSA.getPublic();//obtiene clave pública
            //muestra las claves generadas
            System.out.println("clavePublica: " + clavePublica);
            System.out.println("clavePrivada: " + clavePrivada);
            //texto a encriptar o cifrar
            byte[] bufferClaro = "Programación de Servicios y Procesos 2ºDAMIoT\n".getBytes();
            //Crea cifrador RSA
            Cipher cifrador = Cipher.getInstance("RSA");
            //Pone cifrador en modo ENCRIPTACIÓN utilizando la clave pública
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
            System.out.println("Cifrar con clave pública el Texto:");
            mostrarBytes(bufferClaro);
            //obtiene todo el texto cifrado
            byte[] bufferCifrado = cifrador.doFinal(bufferClaro);
            System.out.println("Texto CIFRADO");
            mostrarBytes(bufferCifrado); //muestra texto cifrado
            //Pone cifrador en modo DESENCRIPTACIÓN utilizando la clave privada
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);

            System.out.println("Descifrar con clave privada");
            //obtiene el texto descifrado
            bufferClaro = cifrador.doFinal(bufferCifrado);

            System.out.println("Texto DESCIFRADO");
            mostrarBytes(bufferClaro);//muestra texto descifrado

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mostrarBytes(byte[] buffer) throws IOException {
        System.out.write(buffer);
    }
}
