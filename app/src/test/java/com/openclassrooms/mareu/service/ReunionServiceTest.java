package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.models.Reunion;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ReunionServiceTest {
    private ReunionApiService service;

    @Before
    public void setup(){
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess(){
        List<Reunion> meetings = service.getReunions();
        List<Reunion> expectedMeetings = DummyReunionGenerator.DUMMY_REUNION;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess(){
        Reunion meetingToDelete = service.getReunions().get(0);
        service.deleteReunion(meetingToDelete);
        assertFalse(service.getReunions().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess(){
        Reunion meetingToAdd = new Reunion(42,"Test","Salle J","21 avr. 2020","18h05","test@test.test");
        service.addReunion(meetingToAdd);
        assertTrue(service.getReunions().contains(meetingToAdd));

    }
}