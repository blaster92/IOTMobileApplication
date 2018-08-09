package polito.iot.iotmobileapplication.utils;

import java.util.ArrayList;

/**
 * Created by user on 07/08/2018.
 */

public class Schedule {

    private String name, objective;
    private ArrayList<Exercise> exercises;

    public Schedule(String name, String objective, ArrayList<Exercise> exercises) {
        this.name = name;
        this.objective = objective;
        this.exercises = exercises;
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
}
