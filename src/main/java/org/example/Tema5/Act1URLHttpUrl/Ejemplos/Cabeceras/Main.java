package org.example.Tema5.Act1URLHttpUrl.Ejemplos.Cabeceras;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce una url");
        String cadenaURL = scanner.nextLine();
        //https://cifpcarlos3.es/cdn/admin/assets/images/galeria/tml09bxe1nXLh35Awzdp.pdf
        //variables locales
        int leido, contentLength;
        char[] bufChar;
        byte[] bufByte;
        //
        try {
            //crea objeto url
            URL url = new URL(cadenaURL);
            //obtiene una conexión al recurso URL
            URLConnection conexion = url.openConnection();

            //obtiene el tipo de contenido
            String contentType = conexion.getContentType();
            System.out.println("Encabezados destacados:");
            System.out.println("- Content-Type: "+ contentType);
            //obtiene el tamaño del contenido
            contentLength = conexion.getContentLength();
            System.out.println("- Content-Length: " + contentLength);
            //obtiene la fecha de la  última modificación
            Date fecha = new Date( conexion.getLastModified() );
            System.out.println("- Fecha de la última modificación (last-modified): "+ fecha );


        } catch (MalformedURLException e) {
            System.err.println("URL sin sentido");
        } catch (IOException e) {
            System.err.println("Error de lectura/escritura");
        }
    }
}
