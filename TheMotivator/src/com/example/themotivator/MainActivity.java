package com.example.themotivator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.themotivator.applist.activity.ApkListActivity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;



public class MainActivity extends Activity {
	
	private final int REQUEST_CODE_PICK_FILE = 2;
	
	private Intent lockServiceIntent   = null;
	private boolean lockServiceRunning = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	final Activity activityForButton = this;
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_main);
        setLockServiceIntent(new Intent(this, LockService.class));
        
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
        final ListView listview = (ListView) findViewById(R.id.listView1);
        String[] values = new String[] { "then let's start...", "show us your results...", "check your rewards...", "start/stop lock service..." };
        int [] icons = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
          list.add(values[i]);
        }
        
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values, icons);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
        		  
        		  switch (position){
        			  case 0:
        				  Intent ApkListActivityIntent = new Intent(MainActivity.this, ApkListActivity.class);
        				  MainActivity.this.startActivity(ApkListActivityIntent);
        				  break;
        				  
        			  case 1:
        				  Intent fileExploreIntent = new Intent(
        	    					com.example.themotivator.FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
        	        				null,activityForButton, com.example.themotivator.FileBrowserActivity.class
        	        				);
        	        		
        	        		startActivityForResult(
        	        				fileExploreIntent,
        	        				REQUEST_CODE_PICK_FILE
        	        				);
        	        		
        	        		break;
        				  
        			  case 2:
        				  Toast.makeText(getApplicationContext(),
        					      "Check your rewards...", Toast.LENGTH_LONG)
        					      .show();
        				  break;
        				  
        			  case 3:        				          				  
          			      if (isLockServiceRunning()){
          			    	  stopService(getLockServiceIntent());
          			    	  setLockServiceRunning(false);
          			    	  Toast.makeText(getApplicationContext(),
              					      "Stopped lock service...", Toast.LENGTH_LONG)
              					      .show();
          			      } else {
          			    	  startService(getLockServiceIntent());
          			    	  setLockServiceRunning(true);
          			    	  Toast.makeText(getApplicationContext(),
              					      "Started lock service...", Toast.LENGTH_LONG)
              					      .show();
         			      }
          			      
         				  
         				  break;
        		  }
        	  }
        	  
        	});
        
    }
    
    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    	  private final Context context;
    	  private final String[] values;
    	  private final int[] icons;

    	  public MySimpleArrayAdapter(Context context, String[] values, int[] icons) {
    	    super(context, R.layout.rowlayout, values);
    	    this.context = context;
    	    this.values	 = values;
    	    this.icons   = icons;
    	  }

    	  @Override
    	  public View getView(int position, View convertView, ViewGroup parent) {
    	    LayoutInflater inflater = (LayoutInflater) context
    	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    	    TextView textView = (TextView) rowView.findViewById(R.id.label);
    	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    	    textView.setText(values[position]);
    	    
    	    // Set an accordant icon
    	    Integer icon_id = icons[position];
    	    imageView.setImageResource(icon_id);

    	    return rowView;
    	  }
    	} 

    public boolean isLockServiceRunning() {
		return lockServiceRunning;
	}

	public void setLockServiceRunning(boolean lockServiceRunning) {
		this.lockServiceRunning = lockServiceRunning;
	}
	
	protected void setLockServiceIntent(Intent lockServiceIntent) {
		this.lockServiceIntent = lockServiceIntent;
	}

	public Intent getLockServiceIntent() {
		return lockServiceIntent;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//test push
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
