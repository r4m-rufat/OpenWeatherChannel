package com.kivitool.openweatherchannel.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.MessagePattern;

import java.io.File;
import java.util.ArrayList;

public class SpinnerDatabase extends SQLiteOpenHelper {

    Context context;

    private static String DbName = "EMPLOYEES";
    private static int Version = 3;


    private static String TB_Tablename = "DB_Tablename";
    private static String CL_Id = "CL_Id";
    private static String CL_Name = "DB_Name";


    public SpinnerDatabase(Context context) {
        super(context, DbName, null, Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_Tablename + "(" +
                CL_Id + " INTEGER PRIMARY KEY ," +
                CL_Name + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_Tablename);
        onCreate(db);

    }

    public long InsertColumn(String CP_Name) {

        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CL_Name, CP_Name);

        long res = sql.insert(TB_Tablename, null, contentValues);

        return res;
    }



    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TB_Tablename + " order by " + CL_Id + " desc";
        Cursor data = db.rawQuery(query, null);

        return data;
    }


    public void removeLocationDataListItem(String name) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("DELETE FROM " + TB_Tablename + " WHERE "+ CL_Name +"='"+ name + "'");
        sqLiteDatabase.close();

    }

    public boolean checkingNull(){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TB_Tablename, null);
        Boolean rowExists;

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }

    public boolean checkIsRowExists(String dbName){

        String[] columns={CL_Id};

        SQLiteDatabase db=this.getReadableDatabase();

        String select=CL_Name + "=?";

        String[] selectControl = {dbName};


        Cursor cursor=db.query(TB_Tablename,columns,select,selectControl,null,null,null);

        int count=cursor.getCount();
        cursor.close();
        db.close();


        if (count>0){
            return true;
        }else {
            return false;
        }

    }

}
