package com.example.app01;



import android.os.Bundle;
import android.widget.*;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.view.Menu;

public class MainActivity extends Activity {

	String[] actions = new String[] {
			"All News",
			"Admission",
			"FAQs"
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayShowTitleEnabled(false);
    	getActionBar().setDisplayShowHomeEnabled(false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions );
        
        
        /** Enabling dropdown list navigation for the action bar */
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
			
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(getBaseContext(), "You selected : " + actions[itemPosition]  , Toast.LENGTH_SHORT).show();
				return false;
			}
		};
	
		/** Setting dropdown items and item navigation listener for the actionbar */
		getActionBar().setListNavigationCallbacks(adapter, navigationListener);        
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
