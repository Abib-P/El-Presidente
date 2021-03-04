package com.elpresidente.ui.graphical;

import com.elpresidente.faction.Faction;
import com.elpresidente.factions.Factions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FactionCorruptionController {

    private boolean isFilled = false;
    private volatile boolean finished;

    Faction selectedFaction;
    public HBox hBox;

    public void setData(Factions factions, int treasury){
        int i = 0;

        selectedFaction = null;

        if(! isFilled) {
            isFilled = true;
            for (Faction faction : factions.getFactions()) {

                if (faction.getKey().equals(Factions.LoyalistsFactionKey))
                    continue;

                Button button = new Button();
                button.setUserData(faction);

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        selectedFaction = faction;
                        finished = true;
                        System.out.println("clicked: " + faction.getName() + " b: " + button.getText());
                    }
                });

                hBox.getChildren().add(hBox.getChildren().size() - 1, button);
            }
        }

        for (Node node: hBox.getChildren() ) {
            Button button = (Button) node;
            Faction faction = (Faction) button.getUserData();

            if (faction == null || faction.getKey().equals(Factions.LoyalistsFactionKey))
                continue;

            if (factions.areLoyalist()) {
                button.setText(faction.getName() + " (" + faction.getPartisanNumber() + "): " + faction.getCorruptionPrice() + "€\n Loyalists: " + -faction.getCorruptionImpactOnLoyalist());
            } else {
                button.setText(faction.getName() + " (" + faction.getPartisanNumber() + "): " + faction.getCorruptionPrice() + "€");
            }

            if (faction.getCorruptionPrice() > treasury || faction.getSatisfaction() >= 100) {
                button.setDisable(true);
            }
        }

    }

    public Faction getFaction(){
        while (!finished) Thread.onSpinWait();
        finished = false;
        return selectedFaction;
    }

    public void onFinish(ActionEvent actionEvent) {
        finished = true;
    }
}
