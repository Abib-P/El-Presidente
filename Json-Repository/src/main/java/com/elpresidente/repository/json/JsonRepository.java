package com.elpresidente.repository.json;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.faction.Faction;
import com.elpresidente.game.GameParameter;
import com.elpresidente.repository.Repository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class JsonRepository implements Repository {
    JSONObject jsonFile;

    public JsonRepository(String filePath){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            jsonFile = (JSONObject) jsonParser.parse(reader);
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

    @Override
    public GameParameter getAllGameParameter() {
        JSONObject gameStartParameters = (JSONObject) jsonFile.get("gameStartParameters");
        JSONObject difficulty = (JSONObject) gameStartParameters.get("NORMAL");
        int industryPercentage = Integer.parseInt(difficulty.get("industryPercentage").toString());
        int treasury = Integer.parseInt(difficulty.get("treasury").toString());
        int foodUnits = Integer.parseInt(difficulty.get("foodUnits").toString());
        int agriculturePercentage = Integer.parseInt(difficulty.get("agriculturePercentage").toString());

        return new GameParameter(Integer.valueOf(Integer.toString(agriculturePercentage)),
                Integer.valueOf(Integer.toString(industryPercentage)),
                Integer.valueOf(Integer.toString(treasury)),
                Integer.valueOf(Integer.toString(foodUnits))
        );
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

        Map<String, Integer> actionOnFaction = new HashMap<>();

        ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("actionOnFaction"))
                .map(effect -> ((JSONObject) effect).get("actionOnFaction"))
                .forEach(action -> {
                    for (Object o : ((JSONObject) action).keySet()) {
                        String key = (String) o;
                        actionOnFaction.put(key, Integer.valueOf(((JSONObject) action).get(key).toString()));
                    }
                });

        Map<String, Integer> actionOnFactor = new HashMap<>();

        ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("actionOnFactor"))
                .map(effect -> ((JSONObject) effect).get("actionOnFactor"))
                .forEach(action -> {
                    for (Object o : ((JSONObject) action).keySet()) {
                        String key = (String) o;
                        actionOnFactor.put(key, Integer.valueOf(((JSONObject) action).get(key).toString()));
                    }
                });

        Choice result = new Choice(choice.get("choice").toString(),actionOnFaction,actionOnFactor,partisanNumber);
        if( choice.get("relatedEvents") != null) {
            JSONArray events = (JSONArray) choice.get("relatedEvents");
            result.setRelatedEvent((List<Event>) events.stream().map(event -> parseEvent((JSONObject) event)).collect(toList()));
        }
        return result;
    }
}
