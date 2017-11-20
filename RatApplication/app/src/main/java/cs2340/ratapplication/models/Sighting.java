package cs2340.ratapplication.models;

import java.io.Serializable;


public class Sighting implements Serializable{
    //public User user;
    public long sightingID;
    public int userID;
    public String locationType;
    public String address;
    public String city;
    public String borough;
    public int zip;
    public double latitude;
    public double longitude;

}
