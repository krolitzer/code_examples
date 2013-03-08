package com.chris.pathfinder;

public class GpsCalculator {
	static double distanceCalculator(GpsHolder g1, GpsHolder g2){
		//returns a distance in miles.
		double lat1 = g1.getLatitude();
		double lng1 = g1.getLongitude();
		double lat2 = g2.getLatitude();
		double lng2 = g2.getLongitude();
		double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;
	    return dist;
				
	}
	static double bearingCalculator(GpsHolder g1, GpsHolder g2){
		 //returns a bearing between 0 - 360 degrees.
		double lat1 = g1.getLatitude();
		double lng1 = g1.getLongitude();
		double lat2 = g2.getLatitude();
		double lng2 = g2.getLongitude();
		double dLon = (lng2 - lng1);
		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) 
				* Math.cos(lat2) * Math.cos(dLon);
		double brng = (Math.atan2(y, x)) * 180 / Math.PI;
		return brng;
		
	 }
}
