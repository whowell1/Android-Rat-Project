package cs2340.ratapplication.models;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by thoma on 10/9/2017.
 */

public class Sighting implements Serializable{
    //public User user;
    public long sightingID;
    public int userID;
    public Timestamp date;
    public String locationType;
    public String address;
    public String city;
    public String borough;
    public int zip;
    public double latitude;
    public double longitude;

}
