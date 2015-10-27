/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author isidro
 */
public class MainServer {
    public static void main(String args[]){
        try{
            ServerSocket server;
            server = new ServerSocket(21000);  // criei meu canal de comunicação
            System.out.println("Server Started at 21000... Waiting for connections");
            
            do{
                
                Socket client = server.accept(); // aceitei a conexão e criei novo canal de comunicaçao
                System.out.println("Connection accepted "+client.getInetAddress().getHostAddress());
                Resposta rep = new Resposta();
                // trabalhar o tratamento das requisições
                // as 2 próximas instruções implementam nosso "receive"
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                Requisicao req = (Requisicao)input.readObject();
                System.out.println("Message Received!");
                switch(req.getMessageType()){
                    case Requisicao.NEW_PLAYER:
                        System.out.println("DEBUG - New Player "+req.getPlayerContent().getId() + " "+req.getPlayerContent().getName());
                        arquivo.setPlayer(req.getPlayerContent());
                        rep.setMessageStatus(Resposta.NEW_PLAYER_OK);
                        break;
                    case Requisicao.GET_PLAYER:
                        System.out.println("DEBUG - Retrieving Player #"+req.getPlayerContent().getId());
                        Player player = arquivo.getPlayer(req.getPlayerContent());
                        if(player == null){
                            rep.setMessageStatus(Resposta.PLAYER_NOT_FOUND);
                        }
                        else{
                            rep.setMessageStatus(Resposta.GET_PLAYER_OK);
                            rep.setPlayerContent(player);
                        }
                        break;
                    case Requisicao.ALL_PLAYERS:
                        System.out.println("DEBUG - All Players ");
                        int[] playersIds = arquivo.listPlayers();
                        if(playersIds == null)
                        {
                            rep.setMessageStatus(Resposta.ALL_PLAYERS_ERROR);
                        }
                        else
                        {
                            rep.setMessageStatus(Resposta.ALL_PLAYERS_OK);
                            rep.setPlayersList(playersIds);
                        }
                        break;
                    default:
                        System.out.println("DEBUG - Error");
                        rep.setMessageStatus(Resposta.NEW_PLAYER_ERROR);
                        break;
                }
                
                System.out.println("Sending response...");
                // envio a resposta de volta ao cliente
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(rep);
                
                output.close();
                input.close();
                client.close();
                
            } while (true);
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
}
