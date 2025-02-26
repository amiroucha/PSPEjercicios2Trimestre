package org.example.Tema7;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Scanner;

public class firmaArchivo {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        // Ruta del archivo a firmar
        System.out.println("Fichero a firmar existente");
        String filePath = leer.nextLine();
        File fichero = new File(filePath);

        //compruebo que el fichero exista
        while (!fichero.exists()) {
            System.out.println("El fichero no existe, intentalo de nuevo");
            filePath = leer.nextLine();
            fichero =  new File(filePath);
        }
        // Genera un par de claves
        KeyPair clave = generarClaves();

        // Firma el archivo y guarda la firma
        try {
            byte[] firma = hacerFirma(filePath, clave.getPrivate());
            // Guarda la firma en un archivo
            try (FileOutputStream fos = new FileOutputStream(filePath+"firmado.txt")) {
                fos.write(firma);
            }
            System.out.println("Firma generada y guardada en " +filePath+"firmado.txt");

            // Verifica la firma
            if (verificarFirma(filePath, clave.getPublic(), firma)) {
                System.out.println("Firma verificada correctamente.");
            } else {
                System.out.println("Firma incorrecta.");
            }
        } catch (Exception e) {
            System.out.println("Error al generar el firma: " + e.getMessage());
        }
    }

    // Método que genera un par de claves (pública y privada)
    public static KeyPair generarClaves() {
        KeyPair claves = null;
        try {
            KeyPairGenerator generador = KeyPairGenerator.getInstance("DSA");
            generador.initialize(512);
            claves = generador.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No se puede generar el clave: " + e.getMessage());
        }
        return claves;
    }

    // Método que realiza la firma digital del archivo
    public static byte[] hacerFirma(String filePath, PrivateKey clave) throws Exception {
        // Lee el contenido del archivo
        File file = new File(filePath);
        byte[] datos = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(datos);
        }

        // Crea el objeto tipo Signature con algoritmo DSA
        Signature firma = Signature.getInstance("DSA");
        // Inicializa la firma con la clave privada
        firma.initSign(clave);
        // Actualiza el resumen del mensaje
        firma.update(datos);
        // Obtiene la firma digital
        byte[] firmado = firma.sign();

        // Imprime la firma en formato Base64
        Base64.Encoder encoder = Base64.getEncoder();
        String cadena = encoder.encodeToString(firmado);
        System.out.println("Firma: " + cadena);

        return firmado;
    }

    // Método que verifica la firma digital del archivo
    public static boolean verificarFirma(String filePath, PublicKey clave, byte[] textoFirmado) throws Exception {
        // Lee el contenido del archivo
        File file = new File(filePath);
        byte[] datos = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(datos);
        }

        // Crea el objeto tipo Signature con algoritmo DSA
        Signature firma = Signature.getInstance("DSA");
        // Inicializa la verificación con la clave pública
        firma.initVerify(clave);
        // Actualiza el resumen de mensaje
        firma.update(datos);
        // Devuelve el resultado de la verificación
        return firma.verify(textoFirmado);
    }

}
/*
*
* public class EliminarFirma {
    public static void main(String[] args) {
        File archivoFirma = new File("firma.txt");

        if (archivoFirma.exists()) {
            if (archivoFirma.delete()) {
                System.out.println("La firma se ha eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar la firma.");
            }
        } else {
            System.out.println("El archivo de firma no existe.");
        }
    }
}
*
* */