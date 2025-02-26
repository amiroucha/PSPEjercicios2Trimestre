/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example.Tema6.Ejemplos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alumno
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String dni_cliente = new String();
        Pattern pat = null;
        Matcher mat = null;
        // para leer del teclado
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Introduce tu DNI (Formato 00000000-A):");
            dni_cliente = scanner.nextLine();

            pat = Pattern.compile("[0-9]{8}-[a-zA-Z]");
            mat = pat.matcher(dni_cliente);

            //Si buscamos coincidencia exacta --> matches()
            //Prueba 47472753-V
            if (mat.matches()) {
                System.out.println("El DNI es correcto " + dni_cliente);
            } else {
                System.out.println("El DNI esta mal " + dni_cliente);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
