package com.elpresidente.repository.json;

import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.repository.Repository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonRepository implements Repository {
    JSONObject jsonFile;

    public JsonRepository(String filePath){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            jsonFile = (JSONObject) jsonParser.parse(reader);
            System.out.print(jsonFile);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Faction> getAllFactions() {

        JSONObject gameStartParameters = (JSONObject) jsonFile.get("gameStartParameters");
        JSONObject difficulty = (JSONObject) gameStartParameters.get("NORMAL");
        JSONObject factions = (JSONObject) difficulty.get("factions");

        Iterator keys = factions.keySet().iterator();

        List<Faction> result = new ArrayList<>();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            JSONObject faction = (JSONObject) factions.get(key);
            result.add(new Faction(
                    key,
                    faction.get("name").toString(),
                    Integer.valueOf(faction.get("satisfactionPercentage").toString()),
                    Integer.valueOf(faction.get("numberOfPartisans").toString()))
            );
        }
        return result;
    }

    @Override
    public List<Event> getAllEvent() {
        return null;
    }
}
