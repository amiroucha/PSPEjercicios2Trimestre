package org.example.Tema5.Act3Correo;
//librerías de apache para FTP

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

//librerías de java
import java.io.IOException;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.util.Scanner;
public class Ejercicio1 {

    //objeto de la clase FTPClient de Apache, con diversos métodos para interactuar y recuperar un archivo de un servidor FTP
    private static FTPClient clienteFTP;
    //flujo de salida para la escritura de datos en un fichero
    private static FileOutputStream ficheroObtenido;
    //URL del servidor ftp.rediris.es, podrías probar con un servidor que tengas instalado en tu máquina
    private static String servidorURL = "127.0.0.1";
    //nombre del fichero (aunque carece de extensión, se trata de un fichero de
    //texto que puede abrise con el bloc de notas)
    //usuario
    private static String usuario = "chai";
    //contraseña
    private static String password = "1234";
    //array de carpetas disponibles
    private static String[] nombreCarpeta;

    /**
     * **************************************************************************
     * recupera el contenido de un fichero desde un Servidor FTP, y lo deposita en
     * un nuevo fichero en el directorio de nuestro proyecto
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        try {
            int reply;
            //creación del objeto cliente FTP
            clienteFTP = new FTPClient();
            //conexión del cliente al servidor FTP
            clienteFTP.connect(servidorURL);

            //omprobación de la conexión
            reply = clienteFTP.getReplyCode();
            //si la conexión  es satisfactoria
            if (FTPReply.isPositiveCompletion(reply)) {
                //abre una sesión con el usuario anónimo
                clienteFTP.login(usuario, password);
                //Activar el modo pasivo para no tener problemas con el Firewall de Windows
                clienteFTP.enterLocalPassiveMode();
                //lista las carpetas de primer nivel del servidor FTP
                System.out.println("Carpetas disponibles en el Servidor:");
                nombreCarpeta = clienteFTP.listNames();
                for (int i = 0; i < nombreCarpeta.length; i++) {
                    System.out.println(nombreCarpeta[i]);
                }
                System.out.println("Que directorio quieres ver?");
                String directorio = entrada.nextLine();
                if(clienteFTP.makeDirectory(directorio)){
                    System.out.println("Se ha creado e directorio "+directorio);
                }
                clienteFTP.changeWorkingDirectory(directorio);
                System.out.println("codigo cambio de directorio: "+clienteFTP.getReplyString());
                nombreCarpeta = clienteFTP.listNames();
                for (int i = 0; i < nombreCarpeta.length; i++) {
                    System.out.println(nombreCarpeta[i]);
                }
                System.out.println("Que fichero quieres obtener?");
                String fichero = entrada.nextLine();

                //nombre que el que va a recuperarse
                ficheroObtenido = new FileOutputStream(fichero);
                //mensaje
                System.out.println("\nDescargando el fichero " + fichero + " de la carpeta " + directorio);
                //recupera el contenido del fichero en el Servidor, y lo escribe en el nuevo fichero del directorio del proyecto
                //También podríamos movernos entre directorios con changeWorkingDirectory
                clienteFTP.retrieveFile("/" + directorio + "/" + fichero, ficheroObtenido);
                //Cada vez que realizas una operación con el servidor te devuelve una respuesta
                System.out.println(clienteFTP.getReplyString());
                //cierra el nuevo fichero
                ficheroObtenido.close();
                //cierra la conexión con el Servidor
                clienteFTP.disconnect();
                System.out.println("Descarga finalizada correctamente");
            } else {
                //desconecta
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión esblecida");
                System.exit(1);
            }
        } catch (SocketException ex) {
            //error de Socket
            System.out.println(ex.toString());
        } catch (IOException ex) {
            //error de fichero
            System.out.println(ex.toString());
        }
    }
}
