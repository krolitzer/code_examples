/**
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;

public enum Direction {
	N, NE, E, SE, S ,SW, W, NW, X;
	
    /**
     * getCurrDir() returns an Direction enum based on what 
     * i and j value are inputed. Used in explore(). 
     * @param i is the i value from a for loop.
     * @param j is the j value from a for loop.
     * @return an enum representing a cardinal Direction.
     **/
    public static Direction getCurrDir(double brng){

    	if( brng > 0.0 && brng < 22.5){
    		return N;
    	}	
    	if( brng > 22.5 && brng < 67.5){
    		return NE;
    	}
    	if( brng > 67.5 && brng < 112.5){
    		return E;
    	}
    	if ( brng > 112.5 && brng < 157.5){
    		return SE;
    	}
    	if ( brng > 157.5 && brng < 202.5){
    		return S;
    	}
    	if( brng > 202.5 && brng < 247.5){
    		return SW;
    	}
    	if( brng > 247.5 && brng < 292.5){
    		return W;
    	}
    	if( brng > 292.5 && brng < 337.5){
    		return NW;
    	}
    	if( brng > 337.5 && brng < 360.1){
    		return N;
    	}
    	return X; //something to satisfy the compiler
    	}
}
