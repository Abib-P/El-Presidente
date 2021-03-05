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
        boolean playing, newGame;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(GraphicalUserInterface.class);
            }
        });

        thread.start();

        Thread.sleep(1000);

        UserInterface userInterface = GraphicalUserInterface.ui ;

        newGame = userInterface.askForNewGame();

        RepositoryUtils repositoryUtils = new JsonRepositoryUtils();

        Map<String, String> AllScenarioNames;
        if( newGame) {
            AllScenarioNames = repositoryUtils.loadAllScenarioName("scenario");
        }else {
            AllScenarioNames = repositoryUtils.loadAllScenarioName("saves");
        }

        do {
            String scenarioFilePath = userInterface.selectScenario(AllScenarioNames);
            System.out.println("selected: "+scenarioFilePath);
            Repository repository = new JsonRepository(scenarioFilePath);

            Game game = new Game(userInterface, repository);

            game.start();

            game.playGame();

            playing = userInterface.askForReplay();

        }while(playing);
        System.out.println("stopped playing");

        thread.join();
    }


}
