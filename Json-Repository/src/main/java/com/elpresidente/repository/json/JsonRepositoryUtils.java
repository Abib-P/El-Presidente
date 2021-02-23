package com.elpresidente.repository.json;

import com.elpresidente.repository.RepositoryUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class JsonRepositoryUtils implements RepositoryUtils {
    public JsonRepositoryUtils(){

    }

    public Map<String,String> loadAllScenarioName(String filePath){
        Map<String,String> result = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        File repertoire = new File(filePath);
        String fileNames[] = repertoire.list();

        if (fileNames != null) {
            for (String fileName : fileNames) {
                try {
                    FileReader reader = new FileReader(filePath+"\\"+fileName);
                    JSONObject jsonFile = (JSONObject) jsonParser.parse(reader);
                    result.put(filePath+"\\"+fileName,getScenarioName(jsonFile));
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
        return result;
    }

    private String getScenarioName(JSONObject jsonFile){
        return jsonFile.get("name").toString();
    }
}
