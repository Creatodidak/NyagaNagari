package id.creatodidak.nyaganagari;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.creatodidak.nyaganagari.API.ApiClient;
import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Adapter.TabAdapter;
import id.creatodidak.nyaganagari.Models.UpdateLoc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Beranda extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FusedLocationProviderClient mFusedLocationClient;
    Double lat, lng;
    ApiInterface apiInterface;
    String nama;
    int PERMISSION_ID = 44;
    TextView loc;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_beranda);



        loc = findViewById(R.id.locx);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();


        loc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        View view1 = getLayoutInflater().inflate(R.layout.customtab, null);
        view1.findViewById(R.id.icon).setBackgroundResource(R.drawable.ichome);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));

        View view2 = getLayoutInflater().inflate(R.layout.customtab, null);
        view2.findViewById(R.id.icon).setBackgroundResource(R.drawable.icdarurat);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        View view3 = getLayoutInflater().inflate(R.layout.customtab, null);
        view3.findViewById(R.id.icon).setBackgroundResource(R.drawable.icpengaduan);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        View view4 = getLayoutInflater().inflate(R.layout.customtab, null);
        view4.findViewById(R.id.icon).setBackgroundResource(R.drawable.icpelayanan);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

        View view5 = getLayoutInflater().inflate(R.layout.customtab, null);
        view5.findViewById(R.id.icon).setBackgroundResource(R.drawable.icpenmas);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view5));

        View view6 = getLayoutInflater().inflate(R.layout.customtab, null);
        view6.findViewById(R.id.icon).setBackgroundResource(R.drawable.icbantuan);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view6));

        View view7 = getLayoutInflater().inflate(R.layout.customtab, null);
        view7.findViewById(R.id.icon).setBackgroundResource(R.drawable.icprofile);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view7));

        View view8 = getLayoutInflater().inflate(R.layout.customtab, null);
        view8.findViewById(R.id.icon).setBackgroundResource(R.drawable.icaccount);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view8));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabAdapter adapter = new TabAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        Session session = new Session(Beranda.this);
        nama = session.getUserDetail().get(Session.NAMA);

    }

    private void updateloc(Double lat, Double lng) {

        Session session;

        session = new Session(Beranda.this);

        String nik;

        nik = session.getUserDetail().get(Session.NIK);

        final String latz = String.valueOf(lat);
        final String lngz = String.valueOf(lng);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateLoc> ulCall= apiInterface.uploc(latz, lngz, nik);
        ulCall.enqueue(new Callback<UpdateLoc>() {
            @Override
            public void onResponse(Call<UpdateLoc> call, Response<UpdateLoc> response) {

               
            }

            @Override
            public void onFailure(Call<UpdateLoc> call, Throwable t) {
                Toast.makeText(Beranda.this, "Periksa Jaringan Internet Anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();

                        if (location == null) {
                            requestNewLocationData();
                        } else {
                             lat = location.getLatitude();
                             lng = location.getLongitude();


                            updateloc(lat, lng);

                            Geocoder geocoder = new Geocoder(Beranda.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                                Address obj = addresses.get(0);
                                String add = obj.getSubAdminArea();



                                if (add.equals("Landak Regency")){
                                    loc.setText(nama+"\n"+"Kabupaten Landak");
                                }else{
                                    loc.setText(nama+"\n"+"Anda Tidak Sedang Di Kabupaten Landak");
                                }


                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Toast.makeText(Beranda.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.QUALITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //loc.setText(mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
             lat = mLastLocation.getLatitude();
             lng = mLastLocation.getLongitude();
            updateloc(lat, lng);
            Geocoder geocoder = new Geocoder(Beranda.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                Address obj = addresses.get(0);
                String add = obj.getSubAdminArea();

                if (add.equals("Landak Regency")){
                    loc.setText(nama+"\n"+"Kabupaten Landak");
                }else{
                    loc.setText(nama+"\n"+"Anda Tidak Sedang Di Kabupaten Landak");
                }





                // Toast.makeText(this, "Address=>" + add,
                // Toast.LENGTH_SHORT).show();

                // TennisAppActivity.showDialog(add);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(Beranda.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}