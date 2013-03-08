/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */

package com.chris.pathfinder;

import java.util.HashMap;
import java.util.Map;

import java.util.Vector;

import android.content.Context;
import android.widget.TextView;

public class RouteManager implements RouteManagerInterface {
	// might be helpful for manager to have an iterator for where we are in the list.
	//	private String[] routes;
	private Map <String, Route> routes;
	private Context mContext;
	public	RoutePoint currentPoint;
	//needs a map to all the vectors
	public RouteManager(){
		routes = new HashMap<String, Route>();
		//String[] routeNames = possibleRoutes();
		Route rTest = new Route("Engineering Center To Macky");
		RoutePoint[] r1 = new RoutePoint[8];
			r1[0] = new RoutePoint(40.007251,-105.263848);
			r1[1] = new RoutePoint(40.007369, -105.265356);
			r1[2] = new RoutePoint(40.007738,-105.266346);
			r1[3] = new RoutePoint(40.007744,-105.267013);
			r1[4] = new RoutePoint(40.00833,-105.267047);
			r1[5] = new RoutePoint(40.009433,-105.269371);
			r1[6] = new RoutePoint(40.009505,-105.271855);
			r1[7] = new RoutePoint(40.009565,-105.272778);
		for(int i = 0; i<8; i++){
			rTest.addNextPoint(r1[i]);
		}
		routes.put("Engineering Center To Macky", rTest);	
		currentPoint = r1[0];
	}
	public Route findRoute(String name){
		return routes.get(name);
	}
	public void setNextCurrPoint(String name){
		Route temp = routes.get(name);
		RoutePoint next;
		int i = 0;
		//Visit the current point and set the next non-visited point to the current point.
		do{
			temp.pointAt(i).setVisited(true);
			next = temp.pointAt(i+1);
			i++;
		}while(temp.pointAt(i).isVisited());
	
	}
	public String currentRouteName(){
		return "Engineering Center to Macky";
	}
	public String directionsNextPoint(GpsHolder g){
		return "Please go " + GpsCalculator.distanceCalculator(currentPoint.getPoint(), g) + " miles " 
				+ Direction.getCurrDir(GpsCalculator.bearingCalculator(currentPoint.getPoint(), g));
	}
	@Override
	public String[] possibleRoutes() {
		String[] r = {"Engineering Center To Macky"};
		return r;
	}
	
	@Override
	public Vector<RoutePoint> getRoute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showDirections(UserLocation ul, Route rt, Context c) {
		
		

	}

}
