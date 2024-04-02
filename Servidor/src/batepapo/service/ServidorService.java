/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batepapo.service;

import batepapo.bean.ChatMessage;
import batepapo.bean.ChatMessage.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vini_
 */
public class ServidorService {
    
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    
    public ServidorService() {
        
        try {
            serverSocket = new ServerSocket(8084);
            
            while (true) {
                socket = serverSocket.accept();
                
                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;
        
        public ListenerSocket(Socket socket) {
            try {
                this.output = new  ObjectOutputStream(socket.getOutputStream());
                this.input  = new  ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
                
        @Override
        public void run() {
            
            ChatMessage message = null;

            try {
                
                while ((message = (ChatMessage) input.readObject()) != null) {
                       Action action = message.getAction();
                       
                       if (action.equals(Action.CONNECT)) {
                          boolean isConnect = connect(message, output);
                       } else if (action.equals(Action.DISCONNECT)) {
                           
                       } else if (action.equals(Action.SEND_ONE)) {
                           //not implemented
                       } else if (action.equals(Action.SEND_ALL)) {
                           
                       } else if (action.equals(Action.USERS_ONLINE)) {
                           //not implemented
                       }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private boolean connect (ChatMessage message, ObjectOutputStream output) {
        sendOne(message, output);
        return true;
    }
    
    private void sendOne(ChatMessage message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
