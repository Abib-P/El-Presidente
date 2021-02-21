package com.elpresidente.input.console;

import com.elpresidente.event.Choice;
import com.elpresidente.event.Event;
import com.elpresidente.input.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {

    @Override
    public Choice getChoice(Event event) {
        Scanner scanner = new Scanner(System.in);
        int index = -1;
        System.out.println(event.getName());

        for (Choice choice: event.getChoices() ) {
            System.out.println(choice.getName());
        }

        do{
            index = scanner.nextInt();
            if( index < 0 || index >= event.getChoices().size()  ){
                System.out.println("Option invalid");
            }
        }while(index < 0 || index >= event.getChoices().size() );

        return event.getChoices().get(index);
    }
}
