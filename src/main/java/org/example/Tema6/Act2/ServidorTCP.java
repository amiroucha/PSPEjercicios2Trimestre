package org.example.Tema6.Act2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorTCP extends Thread{
    static final int Puerto = 2000;
    private final Socket socketCliente;
    Tablero partida;
    private static final Logger log = LogManager.getLogger(ServidorTCP.class);

    //Constructor
    public ServidorTCP(Socket socketCliente, String nombre, Tablero partida) {
        super(nombre);
        this.socketCliente = socketCliente;
        this.partida = partida;
    }

    @Override
    public void run() {//aqui la logica de la comunicacion
        log.info("Se ha conectado un nuevo jugador!! -->  ".concat(getName()));
        //System.out.println("Se ha conectado un nuevo jugador!! -->   "+getName());
        try {
            //preparo el flujo de salida
            DataOutputStream flujoSalida = new DataOutputStream(socketCliente.getOutputStream());
            //preparo el flujo de entrada
            DataInputStream flujoEntrada = new DataInputStream(socketCliente.getInputStream());
            //Bienvenida
            flujoSalida.writeUTF("Servidor del juego: Bienvenido "+getName()+" .Envia una columna");

            int numColumna; //columna que nos manda el cliente de tipo int
            //imprimo el tablero
            log.info(getName().concat(":-->\n").concat(partida.imprimirTablero()));
            //System.out.println(getName()+":-->\n"+partida.imprimirTablero());

            while (true){//mientras que no acabe la partida hago cosas
                //tengo que mandar 2 mensajes
                //mando el tablero
                flujoSalida.writeUTF(partida.imprimirTablero());//este manda el tablero
                flujoSalida.writeUTF("Introduce una columna (0-3):"); //envio la peticion

                //lee el numero de columna mandado por el cliente y lo guarda
                String entradaColumna = flujoEntrada.readUTF();
                try {
                    numColumna = Integer.parseInt(entradaColumna); //parseo la entrada

                    //imprimo el estado de la partida
                    log.info(getName().concat(":--> Seguimos jugando"));
                    log.info(getName().concat(":--> ha elegido la columna: ").concat(String.valueOf(numColumna)));
                    //System.out.println(getName()+":--> Seguimos jugando");
                    //System.out.println(getName() + ":--> ha elegido la columna: " + numColumna);

                    //en caso de que el numero sea incorrecto o que no empiece el cliente
                    if (numColumna < 0 || numColumna > 3 || !partida.colocarFicha(numColumna)) { // validación de columna
                        //envio que la columna no ha sido valida
                        flujoSalida.writeUTF("Has introducido un dato de columna no valido\nFin de la partida");
                        flujoSalida.flush(); // Asegurar que el mensaje se envíe antes de cerrar el socket
                        //imprimo el estado de la partida
                        log.warn(getName().concat(": --> pierde por jugada inválida. Fin de la partida"));
                        //System.out.println(getName() + ": --> pierde por jugada inválida. Fin de la partida");
                        socketCliente.close();
                        break;
                    }
                }catch ( NumberFormatException n){
                    log.error(n.getMessage());
                    log.error("Fin de la partida. Error en la entrada del cliente : -->".concat(getName()));
                    flujoSalida.writeUTF("Entrada inválida de columna. Fin de la partida.");
                    //System.out.println("Error en la entrada del cliente : -->" + getName()+". Fin de la partida.");
                    break;
                }
                //imprimir el esatdo de la partida
                log.info(getName().concat(":--> Despues de la jugada del cliente\n").concat(partida.imprimirTablero()));
                //System.out.println(getName()+":--> Despues de la jugada del cliente\n"+partida.imprimirTablero());

                //comprobar si gano cliente?, si gano servidor? si hay empate?

                // gana cliente------------------------------------------
                if (partida.comprobarVictoria('X')){
                    flujoSalida.writeUTF(partida.imprimirTablero());
                    log.info(getName().concat(":-->\n").concat(partida.imprimirTablero()));
                    //System.out.println(getName()+":-->\n"+partida.imprimirTablero());
                    log.warn(getName().concat("Gano el jugador "));
                    //System.out.println("Gano el jugador "+getName());
                    //servidor de juegos:
                    flujoSalida.writeUTF("Servidor de juegos:\nHAS GANADO\nFin de la partida");
                    break;
                }
                //hay empate despues de jugar el servidor---------------------------------------------------------------
                if(partida.empate()){ //es empate
                    flujoSalida.writeUTF(partida.imprimirTablero());//mando el tablero
                    log.info(getName().concat(":-->\n").concat(partida.imprimirTablero()));
                   // System.out.println(getName()+":-->"+partida.imprimirTablero());//imprimo el tablero
                    log.warn("Empate con el jugador :-->".concat(getName()));
                  //  System.out.println("Empate con el jugador :-->"+getName()); //imprimo el estado del juego
                    //servidor de juegos: mando el estado
                    flujoSalida.writeUTF("Servidor de juegos:\n empate con el servidor\nFin de la partida");
                    break;
                }
                //si no entra en ninguno de los anteriores if, no hay ganador ni empate, se sigue
                flujoSalida.writeUTF("Servidor de juegos:\nSeguimos jugando");

                //turno del servidor--------------------------------------------------------
                //juega el servidor
                int columnaServidor = partida.juegaMaquina();//consigo el lugar donde coloca la ficha
                //ha conseguido colocar su ficha e imprimo donde la puso
                log.info(getName()+": Despues de la jugada del servidor: ".concat(String.valueOf(columnaServidor)).concat("\n").concat(partida.imprimirTablero()));
                //System.out.println(getName()+": Despues de la jugada del servidor: "+ columnaServidor+"\n"+partida.imprimirTablero());

                //ha ganado el servidor
                if (partida.comprobarVictoria('O')) {
                    flujoSalida.writeUTF(partida.imprimirTablero());//mando el tablero

                    System.out.println(getName()+":-->\n"+partida.imprimirTablero()); //imrpimo el tablero
                    //servidor de juegos mando el estado
                    flujoSalida.writeUTF("Servidor de juegos:\nEl servidor gana. Fin de la partida.");
                    //imprimo el estado
                    log.warn("El servidor ha ganado a : --> ".concat( getName()));
                   // System.out.println("El servidor ha ganado a : -->" + getName());
                    break;
                }
                //empate despues de jugar el servidor
                if (partida.empate()){
                    flujoSalida.writeUTF(partida.imprimirTablero());//mando el tablero
                    //servidor de juegos madno el estado
                    flujoSalida.writeUTF("Servidor de juegos:\nEmpate. Fin de la partida.");
                    //imprimo el estado
                    log.warn(getName().concat(":-->Empate después de la jugada del servidor."));
                    //System.out.println(getName()+":-->Empate después de la jugada del servidor.");
                    break;
                }
            }
            socketCliente.close();
        } catch (IOException e) {
            log.error("Error con el cliente : -->".concat(getName()));
            log.error(e.getMessage());

            //System.err.println("Error con el cliente : -->" + getName() + ": " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        //creamos el socket con el puerto a utilizar
        try(ServerSocket skServidor = new ServerSocket(Puerto)){
            log.info("Escucho al puerto ".concat(String.valueOf(Puerto)));
            //System.out.println("Escucho al puerto " + Puerto); //muestro que puerto escucho
            int nCli=1;
            while(true){  //siempre se esta ejecutando
                //espera la conexion al cliente
                Socket socketCliente = skServidor.accept();
                Tablero partida = new Tablero();
                log.info("Soy el servidorTCP esperando a que alguien se conecte");
                //System.out.println("Soy el servidorTCP esperando a que alguien se conecte");//enseña que cliente se conecto
                //inciio el hilo
                new ServidorTCP(socketCliente, "Cliente "+nCli, partida).start();
                nCli++; //aumento el numero de clientes
            }
        }catch (IOException e) {
            log.error("Error en el main : ");
            log.error(e.getMessage());

            throw new RuntimeException(e);
        }
    }

}
