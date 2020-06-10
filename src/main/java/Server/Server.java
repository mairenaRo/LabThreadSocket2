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
        output.writeUTF("Bienvenido al Sistema de compra en línea de ticketes"
                + "\nIngrese el número de la opción que va a solicitar."
                + "\n 1 - Pelicula1 / Horario 00:00 / Sala1"
                + "\n 2 - Pelicula2 / Horario 00:00 / Sala2"
                + "\n 3 - Pelicula3 / Horario 00:00 / Sala3"
                + "\n 4 - Salir");
        message = input.readUTF();
        switch(message){
            case "1":
                output.writeUTF("Película1"
                       + "\nDesclipción de pelicula 1"
                       + "\nDigite la cantidad de asientos a commprar, recuerde que es un tiquete por asiento.");
                break;
            case "2":
                output.writeUTF("Película2"
                       + "\nDesclipción de pelicula 2"
                       + "\nDigite la cantidad de asientos a commprar, recuerde que es un tiquete por asiento.");
                break;
            case "3":
                output.writeUTF("Película3"
                       + "\nDesclipción de pelicula 3"
                       + "\nDigite la cantidad de asientos a commprar, recuerde que es un tiquete por asiento.");
                break;
            case "4":
                output.writeUTF("Gracias por preferirnos");
                break;
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
        new Server().runServer();
    }
}
