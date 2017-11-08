package cs2340.ratapplication.models;

//import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by thoma on 10/9/2017.
 */

public class DatabaseHelper {
    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "ratappdb";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SIGHTINGS = "sightings";


    // Sightings Table Columns
    private static final String KEY_SIGHTING_ID = "id";
    private static final String KEY_SIGHTINGS_USER_ID_FK = "userID";
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
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ISADMIN = "isAdmin";

    public static long addUser(String username, String password) {
        return addUser(username, password, false);

    }
    public static long addUser(String username, String password, boolean isAdmin) {
        username = username.trim();
        password = password.trim();

        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_USER_NAME, username);
        map.put(KEY_USER_PASSWORD, password);
        map.put(KEY_USER_ISADMIN, Boolean.toString(isAdmin));
        try {
            JSONObject json = con.sendingPostRequest("addUser", map).getJSONObject(0);
            return json.getLong("insertId");
        }catch(Throwable t) {
            System.out.println(t);
            return -1;
        }

    }

    public static boolean checkPassword(String username, String password) {
        username = username.trim();
        password = password.trim();

        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_USER_NAME, username);
        map.put(KEY_USER_PASSWORD, password);

        try {
            JSONObject json = con.sendingGetRequest("checkPassword", map).getJSONObject(0);
            return json.getBoolean("validated");
        }catch(Throwable t) {
            System.out.println(t);
            return false;
        }
    }

    public boolean isAdmin(int userID) {
        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_USER_ID, userID);

        try {
            JSONObject json = con.sendingGetRequest("isAdmin", map).getJSONObject(0);
            return json.getBoolean("isAdmin");
        }catch(Throwable t) {
            System.out.println(t);
            return false;
        }
    }

    public static long getUserID(String username) {
        username = username.trim();

        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_USER_NAME, username);

        try {
            JSONObject json = con.sendingGetRequest("getUserID", map).getJSONObject(0);
            return json.getLong("id");
        }catch(Throwable t) {
            System.out.println(t);
            return -1;
        }
    }


    public static long addSighting(long userID, String locationType, String address, String city, String borough, int zip) {

        return addSighting(userID, new Timestamp(System.currentTimeMillis()).toString().substring(0, 10), locationType, address, city, borough, zip, "0", "0");
    }

    public static long addSighting(long userID, String date, String locationType, String address, String city, String borough, int zip, String latitude, String longitude) {

        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_SIGHTINGS_USER_ID_FK, userID);
        map.put(KEY_SIGHTINGS_DATE, date);
        map.put(KEY_SIGHTINGS_LOC, locationType);
        map.put(KEY_SIGHTINGS_ADDRESS, address);
        map.put(KEY_SIGHTINGS_CITY, city);
        map.put(KEY_SIGHTINGS_BOROUGH, borough);
        map.put(KEY_SIGHTINGS_ZIP, zip);
        map.put(KEY_SIGHTINGS_LONG, longitude);
        map.put(KEY_SIGHTINGS_LAT, latitude);

        try {
            JSONObject json = con.sendingPostRequest("addSighting", map).getJSONObject(0);
            return json.getLong("insertId");
        }catch(Throwable t) {
            System.out.println(t);
            return -1;
        }

    }

    public static Sighting getSighting(long sightingID) {
        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_USER_ID, sightingID);

        try {
            JSONObject json = con.sendingGetRequest("getSightingByID", map).getJSONObject(0);
            Sighting sighting =  new Sighting();
            sighting.userID = json.getInt(KEY_SIGHTINGS_USER_ID_FK);
            sighting.locationType = json.getString(KEY_SIGHTINGS_LOC);
            sighting.address = json.getString(KEY_SIGHTINGS_ADDRESS);
            sighting.city = json.getString(KEY_SIGHTINGS_CITY);
            sighting.borough = json.getString(KEY_SIGHTINGS_BOROUGH);
            sighting.zip = json.getInt(KEY_SIGHTINGS_ZIP);
            sighting.longitude = json.getDouble(KEY_SIGHTINGS_LONG);
            sighting.latitude = json.getDouble(KEY_SIGHTINGS_LAT);

            return sighting;

        }catch(Throwable t) {
            System.out.println(t);
            return null;
        }
    }
    public static List<Sighting> getAllSightingsFromUser(String username) {
        return getAllSightingsFromUser(getUserID(username));
    }

    public static List<Sighting> getAllSightingsFromUser(long userID) {
        //TO DO
        return null;
    }

    public static Sighting[] get50sightings() {
        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            JSONArray response =  con.sendingGetRequest("get50Sightings", map);
            System.out.println(response);
            Sighting[] sightings = new Sighting[50];
            int count = 0;
            for(int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                Sighting sighting =  new Sighting();
                sighting.sightingID = json.getLong(KEY_SIGHTING_ID);
                sighting.userID = json.getInt(KEY_SIGHTINGS_USER_ID_FK);
                sighting.locationType = json.getString(KEY_SIGHTINGS_LOC);
                sighting.address = json.getString(KEY_SIGHTINGS_ADDRESS);
                sighting.city = json.getString(KEY_SIGHTINGS_CITY);
                sighting.borough = json.getString(KEY_SIGHTINGS_BOROUGH);
                sighting.zip = json.getInt(KEY_SIGHTINGS_ZIP);
                sighting.longitude = json.getDouble(KEY_SIGHTINGS_LONG);
                sighting.latitude = json.getDouble(KEY_SIGHTINGS_LAT);
                sightings[count] = sighting;
                count++;
            }
            return sightings;

        }catch(Throwable t) {
            System.out.println(t);
            return null;
        }
    }
    public static Sighting[] getSightingsInRange(String start, String end) {
        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("end", end);

        try {
            JSONArray response =  con.sendingGetRequest("getSightingsInRange", map);
            Sighting[] sightings = new Sighting[response.length()];

            for(int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                Sighting sighting =  new Sighting();
                sighting.sightingID = json.getLong(KEY_SIGHTING_ID);
                sighting.userID = json.getInt(KEY_SIGHTINGS_USER_ID_FK);
                sighting.locationType = json.getString(KEY_SIGHTINGS_LOC);
                sighting.address = json.getString(KEY_SIGHTINGS_ADDRESS);
                sighting.city = json.getString(KEY_SIGHTINGS_CITY);
                sighting.borough = json.getString(KEY_SIGHTINGS_BOROUGH);
                sighting.zip = json.getInt(KEY_SIGHTINGS_ZIP);
                sighting.longitude = json.getDouble(KEY_SIGHTINGS_LONG);
                sighting.latitude = json.getDouble(KEY_SIGHTINGS_LAT);
                sightings[i] = sighting;

            }
            return sightings;

        }catch(Throwable t) {
            System.out.println(t);
            return null;
        }
    }

    public static MonthCount[] getSightingsCountByMonth(String start, String end) {
        connectToAPI con = new connectToAPI();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("end", end);

        try {
            JSONArray response =  con.sendingGetRequest("getSightingsCountByMonth", map);
            System.out.println(response);
            MonthCount[] count = new MonthCount[response.length()];

            for(int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                count[i] = new MonthCount();
                count[i].year = json.getInt("year");
                count[i].month = json.getInt("month");
                count[i].count = json.getInt("count");
            }


            return count;

        }catch(Throwable t) {
            System.out.println(t);
            return null;
        }
    }
}