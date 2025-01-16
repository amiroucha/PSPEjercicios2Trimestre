package org.example.Tema6.Act2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
    private final char[][] tablero ={
            {'-','-','-','-'},
            {'-','-','-','-'},
            {'-','-','-','-'}, //slas fichas caen para abajo ACUERDATEEEEEEEEEE
    };
    //cliente = X   Servidor = O

    //compruebo que hay al menos un hueco y que se puede colocar la ficha--------------------------------------------------------
    private boolean columnaHueco(int columna){
        return this.tablero[0][columna] == '-'; //True : hay hueco, false : no hay
        //booleano que devuelve true si se pudo colocar y false si no
    }

    //coloca la ficha en un lugar vacio-----------------------------------------------------------
    //a este metodo juega el cliente
    public boolean colocarFicha(int columna){
        if(columnaHueco(columna)){//si no esta llena esa columna
            //solo lo tengo que comprobar en esa columna
            for (int j = tablero.length-1; j>=0; j--){//recorro de abajo arriba
                //3 -- 2 -- 1 solo hay 3 filas
                if(tablero[j][columna] == '-'){ //si hay hueco se lo asigno
                    tablero[j][columna] = 'X';
                    return true;//se ha podido colocar la ficha
                }
            }
        }
        return false;//no hay hueco en esa columna
    }
    //cuando juega la maquina---------------------------------------------------------------------------
    //solo lo llama la maquina
    public int juegaMaquina() {
        //voy a meter las columnas disponibles con huecos en una lista
        List<Integer> columnasConHueco = new ArrayList<>();
        for (int i = 0; i < tablero[0].length; i++) {
            if (columnaHueco(i)) { // Si la columna tiene al menos un hueco
                columnasConHueco.add(i); // Añadimos la columna a la lista de disponibles
            }
        }
        //recorro esa lista para colocar de forma random la ficha
        if (!columnasConHueco.isEmpty()) {
            Random random = new Random();
            //consigo aleatoriamente el indice de una columna donde se que hay hueco
            int columnaRandom = columnasConHueco.get(random.nextInt(columnasConHueco.size()));
            // Seleccionamos aleatoriamente una columna
            //solo recorremos las filas, ya que las columnas las tnemos en la lista
            for (int j = tablero.length - 1; j >= 0; j--) { // Recorro de abajo hacia arriba
                if (tablero[j][columnaRandom] == '-') { // Busca hueco disponible esa columna seleciconada
                    tablero[j][columnaRandom] = 'O'; // Coloco la ficha de la máquina
                    return columnaRandom; // devuelvo donde he colocado la ficha
                }
            }
        }
        return -1; // No hay columnas con espacio disponible
    }
    //-----------------------------------------------------------------------------------------
    public String imprimirTablero(){
        StringBuilder tableroString = new StringBuilder();
        for (int i=0; i< tablero.length; i++){ //recorre las filas
            for (int j=0; j< tablero[i].length; j++){//recorre las columnas
                tableroString.append(tablero[i][j]).append(" ");
            }
            tableroString.append("\n");//salto línea en cada fila
        }
        return tableroString.toString();
    }
    //-----------------------------------------------------------------------------------------
    public boolean comprobarVictoria(char jugador){
        //compruebo las filas
        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[i].length -2; j++){//solo quiero que me de la vuelta 2 vez por cada fila
                //si entra en el if ha ganado
                if (tablero[i][j] == jugador && tablero[i][j+1] == jugador && tablero[i][j+2] == jugador){
                    return true;
                }
            }
        }
        //compruebo las columnas
        for (int i = 0; i < tablero.length-2; i++){
            for (int j = 0; j < tablero[i].length ; j++){//no resto poque necesito que me recorra todas las columnas
                //si entra en el if ha ganado
                if (tablero[i][j] == jugador && tablero[i+1][j] == jugador && tablero[i+2][j] == jugador){
                    //aqui no tengo que asignar la posicion al jugador ya que antes estoy comprobando justo eso
                    return true;
                }
            }
        }
        //compruebo las diagonales
        if(tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador){ //1 diagonal de izq a derecha
            return true;
        }else if(tablero[0][1] == jugador && tablero[1][2] == jugador && tablero[2][3] == jugador){//2 diagonal de izq a derecha
            return true;
        }else if(tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador){//1 diagonal de derecha a izquierda
            return true;
        }else if(tablero[0][3] == jugador && tablero[1][2] == jugador && tablero[2][1] == jugador){//2 diagonal de derecha a izquierda
            return true;
        }
        return false;//no hay ganador
    }
    //-----------------------------------------------------------------------------------------
    //es empate cuando no hay ningun ganador y esta toddo el tablero lleno
    public boolean empate(){
        for(int i = 0; i< tablero.length; i++){
            for (int j = 0; j < tablero[i].length ;j++)
            {
                if(tablero[i][j] == '-') {//no es empate porque queda algun heuco en el tablero
                    return false;
                }
            }
        }
        return true;
    }
}
