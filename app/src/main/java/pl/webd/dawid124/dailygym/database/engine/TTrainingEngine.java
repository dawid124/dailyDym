package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.webd.dawid124.dailygym.database.ColumnName;
import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TPlanData;
import pl.webd.dawid124.dailygym.database.data.TTrainingData;

/**
 * Created by Java on 2016-05-08.
 */
public class TTrainingEngine extends DB_SqlLiteDbHelper {
    private Context ctx;

    public TTrainingEngine(Context context) {
        super(context);
        ctx = context;
    }

    public List<TTrainingData> getTrainingsForDate(String date) {
        List<TTrainingData> trainingList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String rawQuery = "SELECT t.TRAINING_ID, t.NAME, t.DATE " +
                "FROM TBL_PLAN p JOIN TBL_HISTORY h ON P.PLAN_ID = h.PLAN_FK " +
                "JOIN TBL_TRAINING t ON p.TRAINING_FK = t.TRAINING_ID " +
                "WHERE h.DATE = '" + date + "' " +
                "AND t.ACTIVE = 1 " +
                "GROUP BY t.TRAINING_ID ";

        Cursor cursor = db.rawQuery(rawQuery, null);
        while (cursor.moveToNext()) {
            TTrainingData training = new TTrainingData();
            training.setId(cursor.getLong(0));
            training.setActive(true);
            training.setName(cursor.getString(1));
            training.setCreatedDate(cursor.getString(2));

            TPlanEngine planEngine = new TPlanEngine(ctx);
            training.setTrainingPlans(planEngine.getPlansForDate(date, training));
            trainingList.add(training);
        }
        db.close();
        return trainingList;
    }

    public TTrainingData getTraining(long trainingId) {
        TTrainingData training = new TTrainingData();
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {ColumnName.Training.ID, ColumnName.Training.NAME, ColumnName.Training.DESCRITPIONS, ColumnName.Training.DATE, ColumnName.Training.ACTIVE};
        String selection = ColumnName.Training.ID + "=?";
        String selectionArgs[] = {trainingId + ""};
        Cursor cursor = db.query(TableName.TRAINING, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            training.setId(cursor.getLong(0));
            training.setName(cursor.getString(1));
            training.setDescripion(cursor.getString(2));
            training.setCreatedDate(cursor.getString(3));
            training.setActive(cursor.getInt(4) == 1);
        }
        db.close();
        return training;
    }

    public void createTraining(List<TPlanData> planList, String name, String descriptions) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ContentValues value = new ContentValues();
        value.put(ColumnName.Training.NAME, name);
        value.put(ColumnName.Training.DESCRITPIONS, descriptions);
        value.put(ColumnName.Training.ACTIVE, true);
        value.put(ColumnName.Training.DATE, dateFormat.format(date));
        long trainingId = db.insert(TableName.TRAINING, null, value);
        db.close();

        TPlanEngine planEngine = new TPlanEngine(ctx);
        for (TPlanData plan : planList){
            planEngine.createPlan(plan.getName(), trainingId);
        }
    }
}
