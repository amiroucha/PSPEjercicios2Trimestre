package org.example.Tema5.Act1URLHttpUrl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//esta tarea si la tengo taasteada
public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Introduce una URL (ejemplo: https://google.com):");
        String urlUsuario = leer.nextLine();

        try {
            // Construcción de la URL
            URL url = new URL(urlUsuario);

            // Obtener la dirección IP del dominio
            InetAddress objetoDominio = InetAddress.getByName(url.getHost());
            System.out.println("IP del dominio " + url.getHost() + " : " + objetoDominio.getHostAddress());

            // Comprobar si es alcanzable
            if (objetoDominio.isReachable(5000))
                System.out.println("El dominio " + url.getHost() + " es alcanzable.");
            else
                System.out.println("El dominio " + url.getHost() + " NO es alcanzable.");

            System.out.println("\n------ Información de la URL ------");
            System.out.println("Protocolo = " + url.getProtocol());
            System.out.println("Host = " + url.getHost());
            int puerto = (url.getPort() != -1) ? url.getPort() : url.getDefaultPort();
            System.out.println("Puerto = " + puerto);
            System.out.println("Fichero = " + url.getFile());
            System.out.println("Referencia = " + url.getRef());

            System.out.println("\n------ Información de las cabeceras ------");

            // Abrir conexión con la URL
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setInstanceFollowRedirects(true);
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexion.connect();

            // Imprimir información de cabeceras
            for (Map.Entry<String, List<String>> header : conexion.getHeaderFields().entrySet()) {
                System.out.println(" CABECERA " + header.getKey() + " = " + header.getValue());
            }

            // Si el contenido es una imagen, descargarla
            if (conexion.getContentType() != null && conexion.getContentType().startsWith("image/")) {
                descargarImagen(url, url.getHost());
            }

        } catch (MalformedURLException e) {
            System.err.println("Error: La URL no es válida. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void descargarImagen(URL url, String dominio) {
        System.out.println("\nDetectada imagen. Descargando...");

        try {
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setInstanceFollowRedirects(true);
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");
            conexion.connect();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String contentType = conexion.getContentType();
                String extension = "jpg"; // Valor por defecto
                if (contentType != null && contentType.startsWith("image/")) {
                    extension = contentType.substring(6);
                }
                String nombreArchivo = dominio.replaceAll("[^a-zA-Z0-9]", "_") + "." + extension;

                try (InputStream in = conexion.getInputStream();
                     OutputStream out = new FileOutputStream(nombreArchivo)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Imagen descargada como " + nombreArchivo);
                }
            } else {
                System.err.println("Error al conectar: " + conexion.getResponseCode());
            }

        } catch (IOException e) {
            System.err.println("Error al descargar la imagen: " + e.getMessage());
        }
    }
}
