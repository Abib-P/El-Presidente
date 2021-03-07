package com.elpresidente.save;

import com.elpresidente.game.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class Save {

    public static void saveGame(Game game, String pseudo) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        gson.toJson(game.getGameStatement(pseudo), new FileWriter("saves\\" + pseudo + ".json"));

        final String json = new Gson().toJson(game.getGameStatement(pseudo));
        System.out.println("Resultat save for " + pseudo + " = " + json);

    }


}
