package org.example.EjemploGlobal;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Properties;
import java.util.logging.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.util.regex.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String FORMATOFICHERO = "^[a-zA-Z0-9_-]+\\.(txt|zip|png)$"; // Regex para nombres de archivo válidos
    private static final String FTP_SERVER = "localhost";
    private static final String FTP_USER = "chai";
    private static final String FTP_PASS = "admin";
    private static final String EMAIL_FROM = "pspchaima@gmail.com";
    private static final String EMAIL_TO = "1925046@alu.murciaeduca.es";
    private static final String EMAIL_PASSWORD = "akbr scda yohw jqsx";

    public static void main(String[] args) {
        try {
            // Configurar logger
            Handler fileHandler = new FileHandler("logs.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            // Pedir nombre del archivo
            System.out.println("Introduce el nombre del archivo a descargar desde FTP:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String fileName = reader.readLine();

            // Validar nombre con expresión regular
            if (!Pattern.matches(FORMATOFICHERO, fileName)) {
                logger.warning("Nombre de archivo no válido: " + fileName);
                System.out.println("El nombre del archivo no cumple con el formato esperado.");
                return;
            }

            logger.info("Archivo válido: " + fileName);

            // Descargar el archivo desde FTP
            File downloadedFile = downloadFromFTP(fileName);

            // Generar clave y firma digital
            KeyPair keyPair = generateKeyPair();
            byte[] signature = signFile(downloadedFile, keyPair.getPrivate());

            // Enviar el archivo por email con la firma
            sendEmailWithAttachment(downloadedFile, signature, keyPair.getPublic());

            logger.info("Proceso completado correctamente.");

        } catch (Exception e) {
            logger.severe("Error en la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para descargar el archivo desde un servidor FTP
    private static File downloadFromFTP(String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(FTP_SERVER);
        ftpClient.login(FTP_USER, FTP_PASS);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            boolean success = ftpClient.retrieveFile(fileName, fos);
            if (!success) {
                logger.severe("No se pudo descargar el archivo desde FTP.");
                throw new IOException("Error al descargar archivo desde FTP.");
            }
        }

        ftpClient.logout();
        ftpClient.disconnect();
        logger.info("Archivo descargado desde FTP: " + fileName);
        return file;
    }

    // Método para generar un par de claves RSA
    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Método para firmar un archivo
    private static byte[] signFile(File file, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        signature.update(fileBytes);
        byte[] signedBytes = signature.sign();

        logger.info("Archivo firmado correctamente.");
        return signedBytes;
    }

    // Método para enviar un correo electrónico con el archivo adjunto y la firma digital
    private static void sendEmailWithAttachment(File file, byte[] signature, PublicKey publicKey) throws MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO));
        message.setSubject("Archivo seguro con firma digital");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Adjunto el archivo con su firma digital.");

        // Adjuntar el archivo
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(file);

        // Adjuntar la firma digital
        File signatureFile = new File(file.getName() + ".sig");
        Files.write(signatureFile.toPath(), signature);
        MimeBodyPart signaturePart = new MimeBodyPart();
        signaturePart.attachFile(signatureFile);

        // Adjuntar la clave pública
        File publicKeyFile = new File("publicKey.key");
        Files.write(publicKeyFile.toPath(), publicKey.getEncoded());
        MimeBodyPart publicKeyPart = new MimeBodyPart();
        publicKeyPart.attachFile(publicKeyFile);

        // Unir todo
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);
        multipart.addBodyPart(signaturePart);
        multipart.addBodyPart(publicKeyPart);
        message.setContent(multipart);

        // Enviar correo
        Transport.send(message);
        logger.info("Correo enviado con el archivo y firma digital.");
    }
}