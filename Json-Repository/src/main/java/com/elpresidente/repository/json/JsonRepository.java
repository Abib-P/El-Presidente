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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonRepository implements Repository {
    JSONObject jsonFile;

    public JsonRepository(String filePath) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8);
            jsonFile = (JSONObject) jsonParser.parse(reader);
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("NTM");
            e.printStackTrace();
        }
    }

    @Override
    public List<Faction> getAllFactions() {

        JSONObject gameStartParameters = (JSONObject) jsonFile.get("gameStartParameters");
        JSONObject difficulty = (JSONObject) gameStartParameters.get("NORMAL");
        JSONObject factions = (JSONObject) difficulty.get("factions");

        Iterator<?> keys = factions.keySet().iterator();

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
        List<Event> eventList = new ArrayList<>();
        JSONArray events = (JSONArray) jsonFile.get("events");

        for (Object event : events) {
            eventList.add(parseEvent((JSONObject) event));
        }

        return eventList;
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

    @Override
    public String getName() {
        return jsonFile.get("name").toString();
    }

    @Override
    public String getSaveName() {
        return jsonFile.get("saveName").toString();
    }

    @Override
    public boolean getShowActionOnChoice() {
        return (boolean) jsonFile.get("showAction");
    }

    @Override
    public String getStory() {
        return jsonFile.get("story").toString();
    }

    @Override
    public double getDifficulty() {

        return (double) jsonFile.get("difficulty");

    }

    @Override
    public int getMode() {
        if (jsonFile.get("mode") == null) {
            return 0;
        }
        return 1;

    }

    @Override
    public int getIndex() {
        if (jsonFile.get("index") == null) {
            return 0;
        }
        return ((Long) jsonFile.get("index")).intValue();
    }

    @Override
    public int getSeason() {
        if (jsonFile.get("season") == null) {
            return 0;
        }
        return ((Long) jsonFile.get("season")).intValue();
    }

    private Event parseEvent(JSONObject event) {
        Event result = new Event(event.get("name").toString());
        List<Choice> choices = new ArrayList<>();

        for (Object choice : (JSONArray) event.get("choices")) {
            choices.add(parseChoice((JSONObject) choice));
        }

        result.setChoices(choices);

        return result;
    }

    private Choice parseChoice(JSONObject choice) {
        JSONArray effects = (JSONArray) choice.get("effects");
        Map<String, Integer> actionOnFaction = new HashMap<>();
        Map<String, Integer> actionOnFactor = new HashMap<>();
        List<Event> relatedEvent = new ArrayList<>();
        int partisanNumber = 0;

        for (Object o : effects) {
            JSONObject jo = (JSONObject) o;
            if (jo.containsKey("partisans")) {

                partisanNumber = Integer.parseInt(jo.get("partisans").toString());
            } else if (jo.containsKey("actionOnFaction")) {

                JSONObject actions = (JSONObject) jo.get("actionOnFaction");
                for (Object action : actions.keySet()) {
                    String key = (String) action;
                    actionOnFaction.put(key, Integer.valueOf(actions.get(key).toString()));
                }
            } else if (jo.containsKey("actionOnFactor")) {

                JSONObject actions = (JSONObject) jo.get("actionOnFactor");
                for (Object action : actions.keySet()) {
                    String key = (String) action;
                    actionOnFactor.put(key, Integer.valueOf(actions.get(key).toString()));
                }
            }
        }

      /*

       Integer partisanNumber = (Integer) ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("partisans"))
                .map(effect -> Integer.valueOf(((JSONObject) effect).get("partisans").toString()))
                .reduce(0, (a, b) -> (Integer)a + (Integer) b)

        ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("actionOnFaction"))
                .map(effect -> ((JSONObject) effect).get("actionOnFaction"))
                .forEach(action -> {
                    for (Object o : ((JSONObject) action).keySet()) {
                        String key = (String) o;
                        actionOnFaction.put(key, Integer.valueOf(((JSONObject) action).get(key).toString()));
                    }
                });

        ((JSONArray) choice.get("effects")).stream()
                .filter(effect -> ((JSONObject) effect).containsKey("actionOnFactor"))
                .map(effect -> ((JSONObject) effect).get("actionOnFactor"))
                .forEach(action -> {
                    for (Object o : ((JSONObject) action).keySet()) {
                        String key = (String) o;
                        actionOnFactor.put(key, Integer.valueOf(((JSONObject) action).get(key).toString()));
                    }
                });

                */

        Choice result = new Choice(choice.get("choice").toString(), actionOnFaction, actionOnFactor, partisanNumber);
        if (choice.get("relatedEvents") != null) {
            JSONArray events = (JSONArray) choice.get("relatedEvents");

            for (Object o : events) {
                relatedEvent.add(parseEvent((JSONObject) o));
            }

            result.setRelatedEvent(relatedEvent);
        }
        return result;
    }
}
