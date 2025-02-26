package org.example.Tema5.MaquinaVirtual;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

class Main {
    //Al tener aquí lo principal hace más sencillo hacer el ejercicio
    private static final Properties prop = new Properties();
    private static String cuentaUsuario;
    private static String password;
    private static String imapHost;
    private static String smtpHost;

    private static String puerto;
    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        // Cargar configuraciones desde el archivo properties
        cargarPropiedades();

        String opcion;
        do {
            mostrarMenu();
            opcion =sc.nextLine();
            elegirOpcion(opcion);
        }while (!Objects.equals(opcion, "3"));

    }
    // Muestra el menú de opciones
    public static void mostrarMenu(){

        System.out.println("MENU");
        System.out.println("1. Leer correo");
        System.out.println("2. Enviar Email");
        System.out.println("3. Salir");

    }
    // Ejecuta la opción elegida por el usuario
    public static void elegirOpcion(String opcion)  {

        switch(opcion){
            case "1":
                leerEmail();
                break;
            case "2":
                enviarEmail();
                break;
            case "3":
                System.out.println("Saliendo del programa");
                break;

            default:
                System.out.println("Opcion no valida");
        }
    }
    // Carga las propiedades desde un archivo de configuración
    public static void cargarPropiedades() {
        try {
            prop.load(new FileInputStream("src/main/resources/configuracion.properties"));
            cuentaUsuario = prop.getProperty("email");
            password = prop.getProperty("password");
            imapHost = prop.getProperty("imap_host");
            smtpHost = prop.getProperty("smtp_host");
            puerto = prop.getProperty("smtp_port");
        } catch (IOException ex) {
            System.out.println("Error cargando configuración: " + ex.getMessage());
        }
    }

    public static void leerEmail() {
        try {
            Properties prop = new Properties();
            prop.put("mail.store.protocol", "imaps");
            prop.put("mail.imap.host", imapHost);
            prop.put("mail.imap.port", "143");  // IMAP sin SSL
            prop.put("mail.imap.auth", "true");

            Session session = Session.getInstance(prop, null);
            Store store = session.getStore("imap");

            // Conectar sin SSL
            store.connect(imapHost, cuentaUsuario, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            FlagTerm filtroNoLeidos = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] mensajesNoLeidos = inbox.search(filtroNoLeidos);

            System.out.println("-------------------------------------------------");
            for (Message message : mensajesNoLeidos) {
                System.out.println("Remitente: " + Arrays.toString(message.getFrom()));
                System.out.println("Asunto: " + message.getSubject());
                System.out.println("Fecha: " + message.getSentDate());
                System.out.println("-------------------------------------------------");
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println("Error leyendo correos: " + e.getMessage());
        }
    }



    public static void enviarEmail() {

        try {
            // Solicitar información al usuario
            System.out.print("Destinatario: ");
            String destinatario = sc.nextLine();
            System.out.print("Asunto: ");
            String asunto = sc.nextLine();
            System.out.print("Mensaje: ");
            String mensaje = sc.nextLine();
            System.out.print("Ruta del archivo adjunto: ");
            String rutaFichero = sc.nextLine();

            // Configuración del servidor SMTP
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "false");
            //prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", smtpHost);
            prop.put("mail.smtp.port",puerto );

            // Autenticación de la sesión de correo
            Session session = Session.getInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(cuentaUsuario, password);
                }
            });

            // Crear mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(cuentaUsuario));
            message.setSubject(asunto);

            // Cuerpo del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mensaje);

            // Se adjunta el archivo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(rutaFichero);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(new File(rutaFichero).getName());

            // Combinar cuerpo y adjunto en un solo mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);
            System.out.println("Correo enviado correctamente.");
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }
    }
}
