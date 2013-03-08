/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;


public class GpsHolder {
	private double latitude;
	private double longitude;
	private double altitude;
	
	public GpsHolder(double lat, double lon, double alt){
		setLatitude(lat);
		setLongitude(lon);
		setAltitude(alt);
	}
	public GpsHolder(){
		latitude = 0.0;
		longitude = 0.0;
	}
	public GpsHolder(double lat, double lon){
		setLatitude(lat);
		setLongitude(lon);
	}
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
}
