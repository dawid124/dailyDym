package pl.webd.dawid124.dailygym.database.data;

/**
 * Created by Java on 2016-05-08.
 */
public class TSeriesData {
    private long id;
    private String name;
    private long weight;
    private TExerciseData exercise;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public TExerciseData getExercise() {
        return exercise;
    }

    public void setExercise(TExerciseData exercise) {
        this.exercise = exercise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
