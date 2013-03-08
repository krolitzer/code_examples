/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;

import java.util.Vector;

import android.content.Context;
public interface RouteManagerInterface {
	
	//This is a list of all routes stored in an array of strings.
	//You can use this list to generate a menu to choose from.
	public String[] possibleRoutes();
		
	//This would return a vector of GPS objects related to each route. 
	//The key (name of route) would be inputed into a map would returns the corresponding vector.
	//Each GPS object would contain latitude and longitude (possible altitude)
	//of each turning point or significant point along a path.
	public Vector<RoutePoint> getRoute(String key);
		
	//The implementation of this method could come in various forms.
	//You could do the calculations and display, "walk 30 meters north".
	//Or it could do calculations to manipulate an image of an arrow which
	//points in the correct direction.
	public void showDirections(UserLocation ul, Route r, Context c);
	//needs (user, route)
	//where have I already been?
	/*
	 * another class: containing visited flag and GpsHolder, another layer. 
	 */

	
}
