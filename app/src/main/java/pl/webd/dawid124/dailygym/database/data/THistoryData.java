package pl.webd.dawid124.dailygym.database.data;

import java.util.List;

/**
 * Created by Java on 2016-05-09.
 */
public class THistoryData {
    private long id;
    private TPlanData plan;
    private String date;
    private List<TExerciseData> exercisesList;

    public TPlanData getPlan() {
        return plan;
    }

    public void setPlan(TPlanData plan) {
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TExerciseData> getExercise() {
        return exercisesList;
    }

    public void setExercisesList(List<TExerciseData> exercisesList) {
        this.exercisesList = exercisesList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
