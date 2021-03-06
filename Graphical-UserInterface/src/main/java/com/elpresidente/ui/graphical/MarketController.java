package com.elpresidente.ui.graphical;

import com.elpresidente.game.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MarketController {

    private volatile boolean bought;
    private int amount, maxAmount;

    @FXML
    public Label label;
    @FXML
    public TextField textField;
    @FXML
    public Button buyButton;

    @FXML
    public void initialize() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([.]\\d{0,4})?")) {
                textField.setText(oldValue);
            }else{
                try {
                    amount = Integer.parseInt( textField.getText() );
                    if (amount <0 ) {
                        amount = 0;
                    }if(amount > maxAmount ){
                        amount = maxAmount;
                    }
                    textField.setText( String.valueOf(amount));
                }catch ( NumberFormatException ignored){

                }
            }
        });
        bought = false;
        amount = 0;
    }

    public void setData(int amount, int amountNeeded, int treasury){
        this.maxAmount =  (treasury/ Game.FoodUnitPrice);
        textField.setText("0");

        if(amount>amountNeeded){
            label.setText("You a have enough food \t you can buy up to "+ maxAmount+" at "+ Game.FoodUnitPrice+" /food" );
        }else{
            label.setText("You need "+ (amountNeeded - amount) +" more food \t you can buy up to"+ (treasury/ Game.FoodUnitPrice)+" at "+ Game.FoodUnitPrice+" /food" );
        }

    }

    public int getAmount(){

        while (!bought) Thread.onSpinWait();

        bought = false;
        return amount;
    }

    public void buy() {
        bought = true;
    }


}
