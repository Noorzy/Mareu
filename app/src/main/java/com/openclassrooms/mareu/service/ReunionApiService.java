package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.models.Reunion;

import java.util.List;

public interface ReunionApiService {
    /**
     * @return List of Reunions
     */
    List<Reunion> getReunions();

    /**
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

    void addReunion(Reunion reunion);

}
