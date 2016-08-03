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
import pl.webd.dawid124.dailygym.database.data.THistoryData;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TTrainingData;
import pl.webd.dawid124.dailygym.static_value.AppValue;

/**
 * Created by Java on 2016-05-08.
 */
public class TPlanEngine extends DB_SqlLiteDbHelper{
    private Context ctx;

    public TPlanEngine(Context context) {
        super(context);
        ctx = context;

    }

    public void createPlan(String name, long trainingId){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valuePlan = new ContentValues();

        valuePlan.put(ColumnName.Plan.NAME, name);
        valuePlan.put(ColumnName.Plan.TRAINING_FK, trainingId);
        long planid = db.insert(TableName.PLAN, null, valuePlan);
        db.close();

        THistoryEngine historyEngine = new THistoryEngine(ctx);
        historyEngine.addHistory(planid, AppValue.BASE);
    }


    public List<TPlanData> getPlansForDate(String date, TTrainingData training) {
        List<TPlanData> planList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();


        String rawQuery = "SELECT P.PLAN_ID, P.NAME, h.HISTORY_ID, h.DATE\n" +
                "FROM TBL_PLAN p JOIN TBL_HISTORY h ON P.PLAN_ID = h.PLAN_FK\n" +
                "JOIN TBL_TRAINING t ON p.TRAINING_FK = t.TRAINING_ID\n" +
                "WHERE h.DATE = '" + date + "'\n" +
                "AND t.ACTIVE = 1\n" +
                "AND t.TRAINING_ID = " + training.getId();

        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            TPlanData plan = new TPlanData();
            THistoryData history = new THistoryData();
            plan.setId(cursor.getLong(0));
            plan.setName(cursor.getString(1));

            history.setId(cursor.getLong(2));
            history.setDate(cursor.getString(3));
            history.setPlan(plan);

            THistoryEngine historyEngine = new THistoryEngine(ctx);
            List<THistoryData> historyList = historyEngine.getHistoryForDate(date, plan);
            plan.setTraining(training);
            plan.setHistoryList(historyList);

            planList.add(plan);
        }
        db.close();
        return planList;
    }
/*
    public ArrayList<TPlanData> getAllPlans() {
        ArrayList<TPlanData> planList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String[] columns={ColumnName.Plan.ID,ColumnName.Plan.NAME,ColumnName.Plan.TRAINING_FK};
        Cursor cursor=db.query(TableName.PLAN, columns, null, null, null, null, null);

        while(cursor.moveToNext()){
            TPlanData plan = new TPlanData();
            plan.setId(cursor.getLong(0));
            plan.setName(cursor.getString(1));
            TTrainingEngine trainingEngine = new TTrainingEngine(ctx);
            plan.setTrainingId(cursor.getLong(2));
            //plan.setTraining(trainingEngine.getTraining(cursor.getLong(3)));

            planList.add(plan);
        }
        db.close();
        return planList;
    }
*/
    public List<TPlanData> getAllPlans() {
        List<TPlanData> planList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String rawQuery = "SELECT P.PLAN_ID, P.NAME, h.HISTORY_ID, h.DATE, t.TRAINING_ID\n" +
                "FROM TBL_PLAN p JOIN TBL_HISTORY h ON P.PLAN_ID = h.PLAN_FK\n" +
                "JOIN TBL_TRAINING t ON p.TRAINING_FK = t.TRAINING_ID\n" +
                "WHERE h.DATE = '" + AppValue.BASE + "'\n" +
                "AND t.ACTIVE = 1\n" ;

        Cursor cursor=db.rawQuery(rawQuery, null);
        while(cursor.moveToNext()){
            TPlanData plan = new TPlanData();
            THistoryData history = new THistoryData();
            plan.setId(cursor.getLong(0));
            plan.setName(cursor.getString(1));

            history.setId(cursor.getLong(2));
            history.setDate(cursor.getString(3));
            history.setPlan(plan);

            THistoryEngine historyEngine = new THistoryEngine(ctx);
            List<THistoryData> historyList = historyEngine.getHistoryForDate(AppValue.BASE, plan);
            plan.setHistoryList(historyList);

            TTrainingEngine trainingEngine = new TTrainingEngine(ctx);
            plan.setTraining(trainingEngine.getTraining(cursor.getLong(4)));

            planList.add(plan);
        }
        db.close();
        return planList;
    }

    public TPlanData getPlan(long planId) {
        SQLiteDatabase db = getWritableDatabase();
        TPlanData plan = new TPlanData();

        String rawQuery = "SELECT P.PLAN_ID, P.NAME, h.HISTORY_ID, h.DATE, t.TRAINING_ID\n" +
                "FROM TBL_PLAN p JOIN TBL_HISTORY h ON P.PLAN_ID = h.PLAN_FK\n" +
                "JOIN TBL_TRAINING t ON p.TRAINING_FK = t.TRAINING_ID\n" +
                "WHERE h.DATE = '" + AppValue.BASE + "'\n" +
                "AND t.ACTIVE = 1\n" +
                "AND p.PLAN_ID = " + planId;

        Cursor cursor=db.rawQuery(rawQuery, null);
        if(cursor!=null){
            cursor.moveToFirst();
            THistoryData history = new THistoryData();
            plan.setId(cursor.getLong(0));
            plan.setName(cursor.getString(1));

            history.setId(cursor.getLong(2));
            history.setDate(cursor.getString(3));
            history.setPlan(plan);

            THistoryEngine historyEngine = new THistoryEngine(ctx);
            List<THistoryData> historyList = historyEngine.getHistoryForDate(AppValue.BASE, plan);
            plan.setHistoryList(historyList);

            TTrainingEngine trainingEngine = new TTrainingEngine(ctx);
            plan.setTraining(trainingEngine.getTraining(cursor.getLong(4)));
        }

        db.close();
        return plan;
    }

    public void deletePlan(long planId) {
        SQLiteDatabase db = getWritableDatabase();
/*

        THistoryEngine historyEngine = new THistoryEngine(ctx);
        TExerciseEngine exerciseEngine = new TExerciseEngine(ctx);
        TSeriesEngine seriesEngine = new TSeriesEngine(ctx);

        long historyId = historyEngine.getHistoryIdByPlanID(planId);
        List<Long> exerciseId = exerciseEngine.getExerciseIdByHistoryId(historyId);
        List<Long> exerciseSeriesId = seriesEngine.getExerciseSereisIdByExerciseId(exerciseId) ;


        db.delete(TableName.SERIES_TYPE, ColumnName.SeriesType.ID + " IN (?) ", new String[]{exerciseSeriesId + ""});
        db.delete(TableName.SERIES, ColumnName.ExrciseSeries.EXERCISES_FK + " IN (?)", new String[]{exerciseId +""});
        db.delete(TableName.EXERCISES, ColumnName.Exercise.HISTORY_FK + " =? ", new String[]{historyId + ""});


        db.delete(TableName.HISTORY, ColumnName.History.PLAN_FK + " =? and " + ColumnName.History.DATE + " =?", new String[]{planId+"", AppValue.BASE});
        db.delete(TableName.PLAN, ColumnName.Plan.ID + "=?", new String[]{planId+""});
*/

        ContentValues data=new ContentValues();
        data.put(ColumnName.History.DATE, AppValue.DELETED);
        db.update(TableName.HISTORY, data, ColumnName.History.PLAN_FK +  " =? AND " + ColumnName.History.DATE + " =?", new String[]{planId+"", AppValue.BASE});

        db.close();
    }
}
