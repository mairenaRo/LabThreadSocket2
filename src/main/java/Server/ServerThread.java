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
/**
 *
 * @author Ronny Mairena B64062
 */
public class ServerThread extends Thread{
    
    private Socket connection;
    private DataOutputStream output;
    private DataInputStream input;
    private Cinema cinema;

    public ServerThread(Socket connection) {
        this.connection = connection;
    }
    
    private void getStreams() throws IOException{
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
        input = new DataInputStream(connection.getInputStream());
    }
    
    private void processConnection() throws IOException{
        Movie movie;
        String message = "";
        String txt = "";
        output.writeUTF("Bienvenido al Cine\nDijite la pelicula que desea ver."+cinema.getMoviesString()+"\nDijite S para salir");
        message = input.readUTF();
        if (!message.equals("s")){
            movie = cinema.getMovie(Integer.parseInt(message));
            output.writeUTF(movie.getDescription());
            for (int i = 0; i<= input.readInt(); i++){
                int c = 0;
                int f = 0;
                output.writeUTF("Ingrese por favor la columna y luego la fila.\nL = Libre\nO = Ocupados\nS = Salvados\n"+movie.getSeatsString()+"\nIngrese la columna:");
                c = input.readInt();
            }
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
            getStreams();
            processConnection();
            closeConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
