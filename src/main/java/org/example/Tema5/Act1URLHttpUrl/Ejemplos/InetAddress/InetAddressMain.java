package org.example.Tema5.Act1URLHttpUrl.Ejemplos.InetAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
//mostrar o conseguir informacion base

//Este programa en Java utiliza la clase InetAddress
// para obtener información sobre direcciones IP y
// nombres de host, tanto en la red local como en Internet.
public class InetAddressMain {
    public static void main(String[] args) {

        try {
            //RED LOCAL
            System.out.println("**********LA RED LOCAL*************");

            //Obtiene el objeto InetAddress de localhost
            InetAddress objetoLocalhost = InetAddress.getByName("localhost");//es algo aqui
            System.out.println("IP de localhost:"+objetoLocalhost.getHostAddress());

            //obtiene dirección de mi Equipo-- puedo utilizar:
            //getLocalHost() o getByName("nombredemiEquipo")
            InetAddress MiEquipoLan = InetAddress.getLocalHost();
            //nombre e ip de mi equipo
            System.out.println("Nombre de mi Equipo en la red local:"+MiEquipoLan.getHostName());
            System.out.println("IP de mi Equipo en la red local:"+MiEquipoLan.getHostAddress());


            //En INTERNET--------------------------------------------
            System.out.println("\n********* INTERNET************");
            /*
            Obtiene las direcciones IP de los dominios dados.
            Esto simula cómo un navegador resuelve nombres de dominio a direcciones IP.
            */
            //Obtener objeto InetAddress  de www.rediris.es
            InetAddress wwwrediris = InetAddress.getByName("www.rediris.es");
            //Obtener objeto InetAddress  de ftp.rediris.es
            InetAddress ftprediris = InetAddress.getByName("ftp.rediris.es");

            //Obtiene y muestra la IP del nombre de dominio
            System.out.println("IP de www.rediris.es:"+wwwrediris.getHostAddress());
            System.out.println("IP de ftp.rediris.es:"+ftprediris.getHostAddress());


            //encapsula google.com
            InetAddress[] matrizAddress = InetAddress.getAllByName("www.javatpoint.com");

            //Obtiene y muestras todas las IP asociadas a ese host
            System.out.println("Imprime todas las IP disponibles para www.javatpoint.com: ");
            for (int i = 0; i < matrizAddress.length; i++) {
                System.out.println(matrizAddress[i].getHostAddress());
            }

        } catch (UnknownHostException e) {
            System.out.println(e);
            System.out.println(
                    "Parece que no estás conectado, o que el servidor DNS no ha "
                            + "podido tramitar tu solicitud");
        }
    }
}
