package com.elpresidente.repository.json;

import com.elpresidente.event.Event;
import com.elpresidente.factions.Factions;

import com.elpresidente.repository.Repository;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonRepository implements Repository {
    JSONArray jsonFile;

    public JsonRepository(String filePath){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            jsonFile = (JSONArray) jsonParser.parse(reader);
            System.out.print(jsonFile);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Factions> getAllFactions() {
        //List<Factions> factions = jsonFile.;
        return null;
    }

    @Override
    public List<Event> getAllEvent() {
        return null;
    }
}
