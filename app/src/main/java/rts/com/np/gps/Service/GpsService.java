package rts.com.np.gps.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rts.com.np.gps.Models.Event;


public class GpsService extends Service{
    private Context context;
    private GpsProvider gpsProvider;
    private SocketProvider socketProvider;
    private boolean status=true;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                socketProvider=new SocketProvider(context);
                socketProvider.connect();
                socketProvider.listen();
                gpsProvider= new GpsProvider(context,socketProvider);

            }
        });
        thread.start();
        try {
            thread.join();
            getLocation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onMessage(Event msg){
        System.out.println("i m here");
        getLocation();
    }

    public void getLocation(){
        if(status) {
            gpsProvider.getLocation();
            status=false;
        }
    }
}
