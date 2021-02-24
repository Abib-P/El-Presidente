package com.elpresidente.application;

import com.elpresidente.game.Game;
import com.elpresidente.input.Input;
import com.elpresidente.input.console.ConsoleInput;
import com.elpresidente.output.Output;
import com.elpresidente.output.console.ConsoleOutput;
import com.elpresidente.repository.Repository;
import com.elpresidente.repository.RepositoryUtils;
import com.elpresidente.repository.json.JsonRepository;
import com.elpresidente.repository.json.JsonRepositoryUtils;

import java.util.Map;

public class Application {

    public static void main(String[] args) {
        boolean playing = true;
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();

        RepositoryUtils repositoryUtils = new JsonRepositoryUtils();
        Map<String,String> AllScenarioNames = repositoryUtils.loadAllScenarioName("scenario");

        do {
            String scenarioFilePath = input.selectScenario(AllScenarioNames);

            Repository repository = new JsonRepository(scenarioFilePath);

            Game game = new Game(input, output, repository);

            game.start();

            game.playGame();

            playing = input.askYesNoQuestion("Do you want to replay ?");

        }while(playing);
    }


}
