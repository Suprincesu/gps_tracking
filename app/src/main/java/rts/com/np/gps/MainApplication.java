package rts.com.np.gps;

import android.app.Application;
import android.content.Intent;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import rts.com.np.gps.Service.GpsService;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent service=new Intent(this,GpsService.class);
        startService(service);

        Configuration dbConfiguration=new Configuration.Builder(this).setDatabaseName("mydb.db").create();
        ActiveAndroid.initialize(dbConfiguration);
    }
}
