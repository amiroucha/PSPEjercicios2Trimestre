package org.example.Tema5.URLHttpUrl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
//esta tarea si la tengo taasteada
public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        System.out.println("Introduce un nombre de dominio");
        String dominio = leer.nextLine();

        //Obtiene el objeto InetAddress de localhost
        try {
            InetAddress objetoDominio;
            objetoDominio = InetAddress.getByName(dominio);
            System.out.println("IP del dominio"+dominio+" : "+objetoDominio.getHostAddress());

            if(objetoDominio.isReachable(500))
                System.out.println("El dominio "+dominio+" es alcanzable");
            else System.out.println("El dominio "+dominio+" NO  es alcanzable");

            URL url = new URL("http://www."+dominio);
            System.out.println("Protocolo = "+url.getProtocol());
            System.out.println("Host = " + url.getHost());
            System.out.println("Puerto = " + url.getPort());
            System.out.println("Puerto por defecto = " + url.getDefaultPort());
            System.out.println("Fichero = " + url.getFile());
            System.out.println("Referencia = " + url.getRef());

            System.out.println("----------------------- Informacion de las cabeceras -----------------------" );

            //obtiene una conexión al recurso URL
            URLConnection conexion = url.openConnection();
            System.out.println("Content Type: " + conexion.getContentType());
            System.out.println("Content Length: " + conexion.getContentLength());
            System.out.println("Last-Modified: " + conexion.getLastModified());
            System.out.println("Content Encoding: " + conexion.getContentEncoding());
            System.out.println("Expires: " + conexion.getExpiration());
            System.out.println("If Modified Since: " + conexion.getIfModifiedSince());

            //System.out.println("Conexion cabeceras"+conexion.getHeaderFields());
            System.out.println("Cache-Contro: "+conexion.getHeaderField("Cache-Control"));
            System.out.println("Server: "+conexion.getHeaderField("Server"));

        } catch (IOException e) {
            System.err.println("Error al obtener el IP del dominio: "+e.getMessage());
        }
    }
}
