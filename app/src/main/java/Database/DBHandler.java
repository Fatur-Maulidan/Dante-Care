package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/
public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_dente_care";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_TblData = "tbldata";
    private static final String TABLE_NAME_TblUser = "tbluser";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTbl1 = "CREATE TABLE " + TABLE_NAME_TblData + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nama" + " TEXT,"
                + "lokasi" + " TEXT,"
                + "nomor" + " TEXT,"
                + "tanggal" + " TEXT,"
                + "dokter" + " TEXT,"
                + "keluhan" + " TEXT)";

        String queryTbl2 = "CREATE TABLE " + TABLE_NAME_TblUser + " ("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username" + " TEXT,"
                + "nama" + " TEXT,"
                + "nomor" + " TEXT,"
                + "password" + " TEXT)";

        db.execSQL(queryTbl1);
        db.execSQL(queryTbl2);
    }

    public void addNewDataTblData(String nama, String lokasi, String nomor, String tanggal, String dokter, String keluhan) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nama", nama);
        values.put("lokasi", lokasi);
        values.put("nomor", nomor);
        values.put("tanggal", tanggal);
        values.put("dokter", dokter);
        values.put("keluhan", keluhan);

        db.insert(TABLE_NAME_TblData, null, values);

        db.close();
    }

    public int countDataLokasiDokter(String lokasi, String dokter, String tanggal){
        SQLiteDatabase db = getReadableDatabase();

        String selection = "SELECT COUNT(*) FROM " + TABLE_NAME_TblData + " WHERE lokasi = ? AND dokter = ? AND tanggal = ?";
        String[] selectionArgs = { lokasi, dokter, tanggal };

        Cursor cursor = db.rawQuery(selection, selectionArgs);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public String[] getData(String username){
        SQLiteDatabase db = getReadableDatabase();
        String[] data = null;

        String selection = "SELECT * FROM " + TABLE_NAME_TblUser + " WHERE username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.rawQuery(selection, selectionArgs);
        if (cursor.moveToFirst()) {
            data = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                data[i] = cursor.getString(i);
            }
        }
        cursor.close();

        return data;
    }

    public void addUserData(String username, String nama, String nomor, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("nama", nama);
        values.put("nomor", nomor);
        values.put("password", password);

        db.insert(TABLE_NAME_TblUser, null, values);

        db.close();
    }

    public void updateDataById(int id, String username, String nama, String nomor, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("nama", nama);
        values.put("nomor", nomor);
        values.put("password", password);
        db.update(TABLE_NAME_TblUser, values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean auth(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = "SELECT COUNT(*) FROM " + TABLE_NAME_TblUser + " WHERE username = ? AND password = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.rawQuery(selection, selectionArgs);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        if(count != 1) {
            return false;
        }
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TblData);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TblUser);
        onCreate(db);
    }
}


