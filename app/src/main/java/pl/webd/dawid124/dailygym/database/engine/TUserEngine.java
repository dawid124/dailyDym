package pl.webd.dawid124.dailygym.database.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.webd.dawid124.dailygym.database.DB_SqlLiteDbHelper;
import pl.webd.dawid124.dailygym.database.TableName;
import pl.webd.dawid124.dailygym.database.data.TUserData;

/**
 * Created by Java on 2016-05-08.
 */
public class TUserEngine {
    private DB_SqlLiteDbHelper SQLiteHelper;

    public TUserEngine(Context context) {
        SQLiteHelper = new DB_SqlLiteDbHelper(context);
    }

    public void createUser(String userName){
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ContentValues value = new ContentValues();
        value.put("NAME", userName);
        value.put("DATE_CREATED", dateFormat.format(date));
        db.insertOrThrow(TableName.USER, null, value);
        db.close();
    }

    public TUserData getUuser(){
        TUserData userData = new TUserData();
        SQLiteDatabase db = SQLiteHelper.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] columns={"NAME","DATE_CREATED"};
        Cursor cursor=db.query(TableName.USER,columns,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            userData.setUserName(cursor.getString(0));
            String dateStr = cursor.getString(1);
            try {
                Date date = dateFormat.parse(dateStr);
                userData.setCreatedDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        db.close();
        return userData;
    }
}
