package rts.com.np.gps.Service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import rts.com.np.gps.Models.UserData;
import rts.com.np.gps.Utils.JsonHelper;

public class GpsProvider {
    private Context context;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    private long MIN_TIME = 1000;
    private int MIN_DISTANCE = 5000;
    private SocketProvider socketProvider;
    SimpleDateFormat dateFormatGmt;


    public GpsProvider(Context context, SocketProvider socketProvider) {
        this.socketProvider=socketProvider;
        this.context = context;
        dateFormatGmt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    }

    public void getLocation() {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("GPS", "onLocationChanged: ");
                System.out.println("longitude "+location.getLongitude());
                System.out.println("latitude "+location.getLatitude());

                boolean dataSent=socketProvider.sendData(JsonHelper.convertJSONToString(location.getLatitude(),location.getLongitude(),dateFormatGmt.format(new Date())));
                if(!dataSent) {
                    new UserData(location.getLatitude(), location.getLongitude(), dateFormatGmt.format(new Date()), dataSent).save();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("GPS", "onStatusChanged: ");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("GPS", "onProviderEnabled: ");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("GPS", "onProviderDisabled: ");
            }
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }
}
