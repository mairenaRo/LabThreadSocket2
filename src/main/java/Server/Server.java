/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ronny Mairena B64062
 */
public class Server implements Runnable{
    
    private ServerSocket server;
    private Socket connection;
    private DataOutputStream output;
    private DataInputStream input;
    private final int PORT = 1234;
    private static Cinema cinema;
    private final int PRICE = 2500;
    
    public void runServer(){
        try {
            server = new ServerSocket(PORT);
            waitForConnection();
            getStreams();
            processConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
    
    private void waitForConnection() throws IOException{
        System.out.println("Esperando conexión con el cliente...");
        connection = server.accept();
        System.out.println("Conexión completada\nConexión establecida con "+connection.getInetAddress().getHostAddress());
    }
    
    private void getStreams() throws IOException{
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
        input = new DataInputStream(connection.getInputStream());
    }
    
    private void processConnection() throws IOException{
        int totalPrice = 0; 
        Movie movie;
        String message = "";
        String txt = "";
        message = "Bienvenido al Cine\nDijite la pelicula que desea ver."+cinema.getMoviesString()+"\nDijite S para salir";
        output.writeUTF(message);
        message = input.readUTF();
        if (!message.equals("s")){
            movie = cinema.getMovie(Integer.parseInt(message));
            output.writeUTF(movie.getDescription()+"\nDigite la cantidad de hacintos que desea");
            int tickets = input.readInt();
            for (int i = 0; i< tickets; i++){
                int c = 0;
                int f = 0;
                boolean bought = true;
                do {
                    output.writeUTF("Ingrese por favor la columna y luego la fila.\nL = Libre\nO = Ocupados\nS = Salvados\n" + movie.getSeatsString() + "\nIngrese la COLUMNA:");
                    c = input.readInt();
                    output.writeUTF("Ingrese por favor la columna y luego la fila.\nL = Libre\nO = Ocupados\nS = Salvados\n" + movie.getSeatsString() + "\nIngrese la FILA:");
                    f = input.readInt();
                    bought = movie.setSeat(c, f);
                    output.writeBoolean(bought);
                    if (!bought) {
                        output.writeUTF("Asiento no seleccionable");
                    }else{
                        txt+=""+c+"-"+f+"\n";
                    }
                } while (!bought);
                output.writeUTF("Se han apartado los asientos\n"+txt);
                totalPrice += PRICE;
            }
            output.writeUTF("Asientos seleccionados"+movie.getSeatsString());
            output.writeUTF("Desea confirmar su compra de "+tickets);
            int a = input.readInt();
            if(a<2){
                movie.saveSeatsSelection();
                output.writeUTF("¡Compra exitosa!\n"+txt+"\nPrecio total a pagar: "+totalPrice);
            }else{
                output.writeUTF("¡Compra cancelada!");
            }
        }
    }
    
    private void closeConnection(){
        System.out.println("Cerrando conexión...");
        try {
            output.close();
            input.close();
            connection.close();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Conexión cerrada");        
    }
    public static void main(String[] args) {
        try {
            cinema = new Cinema();
            //new Server().runServer();
            Thread thread;
            thread = new Thread(new Server());
            thread.start();
        } catch (CinemaException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(PORT);
            while(true){
                waitForConnection();
                getStreams();
                processConnection();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        closeConnection();
    }
}
