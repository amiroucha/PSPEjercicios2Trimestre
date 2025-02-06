package org.example.Tema5.FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.example.Tema6.Act2.ClienteTCP;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class FTPCliente {
    //objeto de la clase FTPClient
    private static FTPClient clienteFTP;
    //usuario
    private static String usuario = "anonymous";
    //contraseña
    private static String password = "";


    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        try{
            int respuesta; //va a ser la respuesta del servidor
            //creación del objeto cliente FTP
            clienteFTP = new FTPClient();
            //conexión del cliente al servidor FTP
            clienteFTP.connect("ftp.rediris.es");
            //Comprobación de la conexión
            respuesta = clienteFTP.getReplyCode();
            //si la conexión  se completa ok
            if (FTPReply.isPositiveCompletion(respuesta)) {
                //abre una sesión con el usuario anónimo
                clienteFTP.login(usuario, password);
                //Activar el modo pasivo para no tener problemas con el Firewall de Windows
                clienteFTP.enterLocalPassiveMode();
                System.out.println("Introduce el nombre del directorio deseado");
                String directorio = leer.nextLine();

                if (!clienteFTP.changeWorkingDirectory(directorio)) {
                    System.out.println("No existe el directorio " + directorio+"\nCreando lo...");
                    if(clienteFTP.makeDirectory(directorio)){
                        System.out.println("Directorio creado correctamente");
                    }else{
                        System.out.println("Directorio no se ha podido crear");
                    }
                }
                //me quedo en comprobar lo del directorio y si no crearlo
                //me falta listar el contenido como pde mariajo




            } else {
                //desconecta
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión esblecida");
                System.exit(1);
            }






        } catch (IOException e) {
            System.err.println("FTP client error: " + e.getMessage());
        }


    }
}
