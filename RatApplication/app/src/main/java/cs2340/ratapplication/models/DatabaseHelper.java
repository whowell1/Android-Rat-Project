package cs2340.ratapplication.models;

//import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.Context;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by thoma on 10/9/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "AppDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SIGHTINGS = "sightings";


    // Sightings Table Columns
    private static final String KEY_SIGHTING_ID = "id";
    private static final String KEY_SIGHTINGS_USER_ID_FK = "userId";
    private static final String KEY_SIGHTINGS_DATE = "date";
    private static final String KEY_SIGHTINGS_LOC = "locationType";
    private static final String KEY_SIGHTINGS_ADDRESS = "address";
    private static final String KEY_SIGHTINGS_CITY = "city";
    private static final String KEY_SIGHTINGS_BOROUGH = "borough";
    private static final String KEY_SIGHTINGS_ZIP = "zipcode";
    private static final String KEY_SIGHTINGS_LONG = "longitude";
    private static final String KEY_SIGHTINGS_LAT = "latitude";

    //User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ISADMIN = "isAdmin";

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            System.out.println("DATABASE CREATED-----------------------------------");
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_ISADMIN + " BOOLEAN" +
                ")";

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_SIGHTINGS +
                "(" +
                KEY_SIGHTING_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_SIGHTINGS_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_SIGHTINGS_DATE + " TEXT," +
                KEY_SIGHTINGS_LOC + " TEXT," +
                KEY_SIGHTINGS_ADDRESS + " TEXT," +
                KEY_SIGHTINGS_CITY + " TEXT," +
                KEY_SIGHTINGS_BOROUGH + " TEXT," +
                KEY_SIGHTINGS_ZIP + " INTEGER," +
                KEY_SIGHTINGS_LAT + " FLOAT(5)," +
                KEY_SIGHTINGS_LONG + " FLOAT(5)" +
                ")";

        System.out.println("Create Table: " + CREATE_USERS_TABLE);
        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGHTINGS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    public void destroyPreviousDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGHTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        return addUser(username, password, false);

    }
    public boolean addUser(String username, String password, boolean isAdmin) {
        username = username.trim();
        password = password.trim();

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String USER_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%s'",
        TABLE_USERS,
        TABLE_USERS, KEY_USER_NAME,
        username);
        Cursor cursor = db.rawQuery(USER_QUERY,null);
        try {
            if(cursor.moveToFirst()) {
                cursor.close();
                db.endTransaction();
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put(KEY_USER_NAME, username);
                values.put(KEY_USER_PASSWORD, password);
                values.put(KEY_USER_ISADMIN, isAdmin);

                db.insert(TABLE_USERS, null, values); //null to autoincrement primary key
                cursor.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                return true;
            }
        } catch(Throwable t) {
            db.endTransaction();
            System.out.println("Error: "+ t.getMessage());
            return false;
        }
    }

    public boolean checkPassword(String username, String password) {
        username = username.trim();
        password = password.trim();

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String USER_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%s'",
                TABLE_USERS,
                TABLE_USERS, KEY_USER_NAME,
                username);
        Cursor cursor = db.rawQuery(USER_QUERY,null);
        try {
            if(cursor.moveToFirst()) {
                if(cursor.getString(2).equals(password)) {
                    cursor.close();
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    return true;
                }
            } else {
                cursor.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                return false;
            }
        } catch(Throwable t) {
            db.endTransaction();
            cursor.close();
            System.out.println("Error: " + t.getMessage());
            return false;
        }
        return false;
    }

    public boolean isAdmin(int userID) {

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String USER_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%s'",
                TABLE_USERS,
                TABLE_USERS, KEY_USER_ID,
                userID);
        Cursor cursor = db.rawQuery(USER_QUERY,null);
        try {
            if(cursor.moveToFirst()) {
                if(Boolean.parseBoolean(cursor.getString(3))) {
                    cursor.close();
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    return true;
                }
            } else {
                cursor.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                return false;
            }
        } catch(Throwable t) {
            cursor.close();
            db.endTransaction();
            System.out.println("Error: " + t.getMessage());
        }
        return false;
    }

    public int getUserID(String username) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT 1 FROM %s WHERE %s.%s = '%s'",
                TABLE_USERS,
                TABLE_USERS, KEY_USER_NAME,
                username
        );
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);
        try {
            if(cursor.moveToFirst()) {
                db.endTransaction();
                return (cursor.getInt(0));
            }else {
                throw new NoSuchElementException();
            }

        }catch (Throwable t) {
            db.endTransaction();
            System.out.println("Error: " + t.getMessage());
            return 0;
        }
    }


    public boolean addSighting(int userID, String locationType, String address, String city, String borough, int zip) {

        return addSighting(userID, new Timestamp(System.currentTimeMillis()), locationType, address, city, borough, zip, 0, 0);
    }

    public boolean addSighting(int userID, Timestamp date, String locationType, String address, String city, String borough, int zip, float longitude, float latitude) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_SIGHTINGS_USER_ID_FK, userID);
            values.put(KEY_SIGHTINGS_DATE, date.toString());
            values.put(KEY_SIGHTINGS_LOC, locationType);
            values.put(KEY_SIGHTINGS_ADDRESS, address);
            values.put(KEY_SIGHTINGS_CITY, city);
            values.put(KEY_SIGHTINGS_BOROUGH, borough);
            values.put(KEY_SIGHTINGS_ZIP, zip);
            values.put(KEY_SIGHTINGS_LONG, longitude);
            values.put(KEY_SIGHTINGS_LAT, latitude);

            db.insert(TABLE_SIGHTINGS, null, values); //null to autoincrement primary key
            db.setTransactionSuccessful();
            db.endTransaction();
            return true;

        }catch (Throwable t) {
            db.endTransaction();
            System.out.println("Error: " + t.getMessage());
        }
        return false;

    }
    public boolean sightingsEmpty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT COUNT(*) FROM %s", TABLE_SIGHTINGS);
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);

        try {
            if(cursor.moveToFirst()) {
                System.out.println("NUM ROWS: " + cursor.getInt(0));
                return cursor.getInt(0) == 0;
            }else {
                return false;
            }
        }catch (Throwable t) {
            db.endTransaction();
            System.out.println("Error: " + t.getMessage());
        }
        return false;
    }

    public Sighting getSighting(int sightingID) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%d'",
                TABLE_SIGHTINGS,
                TABLE_SIGHTINGS, KEY_SIGHTING_ID,
                sightingID);
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);
        try {
            if(cursor.moveToFirst()) {
                Sighting sighting =  new Sighting();
                sighting.userID = cursor.getInt(1);
                sighting.locationType = cursor.getString(3);
                sighting.address = cursor.getString(4);
                sighting.city = cursor.getString(5);
                sighting.borough = cursor.getString(6);
                sighting.zip = cursor.getInt(7);
                sighting.longitude = cursor.getFloat(8);
                sighting.latitude = cursor.getFloat(9);
                cursor.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                return sighting;
            }else {
                throw new NoSuchElementException();
            }
        }catch (Throwable t) {
            System.out.println("Error: " + t.getMessage());
            db.endTransaction();
            return null;
        }
    }
    public List<Sighting> getAllSightingsFromUser(String username) {
        return getAllSightingsFromUser(getUserID(username));
    }

    public List<Sighting> getAllSightingsFromUser(int userID) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT * FROM %s WHERE %s.%s = '%d'",
                TABLE_SIGHTINGS,
                TABLE_SIGHTINGS, KEY_SIGHTINGS_USER_ID_FK,
                userID);
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);
        try {
            List<Sighting> list = new ArrayList<Sighting>();
            while(cursor.moveToNext()) {

                Sighting sighting =  new Sighting();
                sighting.userID = cursor.getInt(1);
                sighting.locationType = cursor.getString(3);
                sighting.address = cursor.getString(4);
                sighting.city = cursor.getString(5);
                sighting.borough = cursor.getString(6);
                sighting.zip = cursor.getInt(7);
                sighting.longitude = cursor.getFloat(8);
                sighting.latitude = cursor.getFloat(9);
                list.add(sighting);

            }
            cursor.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            return list;
        }catch (Throwable t) {
            System.out.println("Error: " + t.getMessage());
            db.endTransaction();
            return null;
        }
    }

    public List<Sighting> getAllSightings() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT * FROM %s",
                TABLE_SIGHTINGS
                );
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);
        try {
            List<Sighting> list = new ArrayList<Sighting>();
            while(cursor.moveToNext()) {

                Sighting sighting =  new Sighting();
                sighting.userID = cursor.getInt(1);
                sighting.locationType = cursor.getString(3);
                sighting.address = cursor.getString(4);
                sighting.city = cursor.getString(5);
                sighting.borough = cursor.getString(6);
                sighting.zip = cursor.getInt(7);
                sighting.longitude = cursor.getFloat(8);
                sighting.latitude = cursor.getFloat(9);
                list.add(sighting);

            }
            cursor.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            return list;
        }catch (Throwable t) {
            System.out.println("Error: " + t.getMessage());
            db.endTransaction();
            return null;
        }
    }
    public Sighting[] get50sightings() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        String SIGHTINGS_QUERY = String.format("SELECT * FROM %s",
                TABLE_SIGHTINGS
        );
        Cursor cursor = db.rawQuery(SIGHTINGS_QUERY,null);
        try {
            Sighting[] list = new Sighting[50];
            int counter = 0;
            while(cursor.moveToNext() && counter <50) {

                Sighting sighting =  new Sighting();
                sighting.userID = cursor.getInt(1);
                sighting.locationType = cursor.getString(3);
                sighting.address = cursor.getString(4);
                sighting.city = cursor.getString(5);
                sighting.borough = cursor.getString(6);
                sighting.zip = cursor.getInt(7);
                sighting.longitude = cursor.getFloat(8);
                sighting.latitude = cursor.getFloat(9);
                list[counter] = sighting;
                counter++;

            }
            cursor.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            return list;
        }catch (Throwable t) {
            cursor.close();
            System.out.println("Error: " + t.getMessage());
            db.endTransaction();
            return null;
        }
    }
}