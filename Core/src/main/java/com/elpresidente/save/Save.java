package com.elpresidente.save;

import com.elpresidente.game.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Save {

    public static void saveGame(Game game, String fileName) throws IOException {
        final GsonBuilder builder = new GsonBuilder();

        Writer writer = new FileWriter("saves\\" + fileName + ".json", StandardCharsets.UTF_8);
        new Gson().toJson(game.getGameStatement(fileName), writer);

        writer.close();

    }
}
