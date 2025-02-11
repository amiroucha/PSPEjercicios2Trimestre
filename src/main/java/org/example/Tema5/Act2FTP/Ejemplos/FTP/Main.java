package org.example.Tema5.Act2FTP.Ejemplos.FTP;//librerías de apache para FTP
import org.apache.commons.net.ftp.FTPClient;

//librerías de java
import java.io.FileOutputStream;

public class Main {
    //objeto de la clase FTPClient de Apache, con diversos métodos para interactuar y recuperar un archivo de un servidor FTP
    private static FTPClient clienteFTP;
    //flujo de salida para la escritura de datos en un fichero
    private static FileOutputStream ficheroObtenido;
    //URL del servidor ftp.rediris.es, podrías probar con un servidor que tengas instalado en tu máquina
    private static String servidorURL = "ftp.rediris.es";
    //ruta relativa (en Servidor FTP) de la carpeta que contiene el fichero que vamos a descargar
    private static String rutaFichero = "debian";
    //nombre del fichero (aunque carece de extensión, se trata de un fichero de
    //texto que puede abrise con el bloc de notas)
    private static String nombreFichero = "README";
    //usuario
    private static String usuario = "anonymous";
    //contraseña
    private static String password = "";
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




    }
}

