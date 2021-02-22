package com.elpresidente.game;

public class GameParameter {
    private Integer agriculturePercentage;
    private Integer industryPercentage;
    private Integer treasury;
    private Integer foodUnits;

    public GameParameter(Integer agriculturePercentage, Integer industryPercentage, Integer treasury, Integer foodUnits){
        this.agriculturePercentage = agriculturePercentage;
        this.foodUnits = foodUnits;
        this.treasury = treasury;
        this.industryPercentage = industryPercentage;
    }
}
