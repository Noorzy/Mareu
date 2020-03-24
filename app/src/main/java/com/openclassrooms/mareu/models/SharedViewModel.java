package com.openclassrooms.mareu.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> time = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();

    public void setTime(String inputTime){
        time.setValue(inputTime);
    }
    public LiveData<String> getTime(){
        return time;
    }
    public void setDate(String inputDate){
        date.setValue(inputDate);
    }
    public LiveData<String> getDate(){
        return date;
    }

}
