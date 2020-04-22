package com.openclassrooms.mareu.service;
import com.openclassrooms.mareu.models.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNION = Arrays.asList(
            new Reunion(1, "service marketing","Salle A","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(2,"compta","Salle C","7 mai 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(3,"ventes","Salle D","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(4,"Anniversaire","Salle H","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(5, "service marketing","Salle A","7 mai 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(6,"compta","Salle C","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(7,"ventes","Salle D","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(8,"Anniversaire","Salle H","7 mai 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(9, "service marketing","Salle A","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(10,"compta","Salle C","7 mai 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(11,"ventes","Salle D","21 avr. 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(12,"Anniversaire","Salle H","7 mai 2020","10h30","blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr")
    );

    static List<Reunion> generateReunions(){
        return new ArrayList<>(DUMMY_REUNION);
    }
}
