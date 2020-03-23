package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.models.Reunion;

import java.util.List;

public class DummyReunionApiService implements ReunionApiService {

    public List<Reunion> Reunions = DummyReunionGenerator.generateReunions();

    /**
     * @return list of Reunions
     */
    @Override
    public List<Reunion> getReunions(){
        return Reunions;
    }
}
