package org.example.Tema5.Act3Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
/*
* correo electrónico con adjunto y a varios destinatarios que tu quieras.
* Hace uso de SSL, es = que el del ejemplo pero con lo de arriba
 * */
public class Ejercicio1 {
    //cuenta de usuario en gmail.com
    private static final String cuentaUsuario = "";

    //la contraseña generada desde la configuración de Google
    private static final String password = "";

    //direcciones de correo de los destinatarios (separadas por comas si hay varios)
    private static final String mailDestinatario = "destino1@gmail.com,destino2@gmail.com";

    //ruta del archivo adjunto
    private static final String rutaAdjunto = "C:/ruta/del/archivo.pdf";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");

        //Para transmisión segura a través de TLS
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        //abre una nueva sesión contra el servidor basada en:
        //el usuario, la contraseña y las propiedades especificadas
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(cuentaUsuario, password);
                    }
                });

        try {
            // Crea el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));

            // Múltiples destinatarios separados por comas
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestinatario));

            // Asunto
            message.setSubject("Holi");

            // Parte del mensaje de texto
            MimeBodyPart textoParte = new MimeBodyPart();
            textoParte.setText("Emosido engañado");

            // Parte del archivo adjunto
            MimeBodyPart adjuntoParte = new MimeBodyPart();
            FileDataSource fuenteAdjunto = new FileDataSource(rutaAdjunto);
            adjuntoParte.setDataHandler(new DataHandler(fuenteAdjunto));
            adjuntoParte.setFileName(fuenteAdjunto.getName());

            // Combina las partes en un solo mensaje
            Multipart multiparte = new MimeMultipart();
            multiparte.addBodyPart(textoParte);
            multiparte.addBodyPart(adjuntoParte);

            // Establece el contenido del mensaje
            message.setContent(multiparte);

            // Enviar mensaje
            Transport.send(message);
            System.out.println("Enviado!");

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
