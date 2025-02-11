package org.example.Tema5.Act1URLHttpUrl.Ejemplos.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String USER_AGENT = "Mozilla/5.0";
    /*CAMBIA ESTA URL*/
    private static final String GET_URL = "http://localhost/ejemplo/actionGET.php?userName=mariajo";
    /*CAMBIA ESTA URL*/
    private static final String POST_URL = "http://localhost/ejemplo/actionPOST.php";

    private static final String POST_PARAMS = "userName=mariajo";

    public static void main(String[] args) {

        try {
            System.out.println("-------------------------------------");
            System.out.println("----------------GET------------------");
            System.out.println("-------------------------------------");
            sendGET(); //Ejemplo con GET
            System.out.println("-------------------------------------");
            System.out.println("----------------POST------------------");
            System.out.println("-------------------------------------");
            sendPOST(); //Ejemplo con POST
        } catch (IOException ex) {
            System.err.println(ex.getStackTrace());
        }
    }

    private static void sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code : " + responseCode);
        System.out.println("GET Response Message : "+con.getResponseMessage());
        //Para imprimir todas las cabeceras
        for (Map.Entry<String, List<String>> header : con.getHeaderFields().entrySet()) {
            System.out.println(header.getKey() + "=" + header.getValue());
        }
        System.out.println("Imprimo la respuesta");
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

    private static void sendPOST() throws IOException {
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        System.out.println("POST Response Message : "+con.getResponseMessage());

        //Para imprimir todas las cabeceras
        for (Map.Entry<String, List<String>> header : con.getHeaderFields().entrySet()) {
            System.out.println(header.getKey() + "=" + header.getValue());
        }
        System.out.println("Imprimo la respuesta");
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
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