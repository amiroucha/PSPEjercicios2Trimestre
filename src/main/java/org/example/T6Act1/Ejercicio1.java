package org.example.T6Act1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Ejercicio1 {
    public static void main(String[] args) {
        String cliente = new String();
        Pattern pat = null;
        Matcher mat = null;
        // para leer del teclado
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Introduce tu DNI (Formato 00000000-A):");
            cliente = scanner.nextLine();

            pat = Pattern.compile("[0-9]{8}-[a-zA-Z]");
            mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()
            //Prueba 47472753-V
            if (mat.matches()) {
                System.out.println("El DNI es correcto " + cliente);
            } else {
                System.out.println("El DNI esta mal " + cliente);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}