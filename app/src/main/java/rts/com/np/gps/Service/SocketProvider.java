package rts.com.np.gps.Service;


import android.content.Context;

import com.activeandroid.query.Select;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;
import rts.com.np.gps.Models.UserData;

public class SocketProvider {
    private Context context;
    private Socket socket;
    public SocketProvider(Context context){

        this.context=context;
        try {
//            IO.Options options=new IO.Options();
//            options.path="/rainfall_watch/socket.io";
//            socket = IO.socket("http://hydrology.gov.np",options);
            socket=IO.socket("http://192.168.5.108:3000");
        } catch (URISyntaxException e) {
            System.out.println("Error" + e);
        }
    }

    public boolean sendData(String data){
        if(socket.connected()) {
            socket.emit("message", data);
            return true;
        }
        return false;
    }

    public void connect(){
        socket.connect();
    }

    public void listen(){
        socket.on(Socket.EVENT_CONNECT,onConnect);
    }

    public void close(){
        socket.close();
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            List<UserData> data=new Select()
                    .from(UserData.class)
                    .where("dataSent = ?",false)
                    .execute();
            try {
                JSONObject topLevel = new JSONObject();
                topLevel.put("device", "123");
                JSONArray jsonArray = new JSONArray();
                for (UserData data1 : data) {
                    JSONObject latlng = new JSONObject();
                    latlng.put("lat", data1.getLatitude());
                    latlng.put("lng", data1.getLongitude());
                    latlng.put("timestamp", data1.getTimeStamp());
                    jsonArray.put(latlng);
                }
                topLevel.put("data",jsonArray);
                sendData(topLevel.toString());
            }catch (Exception e){

            }
        }
    };


}
