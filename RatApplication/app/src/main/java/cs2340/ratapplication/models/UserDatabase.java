package cs2340.ratapplication.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by thoma on 10/1/2017.
 */

public class UserDatabase implements Serializable {

    private HashMap<String, String> passMap;
    private HashMap<String, Boolean> adminMap;


    public UserDatabase() {
        passMap = new HashMap<String,String>();
        passMap.put("user", "pass");
        adminMap = new HashMap<String, Boolean>();
        adminMap.put("user", true);
    }
    public boolean addUser(String id, String password) {
        return addUser(id, password, false);

    }
    public boolean addUser(String id, String password, boolean isAdmin) {
        if(passMap.containsKey(id)) {
            return false;
        } else {
            passMap.put(id, password);
            adminMap.put(id, isAdmin);
            return true;
        }
    }
    public boolean checkPassword(String id, String password) {
        if(!passMap.containsKey(id)) {
            return false;
        } else if(passMap.get(id).equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isAdmin(String id) {
        return adminMap.get(id);
    }
}
