package com.elpresidente.elapplication;


import com.elpresidente.game.Game;
import com.elpresidente.repository.Repository;
import com.elpresidente.repository.RepositoryUtils;
import com.elpresidente.repository.json.JsonRepository;
import com.elpresidente.repository.json.JsonRepositoryUtils;
import com.elpresidente.ui.UserInterface;
import com.elpresidente.ui.console.ConsoleUserInterface;
import com.elpresidente.ui.graphical.GraphicalUserInterface;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.Map;

public class El_Application {

    public static void main(String[] args) throws InterruptedException {
        boolean playing = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(GraphicalUserInterface.class);
            }
        });

        thread.start();

        Thread.sleep(1000);

        //UserInterface userInterface = new ConsoleUserInterface();
        UserInterface userInterface = GraphicalUserInterface.ui ;


        RepositoryUtils repositoryUtils = new JsonRepositoryUtils();
        Map<String,String> AllScenarioNames = repositoryUtils.loadAllScenarioName("scenario");

        do {
            String scenarioFilePath = userInterface.selectScenario(AllScenarioNames);

            Repository repository = new JsonRepository(scenarioFilePath);

            Game game = new Game(userInterface, repository);

            game.start();

            game.playGame();

            playing = userInterface.askForReplay();

        }while(playing);

        thread.join();
    }


}
