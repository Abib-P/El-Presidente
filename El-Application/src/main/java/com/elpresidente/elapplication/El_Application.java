package com.elpresidente.elapplication;

import com.elpresidente.App;
import com.elpresidente.game.Game;
import com.elpresidente.repository.Repository;
import com.elpresidente.repository.RepositoryUtils;
import com.elpresidente.repository.json.JsonRepository;
import com.elpresidente.repository.json.JsonRepositoryUtils;
import com.elpresidente.ui.UserInterface;
import com.elpresidente.ui.console.ConsoleUserInterface;
import javafx.application.Application;

import java.util.Map;

public class El_Application {

    public static void main(String[] args) {
        boolean playing = true;
        UserInterface userInterface = new ConsoleUserInterface();

        Application.launch(App.class);

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
    }


}
