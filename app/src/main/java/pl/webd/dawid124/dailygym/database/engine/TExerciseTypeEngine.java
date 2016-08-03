package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import pl.webd.dawid124.dailygym.database.ColumnName;
import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TExerciseTypeData;
import pl.webd.dawid124.dailygym.enum_type.ExerciseTypeEnum;

/**
 * Created by Java on 2016-05-08.
 */
public class TExerciseTypeEngine extends  DB_SqlLiteDbHelper{
    private Context ctx;

    public TExerciseTypeEngine(Context context) {
        super(context);
        ctx = context;
    }

    public ArrayList<TExerciseTypeData> getAllExerciseType(){
        ArrayList<TExerciseTypeData> exerciseTypeList = new ArrayList<>();
        String[] columns={ColumnName.ExerciseType.ID,ColumnName.ExerciseType.NAME,ColumnName.ExerciseType.DESCRIPTIONS,ColumnName.ExerciseType.IMAGE,ColumnName.ExerciseType.TYPE};
        String orderBy = ColumnName.ExerciseType.TYPE + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TableName.EXERCISE_TYPE,columns,null,null,null,null,orderBy);
        while(cursor.moveToNext()){
            TExerciseTypeData exerciseType = new TExerciseTypeData();
            exerciseType.setId(cursor.getInt(0));
            exerciseType.setName(cursor.getString(1));
            exerciseType.setDescriptions(cursor.getString(2));
            if (cursor.getBlob(3) != null){

                byte[] decodedString = Base64.decode(cursor.getBlob(3), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                exerciseType.setImage(decodedByte);

            }

            exerciseType.setType(cursor.getInt(4));

            exerciseTypeList.add(exerciseType);
        }
        db.close();
        return exerciseTypeList;
    }

    public TExerciseTypeData getExercise(long exerciseId) {

            TExerciseTypeData exerciseType = new TExerciseTypeData();
            SQLiteDatabase db = getReadableDatabase();
            String[] columns={ColumnName.ExerciseType.ID,ColumnName.ExerciseType.NAME,ColumnName.ExerciseType.DESCRIPTIONS,ColumnName.ExerciseType.IMAGE,ColumnName.ExerciseType.TYPE};
            String selection = ColumnName.ExerciseType.ID + "=?";
            String selectionArgs[]={exerciseId+""};
            Cursor cursor=db.query(TableName.EXERCISE_TYPE,columns,selection,selectionArgs,null,null,null);
            if(cursor!=null){
                cursor.moveToFirst();
                exerciseType.setId(cursor.getLong(0));
                exerciseType.setName(cursor.getString(1));
                exerciseType.setDescriptions(cursor.getString(2));
                if (cursor.getBlob(3) != null){
                    byte[] decodedString = Base64.decode(cursor.getBlob(3), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    exerciseType.setImage(decodedByte);
                }
                exerciseType.setType(cursor.getInt(4));
            }
            db.close();
            return exerciseType;
    }

    public void createExercise(String name, String descriptions, ExerciseTypeEnum type, Bitmap image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valueExercise = new ContentValues();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
        byte[] image64 = Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        valueExercise.put(ColumnName.ExerciseType.NAME, name);
        valueExercise.put(ColumnName.ExerciseType.DESCRIPTIONS, descriptions);
        valueExercise.put(ColumnName.ExerciseType.TYPE, type.value());
        valueExercise.put(ColumnName.ExerciseType.IMAGE, image64);
        db.insert(TableName.EXERCISE_TYPE, null, valueExercise);

        db.close();
    }
}
