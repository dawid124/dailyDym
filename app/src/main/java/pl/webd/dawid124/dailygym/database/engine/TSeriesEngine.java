package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.database.ColumnName;
import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;

/**
 * Created by Java on 2016-05-09.
 */
public class TSeriesEngine extends DB_SqlLiteDbHelper{
    private Context ctx;

    public TSeriesEngine(Context context) {
        super(context);
        ctx = context;
    }

    public List<TSeriesData> getSeriesFromDate(String date, TExerciseData exercise) {
        List<TSeriesData> seriesList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String rawQuery = "SELECT S.SERIES_TYPE_ID, S.NUMBER, S.WEIGHT\n" +
                "FROM TBL_PLAN P JOIN TBL_HISTORY H ON P.PLAN_ID = H.PLAN_FK\n" +
                "\t\t\t\t\t\tJOIN TBL_EXERCISES E ON H.HISTORY_ID = E.HISTORY_FK\n" +
                "\t\t\t\t\t\tJOIN TBL_EXERCISE_TYPE ET ON ET.EXERCISE_ID = E.EXERCISE_TYPE_FK\n" +
                "\t\t\t\t\t\tJOIN TBL_EXERCISES_SERIES ES ON ES.EXERCISES_FK = E.EXERCISES_ID\n" +
                "\t\t\t\t\t\tJOIN TBL_SERIES_TYPE S ON S.SERIES_TYPE_ID = ES.SERIES_TYPE_FK\n" +
                "WHERE H.DATE = '" + date + "'\n" +
                "AND  E.EXERCISES_ID = " + exercise.getId();

        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            TSeriesData series = new TSeriesData();
            series.setId(cursor.getLong(0));
            series.setName(cursor.getString(1));
            series.setWeight(cursor.getInt(2));
            series.setExercise(exercise);
            seriesList.add(series);
        }
        db.close();
        return seriesList;
    }

    public List<Long> getExercisSereisIdForExerciseId(SQLiteDatabase db, long exerciseId) {
        List<Long> seriesTypeIdList = new ArrayList<>();

        String rawQuery = "SELECT " + ColumnName.ExrciseSeries.SERIES_TYPE_FK + " FROM " + TableName.SERIES +
                " WHERE " + ColumnName.ExrciseSeries.EXERCISES_FK + " = " + exerciseId ;
        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            long id;
            id = cursor.getLong(0);
            seriesTypeIdList.add(id);
        }
        return seriesTypeIdList;
    }

    public boolean updateSeriesForExerciseId(List<TSeriesData> seriesList, List<EditText> seriesDataList) {
        SQLiteDatabase db = getWritableDatabase();
        if (seriesDataList.size() == seriesList.size()) {
            for (int i = 0; i < seriesList.size(); i++) {
                String where = ColumnName.SeriesType.ID + "=?";
                ContentValues dataToInsert = new ContentValues();
                dataToInsert.put(ColumnName.SeriesType.WEIGHT, seriesDataList.get(i).getText().toString());

                String[] whereArgs = new String[] {String.valueOf(seriesList.get(i).getId())};
                db.update(TableName.SERIES_TYPE, dataToInsert, where, whereArgs);
            }
            db.close();
            return true;
        }
        db.close();
        return false;
    }
}
