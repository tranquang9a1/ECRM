package com.fu.group10.capstone.apps.teachermobileapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.fu.group10.capstone.apps.teachermobileapp.dao.ClassroomDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.EquipmentDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ReportDetailDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.ScheduleDAO;
import com.fu.group10.capstone.apps.teachermobileapp.dao.UserDAO;
import com.fu.group10.capstone.apps.teachermobileapp.model.ClassroomInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.Equipment;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentQuantity;
import com.fu.group10.capstone.apps.teachermobileapp.model.EquipmentReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.ReportInfo;
import com.fu.group10.capstone.apps.teachermobileapp.model.User;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by QuangTV on 7/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 8;

    private static final String DATABASE_NAME = "ECRM_DATA";

    private static final String TABLE_REPORT = "tblReport";
    private static final String TABLE_REPORT_DETAIL = "tblReportDetail";
    private static final String TABLE_CLASSROOM = "tblClassroom";
    private static final String TABLE_EQUIPMENT_QUANTITY = "tblEquipmentQuantity";
    private static final String TABLE_SCHEDULE = "tblSchedule";
    private static final String TABLE_USER = "tblUser";
    private static final String TABLE_USER_INFO = "tblUserInfo";
    private static final String TABLE_EQUIPMENT_CATEGORY = "tblEquipmentCategory";
    private static final String TABLE_EQUIPMENT = "tblEquipment";
    private static final String TABLE_EQUIPMENT_CLASSROOM = "tblEquipmentClassroom";


    public static final String EQ_ID = "id";
    public static final String EQ_CLASSID = "classId";
    public static final String EQ_EQUIPMENT_NAME = "equipmentName";
    public static final String EQ_QUANTITY = "quantity";
    public static final String EQ_PRIORITY = "priority";
    public static final String EQ_IS_DELETED = "isDeleted";

    private static final String EC_CLASSID = "classId";
    private static final String EC_EQUIPMENT_NAME = "equipmentName";
    private static final String EC_TIME_REMAIN =  "timeRemain";
    private static final String EC_COMPANY =  "company";
    private static final String EC_IS_DAMAGED = "isDamaged";
    private static final String EC_DAMAGED_LEVEL ="damageLevel";
    private static final String EC_EVALUATE = "evaluate";
    private static final String EC_POSITION = "position";

    private static final String SCHEDULE_ID = "id";
    private static final String SCHEDULE_USERNAME = "username";
    private static final String SCHEDULE_CLASSROOMID = "classroomId";
    private static final String SCHEDULE_TIMEFROM = "timeFrom";
    private static final String SCHEDULE_TIMETO = "timeTo";
    private static final String SCHEDULE_DATE = "date";
    private static final String SCHEDULE_IS_ACTIVE = "isActive";
    private static final String SCHEDULE_CLASSNAME = "name";


    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_FULLNAME = "fullname";
    private static final String USER_PHONE = "phone";
    private static final String USER_DEVICE_ID = "deviceId";



    private static final String CLASSROOM_NAME = "name";
    private static final String CLASSROOM_ID = "classId";
    private static final String CLASSROOM_DAMAGE_LEVEL = "damagedLevel";
    private static final String CLASSROOM_TYPE = "roomType";



    private static final String REPORT_ID = "reportId";
    private static final String REPORT_USERNAME = "username";
    private static final String REPORT_CLASSID = "classId";
    private static final String REPORT_CLASSNAME = "className";
    private static final String REPORT_CREATE = "createTime";
    private static final String REPORT_EVALUATE = "evaluate";
    private static final String REPORT_STATUS = "status";
    private static final String REPORT_CHANGE_ROOM = "changeRoom";
    private static final String REPORT_IS_SYNC = "isSync";
    private static final String REPORT_DAMAGE_LEVEL = "damageLevel";

    private static final String RD_EQUIPMENT_NAME = "equipmentName";
    private static final String RD_REPORT_ID = "reportId";
    private static final String RD_DAMAGED = "damaged";
    private static final String RD_DESCRIPTION = "description";
    private static final String RD_STATUS = "status";
    private static final String RD_IS_SYNC = "isSync";

    private static final String EC_ID = "id";
    private static final String EC_NAME = "name";
    private static final String EC_IMAGE = "images";

    private static final String EQUIPMENT_ID = "equipmentId";
    private static final String EQUIPMENT_CATEGORY_ID = "categoryId";
    private static final String EQUIPMENT_CLASSROOM_ID  = "classroomId";
    private static final String EQUIPMENT_POSITION = "position";
    private static final String EQUIPMENT_STATUS = "status";
    private static final String EQUIPMENT_TIMEREMAIN = "timeRemain";
    private static final String EQUIPMENT_NAME = "name";
    private static final String EQUIPMENT_SERIAL = "serialNumber";
    private static final String EQUIPMENT_IS_SYNC = "isSync";




    private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE + "(" + SCHEDULE_ID +
            " INTEGER PRIMARY KEY," + SCHEDULE_USERNAME + " TEXT," + SCHEDULE_CLASSROOMID + " INTEGER,"
            + SCHEDULE_CLASSNAME + " TEXT," + SCHEDULE_TIMEFROM + " TEXT," + SCHEDULE_TIMETO + " TEXT," + SCHEDULE_DATE
            + " TEXT, " + SCHEDULE_IS_ACTIVE + " INTEGER" + ")";

    private static final String CREATE_TABLE_EQUIPMENT_CATEGORY = "CREATE TABLE " +
            TABLE_EQUIPMENT_CATEGORY + "("  + EC_ID + " TEXT," + EC_NAME + " TEXT," + EC_IMAGE + " TEXT" + ")";

    private static final String CREATE_TABLE_EQUIPMENT_CLASSROOM = "CREATE TABLE " + TABLE_EQUIPMENT_CLASSROOM + "("
            + EC_CLASSID + " INTEGER," + EC_EQUIPMENT_NAME + " TEXT," + EC_POSITION + " TEXT," + EC_COMPANY + " TEXT,"
            + EC_DAMAGED_LEVEL + " TEXT," + EC_TIME_REMAIN + " TEXT," + EC_EVALUATE + " TEXT," + EC_IS_DAMAGED
            + " INTEGER" + ")";

    private static final String CREATE_TABLE_EQUIPMENT = "CREATE TABLE " + TABLE_EQUIPMENT + "(" + EQUIPMENT_ID +
            " INTEGER PRIMARY KEY," + EQUIPMENT_CATEGORY_ID  + " INTEGER," + EQUIPMENT_CLASSROOM_ID + " INTEGER," +
            EQUIPMENT_POSITION + " TEXT," + EQUIPMENT_STATUS + " BIT," + EQUIPMENT_TIMEREMAIN + " INTEGER," +
            EQUIPMENT_NAME + " TEXT," + EQUIPMENT_SERIAL + " TEXT," + EQUIPMENT_IS_SYNC + " BIT" + ")";

    private static final String CREATE_TABLE_REPORT_DETAIL = "CREATE TABLE " + TABLE_REPORT_DETAIL + "(" +
            RD_EQUIPMENT_NAME + " TEXT," + RD_REPORT_ID + " INTEGER," + RD_DAMAGED + " TEXT," + RD_DESCRIPTION +
            " TEXT," + RD_STATUS + " BIT," + RD_IS_SYNC + " BIT" + ")";

    private static final String CREATE_TABLE_REPORT = "CREATE TABLE " + TABLE_REPORT + "(" + REPORT_ID +
            " INTEGER PRIMARY KEY," + REPORT_USERNAME + " TEXT," + REPORT_CLASSID + " INTEGER," + REPORT_CLASSNAME +
            " TEXT," + REPORT_CREATE + " DATETIME," + REPORT_EVALUATE + " TEXT," + REPORT_DAMAGE_LEVEL + " INTEGER," +
            REPORT_STATUS + " BIT," + REPORT_CHANGE_ROOM + " INTEGER, " + REPORT_IS_SYNC + " BIT" + ")";


    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + USER_USERNAME +
            " TEXT PRIMARY KEY ," + USER_PASSWORD + " TEXT," + USER_FULLNAME + " TEXT," + USER_PHONE + " TEXT," +
            USER_DEVICE_ID + " TEXT" + ")";

    private static final String CREATE_TABLE_CLASSROOM = "CREATE TABLE " + TABLE_CLASSROOM + "(" + CLASSROOM_ID +
            " INTEGER PRIMARY KEY," + CLASSROOM_NAME + " TEXT," + CLASSROOM_TYPE + " INTEGER," + CLASSROOM_DAMAGE_LEVEL
            + " INTEGER" + ")";

    private static final String CREATE_TABLE_EQUIPMENT_QUANTITY = "CREATE TABLE " + TABLE_EQUIPMENT_QUANTITY + "("
            + EQ_ID + " INTEGER PRIMARY KEY, " + EQ_CLASSID + " TEXT," + EQ_EQUIPMENT_NAME + " TEXT,"
            + EQ_QUANTITY + " INTEGER," + EQ_PRIORITY + " INTEGER," + EQ_IS_DELETED + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_REPORT);
        sqLiteDatabase.execSQL(CREATE_TABLE_REPORT_DETAIL);
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPMENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CLASSROOM);
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPMENT_CLASSROOM);
        sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPMENT_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPMENT_QUANTITY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSROOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CLASSROOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_QUANTITY);


        onCreate(sqLiteDatabase);
    }

    public void insertSchedule(ScheduleDAO schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCHEDULE_ID, schedule.getId());
        values.put(SCHEDULE_CLASSROOMID, schedule.getClassId());
        values.put(SCHEDULE_USERNAME, schedule.getUsername());
        values.put(SCHEDULE_TIMEFROM, schedule.getTimeFrom());
        values.put(SCHEDULE_TIMETO, schedule.getTimeTo());
        values.put(SCHEDULE_DATE, schedule.getDate());
        values.put(SCHEDULE_IS_ACTIVE, schedule.getIsActive());
        values.put(SCHEDULE_CLASSNAME, schedule.getClassName());

        db.insert(TABLE_SCHEDULE, null, values);
        db.close();

    }

    public void insertUser(UserDAO user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FULLNAME, user.getFullname());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_DEVICE_ID, user.getDeviceId());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void insertReport(ReportDAO report, boolean flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REPORT_CLASSID, report.getClassId());
        values.put(REPORT_CHANGE_ROOM, report.getChangedRoom());
        values.put(REPORT_CLASSNAME, report.getClassName());
        values.put(REPORT_CREATE, report.getCreateTime());
        values.put(REPORT_DAMAGE_LEVEL, report.getDamageLevel());
        values.put(REPORT_ID, report.getReportId());
        values.put(REPORT_EVALUATE, report.getEvaluate());
        if (flag) {
            values.put(REPORT_IS_SYNC, true);
        } else {
            values.put(REPORT_IS_SYNC, false);
        }
        values.put(REPORT_STATUS, report.getStatus());
        values.put(REPORT_USERNAME, report.getUsername());

        db.insert(TABLE_REPORT, null, values);
        db.close();
    }

    public void insertReportDetail(int reportId, ReportDetailDAO detail, boolean flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RD_DAMAGED, detail.getDamaged());
        values.put(RD_DESCRIPTION, detail.getDescription());
        values.put(RD_EQUIPMENT_NAME, detail.getEquipmentName());
        values.put(RD_IS_SYNC, flag);
        values.put(RD_REPORT_ID, reportId);
        values.put(RD_STATUS, detail.isStatus());

        db.insert(TABLE_REPORT_DETAIL, null, values);
        db.close();
    }

    public UserDAO getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        UserDAO user = new UserDAO();
        String query = "SELECT * FROM " + TABLE_USER ;
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        } else {
            return null;
        }


        user.setUsername(c.getString(c.getColumnIndex(USER_USERNAME)));
        user.setPassword(c.getString(c.getColumnIndex(USER_PASSWORD)));
        user.setPhone(c.getString(c.getColumnIndex(USER_PHONE)));
        user.setFullname(c.getString(c.getColumnIndex(USER_FULLNAME)));

        return user;
    }


    public List<ReportInfo> getReport() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ReportInfo> result = new ArrayList<ReportInfo>();
        String query = "SELECT * FROM " + TABLE_REPORT ;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                ReportInfo report = new ReportInfo();
                report.setClassName(c.getString(c.getColumnIndex(REPORT_CLASSNAME)));
                report.setUsername(c.getString(c.getColumnIndex(REPORT_USERNAME)));
                report.setStatus(c.getInt(c.getColumnIndex(REPORT_STATUS)));
                report.setReportId(c.getInt(c.getColumnIndex(REPORT_ID)));
                report.setFullname(c.getString(c.getColumnIndex(REPORT_USERNAME)));
                report.setChangedRoom(c.getString(c.getColumnIndex(REPORT_CHANGE_ROOM)));
                report.setClassId(c.getInt(c.getColumnIndex(REPORT_CLASSID)));
                report.setCreateTime(c.getString(c.getColumnIndex(REPORT_CREATE)));
                report.setDamageLevel(c.getInt(c.getColumnIndex(REPORT_DAMAGE_LEVEL)));
                report.setEvaluate(c.getString(c.getColumnIndex(REPORT_EVALUATE)));
                List<EquipmentReportInfo> equip = getEquipmentReport(report.getReportId());
                report.setEquipments(equip);
                result.add(report);
            } while (c.moveToNext());
        }

        return result;
    }


    public int getMaxReportId() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REPORT ;
        Cursor c = db.rawQuery(query, null);
        int id = 0;
        if (c.moveToFirst()) {
            do {
                id = c.getInt(c.getColumnIndex(REPORT_ID));
            } while (c.moveToNext());
        }
        return id;
    }


    public List<ReportInfo> getReportIsNotSync() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ReportInfo> result = new ArrayList<ReportInfo>();
        String query = "SELECT * FROM " + TABLE_REPORT + " WHERE " + REPORT_IS_SYNC + " = 0";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                ReportInfo report = new ReportInfo();
                report.setClassName(c.getString(c.getColumnIndex(REPORT_CLASSNAME)));
                report.setUsername(c.getString(c.getColumnIndex(REPORT_USERNAME)));
                report.setStatus(c.getInt(c.getColumnIndex(REPORT_STATUS)));
                report.setReportId(c.getInt(c.getColumnIndex(REPORT_ID)));
                report.setFullname(c.getString(c.getColumnIndex(REPORT_USERNAME)));
                report.setChangedRoom(c.getString(c.getColumnIndex(REPORT_CHANGE_ROOM)));
                report.setClassId(c.getInt(c.getColumnIndex(REPORT_CLASSID)));
                report.setCreateTime(c.getString(c.getColumnIndex(REPORT_CREATE)));
                report.setDamageLevel(c.getInt(c.getColumnIndex(REPORT_DAMAGE_LEVEL)));
                report.setEvaluate(c.getString(c.getColumnIndex(REPORT_EVALUATE)));
                List<EquipmentReportInfo> equip = getEquipmentReport(report.getReportId());
                report.setEquipments(equip);
                result.add(report);
            } while (c.moveToNext());
        }

        return result;
    }

    public List<EquipmentReportInfo> getEquipmentReport(int reportId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<EquipmentReportInfo> result = new ArrayList<EquipmentReportInfo>();
        String query = "SELECT * FROM " + TABLE_REPORT_DETAIL + " WHERE " + RD_REPORT_ID + " = " + reportId ;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                EquipmentReportInfo equipment = new EquipmentReportInfo();
                equipment.setDamaged(c.getString(c.getColumnIndex(RD_DAMAGED)));
                equipment.setDescription(c.getString(c.getColumnIndex(RD_DESCRIPTION)));
                equipment.setEquipmentName(c.getString(c.getColumnIndex(RD_EQUIPMENT_NAME)));
                equipment.setStatus(c.getInt(c.getColumnIndex(RD_STATUS)) != 0);

                result.add(equipment);
            } while (c.moveToNext());
        }

        return result;
    }

    public List<EquipmentQuantity> getQuantity(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<EquipmentQuantity> result = new ArrayList<EquipmentQuantity>();
        String query = "SELECT * FROM " + TABLE_EQUIPMENT_QUANTITY + " WHERE " + EQ_CLASSID + " = " + classId ;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                EquipmentQuantity equipment = new EquipmentQuantity();
                equipment.setQuantity(c.getInt(c.getColumnIndex(EQ_QUANTITY)));
                equipment.setIsDeleted(c.getInt(c.getColumnIndex(EQ_IS_DELETED)) != 0);
                equipment.setEquipmentName(c.getString(c.getColumnIndex(EQ_EQUIPMENT_NAME)));
                equipment.setPriority(c.getInt(c.getColumnIndex(EQ_PRIORITY)));
                equipment.setClassId(c.getInt(c.getColumnIndex(EQ_CLASSID)));

                result.add(equipment);
            } while (c.moveToNext());
        }

        return result;
    }

    public void syncEquipment(String classId, Equipment equipment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EC_CLASSID, classId);
        values.put(EC_COMPANY, equipment.getCompany());
        values.put(EC_DAMAGED_LEVEL, equipment.getDamageLevel());
        values.put(EC_EQUIPMENT_NAME, equipment.getEquipmentName());
        values.put(EC_EVALUATE, equipment.getEvaluate());
        values.put(EC_TIME_REMAIN, equipment.getTimeRemain());
        values.put(EC_POSITION, equipment.getPosition());
        values.put(EC_IS_DAMAGED, equipment.isDamaged());

        db.insert(TABLE_EQUIPMENT_CLASSROOM, null, values);
        db.close();
    }

    public void updateEquipment(Equipment equipment, int classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EC_IS_DAMAGED, 0);
        db.update(TABLE_EQUIPMENT_CLASSROOM, values, EC_EQUIPMENT_NAME + "='" + equipment.getEquipmentName() + "' AND " + EC_CLASSID + " = " + classId, null);
        db.close();
    }

    public void updateDamageLevel(int classId, int damageLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASSROOM_DAMAGE_LEVEL, damageLevel);
        db.update(TABLE_CLASSROOM, values, CLASSROOM_ID + " = " + classId, null);
        db.close();
    }

    public User login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + USER_USERNAME + " = '" +username + "' AND " +
                USER_PASSWORD + " = '" + password + "'";

        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }
        User user = new User();
        if (c.getCount() > 0) {
            user.setUsername(c.getString(c.getColumnIndex(USER_USERNAME)));
            user.setPassword(c.getString(c.getColumnIndex(USER_PASSWORD)));
            user.setPhone(c.getString(c.getColumnIndex(USER_PHONE)));
            user.setFullname(c.getString(c.getColumnIndex(USER_FULLNAME)));
            return user;
        }



        return null;

    }


    public List<ClassroomInfo> getSchedule() {
        SQLiteDatabase db = this.getReadableDatabase();
        Date currentDate = new Date();
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        //Long current = (now.getTime().getTime()/10000) * 10000;
        List<ClassroomInfo> result = new ArrayList<ClassroomInfo>();
        String query = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " +  SCHEDULE_DATE + " = " + "(SELECT date('now')) AND isActive=1" ;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                ClassroomInfo schedule = new ClassroomInfo();
                schedule.setClassid(c.getInt(c.getColumnIndex(SCHEDULE_CLASSROOMID)));
                schedule.setClassName(c.getString(c.getColumnIndex(SCHEDULE_CLASSNAME)));
                schedule.setTimeFrom(c.getString(c.getColumnIndex(SCHEDULE_TIMEFROM)));
                schedule.setTimeTo(c.getString(c.getColumnIndex(SCHEDULE_TIMETO)));
                schedule.setDate(c.getString(c.getColumnIndex(SCHEDULE_DATE)));
                result.add(schedule);
            } while (c.moveToNext());
        }
        return result;

    }

    public List<Equipment> getEquipment(int classID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EQUIPMENT_CLASSROOM + " WHERE " + EC_CLASSID + " = '" + classID + "'";
        List<Equipment> result =  new ArrayList<Equipment>();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Equipment equipment = new Equipment();
                equipment.setIsDamaged(c.getInt(c.getColumnIndex(EC_IS_DAMAGED)) != 0);
                equipment.setPosition(c.getString(c.getColumnIndex(EC_POSITION)));
                equipment.setEvaluate(c.getString(c.getColumnIndex(EC_EVALUATE)));
                equipment.setCompany(c.getString(c.getColumnIndex(EC_COMPANY)));
                equipment.setEquipmentName(c.getString(c.getColumnIndex(EC_EQUIPMENT_NAME)));
                equipment.setTimeRemain(c.getString(c.getColumnIndex(EC_TIME_REMAIN)));
                equipment.setDamageLevel(c.getString(c.getColumnIndex(EC_DAMAGED_LEVEL)));
                result.add(equipment);
            } while (c.moveToNext());
        }
        return result;

    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }

    }

    public void truncateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE, null, null);
        db.delete(TABLE_CLASSROOM, null, null);
        db.delete(TABLE_EQUIPMENT, null, null);
        db.delete(TABLE_EQUIPMENT_CLASSROOM, null, null);
        db.delete(TABLE_EQUIPMENT_CATEGORY, null, null);
        db.delete(TABLE_REPORT, null, null);
        db.delete(TABLE_REPORT_DETAIL, null, null);
        db.delete(TABLE_EQUIPMENT_QUANTITY, null, null);

    }

    public void addEquipmentQuantity(EquipmentQuantity eq){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EQ_CLASSID, eq.getClassId());
        values.put(EQ_EQUIPMENT_NAME, eq.getEquipmentName());
        values.put(EQ_IS_DELETED, eq.isDeleted());
        values.put(EQ_PRIORITY,eq.getPriority());
        values.put(EQ_QUANTITY, eq.getQuantity());


        db.insert(TABLE_EQUIPMENT_QUANTITY, null, values);
        db.close();
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
    }

    public void deleteEquipment() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EQUIPMENT_CATEGORY, null, null);
    }

    public void insertClassroom(ClassroomDAO dao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CLASSROOM_DAMAGE_LEVEL, dao.getDamageLevel());
        values.put(CLASSROOM_ID, dao.getClassId());
        values.put(CLASSROOM_NAME, dao.getClassName());
        values.put(CLASSROOM_TYPE, dao.getRoomType());

        db.insert(TABLE_CLASSROOM, null, values);
        db.close();
    }

    public int getDamageLevel(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLASSROOM + " WHERE " + CLASSROOM_ID + " = " + classId;

        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }

        return c.getInt(c.getColumnIndex(CLASSROOM_DAMAGE_LEVEL));
    }

    public void insertEquipment(String id, String name, byte[] image) {
        SQLiteDatabase db               =   this.getWritableDatabase();
        String sql                      =   "INSERT OR REPLACE INTO  " +
                TABLE_EQUIPMENT_CATEGORY +" (id,name,images) VALUES(?,?,?)";
        SQLiteStatement insertStmt      =   db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, id);
        insertStmt.bindString(2, name);
        insertStmt.bindBlob(3, image);
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
                dao.setId(cursor.getString(0));
                dao.setName(cursor.getString(1));
                dao.setImages(cursor.getBlob(2));
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
