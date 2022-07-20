package id.creatodidak.nyaganagari.Fragment;

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
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.creatodidak.nyaganagari.API.ApiInterface;
import id.creatodidak.nyaganagari.Beranda;
import id.creatodidak.nyaganagari.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PanikFr#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PanikFr extends Fragment {

    FusedLocationProviderClient mFusedLocationClient;
    Double lat, lng;
    TextView kord;
    ApiInterface apiInterface;
    int PERMISSION_ID = 44;

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    ImageView pnk, amn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PanikFr() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PanikFr.
     */
    // TODO: Rename and change types and number of parameters
    public static PanikFr newInstance(String param1, String param2) {
        PanikFr fragment = new PanikFr();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panik, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        pnk = view.findViewById(R.id.btnPanik);
        amn = view.findViewById(R.id.btnPanik2);
        kord = view.findViewById(R.id.koordinat);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        // method to get the location


        pnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pnk.setVisibility(View.GONE);
                amn.setVisibility(View.VISIBLE);
                getLastLocation();
            }



        });

        amn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amn.setVisibility(View.GONE);
                pnk.setVisibility(View.VISIBLE);
            }
        });



    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
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
                        kord.setText("("+ lat +","+ lng +")");




                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                            Address obj = addresses.get(0);
                            String add = obj.getSubAdminArea();

                            if (add.equals("Landak Regency")){
                                panik(lat, lng);
                            }else if(add.equals("Kabupaten Landak")){
                                panik(lat, lng);
                            }else{
                                pnk.setEnabled(false);
                                Toast.makeText(getContext(), "Diluar Jangkauan", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.QUALITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //loc.setText(mLastLocation.getLatitude()+","+mLastLocation.getLongitude());
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
            kord.setText("("+ lat +","+ lng +")");

            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                Address obj = addresses.get(0);
                String add = obj.getSubAdminArea();

                if (add.equals("Landak Regency")){
                    panik(lat, lng);
                }else if(add.equals("Kabupaten Landak")){
                    panik(lat, lng);
                }else{
                    pnk.setEnabled(false);
                    Toast.makeText(getContext(), "Diluar Jangkauan", Toast.LENGTH_SHORT).show();
                }



            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void panik(Double lat, Double lng) {

        Toast.makeText(getContext(), "Personil Meluncur Ke TKP", Toast.LENGTH_SHORT).show();


        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            String dial = "tel:110";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            pnk.setEnabled(true);
        } else {
            pnk.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }

    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {

        }
    }
}