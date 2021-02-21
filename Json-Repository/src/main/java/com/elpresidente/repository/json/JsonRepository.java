package com.elpresidente.repository.json;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.repository.Repository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class JsonRepository implements Repository {
    JSONObject jsonFile;

    public JsonRepository(String filePath){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            jsonFile = (JSONObject) jsonParser.parse(reader);
            System.out.println(jsonFile);
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

        JSONArray events = (JSONArray) jsonFile.get("events");

        return (List<Event>) events.stream().map(event -> parseEvent((JSONObject) event)).collect(toList());
    }

    private Event parseEvent(JSONObject event){
        Event result = new Event(event.get("name").toString());
        List<Choice> choices = (List<Choice>) ((JSONArray) event.get("choices")).stream()
                .map(choice -> parseChoice((JSONObject) choice))
                .collect(toList());

        result.setChoices(choices);

        return result;
    }

    private Choice parseChoice(JSONObject choice){

        Integer partisanNumber = (Integer) ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("partisans"))
                .map(effect -> Integer.valueOf(((JSONObject) effect).get("partisans").toString()))
                .reduce(0, (a, b) -> (Integer)a + (Integer) b);

        Map<String,Integer> actionOnFaction =

        System.out.print("\n" + partisanNumber);
        //Choice result = new Choice(choice.get("choice").toString(),,,partisanNumber);
        //add related event
        return null;
    }
}
