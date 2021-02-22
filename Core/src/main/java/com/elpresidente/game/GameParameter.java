package com.elpresidente.game;

public class GameParameter {
    public Integer agriculturePercentage;
    public Integer industryPercentage;
    public Integer treasury;
    public Integer foodUnits;

    public GameParameter(Integer agriculturePercentage, Integer industryPercentage, Integer treasury, Integer foodUnits){
        this.agriculturePercentage = agriculturePercentage;
        this.foodUnits = foodUnits;
        this.treasury = treasury;
        this.industryPercentage = industryPercentage;
    }
}
