/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package batepapo.service;

import batepapo.bean.ChatMessage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vini_
 */
public class ClienteService {
     private Socket socket;
     private ObjectOutputStream output;
     
     public Socket connect() {
         try {
             this.socket = new Socket("localhost", 8084);
             this.output = new ObjectOutputStream(socket.getOutputStream());
         } catch (IOException ex) {
             Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return socket;
     }
     
     public void send(ChatMessage message) {
         try {
             output.writeObject(message);
         } catch (IOException ex) {
             Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
} 
