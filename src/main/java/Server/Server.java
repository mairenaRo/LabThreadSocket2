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
import javax.swing.JOptionPane;

/**
 *
 * @author Ronny Mairena B64062
 */
public class Server {
    private ServerSocket server;
    private Socket connection;
    private final int PORT = 1234;
    private DataOutputStream output;
    private DataInputStream input;
    private boolean exit;
    
    public void runServer(){
        try {
            server = new ServerSocket(PORT);
            waitForConnection();
            getStreams();
            processConnection();
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
    
    private void getStreams() throws IOException{
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
        input = new DataInputStream(connection.getInputStream());
    }
    
    private void processConnection() throws IOException{
        String message = "";
        String txt = "";
        do{
            txt = input.readUTF();
            message = JOptionPane.showInputDialog("Client\n"+txt);
            output.writeUTF(message);
        }while(!message.equals("fin"));
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
        new Server().runServer();
    }
}
