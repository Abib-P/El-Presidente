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

    public void addTreasury(int amount){
        treasury += amount;
        if (treasury < 0){
            treasury = 0;
        }
    }

    public void addFood(int amount){
        foodUnits += amount;
        if (foodUnits < 0){
            foodUnits = 0;
        }
    }

    public void addAgriculture(int amount){
        agriculturePercentage += amount;
        if(amount <= 0 ){
            if( agriculturePercentage < 0) agriculturePercentage = 0;
        }else {

            if(agriculturePercentage > 100){
                amount -= (agriculturePercentage - 100);
                agriculturePercentage = 100;
            }

            if (agriculturePercentage + industryPercentage > 100) {
                addIndustries(-amount); //agriculturePercentage + industryPercentage - 100;
            }
        }
    }

    public void addIndustries(int amount){
        industryPercentage += amount;
        if(amount <= 0){
            if( industryPercentage < 0) industryPercentage = 0;
        }else {

            if(industryPercentage > 100){
                amount -= (industryPercentage - 100);
                industryPercentage = 100;
            }

            if (agriculturePercentage + industryPercentage > 100) {
                addAgriculture( -amount);
            }
        }
    }

    public Integer getAgriculturePercentage() {
        return agriculturePercentage;
    }

    public Integer getIndustryPercentage() {
        return industryPercentage;
    }

    public Integer getTreasury() {
        return treasury;
    }

    public Integer getFoodUnits() {
        return foodUnits;
    }
}
