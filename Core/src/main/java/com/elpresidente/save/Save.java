package com.elpresidente.save;

import com.elpresidente.game.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Save {

    public static void saveGame(Game game, String pseudo) throws IOException {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

//        gson.toJson(game.getGameStatement(pseudo), new FileWriter("saves\\" + pseudo + ".json"));

        final String json = new Gson().toJson(game.getGameStatement(pseudo));
        System.out.println("Resultat save for " + pseudo + " = " + json);

        Writer writer = new FileWriter("saves\\" + pseudo + ".json", StandardCharsets.UTF_8);
        new Gson().toJson(game.getGameStatement(pseudo), writer);

        writer.close();

    }
}
