package rts.com.np.gps.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="UserData")
public class UserData extends Model{

    @Column(name="latitude")
    double latitude;
    @Column(name="logitude")
    double longitude;
    @Column(name="timeStamp")
    String timeStamp;
    @Column(name="dataSent")
    boolean dataSent;

    public UserData(double latitude, double longitude, String format, boolean dataSent){
        super();
    }

    public UserData(double latitude, double longitude, String timeStamp) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean isDataSent() {
        return dataSent;
    }

    public void setDataSent(boolean dataSent) {
        this.dataSent = dataSent;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
