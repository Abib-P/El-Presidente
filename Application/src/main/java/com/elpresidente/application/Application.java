package com.elpresidente.application;

import com.elpresidente.game.Game;
import com.elpresidente.input.Input;
import com.elpresidente.input.console.ConsoleInput;
import com.elpresidente.output.Output;
import com.elpresidente.output.console.ConsoleOutput;
import com.elpresidente.repository.Repository;
import com.elpresidente.repository.json.JsonRepository;

public class Application {
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        Repository repository = new JsonRepository("Json-Repository/src/test/resources/attackOnTitans.json");

        Game game = new Game(input,output,repository);

        game.start();

        game.playGame();
    }
}
