package pl.webd.dawid124.dailygym.database.data;

import android.graphics.Bitmap;

import pl.webd.dawid124.dailygym.enum_type.ExerciseTypeEnum;

/**
 * Created by Java on 2016-05-08.
 */
public class TExerciseTypeData {
    private long id;
    private String name;
    private String descriptions;
    private Bitmap image;
    private ExerciseTypeEnum type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String dexcriptions) {
        this.descriptions = dexcriptions;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExerciseTypeEnum getType() {
        return type;
    }

    public void setType(int type) {
        this.type = ExerciseTypeEnum.Get(type);
    }
}