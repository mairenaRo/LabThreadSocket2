/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ronny Mairena B64062
 */
public class ServerThread extends Thread{
    
    private Socket connection;
    private DataOutputStream output;
    private DataInputStream input;
    private Cinema cinema;
    private final int PRICE = 2500;

    public ServerThread(Socket connection) {
        this.connection = connection;
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
            output.writeUTF(movie.getDescription());
            for (int i = 0; i<= input.readInt(); i++){
                int c = 0;
                int f = 0;
                boolean bought;
                do {
                    output.writeUTF("Ingrese por favor la columna y luego la fila.\nL = Libre\nO = Ocupados\nS = Salvados\n" + movie.getSeatsString() + "\nIngrese la COLUMNA:");
                    c = input.readInt();
                    output.writeUTF("Ingrese por favor la columna y luego la fila.\nL = Libre\nO = Ocupados\nS = Salvados\n" + movie.getSeatsString() + "\nIngrese la FILA:");
                    f = input.readInt();
                    bought = movie.setSeat(f, c);
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
            movie.saveSeatsSelection();
            output.writeUTF("¡Compra exitosa!\n"+txt+"\nPrecio total a pagar: "+totalPrice);
        }
    }
    
    private void closeConnection(){
        System.out.println("Cerrando conexión...");
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Conexión cerrada");        
    }

    @Override
    public void run() {
        try {
            cinema = new Cinema();
            processConnection();
            getStreams();
            closeConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CinemaException ex) {
            ex.printStackTrace();
        }
    }

}
