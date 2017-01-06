package freaktemplate.singlerestau;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import freaktemplate.singlerestau.utils.CustomMarker;
import freaktemplate.singlerestau.utils.GPSTracker;
import freaktemplate.singlerestau.utils.v2GetRouteDirection;

public class MainActivity extends FragmentActivity {

	LocationManager locManager;
	Drawable drawable;
	Document document;
	v2GetRouteDirection v2GetRouteDirection;
	LatLng fromPosition;
	LatLng toPosition;
	GoogleMap mGoogleMap;
	MarkerOptions markerOptions;
	Location location;

	String lat, lng, map, nm, ad, id, rate;
	double destlat, destlng, curlst, curlng;
	GPSTracker gps;
	double latitude;
	double longitude;

	Button btn_detail;
	private HashMap<CustomMarker, Marker> markersHashMap;
	private Iterator<Entry<CustomMarker, Marker>> iter;
	private CameraUpdate cu;
	private CustomMarker customMarkerOne, customMarkerTwo;
	String lati, longi;
	Button btn_route;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_route = (Button) findViewById(R.id.btn_route);
		Intent iv = getIntent();

		try {
			lati = iv.getStringExtra("lat");
			longi = iv.getStringExtra("longi");
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		try {
			destlat = Double.parseDouble(lati);
			destlng = Double.parseDouble(longi);

		} catch (NumberFormatException e) {
			// TODO: handle exception
		}

		btn_detail = (Button) findViewById(R.id.btn_detail);

		markersHashMap = new HashMap<CustomMarker, Marker>();
		MapsInitializer.initialize(getApplicationContext());

		gps = new GPSTracker(MainActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {
			
			try {
				latitude = gps.getLatitude();
				longitude = gps.getLongitude();
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			

		} else {

			gps.showSettingsAlert();
		}

		try {

		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		v2GetRouteDirection = new v2GetRouteDirection();
		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mGoogleMap = supportMapFragment.getMap();

		// Enabling MyLocation in Google Map
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
		mGoogleMap.getUiSettings().setCompassEnabled(true);
		mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
		mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
		mGoogleMap.setTrafficEnabled(true);
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(-5));
		markerOptions = new MarkerOptions();
		fromPosition = new LatLng(latitude, longitude);

		toPosition = new LatLng(21.17695, 72.84195);

		LatLng position = new LatLng(latitude, longitude);
		customMarkerOne = new CustomMarker("markerOne", latitude, longitude);
		MarkerOptions markerOption = new MarkerOptions().position(

		new LatLng(customMarkerOne.getCustomMarkerLatitude(), customMarkerOne.getCustomMarkerLongitude()))
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).title("");

		Marker newMark = mGoogleMap.addMarker(markerOption);

		addMarkerToHashMap(customMarkerOne, newMark);

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
		btn_route.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// GetRouteTask getRoute = new GetRouteTask();

				// getRoute.execute();

				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?saddr=" + destlat + "," + destlng + "&daddr=" + latitude
								+ "," + longitude));
				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		});
		btn_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private class GetRouteTask extends AsyncTask<String, Void, String> {

		private ProgressDialog Dialog;
		String response = "";

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(MainActivity.this);
			Dialog.setMessage("Loading route...");
			Dialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			// Get All Route values
			document = v2GetRouteDirection.getDocument(fromPosition, toPosition,
					freaktemplate.singlerestau.utils.v2GetRouteDirection.MODE_DRIVING);
			response = "Success";
			return response;

		}

		@Override
		protected void onPostExecute(String result) {
			mGoogleMap.clear();
			if (response.equalsIgnoreCase("Success")) {
				ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
				PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);

				for (int i = 0; i < directionPoint.size(); i++) {
					rectLine.add(directionPoint.get(i));
				}
				// Adding route on the map
				mGoogleMap.addPolyline(rectLine);
				markerOptions.position(toPosition);
				markerOptions.draggable(true);
				customMarkerOne = new CustomMarker("markerOne", latitude, longitude);
				customMarkerTwo = new CustomMarker("markerOne", 21.12, 73.12);
				Marker newMark = mGoogleMap.addMarker(markerOptions);
				// mGoogleMap.addMarker(markerOptions);

				addMarkerToHashMap(customMarkerOne, newMark);
				addMarkerToHashMap(customMarkerTwo, newMark);
				zoomToMarkers(btn_detail);
				mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker arg0) {
						// TODO Auto-generated method stub
						Button btn_detail = (Button) findViewById(R.id.btn_detail);

						return false;
					}
				});
			}

			Dialog.dismiss();
		}
	}

	public void addMarkerToHashMap(CustomMarker customMarker, Marker marker) {
		setUpMarkersHashMap();
		markersHashMap.put(customMarker, marker);

	}

	public void setUpMarkersHashMap() {
		if (markersHashMap == null) {
			markersHashMap = new HashMap<CustomMarker, Marker>();
		}
	}

	public void zoomToMarkers(View v) {
		zoomAnimateLevelToFitMarkers(120);
	}

	public void zoomAnimateLevelToFitMarkers(int padding) {
		iter = markersHashMap.entrySet().iterator();
		LatLngBounds.Builder b = new LatLngBounds.Builder();

		LatLng ll = null;
		while (iter.hasNext()) {
			Map.Entry mEntry = iter.next();
			CustomMarker key = (CustomMarker) mEntry.getKey();
			
			try {
				ll = new LatLng(key.getCustomMarkerLatitude(), key.getCustomMarkerLongitude());
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
			
			b.include(ll);
		}
		try {
			LatLngBounds bounds = b.build();
			Log.d("bounds", "" + bounds);

			// Change the padding as per needed
			cu = CameraUpdateFactory.newLatLngBounds(bounds, 25);
			mGoogleMap.animateCamera(cu);
		} catch (IllegalStateException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

}
