package org.example.Tema5.Entregable.example.Ejercicio1FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

//mi portatil
//chai
//123456
public class Main1 {
    // Objeto de la clase FTPClient
    private static final FTPClient clienteFTP = new FTPClient();
    static Properties prop = new Properties();
    // Leer entradas necesarias
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        String opcion;
        conexionSesion(); // Abro la sesión
        do {
            menu();
            System.out.println("Elija una opción del menú: ");
            opcion = entrada.nextLine();
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
                    System.out.println("Saliendo del menú");
                    cerrarConexion();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (!opcion.equals("6"));
    }

    // Método para imprimir el menú
    public static void menu() {
        System.out.println("------------------------ Menú ------------------------");
        System.out.println("1. Mostrar el directorio actual");
        System.out.println("2. Entrar a un directorio");
        System.out.println("3. Subir al directorio padre");
        System.out.println("4. Subir fichero");
        System.out.println("5. Borrar un fichero");
        System.out.println("6. Salir");
    }

    // Conexión y login en el servidor FTP
    public static void conexionSesion() {
        try {
            prop.load(new FileInputStream("src/main/java/org/example/Tema5/Entregable/example/configuracion.properties"));
            String usuario = prop.getProperty("usuario");
            String password = prop.getProperty("password");
            String servidor = prop.getProperty("servidor");

            clienteFTP.connect(servidor);
            if (FTPReply.isPositiveCompletion(clienteFTP.getReplyCode())) {
                System.out.println("Respuesta servidor (conexión): " + clienteFTP.getReplyString());
                if (clienteFTP.login(usuario, password)) {
                    System.out.println("Respuesta servidor (login): " + clienteFTP.getReplyString());
                    // Activa el modo pasivo para evitar problemas con el firewall
                    clienteFTP.enterLocalPassiveMode();
                } else {
                    clienteFTP.disconnect();
                    System.err.println("FTP ha rechazado la conexión establecida");
                    System.exit(1);
                }
            } else {
                System.err.println("No se pudo conectar al servidor FTP");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            System.exit(1);
        }
    }

    // Cierra la conexión sin intentar imprimir respuestas nulas
    private static void cerrarConexion() {
        if (clienteFTP.isConnected()) {
            try {
                clienteFTP.logout();
                clienteFTP.disconnect();
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
            System.out.println("Conexión cerrada.");
        }
    }

    // Ejercicio 1: Mostrar el directorio actual
    public static void mostrarDirectorioActual() {
        try {
            String currentDir = clienteFTP.printWorkingDirectory();
            System.out.println("Directorio actual: " + currentDir);
            FTPFile[] archivos = clienteFTP.listFiles();
            System.out.println("Contenido del directorio:");
            for (FTPFile ftpFile : archivos) {
                System.out.println(ftpFile); //con el .getname me saca solo el nombre
            }
            // Se imprime una única respuesta del servidor
            System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
        } catch (IOException e) {
            System.err.println("Error al mostrar el directorio: " + e.getMessage());
        }

    }

    // Ejercicio 2: Entrar a un directorio existente (sin crearlo si no existe)
    public static void entrarDirectorio() {
        try {
            System.out.println("Introduce el nombre del directorio:");
            String directorio = entrada.nextLine();
            if (clienteFTP.changeWorkingDirectory(directorio)) {
                System.out.println("Directorio cambiado a: " + clienteFTP.printWorkingDirectory());
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
            } else {
                // Se muestra el error del servidor sin crear el directorio
                System.out.println("Error: El directorio '" + directorio + "' no existe.");
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
            }
        } catch (IOException e) {
            System.err.println("Error al entrar al directorio: " + e.getMessage());
        }
    }

    // Ejercicio 3: Subir al directorio padre sin volver a hacer login
    public static void irDirectorioPadre() {
        try {
            if (clienteFTP.changeToParentDirectory()) {
                System.out.println("Ha subido al directorio padre: " + clienteFTP.printWorkingDirectory());
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
            } else {
                System.out.println("Error: No se pudo subir al directorio padre.");
                System.out.println("Respuesta servidor: " + clienteFTP.getReplyString());
            }
        } catch (IOException e) {
            System.err.println("Error al ir al directorio padre: " + e.getMessage());
        }
    }

    // Ejercicio 4: Subir fichero (se valida si existe en el equipo local)
    public static void subirFichero() {
        try {
            System.out.println("Introduzca la ruta del fichero a subir:");
            String rutaFichero = entrada.nextLine();
            File file = new File(rutaFichero);
            if (!file.exists()) {
                System.out.println("Error: El fichero '" + rutaFichero + "' no existe localmente.");
                return;
            }
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
            // Se extrae solo el nombre del archivo para almacenarlo en el servidor
            String remoteFileName = file.getName();
            try (FileInputStream inputStream = new FileInputStream(file)) {
                boolean exito = clienteFTP.storeFile(remoteFileName, inputStream);
                if (exito) {
                    System.out.println("Fichero subido correctamente: " + remoteFileName);
                } else {
                    System.out.println("Error al subir el fichero: " + clienteFTP.getReplyString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al subir el fichero: " + e.getMessage());
        }
    }

    // Ejercicio 5: Borrar fichero (muestra respuesta del servidor según exista o no)
    public static void borrarFichero() {
        try {
            System.out.println("Introduce el nombre del fichero a borrar:");
            String fichero = entrada.nextLine();
            boolean exito = clienteFTP.deleteFile(fichero);
            if (exito) {
                System.out.println("Fichero borrado correctamente: " + fichero);
            } else {
                System.out.println("Error al borrar el fichero: " + clienteFTP.getReplyString());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar el fichero: " + e.getMessage());
        }
    }
}
