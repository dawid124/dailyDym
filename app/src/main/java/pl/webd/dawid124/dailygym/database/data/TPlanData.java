package pl.webd.dawid124.dailygym.database.data;

import java.util.List;

/**
 * Created by Java on 2016-05-08.
 */
public class TPlanData {
    private long id;
    private String name;
    private List<THistoryData> historyList;
    private TTrainingData training;
    private long trainingId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public List<THistoryData> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<THistoryData> historyList) {
        this.historyList = historyList;
    }

    public TTrainingData getTraining() {
        return training;
    }

    public void setTraining(TTrainingData training) {
        this.training = training;
    }
}
