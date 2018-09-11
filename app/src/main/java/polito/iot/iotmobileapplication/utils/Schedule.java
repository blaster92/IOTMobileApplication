package polito.iot.iotmobileapplication.utils;

import java.util.ArrayList;

/**
 * Created by user on 07/08/2018.
 */

public class Schedule {

    private String ID,name, objective, details, start_date,end_date;
    private int days;
    private ArrayList<Exercise> exercises;

    public Schedule(String ID,String name, String objective,String details, String start_date, String end_date, int days, ArrayList<Exercise> exercises) {

        this.ID = ID;
        this.name = name;
        this.objective = objective;
        this.exercises = exercises;
        this.details = details;
        this.start_date = start_date.replace("-","/");
        this.end_date = end_date.replace("-","/");
        this.days = days;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
