/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ronny Mairena B64062
 */
public class Server {
    private ServerSocket server;
    private Socket connection;
    private final int PORT = 1234;
    
    public void runServer(){
        try {
            server = new ServerSocket(PORT);
            waitForConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        
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
        new Server().runServer();
    }
}
