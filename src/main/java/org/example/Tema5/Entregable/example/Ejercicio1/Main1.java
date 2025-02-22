package org.example.Tema5.Entregable.example.Ejercicio1;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main1 {
    //objeto de la clase FTPClient
    private static final FTPClient clienteFTP = new FTPClient();
    static Properties prop = new Properties(); //estan en el metodo conexion
    //leer entradas necesarias
    private static final Scanner entrada = new Scanner(System.in);
    public static void main(String[] args) {
        String opcion;
        conexionSesion();//abro la sesion
        do {
            menu();
            System.out.println("Elija una opcion del menu: ");
            opcion = entrada.nextLine();
            //entrada.nextLine();
            switch (opcion) {
                case "1":
                    mostrarDirectorioActual();
                    break;
                case "2":
                    entrarDirectorio();
                    break;
                case "3":
                    irDirectorioPadre();
                    break;
                case "4":
                    subirFichero();
                    break;
                case "5":
                    borrarFichero();
                    break;
                case "6":
                    System.out.println("Saliendo del menu");
                    cerrarConexion();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while(!opcion.equals("6"));
    }
    //solo imprime el menu
    public static void menu() {
        System.out.println("------------------------Menu------------------------");
        System.out.println("1.Mostrar el directorio actual");
        System.out.println("2.Entrar directorio");
        System.out.println("3.Subir al directorio padre");
        System.out.println("4.Subir fichero");
        System.out.println("5.Borrar un fichero ");
        System.out.println("6.Salir");
    }

    public static void conexionSesion() {
        try {
            prop.load(new FileInputStream("src/main/resources/configuracion.properties"));//cargo las propiedades
            String usuario = prop.getProperty("usuario");
            String password =prop.getProperty("password");
            String servidor = prop.getProperty("servidor");

            clienteFTP.connect(servidor);//conexión del cliente al servidor FTP
            //si la conexion es exitosa
            if (FTPReply.isPositiveCompletion(clienteFTP.getReplyCode())) {
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());//imprime la respuesta del servidor
                //abre una sesión con el usuario
                if (clienteFTP.login(usuario, password)) {
                    System.out.println("Respuesta servidor: " + clienteFTP.getReplyString()); //imprime la respuesta del servidor
                    //Activar el modo pasivo para no tener problemas con el Firewall de Windows
                    clienteFTP.enterLocalPassiveMode();
                }else {
                    //desconecta
                    clienteFTP.disconnect();
                    System.err.println("FTP ha rechazado la conexión esblecida");
                    System.exit(1);
                }
            }
        }catch (IOException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
    //metodo para cerrar la conexion
    private static void cerrarConexion() {
        if (clienteFTP.isConnected()) { //si esta conectado el servidor
            try {
                clienteFTP.disconnect(); //desconecto
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
            //si imprime null se ha cerrado correctamente
            System.out.println("Respuesta Servidor " + clienteFTP.getReplyString()); //imprime la respuesta del servidor
        }
    }
    //Ejercicio 1---------------------------------------------------
    public static void mostrarDirectorioActual() {
        try {
            /*-------------------------------------------------*/
            clienteFTP.printWorkingDirectory();
            //aqui ya me dice en que directorio estoy
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());

            System.out.println("Contenido del directorio: ");
            //recojo los archivos de ese directorio
            FTPFile[] archivosRaizDisponible = clienteFTP.listFiles();
            for (FTPFile ftpFile : archivosRaizDisponible) {
                System.out.println(ftpFile);//imprimo los archivos
            }
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Ejercicio 2---------------------------------------------------
    public static void entrarDirectorio() {
        try {
            //.....................................
            System.out.println("Introduce el nombre de un directorio");
            String directorio = entrada.nextLine();//leo donde quiere entrar
            if(!clienteFTP.changeWorkingDirectory(directorio))
            {
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
                clienteFTP.makeDirectory(directorio);
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
                clienteFTP.changeWorkingDirectory(directorio);
            }//cambia de directorio
            // el servidor en su respuesta
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Ejercicio 3---------------------------------------------------
    public static void irDirectorioPadre(){
        conexionSesion();//abro la conexion
        try {
            //si se ha podidio cambiar o no lo dice el servidor
            clienteFTP.changeToParentDirectory();
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
        } catch (IOException e) {
            System.err.println("Error al ir al directorio padre: " + e.getMessage());
        }
    }



    //Ejercicio 4--------------------------------------------------
    public static void subirFichero(){
        try {
            System.out.println("Introduzca el nombre del fichero a introducir al servidor: ");
            String fichero = entrada.nextLine(); //leo el fichero que quiere subir

            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE); //tipo a binario
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());

            FileInputStream inputStream = new FileInputStream(fichero);//creo un flijo de entrada
            clienteFTP.storeFile(fichero, inputStream); //se sube supuestamente

            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
        } catch (IOException e) {
            System.out.println("Error al subir el fichero: " + e.getMessage());
        }
    }
    //Ejercicio 5--------------------------------------------------
    public static void borrarFichero(){
        try {
            System.out.println("Introduce el fichero a borrar");
            String fichero = entrada.nextLine();

            clienteFTP.deleteFile(fichero);//trata de eliminar el fichero

            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());

        } catch (IOException e) {
           System.err.println("Error al eliminar el fichero: " + e.getMessage());
        }

    }



}
