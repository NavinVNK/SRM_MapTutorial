package in.ac.srmuniv.mapstutorial;


import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SRM_MapActivity extends AppCompatActivity implements LocationListener {
    GoogleMap map;
    MarkerOptions marker;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        // map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // map.setMapType(GoogleMap.MAP_TYPE_NONE);
        // map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // Getting LocationManager object from System Service LOCATION_SERVICE
        // Showing / hiding your current location
        map.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        map.getUiSettings().setZoomControlsEnabled(true);

        // Enable / Disable my location button
        map.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        map.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        map.getUiSettings().setRotateGesturesEnabled(true);

        // Enable / Disable zooming functionality
        map.getUiSettings().setZoomGesturesEnabled(true);
        marker = new MarkerOptions();
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }

    /*    if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }*/
        locationManager.requestLocationUpdates(provider, 20000, 0, this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio0) {
					map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				} else if (checkedId == R.id.radio1) {
					map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				} else if (checkedId == R.id.radio2) {
					map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				}
				else
				{
					map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				}

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting latitude
        double latitude = location.getLatitude();

        // Getting longitude
        double longitude = location.getLongitude();
        final LatLng LOCATION = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION, 16);
        map.animateCamera(update);
        marker.position(LOCATION).title("I am K.Nawin Find me here!");
        map.addMarker(marker);
        try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addressList = geocoder.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);
			Address address1 = addressList.get(0);
			StringBuilder sb1 = new StringBuilder();
			sb1.append(address1.getSubLocality()).append("\n");
			sb1.append(address1.getLocality()).append("\n");
			sb1.append(address1.getSubAdminArea()).append("\n");
			sb1.append(address1.getAdminArea()).append("\n");
			sb1.append(address1.getPostalCode()).append("\n");
			
			sb1.append(address1.getCountryName());
			String results1 = sb1.toString();
	
			StringBuffer objStringBuffer = new StringBuffer();
			//objStringBuffer.append(message);
			objStringBuffer.append(" \nAddress:\n" + results1);


			Toast.makeText(getBaseContext(), objStringBuffer.toString(),
					Toast.LENGTH_SHORT).show();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}




