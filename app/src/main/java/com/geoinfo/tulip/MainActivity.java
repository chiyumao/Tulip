package com.geoinfo.tulip;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.TextView;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView responseTxtv;
    TextView locTxtv;
    ServiceReq serviceReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTxtv = (TextView) findViewById(R.id.responseTextView);
        locTxtv = (TextView) findViewById(R.id.locationTextView);
        serviceReq = new ServiceReq(this, responseTxtv);
        serviceReq.connectServer();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locTxtv.setText(location.getLatitude() + " : " + location.getLongitude());
                serviceReq.reportLocation (location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,10, locationListener);

    }
}
