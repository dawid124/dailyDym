package pl.webd.dawid124.dailygym.database.data;

import java.util.List;

/**
 * Created by Java on 2016-05-08.
 */
public class TTrainingData {
    private long id;
    private String name;
    private String descripion;
    private boolean active;
    private String createdDate;
    private List<TPlanData> trainingPlans;

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

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<TPlanData> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(List<TPlanData> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
