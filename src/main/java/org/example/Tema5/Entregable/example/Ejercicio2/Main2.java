package org.example.Tema5.Entregable.example.Ejercicio2;

import javax.activation.DataHandler;
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



public class Main2 {
    private static final Scanner entrada = new Scanner(System.in);
    private static final Properties properti = new Properties();
    private static String cuentaUsuario = "";
    private static String password = "";

    public static void main(String[] args) {
        String opcion;
        do {
            menu();
            System.out.println("Elija una opcion del menu: ");
            opcion = entrada.nextLine();
            //entrada.nextLine();
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
            properti.load(new FileInputStream("src/main/resources/configuracion.properties"));
            cuentaUsuario = properti.getProperty("email");
            password = properti.getProperty("password2");

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
            Session session = Session.getDefaultInstance(properti);

            Store store = null; //protocolo es ”imaps”
            store = session.getStore("imaps");
            store.connect(properti.getProperty("imapHost"), cuentaUsuario, password); //host es "pop.gmail.com" o "imap.gmail.com"

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            //recogemos los correos en una lusta
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

        } catch (MessagingException e) {
            System.out.println("Error al leer el email : " + e.getMessage());
        }


    }
    public static void enviarCorreo() {
        try {
            confProperties();
            Session session = Session.getDefaultInstance(properti,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(cuentaUsuario, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);

            System.out.println("Introduce el destinatario");
            String destinatario = entrada.nextLine();
            System.out.println("Introduce el asunto ");
            String asunto = entrada.nextLine();
            System.out.println("Introduce el cuerpo del mensaje");
            String cuerpo = entrada.nextLine();
            System.out.println("Introduce el nombre del fichero");
            String ficheroRuta = entrada.nextLine();

            message.setFrom(new InternetAddress(cuentaUsuario));
            //destinatario
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(cuentaUsuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            //asunto
            message.setSubject(asunto);

            //para la parte del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            //cuerpo del mensaje
            messageBodyPart.setText(cuerpo);

            //fichero
            //Fichero en la ruta de mi proyecto
            MimeBodyPart adjuntoMime = new MimeBodyPart();
            FileDataSource source = new FileDataSource(ficheroRuta);
            adjuntoMime.setDataHandler(new DataHandler(source));
            adjuntoMime.setFileName(new File(ficheroRuta).getName());

            Multipart correoMultipart = new MimeMultipart();
            correoMultipart.addBodyPart(messageBodyPart);
            correoMultipart.addBodyPart(adjuntoMime);
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
