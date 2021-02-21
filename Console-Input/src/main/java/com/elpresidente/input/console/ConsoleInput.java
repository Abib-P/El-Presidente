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
        System.out.println("event: "+event.getName());

        for (int i = 0; i < event.getChoices().size(); i++) {
            System.out.println("    "+i+". "+ event.getChoices().get(i).getName());
        }

        do{
            System.out.print("choix: ");
            index = scanner.nextInt();
            if( index < 0 || index >= event.getChoices().size()  ){
                System.out.println("Option invalid");
            }
        }while(index < 0 || index >= event.getChoices().size() );

        return event.getChoices().get(index);
    }
}
