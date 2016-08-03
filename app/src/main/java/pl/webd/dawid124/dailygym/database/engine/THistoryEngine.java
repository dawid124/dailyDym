package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.webd.dawid124.dailygym.database.ColumnName;
import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TExerciseData;
import pl.webd.dawid124.dailygym.database.data.THistoryData;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TSeriesData;
import pl.webd.dawid124.dailygym.static_value.AppValue;

/**
 * Created by Java on 2016-05-09.
 */
public class THistoryEngine extends DB_SqlLiteDbHelper{
    private Context ctx;

    public THistoryEngine(Context context) {
        super(context);
        ctx = context;
    }

    public List<THistoryData> getHistoryForDate(String date, TPlanData plan) {
        List<THistoryData> historyList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String rawQuery = "SELECT h.HISTORY_ID, h.DATE\n" +
                "FROM TBL_PLAN p JOIN TBL_HISTORY h ON P.PLAN_ID = h.PLAN_FK\n" +
                "\t\t\t\t\t\tJOIN TBL_TRAINING t ON p.TRAINING_FK = t.TRAINING_ID\n" +
                "WHERE h.DATE = '" + date + "'\n" +
                "AND p.PLAN_ID = " + plan.getId();

        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            THistoryData history = new THistoryData();

            history.setId(cursor.getLong(0));
            history.setDate(cursor.getString(1));
            history.setPlan(plan);

            TExerciseEngine exerciseEngine = new TExerciseEngine(ctx);
            List<TExerciseData> exerciseList = exerciseEngine.getExerciseForDate(date, history);
            history.setExercisesList(exerciseList);

            historyList.add(history);
        }
        db.close();
        return historyList;
    }

    public Long addHistory(long planId, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valueHistory = new ContentValues();
        valueHistory.put(ColumnName.History.PLAN_FK, planId);
        valueHistory.put(ColumnName.History.DATE, date);
        long historyId = db.insert(TableName.HISTORY, null, valueHistory);
        db.close();
        return historyId;
    }

    public Long getHistoryForPlanID(long planId, String date) {
        long hisotryId = -1;
        SQLiteDatabase db = getWritableDatabase();
        String[] columns={ColumnName.History.ID};

        String selection = ColumnName.History.DATE + "=?" + " AND " + ColumnName.History.PLAN_FK + "=?";
        String selectionArgs[]={date+"",planId+""};

        Cursor cursor=db.query(TableName.HISTORY, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
            hisotryId = cursor.getLong(0);
        }
        db.close();
        return hisotryId;
    }

    public boolean addNewTrainingToHistory(long planId, String date) {
        TPlanEngine planEngine = new TPlanEngine(ctx);
        TExerciseEngine exerciseEngine = new TExerciseEngine(ctx);
        TPlanData plan = planEngine.getPlan(planId);
        long historyId = addHistory(planId,date);
        List<TExerciseData> exerciseList = plan.getHistoryList().get(0).getExercise();

        for (TExerciseData exercise : exerciseList) {
            List<TSeriesData> seriesList = exercise.getSeriesList();
            exerciseEngine.addExerciseToHistoryPlan(exercise.getTypeId(), historyId, seriesList);
        }
        return true;
    }

    public boolean isPlanOnTheHistroyList(String date, long planId) {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns={ColumnName.History.ID};

        String selection = ColumnName.History.DATE + "=?" + " AND " + ColumnName.History.PLAN_FK + "=?";
        String selectionArgs[]={date+"",planId+""};

        Cursor cursor=db.query(TableName.HISTORY, columns, selection, selectionArgs, null, null, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Long getHistoryIdByPlanID(long planId) {
        SQLiteDatabase db = getWritableDatabase();
        Long id = -1l;
        String rawQuery = "SELECT " + ColumnName.History.ID + " FROM " + TableName.HISTORY +
                " WHERE " + ColumnName.History.PLAN_FK + " = " + planId;
        Cursor cursor=db.rawQuery(rawQuery, null);
        if(cursor!=null) {
            cursor.moveToFirst();
            id = cursor.getLong(0);
        }
        db.close();
        return id;
    }

    public int getCountActivePlan() {
        SQLiteDatabase db = getWritableDatabase();
        int count = -1;
        String rawQuery = "SELECT COUNT(" + ColumnName.History.DATE + ")" +
                " FROM " + TableName.HISTORY + " h" +
                " WHERE " + ColumnName.History.DATE + " IN ('" + AppValue.BASE + "', null)";

        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        db.close();
        return count;
    }
}
