package org.example.Tema5.Act1URLHttpUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
//HTTP URL CONNECTION . GET . POST
public class Ejercicio2 {

    public class Main {
        private static final String USER_AGENT = "Mozilla/5.0";

        // Asegúrate de que la URL apunta a la ubicación correcta en tu servidor local.
        // Por ejemplo, si tienes los archivos en C:\xampp\htdocs\EjemploPHP,
        // la URL debería ser similar a:
        // http://localhost/EjemploPHP/actionGET.php?userName=chai&clave=1234
        private static final String GET_URL = "http://localhost/EjemploPHP/actionGET.php?userName=chai&clave=1234";

        // Similar para el POST: se recomienda colocar los archivos PHP en la carpeta pública.
        private static final String POST_URL = "http://localhost/EjemploPHP/actionPOST.php";

        // Se añaden ambos parámetros separados por "&"
        private static final String POST_PARAMS = "userName=chai&clave=1234";

        public static void main(String[] args) {
            try {
                System.out.println("-------------------------------------");
                System.out.println("----------------GET------------------");
                System.out.println("-------------------------------------");
                sendGET(); // Ejemplo con GET
                System.out.println("-------------------------------------");
                System.out.println("----------------POST-----------------");
                System.out.println("-------------------------------------");
                sendPOST(); // Ejemplo con POST
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private static void sendGET() throws IOException {
            URL url = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code : " + responseCode);
            System.out.println("GET Response Message : " + con.getResponseMessage());

            // Imprimir cabeceras
            for (Map.Entry<String, List<String>> header : con.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + " = " + header.getValue());
            }

            System.out.println("Imprimo la respuesta:");
            if (responseCode == HttpURLConnection.HTTP_OK) { // éxito
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        }

        private static void sendPOST() throws IOException {
            URL url = new URL(POST_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // Habilitar envío de datos para POST
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);
            System.out.println("POST Response Message : " + con.getResponseMessage());

            // Imprimir cabeceras
            for (Map.Entry<String, List<String>> header : con.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + " = " + header.getValue());
            }

            System.out.println("Imprimo la respuesta:");
            if (responseCode == HttpURLConnection.HTTP_OK) { // éxito
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("POST request not worked");
            }
        }
    }

}
