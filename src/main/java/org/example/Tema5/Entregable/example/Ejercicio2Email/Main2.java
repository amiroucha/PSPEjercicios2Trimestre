package org.example.Tema5.Entregable.example.Ejercicio2Email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.search.FlagTerm;

//funcionaa bien, esta corregido

public class Main2 {
    private static final Scanner entrada = new Scanner(System.in);
    private static final Properties properti = new Properties();
    private static String cuentaUsuario = "";
    private static String password = "";
    private static Session session; // Sesión única para toda la aplicación

    public static void main(String[] args) {
        String opcion;
        do {
            menu();
            System.out.println("Elija una opcion del menu: ");
            opcion = entrada.nextLine();
            switch (opcion) {
                case "1":
                    leerCorreo();
                    break;
                case "2":
                    enviarCorreo();
                    break;
                case "3":
                    System.out.println("Saliendo del menu. Adios.");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while(!opcion.equals("3"));

    }
    private static void confProperties(){
        try {
            properti.load(new FileInputStream("src/main/java/org/example/Tema5/Entregable/example/configuracion.properties"));
            cuentaUsuario = properti.getProperty("email");
            password = properti.getProperty("password2");

            // Configura la sesión única
            session = Session.getInstance(properti, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(cuentaUsuario, password);
                }
            });
        } catch (IOException e) {
            System.out.println("Error al leer el properties de configuracion : " + e.getMessage());
        }
    }
    public static void menu() {
        System.out.println("------------------------Menu------------------------");
        System.out.println("1.Leer correo");
        System.out.println("2.Enviar correo");
        System.out.println("3.Salir");
    }
    public static void leerCorreo() {
        try {
            confProperties();

            Store store = null; //protocolo es ”imaps”
            store = session.getStore("imaps");
            store.connect(properti.getProperty("imapHost"), cuentaUsuario, password); //host es "pop.gmail.com" o "imap.gmail.com"

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Filtro para obtener solo correos NO LEÍDOS
            FlagTerm filtroNoLeidos = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] mensajesNoLeidos = inbox.search(filtroNoLeidos);

            System.out.println("-------------------------------------------------");
            for (Message message : mensajesNoLeidos) {
                if (!message.isSet(Flags.Flag.SEEN)) {// Solo mostrar correos no leídos
                    System.out.println("Remitente: " + Arrays.toString(message.getFrom()));
                    System.out.println("Asunto: " + message.getSubject());
                    System.out.println("Fecha: " + message.getSentDate());
                    System.out.println("-------------------------------------------------");
                }
            }
            inbox.close(false);
            store.close();

        } catch (MessagingException e) {
            System.out.println("Error al leer el email : " + e.getMessage());
            throw new RuntimeException();
        }


    }
    public static void enviarCorreo() {
        try {
            confProperties();

            System.out.println("Introduce el destinatario");
            String destinatario = entrada.nextLine();
            System.out.println("Introduce el asunto ");
            String asunto = entrada.nextLine();
            System.out.println("Introduce el cuerpo del mensaje");
            String cuerpo = entrada.nextLine();
            System.out.println("Introduce el nombre del fichero");
            String ficheroRuta = entrada.nextLine();

            // Crear mensaje de correo----------------------------------
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));
            //destinatario oculto
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(cuentaUsuario));
            //destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            //asunto
            message.setSubject(asunto);


            //cuerpo del mensaje------------------------------------------
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(cuerpo);

            //fichero----------------------------------------------------------
            //Fichero en la ruta de mi proyecto
            MimeBodyPart adjuntoMime = new MimeBodyPart();
            DataSource source = new FileDataSource(ficheroRuta);
            adjuntoMime.setDataHandler(new DataHandler(source));
            adjuntoMime.setFileName(new File(ficheroRuta).getName());
            //fichero------
            System.out.print("Ruta del otro archivo adjunto: ");
            String ficheroRuta2 = entrada.nextLine();
            // Se adjunta el archivo
            MimeBodyPart adjuntoMime2 = new MimeBodyPart();
            DataSource source2 = new FileDataSource(ficheroRuta2);
            adjuntoMime2.setDataHandler(new DataHandler(source2));
            adjuntoMime2.setFileName(new File(ficheroRuta2).getName());


            // Combinar cuerpo y adjunto en un solo mensaje---------------------
            Multipart correoMultipart = new MimeMultipart();
            correoMultipart.addBodyPart(messageBodyPart);
            correoMultipart.addBodyPart(adjuntoMime);
            correoMultipart.addBodyPart(adjuntoMime2);

            //asigna el contenido al mensaje
            message.setContent(correoMultipart);

            //envía el mensaje, realizando conexión, transmisión y desconexión
            Transport.send(message);
            System.out.println("Se ha enviado el correo");

        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }


    }
}

/* Cargas todos los mensajes y luego compruebas si son no leídos.
//recogemos los correos en una lista
Message[] correosLista = inbox.getMessages();//consigue todos los mensajes
            System.out.println("-------------Emails-------------");
boolean encontrado = false;
            for (Message mensaje : correosLista) {
        //Solo enseñamos los que no han sido leidos
        if (!mensaje.isSet(Flags.Flag.SEEN)) {//en caso de no haber sido leidos
        System.out.println("Remitente del email: "+ Arrays.toString(mensaje.getFrom()));
        System.out.println("Asunto: "+mensaje.getSubject());
        System.out.println("Fecha de envío: "+mensaje.getSentDate());
encontrado = true;
        System.out.println("---------------------------------------------------------------");
                }
                        }
                        if(!encontrado)System.out.println("No hay emails sin leer");

*/
