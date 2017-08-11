package rts.com.np.gps.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonHelper {

    public static String convertJSONToString(double latitude, double longitude, String format){
        try{
            JSONObject core=new JSONObject();
            core.put("device","123");
            JSONArray jsonArray=new JSONArray();
            JSONObject data=new JSONObject();
            data.put("lat",latitude);
            data.put("lng",longitude);
            data.put("timestamp",format);
            jsonArray.put(data);
            core.put("data",jsonArray);
            return core.toString();
        }catch (Exception e){return null;}
    }
}
