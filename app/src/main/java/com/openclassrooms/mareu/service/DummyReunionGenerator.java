package com.openclassrooms.mareu.service;
import com.openclassrooms.mareu.models.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyReunionGenerator {

    public static List<Reunion> DUMMY_REUNION = Arrays.asList(
            new Reunion(1, "service marketing","A",12,30,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(2,"compta","C",15,45,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(3,"ventes","D",9,20,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(4,"Anniversaire","H",18,0,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(5, "service marketing","A",12,30,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(6,"compta","C",15,45,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(7,"ventes","D",9,20,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(8,"Anniversaire","H",18,0,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(9, "service marketing","A",12,30,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(10,"compta","C",15,45,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(11,"ventes","D",9,20,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr"),
            new Reunion(12,"Anniversaire","H",18,0,"blala@gmail.com , gigi@hotmail.fr , terry@yahoo.com , fifi@outlook.fr")
    );

    static List<Reunion> generateReunions(){
        return new ArrayList<>(DUMMY_REUNION);
    }



}
