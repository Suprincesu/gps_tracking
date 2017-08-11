package rts.com.np.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import rts.com.np.gps.Models.Event;
import rts.com.np.gps.Presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 123;
    private MainPresenter presenter;
    @BindView(R.id.langValue)
    TextView longitude;
    @BindView(R.id.latValue)
    TextView latitude;
    @BindView(R.id.mac)
    TextView mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter=new MainPresenter(this);
        mac.setText(presenter.getMacAddr());
        String reqString = Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        System.out.println(reqString);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("GPS", "onRequestPermissionsResult: Permission granted");
                getLocation();
            }else{
                Log.d("GPS", "onRequestPermissionsResult: Permission denied");
            }
        }
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        EventBus.getDefault().postSticky(new Event("Location"));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }
}
