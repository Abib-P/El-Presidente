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

    public int getCorruptionImpactOnLoyalist(){
        return getCorruptionPrice()/10;
    }

    public void addPartisanNumber(Integer partisanNumber) {
        if( this.partisanNumber + partisanNumber < 0){
            partisanNumber += partisanNumber - this.partisanNumber;
        }

        this.partisanNumber += partisanNumber;
        if(this.partisanNumber < 0)
            this.partisanNumber = 0;
        if( partisanNumber < 0)
            this.addSatisfaction( SatisfactionLostByPartisan * partisanNumber);
    }

    public void addSatisfaction(Integer satisfaction) {
        if(this.satisfaction <= 0)
            return;

        this.satisfaction += satisfaction;

        if(this.satisfaction <= 0 ){
            this.satisfaction = 0;
        }else if (this.satisfaction >= 100){
            this.satisfaction = 100;
        }
    }

    public Integer getPartisanNumber() {
        return partisanNumber;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }
}
