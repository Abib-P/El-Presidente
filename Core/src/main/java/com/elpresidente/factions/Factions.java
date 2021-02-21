package com.elpresidente.factions;

import com.elpresidente.faction.Faction;

import java.util.List;

public class Factions {
    List<Faction> factions;

    public Factions(){

    }

    public Integer getSatisfaction(){
        return factions.stream()
                .map(faction -> faction.getSatisfaction()*faction.getPartisanNumber())
                .reduce(0, Integer::sum) / getTotalNumberOfPartisan();
    }

    private Integer getTotalNumberOfPartisan(){
        return factions.stream()
                .map(Faction::getPartisanNumber)
                .reduce(0, Integer::sum);
    }
}
