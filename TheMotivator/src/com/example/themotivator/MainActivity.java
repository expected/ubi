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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
        final ListView listview = (ListView) findViewById(R.id.listView1);
        String[] values = new String[] { "then let's\nstart...", "show us your\nresults...", "check your\nrewards..." };
        int [] icons = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};

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
//        				  Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        				  MainActivity.this.startActivity(ApkListActivityIntent);
        				  break;
        				  
        			  case 1:
        				  //amKillProcess("com.facebook.katana");
        				  
        				  Toast.makeText(getApplicationContext(),
        					      "Killing facebook process..." + (killPackageProcesses("com.facebook.katana") ? "Killed":"Not killed"), 
        					      Toast.LENGTH_LONG).show();
        				  /*
        				  Toast.makeText(getApplicationContext(),
        					      "Show us your results...", Toast.LENGTH_LONG)
        					      .show();
        				  
        				  List<PackageInfo> packageList = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        				  for (PackageInfo packageInfo : packageList){
        					  Log.d("DEBUG:", packageInfo.packageName);
        				  }
        				  
        				  Toast.makeText(getApplicationContext(),
        					      "App enabled = " + getPackageManager().getApplicationEnabledSetting("com.facebook.katana"), 
        					      Toast.LENGTH_LONG).show();
        				  */
        				  
        				  /*
        				  if (getPackageManager().getApplicationEnabledSetting("com.facebook.katana") == getPackageManager().COMPONENT_ENABLED_STATE_ENABLED){
        					  //last argument if we want to kill the app
        					  Toast.makeText(getApplicationContext(),
            					      "Enabling facebook...", Toast.LENGTH_LONG)
            					      .show();
        					  getPackageManager().setApplicationEnabledSetting("com.facebook.katana", getPackageManager().COMPONENT_ENABLED_STATE_DISABLED, 0);
        				  } else {
        					  Toast.makeText(getApplicationContext(),
            					      "Disabling facebook...", Toast.LENGTH_LONG)
            					      .show();
        					  getPackageManager().setApplicationEnabledSetting("com.facebook.katana", getPackageManager().COMPONENT_ENABLED_STATE_ENABLED, 0);
        				  }*/
        				  break;
        				  
        			  case 2:
        				  Toast.makeText(getApplicationContext(),
        					      "Check your rewards...", Toast.LENGTH_LONG)
        					      .show();
        				  break;
        		  }
        	  }
        	  
        	  private ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        	  
        	  public boolean killPackageProcesses(String packagename) {
    		    boolean result = false;
    		    
    		    /*
    		    Intent startMain = new Intent(Intent.ACTION_MAIN); 
    		    startMain.addCategory(Intent.CATEGORY_HOME); 
    		    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		    startActivity(startMain);
    		    */
    		    
    		    List<RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
    		    for (RunningAppProcessInfo rapi : runningProcesses){
    	    	  Log.d("DEBUG before:", rapi.processName);
    		    }
    		    
    		    if (am != null) {
    		    	String packagename_dash = packagename + ":dash";
    		        am.killBackgroundProcesses(packagename);
    		        am.killBackgroundProcesses(packagename_dash);
    		        am.killBackgroundProcesses("com.android.calculator2");
    		        result = !isPackageRunning(packagename) && !isPackageRunning(packagename_dash);
    		    } else {
    		        result = false;
    		    }
        		    
    		    runningProcesses = am.getRunningAppProcesses();         	      
          	    for (RunningAppProcessInfo rapi : runningProcesses){
          	      Log.d("DEBUG after:", rapi.processName);
          	    }
          	    
          	    new MyActivityManager(getBaseContext()).clearRecentTasks();

        		return result;
        	  }
        	  
        	  public boolean isPackageRunning(String packagename) {
        		    return findPIDbyPackageName(packagename) != -1;
        	  }
        	  
        	  public int findPIDbyPackageName(String packagename) {
    		    int result = -1;

    		    if (am != null) {
    		        for (RunningAppProcessInfo pi : am.getRunningAppProcesses()){
    		            if (pi.processName.equalsIgnoreCase(packagename)) {
    		                result = pi.pid;
    		            }
    		            if (result != -1) break;
    		        }
    		    } else {
    		        result = -1;
    		    }

    		    return result;
        	  }
        	  
        	  public void amKillProcess(String process)
        	  {
        	      ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        	      final List<RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        	      for (RunningAppProcessInfo rapi : runningProcesses){
        	    	  Log.d("DEBUG before:", rapi.processName);
        	      }
        	      
        	      for(RunningAppProcessInfo runningProcess : runningProcesses) 
        	      {
        	          if(runningProcess.processName.equals(process)) 
        	          {               
        	              android.os.Process.sendSignal(runningProcess.pid, android.os.Process.SIGNAL_KILL);   
        	          }
        	      }
        	      
        	      for (RunningAppProcessInfo rapi : runningProcesses){
        	    	  Log.d("DEBUG after:", rapi.processName);
        	      }
        	      
        	      
        	  }
        	  
        	});
        
    }
    
    
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
            List<String> objects) {
          super(context, textViewResourceId, objects);
          for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
          }
        }

        @Override
        public long getItemId(int position) {
          String item = getItem(position);
          return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
          return true;
        }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
