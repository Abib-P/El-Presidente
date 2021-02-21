package com.elpresidente.faction;

public class Faction {
    private final String key;
    private final String name;
    private Integer partisanNumber;
    private Integer satisfaction;

    public Faction(String key, String name, Integer satisfaction, Integer partisanNumber){
        this.key = key;
        this.name = name;
        this.partisanNumber = partisanNumber;
        this.satisfaction = satisfaction;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Integer getPartisanNumber() {
        return partisanNumber;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }
}
