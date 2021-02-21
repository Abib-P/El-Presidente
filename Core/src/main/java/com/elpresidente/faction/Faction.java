package com.elpresidente.faction;

public class Faction {

    public static final int CorruptionPriceByPartisan = 15;
    public static final int SatisfactionLostByPartisan = -2;

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

    public int getCorruptionPrice(){
        return partisanNumber * Faction.CorruptionPriceByPartisan;
    }

    public void addPartisanNumber(Integer partisanNumber) {
        this.partisanNumber += partisanNumber;
        if( partisanNumber < 0)
            this.addSatisfaction( SatisfactionLostByPartisan);

    }

    public void addSatisfaction(Integer satisfaction) {
        if(this.satisfaction <= 0) return;
        this.satisfaction += satisfaction;
    }

    public Integer getPartisanNumber() {
        return partisanNumber;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }
}
