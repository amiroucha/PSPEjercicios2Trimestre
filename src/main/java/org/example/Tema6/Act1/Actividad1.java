package org.example.Tema6.Act1;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Actividad1 {
    public static void main(String[] args) {
        System.out.println("Elige un numero del 1 al 6: ");
        // para leer del teclado
        Scanner scanner = new Scanner(System.in);
        String opcion = scanner.nextLine();

        switch (opcion){
            case "1":
                Ej1();
                break;
            case "2":
                Ej2();
                break;
            case "3":
                Ej3();
                break;
            case "4":
                Ej4();
                break;
            case "5":
                Ej5();
                break;
            case "6":
                Ej6();
                break;
            default:
                System.out.println("Opcion Incorrecta");

        }

    }
    public static void Ej1(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce un numero negativo de 3 cifras:");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("-([1-9][0-9]{2})");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("El numero es valido " + cliente);
            } else {
                System.out.println("El numero no es valido " + cliente);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Ej2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Clave formada por un mínimo de 6 letras " +
                "mayúsculas y/o minúsculas y un máximo de 8");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("[a-zA-Z]{6,8}");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("La clave es valida " + cliente);
            } else {
                System.out.println("La clave no es valida " + cliente);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public static void Ej3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("  Clave de longitud de 6. Los 3 primeros caracteres son letras mayúsculas o\n" +
                "minúsculas, y los 3 siguientes números del 0 al 9.");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("[a-zA-Z]{3}[0-9]{3}");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("La clave es valida " + cliente);
            } else {
                System.out.println("La clave no es valida " + cliente);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public static void Ej4() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Número de teléfono con el siguiente formato");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("[1-9]{3}[0-9]{6}");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("El numero es valida " + cliente);
            } else {
                System.out.println("El numero no es valida " + cliente);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Ej5() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Código de empleado que empieza por una letra A, B o C seguido de un guión y a\n" +
                "continuación 5 números del 1 al 9.");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("[ABC]-[1-9]{5}");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("La clave es valida " + cliente);
            } else {
                System.out.println("La clave no es valida " + cliente);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public static void Ej6() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Un código postal español teniendo en cuenta que el código " +
                "postal “más alto” debería ser 52999. Es decir, no existe un " +
                "código postal en España que empiece por 53 o superior.");
        String cliente = scanner.nextLine();
        try {
            Pattern pat = Pattern.compile("[1-5][0-2][-d]{3}");
            Matcher mat = pat.matcher(cliente);

            //Si buscamos coincidencia exacta --> matches()

            if (mat.matches()) {
                System.out.println("La clave es valida " + cliente);
            } else {
                System.out.println("La clave no es valida " + cliente);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}