package pl.webd.dawid124.dailygym.database.data;

import android.graphics.Bitmap;

import java.util.List;

import pl.webd.dawid124.dailygym.enum_type.ExerciseTypeEnum;

/**
 * Created by Java on 2016-05-08.
 */
public class TExerciseData {
    private long id = -1;
    private long typeId;
    private String name;
    private String descriptions;
    private Bitmap image;
    private ExerciseTypeEnum type;
    private List<TSeriesData> seriesList;
    private THistoryData history;

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

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ExerciseTypeEnum getType() {
        return type;
    }

    public void setType(int type) {
        this.type = ExerciseTypeEnum.Get(type);
    }

    public List<TSeriesData> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<TSeriesData> seriesList) {
        this.seriesList = seriesList;
    }

    public THistoryData getHistory() {
        return history;
    }

    public void setHistory(THistoryData history) {
        this.history = history;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }
}
