package com.elpresidente.faction;

public class Faction {
    private final String name;
    private Integer partisanNumber;
    private Integer satisfaction;

    public Faction(String name, Integer satisfaction, Integer partisanNumber){
        this.name = name;
        this.partisanNumber = partisanNumber;
        this.satisfaction = satisfaction;
    }


}
