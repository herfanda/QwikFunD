package affan.id.qwikfund.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Herfanda on 10/1/2017 AD.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "QWIKFUND_API";

    // Login table name
    private static final String TABLE_USER = "QWIKFUND_USER";

    private static final String TABLE_WELCOME_SIMULASI = "WELCOME_SIMULASI_USER";

    // Login Table Columns names
    private static final String KEY_TYPE = "TYPE";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PASSWORD = "USER_PASSWORD";

    private static final String KEY_TOKEN = "TOKEN";

    // data welcome simulasi
    private static final String KEY_TUJUAN_PINJAMAN = "TUJUAN_PINJAMAN";
    private static final String KEY_JUMLAH_PINJAMAN = "JUMLAH_PINJAMAN";
    private static final String KEY_LAMA_PINJAMAN = "LAMA_PINJAMAN";
    private static final String KEY_BUNGA_SIMULASI = "BUNGA_SIMULASI";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        loginTable(db);
        welcomeSimulasiData(db);



    }

    private void loginTable(SQLiteDatabase db){
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER +
                "("+ KEY_EMAIL +" TEXT,"
                + KEY_TOKEN + " TEXT," + KEY_TYPE + " TEXT)";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    private void welcomeSimulasiData(SQLiteDatabase db){
        String CREATE_WELCOME_SIMULASI = "CREATE TABLE " + TABLE_WELCOME_SIMULASI +
                "("+ KEY_TUJUAN_PINJAMAN +" TEXT," + KEY_JUMLAH_PINJAMAN + " TEXT," + KEY_LAMA_PINJAMAN + " TEXT,"
                + KEY_BUNGA_SIMULASI + " TEXT)";

        db.execSQL(CREATE_WELCOME_SIMULASI);
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String email, String password, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // user id
        values.put(KEY_PASSWORD, password); // password
        values.put(KEY_TYPE, type); // type user

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addInitDataLogin(String token, String type){
        SQLiteDatabase dbDatauser = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TOKEN,token);
        contentValues.put(KEY_TYPE,type);

        long dataLogin = dbDatauser.insert(TABLE_USER,null,contentValues);
        dbDatauser.close();

        Log.d(TAG, "Data User Login inserted into sqlite: " + dataLogin);
    }

    public void addDataWelcomeSimulasi(String tujuan_pinjaman,String jumlah_pinjaman, String lama_pinjaman, String bunga){
        SQLiteDatabase dbSimulasiUser = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TUJUAN_PINJAMAN,tujuan_pinjaman);
        contentValues.put(KEY_JUMLAH_PINJAMAN,jumlah_pinjaman);
        contentValues.put(KEY_LAMA_PINJAMAN,lama_pinjaman);
        contentValues.put(KEY_BUNGA_SIMULASI,bunga);

        long dataSimulasi = dbSimulasiUser.insert(TABLE_WELCOME_SIMULASI,null,contentValues);
        dbSimulasiUser.close();

        Log.d(TAG, "Data User Welcome Simulasi inserted into sqlite: " + dataSimulasi);

    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("email", cursor.getString(1));
            user.put("password", cursor.getString(2));
            user.put("type", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
