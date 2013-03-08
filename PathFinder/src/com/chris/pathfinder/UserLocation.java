/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;



import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import android.app.Service;
import android.content.*;

public class UserLocation extends Service implements LocationListener, Runnable{
	private GpsHolder currPoint;
	private GpsHolder nextPoint;
	private double bearing;
	private double distanceNextPoint;
	private final Context mContext;
	boolean isGPSEnabled;
	boolean isNetworkEnabled;
	boolean canGetLocation;
	Location location;
	// The minimum distance to change Updates in meters
    private static final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final int MIN_TIME_BW_UPDATE = 60000;
    LocationManager locationManager;
    Context c;
	
	
	public UserLocation(){
		currPoint = new GpsHolder();
		nextPoint = new GpsHolder();
		mContext = c;
		findLocation();
		nextPoint.setLatitude(40.010056);
		nextPoint.setLongitude(-105.272725);
		setBearing();
		setDistanceNextPoint();
	}
	public UserLocation(Context con){
		mContext = con;
		currPoint = new GpsHolder();
		nextPoint = new GpsHolder();
		findLocation();
		nextPoint.setLatitude(40.010056);
		nextPoint.setLongitude(-105.272725);
		setBearing();
		setDistanceNextPoint();
	}
	
	@Override
	public void onLocationChanged(Location location) {
		currPoint.setLatitude(location.getLatitude());
		currPoint.setLongitude(location.getLongitude());
		setBearing();
		setDistanceNextPoint();
	}

	@Override
	public void onProviderDisabled(String provider) {
	
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status,
			Bundle extras) {
		
	}
	
	@Override
	public void run() {
		findLocation();
		
	}
	public synchronized void findLocation(){
		
		try{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            }else{
            	this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATE, 
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                        	
                            currPoint.setLatitude(location.getLatitude());
                            currPoint.setLongitude(location.getLongitude());
                        }
                    }
                }
               
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATE,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                currPoint.setLatitude(location.getLatitude());
                                currPoint.setLongitude(location.getLongitude());
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
		
	
	
	
	 void setBearing(){
		bearing = GpsCalculator.bearingCalculator(currPoint, nextPoint);
	}
	 double getBearing(){
		 return bearing;
	 }
	 double getDistanceToNextPoint(){
		 return distanceNextPoint;
	 }
	void setDistanceNextPoint(){
		distanceNextPoint = GpsCalculator.distanceCalculator(currPoint, nextPoint);
	}
	
	String directions(){
		return ("Please go " + distanceNextPoint + " miles " + Direction.getCurrDir(bearing));
	}
	public GpsHolder getCurrPoint(){
		return this.currPoint;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	
	
}