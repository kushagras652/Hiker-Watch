package com.example.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView6=findViewById(R.id.textView6);
        TextView textView7=findViewById(R.id.textView7);
        TextView textView8=findViewById(R.id.textView8);
        TextView textView5=findViewById(R.id.textView5);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
//                Log.i("tag", location.toString());
                double latitude= location.getLatitude();
                double longitude=location.getLongitude();
                double altitude=location.getAltitude();
                textView6.setText(Double.toString(latitude));
                textView7.setText(Double.toString(longitude));
                textView8.setText(Double.toString(altitude));
                Geocoder geocoder=new Geocoder(getApplicationContext(),Locale.getDefault());
                String address="COULD NOT FIND ADDRESS";

                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                   if(addressList!=null && addressList.size()>0){
                       address="ADDRESS :\n";
                   }
                   if(addressList.get(0).getThoroughfare()!=null){
                       address+=addressList.get(0).getThoroughfare()+"\n";
                   }
                    if(addressList.get(0).getLocality()!=null){
                        address+=addressList.get(0).getLocality()+" ";
                    }
                    if(addressList.get(0).getAdminArea()!=null){
                        address+=addressList.get(0).getAdminArea()+" ";
                    }
                    if(addressList.get(0).getPostalCode()!=null){
                        address+=addressList.get(0).getPostalCode();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView5.setText(address);
            }
        };
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5000,locationListener);
                }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
    }
    }