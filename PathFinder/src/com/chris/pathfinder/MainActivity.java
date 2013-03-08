package com.chris.pathfinder;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {
	 	UserLocation ul;
	 	RouteManager rm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ul = new UserLocation(this);
		Thread t = new Thread(ul);
		t.start();
		
		
		rm = new RouteManager();
    	rm.showDirections(ul, rm.findRoute("Test"), this);
    	
    	
    	
    	setup();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
    @Override
    protected void onStart() {
        super.onStart();
        
    }
    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }
    public void setup(){
    	TextView t = new TextView(this);
    	t = (TextView)findViewById(R.id.textView1);
    	t.setText(rm.currentRouteName());
    	
    	
    	TextView t2 = new TextView(this);
    	t2 = (TextView)findViewById(R.id.textView3);
    	String dtnp = ul.getDistanceToNextPoint() + "";
    	t2.setText(dtnp);
    	
    	TextView t3 = new TextView(this);
    	t3 = (TextView)findViewById(R.id.textView5);
    	t3.setText(rm.directionsNextPoint(ul.getCurrPoint()));
    }
    	

	
}
