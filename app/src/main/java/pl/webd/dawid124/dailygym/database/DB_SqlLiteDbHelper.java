package pl.webd.dawid124.dailygym.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Java on 2016-05-08.
 */
public class DB_SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Dailyfit.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;
    public DB_SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

// Path to the just created empty db
        String outFileName = getDatabasePath();

// if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

// Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

// transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

// Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub

    }

/*
    public Cursor wczytajDane(){
        String[] kolumny={"ID","Imie","Waga","Wzrost","Wiek","Plec","Budowa","Aktywnosc","xyz"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("Dane",kolumny,null,null,null,null,null);

        return kursor;
    }


    public void dodajDane(String imie,int waga, int wzrost, int wiek, int plec, int budowa, int aktywnosc){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Imie", imie);
        wartosci.put("Waga",waga);
        wartosci.put("Wzrost", wzrost);
        wartosci.put("Wiek", wiek);
        wartosci.put("Plec", plec);
        wartosci.put("Budowa", budowa);
        wartosci.put("Aktywnosc", aktywnosc);
        wartosci.put("xyz", 1);
        db.insertOrThrow("Dane",null, wartosci);
    }


    public DB_Dane dajDane(){
        DB_Dane dane=new DB_Dane();
        SQLiteDatabase db = getReadableDatabase();
        String[] kolumny={"ID","Imie","Waga","Wzrost","Wiek","Plec","Budowa","Aktywnosc","xyz"};
        Cursor kursor=db.query("Dane",kolumny,null,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            dane.setNr(kursor.getLong(0));
            dane.setImie(kursor.getString(1));
            dane.setWaga(kursor.getInt(2));
            dane.setWzrost(kursor.getInt(3));
            dane.setWiek(kursor.getInt(4));
            dane.setPlec(kursor.getInt(5));
            dane.setBudowa(kursor.getInt(6));
            dane.setAktywnosc(kursor.getInt(7));
            dane.setXYZ(kursor.getInt(8));
        }
        return dane;
    }

    public DB_Diety dajDieta(){
        DB_Diety Diety=new DB_Diety();
        SQLiteDatabase db = getReadableDatabase();
        int ID = 1;
        String args[]={ID+""};
        String[] kolumny={"ID","Nazwa","Opis","Fota","Posilki","Kalorie","Typ","Aktywna","Posilek1","Posilek2","Posilek3","Posilek4","Posilek5"};
        Cursor kursor=db.query("Diety",kolumny, "Aktywna=?",args,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            Diety.setNr(kursor.getLong(0));
            Diety.setNazwa(kursor.getString(1));
            Diety.setOpis(kursor.getString(2));
            Diety.setFota(kursor.getString(3));
            Diety.setPosilki(kursor.getInt(4));
            Diety.setKalorie(kursor.getInt(5));
            Diety.setTyp(kursor.getInt(6));
            Diety.setPosilek1(kursor.getInt(8));
            Diety.setPosilek2(kursor.getInt(9));
            Diety.setPosilek3(kursor.getInt(10));
            Diety.setPosilek4(kursor.getInt(11));
            Diety.setPosilek5(kursor.getInt(12));

        }
        return Diety;
    }

    public DB_Posilki dajPosilek(long ID){
        DB_Posilki Posilki=new DB_Posilki();
        SQLiteDatabase db = getReadableDatabase();
        String args[]={ID+""};
        String[] kolumny={"ID","Posilek1","Posilek2","Posilek3","Posilek4","Posilek5","Posilek6","Posilek7"};
        Cursor kursor=db.query("Posilki",kolumny, "ID=?",args,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            Posilki.setNr(kursor.getLong(0));
            Posilki.setPosilek1(kursor.getString(1));
            Posilki.setPosilek2(kursor.getString(2));
            Posilki.setPosilek3(kursor.getString(3));
            Posilki.setPosilek4(kursor.getString(4));
            Posilki.setPosilek5(kursor.getString(5));
            Posilki.setPosilek6(kursor.getString(6));
            Posilki.setPosilek7(kursor.getString(7));
        }
        return Posilki;
    }

    public List<DB_Diety> dajWszystkie(){
        List<DB_Diety> DietyList = new LinkedList<DB_Diety>();
        String[] kolumny={"ID","Nazwa","Opis","Fota","Posilki","Kalorie","Typ"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("Diety",kolumny,null,null,null,null,null);
        while(kursor.moveToNext()){
            DB_Diety Diety = new DB_Diety();
            Diety.setNr(kursor.getLong(0));
            Diety.setNazwa(kursor.getString(1));
            Diety.setOpis(kursor.getString(2));
            Diety.setFota(kursor.getString(3));
            Diety.setPosilki(kursor.getInt(4));
            Diety.setKalorie(kursor.getInt(5));
            Diety.setTyp(kursor.getInt(6));
            DietyList.add(Diety);
        }
        return DietyList;
    }

    public List<DB_Treningi> dajWszystkieTreningi(){
        List<DB_Treningi> TreningiList = new LinkedList<DB_Treningi>();
        String[] kolumny={"ID","Nazwa","Opis","Fota","Plec","Typ","Aktywna"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("Treningi",kolumny,null,null,null,null,null);
        while(kursor.moveToNext()){
            DB_Treningi Treningi = new DB_Treningi();
            Treningi.setNr(kursor.getLong(0));
            Treningi.setNazwa(kursor.getString(1));
            Treningi.setOpis(kursor.getString(2));
            Treningi.setFota(kursor.getString(3));
            Treningi.setPlec(kursor.getInt(4));
            Treningi.setTyp(kursor.getInt(5));
            Treningi.setAktywna(kursor.getInt(6));
            TreningiList.add(Treningi);
        }
        return TreningiList;
    }

    public List<DB_Lacznik> dajLacznik(Long ID){
        List<DB_Lacznik> LacznikList = new LinkedList<DB_Lacznik>();
        String[] kolumny={"ID","ID_Treningu","ID_Cwiczenia"};
        String args[]={ID+""};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("Lacznik",kolumny, "ID_Treningu=?",args,null,null,null,null);
        while(kursor.moveToNext()){
            DB_Lacznik Lacznik = new DB_Lacznik();
            Lacznik.setNr(kursor.getLong(0));
            Lacznik.setID_T(kursor.getLong(1));
            Lacznik.setID_C(kursor.getLong(2));
            LacznikList.add(Lacznik);
        }
        return LacznikList;
    }

    public DB_Cwiczenia dajCwiczenie(long ID){
        DB_Cwiczenia Cwiczenia=new DB_Cwiczenia();
        SQLiteDatabase db = getReadableDatabase();
        String args[]={ID+""};
        String[] kolumny={"ID","Nazwa","Opis"};
        Cursor kursor=db.query("Cwiczenia",kolumny, "ID=?",args,null,null,null,null);
        if(kursor!=null){
            kursor.moveToFirst();
            Cwiczenia.setNr(kursor.getLong(0));
            Cwiczenia.setNazwa(kursor.getString(1));
            Cwiczenia.setOpis(kursor.getString(2));
        }
        return Cwiczenia;
    }

    public void aktualizujDane(String imie,int waga, int wzrost, int wiek, int plec, int budowa, int aktywnosc){
        int nr = 1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Imie", imie);
        wartosci.put("Waga",waga);
        wartosci.put("Wzrost", wzrost);
        wartosci.put("Wiek", wiek);
        wartosci.put("Plec", plec);
        wartosci.put("Budowa", budowa);
        wartosci.put("Aktywnosc", aktywnosc);
        wartosci.put("xyz", 1);
        String args[]={nr+""};
        db.update("Dane", wartosci,"xyz=?",args);
    }

    public void kasujDieta(){
        int ID = 1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Aktywna", 0);
        String args[]={ID+""};
        db.update("Diety", wartosci,"Aktywna=?",args);
    }

    public void aktualizujDieta(long ID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Aktywna", 1);
        String args[]={ID+""};
        db.update("Diety", wartosci,"ID=?",args);
    }

    public void kasujDane(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] argumenty={""+id};
        db.delete("Dane", "ID=?", argumenty);
    }

    public void aktualizujTrening(long ID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Aktywna", 1);
        String args[]={ID+""};
        db.update("Treningi", wartosci,"ID=?",args);
    }
    */
}


