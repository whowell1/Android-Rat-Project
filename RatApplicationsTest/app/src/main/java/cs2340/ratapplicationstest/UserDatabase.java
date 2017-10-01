package cs2340.ratapplicationstest;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by thoma on 10/1/2017.
 */

public class UserDatabase implements Serializable {

    private HashMap<String, String> map;


    public UserDatabase() {
        map = new HashMap<String,String>();
        map.put("user", "pass");
    }
    public boolean addUser(String id, String password) {
        if(map.containsKey(id)) {
            return false;
        } else {
            map.put(id, password);
            return true;
        }
    }
    public boolean checkPassword(String id, String password) {
        if(!map.containsKey(id)) {
            return false;
        } else if(map.get(id).equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
