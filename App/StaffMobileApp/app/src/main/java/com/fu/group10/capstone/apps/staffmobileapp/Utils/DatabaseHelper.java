package com.fu.group10.capstone.apps.staffmobileapp.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.fu.group10.capstone.apps.staffmobileapp.dao.EquipmentDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QuangTV on 7/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ECRM_DATA";

    private static final String TABLE_EQUIPMENT_CATEGORY = "tblEquipmentCategory";

    private static final String EC_NAME = "name";
    private static final String EC_IMAGE = "images";


    private static final String CREATE_TABLE_EQUIPMENT_CATEGORY = "CREATE TABLE " +
            TABLE_EQUIPMENT_CATEGORY + "("  + EC_NAME + " TEXT," + EC_IMAGE + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPMENT_CATEGORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CATEGORY);

        onCreate(sqLiteDatabase);
    }



    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }

    }

    public void deleteEquipment() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EQUIPMENT_CATEGORY, null, null);
    }


    public void insertEquipment(String name, byte[] image) {
        SQLiteDatabase db               =   this.getWritableDatabase();
        String sql                      =   "INSERT OR REPLACE INTO " +
                TABLE_EQUIPMENT_CATEGORY +" (name,images) VALUES(?,?)";
        SQLiteStatement insertStmt      =   db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, name);
        insertStmt.bindBlob(2, image);
        insertStmt.executeInsert();
        db.close();
    }



    public List<EquipmentDAO> getEquipments() {
        List<EquipmentDAO> result = new ArrayList<>();

        SQLiteDatabase db       =   this.getWritableDatabase();
        String sql              =   "SELECT * FROM " + TABLE_EQUIPMENT_CATEGORY;
        Cursor cursor           =   db.rawQuery(sql, new String[] {});

        if(cursor.moveToFirst()){
            do {
                EquipmentDAO dao = new EquipmentDAO();
                dao.setName(cursor.getString(0));
                dao.setImages(cursor.getBlob(1));
                result.add(dao);
            } while (cursor.moveToNext());

        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        if(cursor.getCount() == 0){
            return null;
        } else {
            return result;
        }

    }


}
