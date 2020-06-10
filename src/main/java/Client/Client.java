/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Ronny Mairena B64062
 */
public class Client {
    
    private DataOutputStream output;
    private DataInputStream input;
    private Socket client;
    private final String HOST = "127.0.0.1";
    private final int PORT = 1234;
    
    public void runClient(){
        try {
            connectionServer();
            getStreams();
            processConection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        
    }
    
    private void connectionServer() throws IOException{
        System.out.println("Conectando al servidor...");
        client = new Socket(HOST , PORT);
        System.out.println("Conexión exitosa\nConectado a "+client.getInetAddress().getHostName());
    }
    
    private void getStreams() throws IOException{
        output = new DataOutputStream(client.getOutputStream());
        output.flush();
        input = new DataInputStream(client.getInputStream());
    }
    
    private void processConection() throws IOException{
        String message = "";
        String txt = "";
        txt = input.readUTF();
        message = JOptionPane.showInputDialog(txt);
        output.writeUTF(message);
        txt = input.readUTF();
        message = JOptionPane.showInputDialog(txt);
        output.writeUTF(message);
    }
    
    private void closeConnection(){
        JOptionPane.showMessageDialog(null, "Cerrando conexión...");
        try {
            output.close();
            input.close();
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Conexión cerrada");
    }
    
    public static void main(String[] args) {
        new Client().runClient();
    }
}
