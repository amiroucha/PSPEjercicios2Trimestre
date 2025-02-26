package org.example.Tema6.Entregable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        log.debug("Chaimae Amirou el Adami");
        Scanner scanner = new Scanner(System.in);
        //expresion del nombre del fichero
        String expresion1 = "[a-z]{2}_[a-z0-9]{2,5}(.txt|.csv)";
        //expresion de la primera linea
        String expresionLin1 = "97([89])[0-9]{10}";
        //expresion de la segunda linea
        String expresionLin2 = "@[a-zA-Z][a-zA-Z_\\-0-9]{1,10}";
        //expresion de la tercera linea
        String expresionLin3 = "(AABB|aabb)[a-zA-Z\\d]{5}\\d";
        String linea;

        //pedir datos al usuario
        System.out.println("Introduce un nombre de fichero");
        String clienteFichero = scanner.nextLine();

        try{

            //Si el nombre del fichero es valido respecto a la expresion
            if (validarExpresion(expresion1, clienteFichero)) {
                log.info("Nombre de fichero con formato válido: ".concat(clienteFichero));
                System.out.println("Nombre de fichero con formato válido: " + clienteFichero);
                //creo la ruta del fichero
                File fichero = new File("./".concat(clienteFichero));
                //creo el buffer para leer
                int contador = 1;
                BufferedReader leer = new BufferedReader(new FileReader(fichero));
                while((linea = leer.readLine()) != null){//mientras que tenga algo que leer
                    //imprimo la linea que es lo que tengo que validar, este caso el isbn
                    if (contador == 1){
                        contador++;
                        if(linea.matches(expresionLin1)){
                            log.info("ISBN valido".concat(linea));
                            System.out.println("ISBN válido: ".concat(linea));
                        } else{
                            log.warn("ISBN no valido".concat(linea));
                            System.out.println("ISBN NO válido: ".concat(linea));
                        }
                    } else if (contador==2) {
                        contador++;
                        if(linea.matches(expresionLin2)){
                            log.info("Nombre de usuario valido".concat(linea));
                            System.out.println("Nombre de usuario válido: ".concat(linea));
                        }else{
                            log.warn("Nombre de usuario NO válido".concat(linea));
                            System.out.println("Nombre de usuario NO válido: ".concat(linea));
                        }
                    }else if (contador==3) {
                        contador++;
                        if(linea.matches(expresionLin3)) {
                            log.info("Código valido".concat(linea));
                            System.out.println("Código válido: ".concat(linea));
                        }else{
                            log.warn("Código no valido".concat(linea));
                            System.out.println("Código  NO válido: ".concat(linea));
                        }

                    }
                }

                leer.close();
            }else{
                log.warn("Nombre de fichero NO valido: ".concat(clienteFichero));
                System.out.println("Nombre del fichero no valido");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            log.error("Error en el manejo del fichero : ".concat(e.getMessage()));
        }

    }
    public static boolean validarExpresion(String expresion, String clienteFichero){
        Pattern pat = Pattern.compile(expresion);
        Matcher mat = pat.matcher(clienteFichero);
        return mat.matches();
    }
}