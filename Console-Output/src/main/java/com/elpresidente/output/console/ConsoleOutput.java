package com.elpresidente.output.console;

import com.elpresidente.output.Output;

public class ConsoleOutput implements Output {

    public void displayString(String string){
        System.out.println(string);
    }
}
