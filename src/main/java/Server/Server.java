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
public class Server{
    
    private ServerSocket server;
    private Socket connection;
    private final int PORT = 1234;
    private static Cinema cinema;
    
    public void runServer(){
        try {
            server = new ServerSocket(PORT);
            while(true){
                waitForConnection();
                ServerThread thread = new ServerThread(connection, cinema);
                thread.start();
            }
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
    
    private void closeConnection(){
        System.out.println("Cerrando conexión...");
        try {
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
            new Server().runServer();
        } catch (CinemaException ex) {
            ex.printStackTrace();
        }
    }
}
