package org.example.Tema5.FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.IOException;
import java.util.Scanner;

public class FTPCliente {
    //objeto de la clase FTPClient
    private static FTPClient clienteFTP;
    //usuario
    private static final String usuario = "chai";
    //contraseña
    private static final String password = "admin";
    private static final String servidor = "localhost";

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        try{
            clienteFTP = new FTPClient();   //creación del objeto cliente FTP

            clienteFTP.connect(servidor);//conexión del cliente al servidor FTP
            //Comprobación de la conexión
            //si la conexión  se completa ok
            if (FTPReply.isPositiveCompletion(clienteFTP.getReplyCode())) {
                System.out.println(clienteFTP.getReplyString()); //imprime la respuesta del servidor
                //abre una sesión con el usuario
                if(clienteFTP.login(usuario, password)){
                    System.out.println(clienteFTP.getReplyString()); //imprime la respuesta del servidor
                    System.out.println("Sesión iniciada correctamente");

                    //Activar el modo pasivo para no tener problemas con el Firewall de Windows
                    clienteFTP.enterLocalPassiveMode();
                    /*-------------------------------------------------*/
                    System.out.println("Listo los directorios disponibles");
                    System.out.println("Lista de directorios accesibles:");
                    FTPFile[] archivosRaizDisponible = clienteFTP.listFiles();
                    for (FTPFile archivo : archivosRaizDisponible) {
                        if (archivo.isDirectory()) {
                            System.out.println("📂 " + archivo.getName());
                        }
                    }
                    /*-------------------------------------------------*/

                    System.out.println("Introduce el nombre del directorio deseado");
                    String directorio = leer.nextLine();

                    if (!clienteFTP.changeWorkingDirectory(directorio)) {
                        System.out.println("No existe el directorio " + directorio+"\nCreando...");
                        if(clienteFTP.makeDirectory(directorio)){
                            System.out.println("Directorio creado correctamente");
                        }else{
                            System.out.println("Directorio no se ha podido crear");
                        }
                    }

                    clienteFTP.changeWorkingDirectory(directorio);
                    System.out.println("Contenido del directorio " + directorio + ":");

                    FTPFile[] archivos = clienteFTP.listFiles();
                    for (FTPFile archivo : archivos) {
                        System.out.println(archivo.getName());
                    }
                    //cerrar sesion del usuario
                    clienteFTP.logout();
                }else{
                    System.out.println(clienteFTP.getReplyString()); //imprime la respuesta del servidor
                    System.out.println("Error en la autenticación");
                }

            } else {
                //desconecta
                System.out.println(clienteFTP.getReplyString()); //imprime la respuesta del servidor
                System.err.println("FTP ha rechazado la conexión esblecida");
            }

        } catch (IOException e) {
            System.err.println("FTP client error: " + e.getMessage());
            System.out.println(clienteFTP.getReplyString()); //imprime la respuesta del servidor
        }finally {
            if (clienteFTP.isConnected()) {
                try {
                    clienteFTP.disconnect();
                    System.out.println("Respuesta Servidor "+clienteFTP.getReplyString()); //imprime la respuesta del servidor
                    System.out.println("Conexión cerrada");
                } catch (IOException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }


    }
}
