package cs2340.ratapplication.models;

//import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.*;
import android.content.Context;

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

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
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
                KEY_USER_PASSWORD + " TEXT" +
                ")";

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_SIGHTINGS +
                "(" +
                KEY_SIGHTING_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_SIGHTINGS_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_SIGHTINGS_DATE + " DATETIME NOT NULL DEFAULT(GETDATE())," +
                KEY_SIGHTINGS_LOC + " TEXT," +
                KEY_SIGHTINGS_ADDRESS + " TEXT," +
                KEY_SIGHTINGS_CITY + " TEXT," +
                KEY_SIGHTINGS_BOROUGH + " TEXT," +
                KEY_SIGHTINGS_ZIP + " INTEGER," +
                KEY_SIGHTINGS_LAT + " FLOAT(5)," +
                KEY_SIGHTINGS_LONG + " FLOAT(5)" +
                ")";

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
}


}