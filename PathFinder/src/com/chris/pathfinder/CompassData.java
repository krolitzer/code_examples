/**
 * CompassData.java
 * 
 * @author Chris Costello
 * "On my honor, as a University of Colorado at Boulder student, I have neither given nor received 
 * unauthorized assistance on this work."
 */
package com.chris.pathfinder;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;


public class CompassData extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private Sensor mOrientation;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	    
	  }
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		
	}
	

}
