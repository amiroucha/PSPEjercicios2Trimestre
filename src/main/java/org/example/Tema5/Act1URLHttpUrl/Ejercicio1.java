package org.example.Tema5.Act1URLHttpUrl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//esta tarea si la tengo taasteada
public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Introduce un nombre de dominio (ejemplo: google.com):");
        String dominio = leer.nextLine();

        try {
            // Obtener la dirección IP del dominio
            InetAddress objetoDominio = InetAddress.getByName(dominio);
            System.out.println("IP del dominio " + dominio + " : " + objetoDominio.getHostAddress());

            // Comprobar si es alcanzable
            if (objetoDominio.isReachable(5000))
                System.out.println("El dominio " + dominio + " es alcanzable.");
            else
                System.out.println("El dominio " + dominio + " NO es alcanzable.");

            // Construcción correcta de la URL
            URL url = new URL("https://" + dominio);
            System.out.println("\n------ Información de la URL ------");
            System.out.println("Protocolo = " + url.getProtocol());
            System.out.println("Host = " + url.getHost());
            System.out.println("Puerto = " + url.getPort());
            System.out.println("Puerto por defecto = " + url.getDefaultPort());
            System.out.println("Fichero = " + url.getFile());
            System.out.println("Referencia = " + url.getRef());

            System.out.println("\n------ Información de las cabeceras ------");

            // Abrir conexión con la URL
            URLConnection conexion = url.openConnection();
            conexion.connect();

            // Imprimir información de cabeceras
            System.out.println("Content Type: " + conexion.getContentType());
            System.out.println("Content Length: " + conexion.getContentLength());
            System.out.println("Last-Modified: " + conexion.getLastModified());
            System.out.println("Content Encoding: " + conexion.getContentEncoding());
            System.out.println("Expires: " + conexion.getExpiration());
            System.out.println("If Modified Since: " + conexion.getIfModifiedSince());

            // Obtener y mostrar todas las cabeceras
            for (Map.Entry<String, List<String>> header : conexion.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + " = " + header.getValue());
            }

            // Obtener cabeceras específicas
            System.out.println("Cache-Control: " + conexion.getHeaderField("Cache-Control"));
            System.out.println("Server: " + conexion.getHeaderField("Server"));

            // Si el contenido es una imagen, descargarla
            if (conexion.getContentType() != null && conexion.getContentType().startsWith("image/")) {
                descargarImagen(url, dominio);
            }

        } catch (MalformedURLException e) {
            System.err.println("Error: La URL no es válida. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void descargarImagen(URL url, String dominio) {
        System.out.println("\nDetectada imagen. Descargando...");

        try (InputStream in = url.openStream();
             OutputStream out = new FileOutputStream(dominio + ".jpg")) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            System.out.println("Imagen descargada como " + dominio + ".jpg");
        } catch (IOException e) {
            System.err.println("Error al descargar la imagen: " + e.getMessage());
        }
    }
}
