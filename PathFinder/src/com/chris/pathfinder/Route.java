/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;

import java.util.*;

public class Route {
	private Vector<RoutePoint> path;
	private String name;
	
	/*
	 * start point
	 * end point 
	 * next point
	 * total length
	 * 
	 */
	
	public Route(String name){
		path = new Vector<RoutePoint>();
		setName(name);
	}
	
	public Vector<RoutePoint> getPath() {
		return path;
	}
	public void setPath(Vector<RoutePoint> path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RoutePoint getDestination() {
		return path.lastElement();
	}
	public void addNextPoint(RoutePoint next){
		path.add(next);
	}
	public void setDestination(RoutePoint destination) {
		path.add(destination);
	}
	public RoutePoint getFirstPoint() {
		return path.firstElement();
	}
	public void setFirstPoint(RoutePoint firstPoint) {
		path.add(0, firstPoint);
	}
	public RoutePoint pointAt(int i){
		return path.get(i);
	}
	
}
