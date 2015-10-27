/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author André
 */
public abstract class arquivo {

    public static Player getPlayer(Player player) throws FileNotFoundException, IOException {
        String caminho = System.getProperty("user.dir") + "/Users/" + String.valueOf(player.getId()) + ".txt";
        if (new File(caminho).isFile()) {
            BufferedReader leitura;
            leitura = new BufferedReader(new FileReader(caminho));
            player.setName(leitura.readLine());
            player.setEmail(leitura.readLine());
            player.setScore(Float.parseFloat(leitura.readLine()));
            return player;
        } else {
            return null;
        }
    }

    public static void setPlayer(Player player) throws IOException {
        BufferedWriter gravar;
        String caminho = System.getProperty("user.dir") + "/Users/" + String.valueOf(player.getId()) + ".txt";
        gravar = new BufferedWriter(new FileWriter(new File(caminho)));
        gravar.write(player.getName());
        gravar.write(System.getProperty("line.separator"));
        gravar.write(player.getEmail());
        gravar.write(System.getProperty("line.separator"));
        gravar.write(String.valueOf(player.getScore()));
        gravar.close();
    }

    public static int[] listPlayers() 
    {
        String caminho = System.getProperty("user.dir") + "/Users/";
        File folder = new File(caminho);
        File[] listOfFiles = folder.listFiles();
        int playersIds[] = new int[listOfFiles.length];
        
        int aux=0;
        for (File file : listOfFiles) {
            if (file.isFile()) 
            {
                System.out.println(file.getName());
                playersIds[aux] = Integer.parseInt(file.getName().replaceFirst(".txt", ""));
                aux++;
            }
        }
        return playersIds;
    }
}
