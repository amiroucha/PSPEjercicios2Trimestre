package org.example.Tema6.Act2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    //El cliente tiene la X
    static final String HOST = "localhost";
    static final int Puerto = 2000;
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        try(Socket sCliente = new Socket(HOST, Puerto)){//construir la direccion del socket del servidor

            //crear los flujos de entrada y salida
            DataOutputStream flujoSalida = new DataOutputStream(sCliente.getOutputStream());
            DataInputStream flujoEntrada = new DataInputStream(sCliente.getInputStream());
            //recojo la bienvenida
            System.out.println(flujoEntrada.readUTF());
            while (true)
            {
                try {
                    //recojo mensaje : tablero
                    System.out.println("Tablero:\n"+flujoEntrada.readUTF());//imprimo el tablero que me manda el servidor

                    //recojo mensaje de pedir columnas
                    String servidorMensaje = flujoEntrada.readUTF();//recojo el mensaje de las columnas
                    System.out.println(servidorMensaje);// Solicitud de columna lo imprimo

                    if (servidorMensaje.contains("Fin de la partida")) {//si el mensaje tiene fin se acaba el bucle
                        //System.out.println(servidorMensaje);
                        System.out.println("Cierro conexion con el servidor");
                        break;
                    }

                    // Leer la columna del jugador
                    String columna = leer.nextLine();
                    // Mando el número de columna
                    flujoSalida.writeUTF(columna);
                   //lo mando como un string y ya intento el parseo en el servidor

                    //lee el estado y lo imprime
                    System.out.println(flujoEntrada.readUTF());
                }catch (EOFException e) {
                    System.out.println("El servidor ha cerrado la conexión.");
                    break; // Salir del bucle si se cierra la conexion
                }
            }
        } catch (IOException e) {
            System.err.println("Error de conexion con el servidor: "+e);
        }
    }
}
