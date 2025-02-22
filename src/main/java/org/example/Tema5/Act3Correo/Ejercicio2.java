package org.example.Tema5.Act3Correo;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataSource;
public class Ejercicio2 {
    //cuenta de usuario en gmail.com
    private static final String cuentaUsuario = "redes@psp.local";

    //la contraseña que pones aquí no es la de tu correo, sino la que generas desde Gmail para que se conecte tu aplicación
  /*Ve a tu cuenta de Google.
  Selecciona Seguridad.
  En "Iniciar sesión en Google", selecciona Verificación en dos pasos.
  En la parte inferior de la página, selecciona Contraseñas de aplicación.
  Introduce un nombre que te ayude a recordar dónde utilizarás la contraseña de aplicación.
  Selecciona Generar.
  Para introducir la contraseña de aplicación, sigue las instrucciones que aparecen en pantalla. La contraseña de aplicación es el código de 16 caracteres que se genera en tu dispositivo.
  Selecciona Hecho.*/
    private static final String password = "redes";
    //dirección de correo del destinatario58
    private static final String mailDestinatario = "redes@psp.local";
    //redes@psp.local email del servidor de la máquina virtual

    /**
     ****************************************************************************
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        //valora propiedades para construir la sesión con el servidor
        Properties props = new Properties();
        //servidor SMTP
        //GMAIL --> smtp.gmail.com
        //local mira la ip con ip address
        //props.put("mail.smtp.host", "192.168.1.3");
        props.put("mail.smtp.host", "172.10.0.117");

        //identificación requerida
        props.put("mail.smtp.auth", "true");

        //Descomenta las siguientes líneas para enviar por SMTPS (SMTP sobre SSL)

        //puerto para el socket de sesión
        //props.put("mail.smtp.socketFactory.port", "465");
        //tipo de socket
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //puerto smtp
        //props.put("mail.smtp.port", "465");


        //Para transmisión segura a través de TLS
//        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "25");
        //El puerto SMTP seguro de Google

        //Sin seguridad
        //props.put("mail.smtp.port", "25");

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
//            ------------------------MENSAJE SOLO------------
            //compone el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestinatario));
//            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse( "5986905@alu.murciaeduca.es"));
            //asunto
            message.setSubject("Holi");
            //cuerpo del mensaje
//            message.setText("Emosido engañado");
            //envía el mensaje, realizando conexión, transmisión y desconexión
//            Transport.send(message);
            //lo da por enviado
//            System.out.println("Enviado!");



//            ---------------CON ADJUNTO-------------------------

            Multipart multipart = new MimeMultipart();

            BodyPart textPart=new MimeBodyPart();
            textPart.setText("Emosido engañado");
            multipart.addBodyPart(textPart);

            BodyPart archivo = new MimeBodyPart();
            String filename="./test.txt";
            String name="password.txt";
            DataSource source = new FileDataSource(filename);
            archivo.setDataHandler(new DataHandler(source));
            archivo.setFileName(name);
            multipart.addBodyPart(archivo);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Enviado!");

        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

}
