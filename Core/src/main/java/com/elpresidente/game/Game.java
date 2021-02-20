package com.elpresidente.game;

import com.elpresidente.input.Input;
import com.elpresidente.output.Output;
import com.elpresidente.repository.Repository;
import com.elpresidente.rules.Rules;

public class Game {
    final Input input;
    final Output output;
    final Repository repository;
    Rules rules;

    public Game(Input input, Output output, Repository repository){
        this.input = input;
        this.output = output;
        this.repository = repository;
    }

    public void start(){
        //ask for rules the player want use and difficulty
    }

    private void playGame(){

    }
}
