package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.database.ColumnName;
import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.THistoryData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.static_value.AppValue;

/**
 * Created by Java on 2016-05-08.
 */
public class TExerciseEngine extends DB_SqlLiteDbHelper{
    private Context ctx;

    public TExerciseEngine(Context context) {
        super(context);
        ctx = context;
    }

    public Long addExerciseToBasePlan(long exerciseTypeId, long planId, List<TSeriesData> seriesList) {
        THistoryEngine historyEngine = new THistoryEngine(ctx);
        //Long historyId = historyEngine.addHistory(planId, AppValue.BASE);
        Long historyId = historyEngine.getHistoryForPlanID(planId, AppValue.BASE);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues valueExercise = new ContentValues();
        valueExercise.put(ColumnName.Exercise.HISTORY_FK, historyId);
        valueExercise.put(ColumnName.Exercise.EXERCISE_TYPE_FK, exerciseTypeId);
        long exerciseId = db.insert(TableName.EXERCISES, null, valueExercise);

        for (TSeriesData series : seriesList) {
            ContentValues valueSeries = new ContentValues();
            ContentValues valueExerciseSeries = new ContentValues();
            valueSeries.put(ColumnName.SeriesType.NUMBER, series.getName());
            valueSeries.put(ColumnName.SeriesType.WEIGHT, series.getWeight());
            Long serisesId = db.insert(TableName.SERIES_TYPE, null, valueSeries);

            valueExerciseSeries.put(ColumnName.ExrciseSeries.SERIES_TYPE_FK, serisesId);
            valueExerciseSeries.put(ColumnName.ExrciseSeries.EXERCISES_FK, exerciseId);
            db.insertOrThrow(TableName.SERIES, null, valueExerciseSeries);
        }

        db.close();
        return exerciseId;
    }

    public Long addExerciseToHistoryPlan(long exerciseTypeId, long historyId, List<TSeriesData> seriesList) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valueExercise = new ContentValues();
        valueExercise.put(ColumnName.Exercise.HISTORY_FK, historyId);
        valueExercise.put(ColumnName.Exercise.EXERCISE_TYPE_FK, exerciseTypeId);
        long exerciseId = db.insert(TableName.EXERCISES, null, valueExercise);

        for (TSeriesData series : seriesList) {
            ContentValues valueSeries = new ContentValues();
            ContentValues valueExerciseSeries = new ContentValues();
            valueSeries.put(ColumnName.SeriesType.NUMBER, series.getName());
            valueSeries.put(ColumnName.SeriesType.WEIGHT, series.getWeight());
            Long serisesId = db.insert(TableName.SERIES_TYPE, null, valueSeries);

            valueExerciseSeries.put(ColumnName.ExrciseSeries.SERIES_TYPE_FK, serisesId);
            valueExerciseSeries.put(ColumnName.ExrciseSeries.EXERCISES_FK, exerciseId);
            db.insertOrThrow(TableName.SERIES, null, valueExerciseSeries);
        }

        db.close();
        return exerciseId;
    }

    public List<TExerciseData> getExerciseForDate(String date, THistoryData history) {
        List<TExerciseData> exerciseList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String rawQuery = "SELECT E." + ColumnName.Exercise.ID + ", E." + ColumnName.Exercise.EXERCISE_TYPE_FK + ", ET." + ColumnName.ExerciseType.NAME + ", ET." + ColumnName.ExerciseType.DESCRIPTIONS + ", ET."
                + ColumnName.ExerciseType.IMAGE + ", ET." + ColumnName.ExerciseType.TYPE + "\n" +
                "FROM " + TableName.PLAN + " P JOIN " + TableName.HISTORY + " H ON P." + ColumnName.Plan.ID + " = H." + ColumnName.History.PLAN_FK + "\n" +
                "JOIN " + TableName.EXERCISES + " E ON H." + ColumnName.History.ID + " = E." + ColumnName.Exercise.HISTORY_FK + "\n" +
                "JOIN " + TableName.EXERCISE_TYPE + " ET ON ET." + ColumnName.ExerciseType.ID + "= E." + ColumnName.Exercise.EXERCISE_TYPE_FK + "\n" +
                "WHERE H." + ColumnName.History.DATE + " = '" + date + "'\n" +
                "AND P." + ColumnName.Plan.ID + " = " + history.getPlan().getId() ;

        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            TExerciseData exercise = new TExerciseData();
            exercise.setId(cursor.getLong(0));
            exercise.setTypeId(cursor.getLong(1));
            exercise.setName(cursor.getString(2));
            exercise.setDescriptions(cursor.getString(3));
            if (cursor.getBlob(4) != null){
                byte[] decodedString = Base64.decode(cursor.getBlob(4), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                exercise.setImage(decodedByte);
            }
            exercise.setType(cursor.getInt(5));
            exercise.setHistory(history);

            TSeriesEngine seriesEngine = new TSeriesEngine(ctx);
            exercise.setSeriesList(seriesEngine.getSeriesFromDate(date, exercise));


            exerciseList.add(exercise);
        }
        db.close();
        return exerciseList;
    }

    public void deleteExerciseFromPlan(long exerciseId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TableName.SERIES, ColumnName.ExrciseSeries.EXERCISES_FK + "=?", new String[]{exerciseId+""});
        db.delete(TableName.EXERCISES, ColumnName.Exercise.ID + "=?", new String[]{exerciseId+""});
        db.close();
    }

    public List<Long> getExerciseIdByHistoryId(long historyId) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        String rawQuery = "SELECT " + ColumnName.Exercise.ID + " FROM " + TableName.EXERCISES +
                " WHERE " + ColumnName.Exercise.HISTORY_FK + " = " + historyId;
        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            long id;
            id = cursor.getLong(0);
            idList.add(id);
        }
        db.close();
        return idList;
    }
}
