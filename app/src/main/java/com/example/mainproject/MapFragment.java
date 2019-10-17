package com.example.mainproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private static final String TAG = "MapTest";
    private MapFragmentMethods mapFragmentMethods;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private SupportMapFragment mapFragment;

    //data to be passed in web request stored in below declared variables
    private String myLocation;
    private double myLocationLat;
    private double myLocationLng;
    private String searchedPlace;
    private double searchedLat;
    private double searchedLng;



    //widgets
    private EditText mSearchText;
    private ImageView mGps;
    private ImageView mSearchButton;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_map,container,false);
        mSearchText =v.findViewById(R.id.input_search);
        mSearchText.setText("");
        mGps = v.findViewById(R.id.ic_gps);
        mSearchButton=v.findViewById(R.id.ic_magnify);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        searchedLat = LatAndLongTrack.SEARCHED_LAT;
        searchedLng = LatAndLongTrack.SEARCHED_LONG;
        Log.d(TAG, "onCreateView in MapFragment: " + LatAndLongTrack.SEARCHED_LAT + "  " + LatAndLongTrack.SEARCHED_LONG);
        Log.d(TAG, "onCreateView: Searched Place" + searchedPlace);
        getLocationPermission();
        if (searchedLat != 0 && searchedLng != 0) {
            moveCamera(new LatLng(searchedLat, searchedLng), DEFAULT_ZOOM, "Location");
        }

        //if (!Places.isInitialized()) {
        //    Places.initialize(getApplicationContext(), "AIzaSyD_t1wQGD1YmHz4ZaAYIroY8UPpJrOurDE");
        //}t

        // Initialize the AutocompleteSupportFragment.ed
        /*AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                moveCamera(Objects.requireNonNull(place.getLatLng()),DEFAULT_ZOOM,"Selected Location");
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.Log.i(TAG, "An error occurred: " + status);
            }
        });*/

        return v;
    }
    private void init () {

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {


                    geoLocate();
                    hideSoftKeyboard();
                    return true;
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatAndLongTrack.SEARCHED_LAT = 0.0;
                LatAndLongTrack.SEARCHED_LONG = 0.0;
                getDeviceLocation();
            }
        });

        hideSoftKeyboard();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mapFragmentMethods = (MapFragmentMethods) context;
    }

    private void geoLocate () {

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

//            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));

            LatAndLongTrack.SEARCHED_LAT = address.getLatitude();
            LatAndLongTrack.SEARCHED_LONG = address.getLongitude();

        }
    }

    private void getDeviceLocation () {
        // Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        try {
            if (mLocationPermissionsGranted) {

                //FusedLocationProviderClient mFusedLocationProviderClient =
                // LocationServices.getFusedLocationProviderClient(this);

// Get the last known location
                /*mFusedLocationProviderClient.getLastLocation()
                        .addOnCompleteListener(MapActivity.this, new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                // ...
                            }
                        });*/

               /* mFusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                }
                            }
                        });*/


                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
//                            Log.d(TAG, String.valueOf(currentLocation.getLatitude())+String.valueOf(currentLocation.getLongitude()));
                            myLocationLat = currentLocation.getLatitude();
                            myLocationLng = currentLocation.getLongitude();
                            //put intent here and pass above two values
                            Geocoder geocoder=new Geocoder(getActivity());
                            List <Address> addresses=new ArrayList<>();
                            try {
                                addresses=geocoder.getFromLocation(myLocationLat,myLocationLng,1);
                            } catch (IOException e) {
                                Log.d(TAG,e.getMessage());
                            }
                            if (addresses.size()>0){
                                myLocation=addresses.get(0).getAddressLine(0).toString();
                            }

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            //makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera (LatLng latLng,float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        mapFragmentMethods.sendLocationDetails(latLng.latitude, latLng.longitude);


        hideSoftKeyboard();
    }

    private void initMap () {

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
                //   Log.d(TAG, "onMapReady: map is ready");
                mMap = googleMap;

                if (mLocationPermissionsGranted) {
                    getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

                    init();
                }
            }

        });
        Log.d(TAG, "initMap: initializing map");

    }


    private void getLocationPermission () {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard () {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

    interface MapFragmentMethods {
        void sendLocationDetails(double latitude, double longitude);
    }
}