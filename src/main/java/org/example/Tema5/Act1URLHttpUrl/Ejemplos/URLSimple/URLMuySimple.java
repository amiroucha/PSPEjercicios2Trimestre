package org.example.Tema5.Act1URLHttpUrl.Ejemplos.URLSimple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class URLMuySimple {
    /**
     *
     * Leemos un fichero del ftp de la rediris y
     * lo descargamos http://ftp.rediris.es/sites/apache.org/favicon.ico
     * @param args no hay argumentos
     */
    public static void main(String[] args) {
        try {
            //Prueba a poner esta url en el navegador http://ftp.rediris.es/sites/apache.org/favicon.ico
            //Puedes ver otros ficheros para probar -->  http://ftp.rediris.es
            URL url = new URL("http://ftp.rediris.es/sites/apache.org/favicon.ico");

            System.out.println("protocol = " + url.getProtocol());
            System.out.println("host = " + url.getHost());
            System.out.println("filename = " + url.getFile());
            System.out.println("port = " + url.getDefaultPort());
            System.out.println("ref = " + url.getRef());//En este caso no tiene "anchor"


            //Descargar un Archivo desde una URL------------------------------------------


            //conecta a esa URL
            // Abre un flujo de entrada para leer datos desde la URL.
            InputStream flujoIn = url.openStream();
            // Mejora el rendimiento al leer datos en bloques.
            BufferedInputStream in = new BufferedInputStream(flujoIn);

            //url.getFile obtiene toda la ruta, sólo queremos el nombre del fichero
            String[] rutas = url.getFile().split("/");
            System.out.println("Nombre del fichero a descargar "+rutas[rutas.length - 1]);

            //Escribir el Archivo en Disco-------------------------------------

            //Creamos el flujo para guardar el fichero
            // Crea el archivo en disco
            FileOutputStream fileOutput = new FileOutputStream(rutas[rutas.length - 1]);
            // Permite escribir datos en bloques para mayor eficiencia.
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);

            // Bucle para leer de un fichero y escribir en el otro.
            //Lee datos de la URL en bloques de 1000 bytes y los escribe en el archivo.
            byte[] array = new byte[1000];
            int leidos = in.read(array);
            while (leidos > 0) {
                bufferedOutput.write(array, 0, leidos);
                leidos = in.read(array);
            }

            //Cerramos
            in.close();
            bufferedOutput.close();
            fileOutput.close();


            URL url2 = new URL("http://java.sun.com:80/tutorial/intro.html#DOWNLOADING");

            System.out.println("protocol = " + url2.getProtocol());
            System.out.println("host = " + url2.getHost());
            System.out.println("filename = " + url2.getFile());
            System.out.println("port = " + url2.getDefaultPort());
            System.out.println("ref = " + url2.getRef());

        } catch (MalformedURLException ex) {
            System.err.println("Error URL mal formada " + ex);
        } catch (IOException ex) {
            System.err.println("Error " + ex);
        }

    }
}
