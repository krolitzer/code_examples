/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;


public class RoutePoint {
	private boolean visited;
	private GpsHolder point;
	
	public RoutePoint(double lat, double lng){
		point = new GpsHolder(lat, lng);
		point.setLatitude(lat);
		point.setLongitude(lng);
		visited = false;
	}
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public GpsHolder getPoint() {
		return point;
	}

	public void setPoint(GpsHolder point) {
		this.point = point;
	}
	
}
