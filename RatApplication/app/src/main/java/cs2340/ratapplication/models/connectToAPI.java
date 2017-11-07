package cs2340.ratapplication.models;

/**
 * Created by thoma on 11/1/2017.
 */
        import android.os.AsyncTask;

        import org.json.JSONArray;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.Map;

/**
 * @author Thomas Olson
 */
public class connectToAPI {

    private final String USER_AGENT = "Mozilla/5.0";



    // HTTP GET request
    public JSONArray sendingGetRequest(String function, Map<String, Object> params) throws Exception {

        String url = "https://2xcxiwvvy8.execute-api.us-east-2.amazonaws.com/prod/";
        url += function;

        String getArgs = "?";
        for (String key : params.keySet()) {
            try {
                getArgs += key + "=" + params.get(key) + "&";
            }catch(Throwable t) {
                System.out.println(t);
            }

        }
        getArgs += "fix=true";
        url += getArgs;

        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // By default it is GET request
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);
            //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            //con.setRequestProperty("Content-Type","application/json");

            // Send get request
            int responseCode = con.getResponseCode();
            System.out.println("Sending get request : "+ url);
            System.out.println("Response code : "+ responseCode);

            // Reading response from input Stream
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();

            //printing result from response
            System.out.println(response.toString());
            return new JSONArray(response.toString());


        }catch(Throwable t) {
            System.out.println(t);
            return new JSONArray();
        }


    }

    // HTTP Post request
    public JSONArray sendingPostRequest(String function, Map<String, Object> params) throws Exception {

        String url = "https://2xcxiwvvy8.execute-api.us-east-2.amazonaws.com/prod/";
        url += function;

        String postArgs = "?";
        for (String key : params.keySet()) {
            postArgs += key + "=" + params.get(key) + "&";
        }
        postArgs += "fix=true";
        url += postArgs;

        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //System.out.println("Connection Established");

            // Setting basic post request
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-Type","application/json");
            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Post Data : " + postArgs);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();

            //printing result from response
            System.out.println("Response: " + response.toString());

            return new JSONArray(response.toString());


        }catch(Throwable t) {
            System.out.println(t);
            return new JSONArray();
        }

    }
}