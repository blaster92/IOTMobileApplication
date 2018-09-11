package polito.iot.iotmobileapplication.utils;

/**
 * Created by user on 07/08/2018.
 */

public class Exercise {

    private String name, muscolar_zone, description, details,url;
    private int repetitions;
    private float weight;

    public Exercise(String name, String muscolar_zone, String description, String details, int repetitions, float weight,String url) {
        this.name = name;
        this.muscolar_zone = muscolar_zone;
        this.description = description;
        this.details = details;
        this.repetitions = repetitions;
        this.weight = weight;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscolarZone() {
        return muscolar_zone;
    }

    public void setMuscolarZone(String muscolar_zone) {
        this.muscolar_zone = muscolar_zone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
