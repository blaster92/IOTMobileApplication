package polito.iot.iotmobileapplication.utils;

/**
 * Created by user on 07/08/2018.
 */

public class Exercise {

    private String name, duration, description;
    private int series, numbers;

    public Exercise(String name, String duration, String description, int series, int numbers) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.series = series;
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
