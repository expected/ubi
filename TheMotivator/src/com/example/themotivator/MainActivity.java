package com.example.themotivator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.themotivator.applist.activity.ApkListActivity;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        				  Toast.makeText(getApplicationContext(),
        					      "Show us your results...", Toast.LENGTH_LONG)
        					      .show();
        				  break;
        				  
        			  case 2:
        				  Toast.makeText(getApplicationContext(),
        					      "Check your rewards...", Toast.LENGTH_LONG)
        					      .show();
        				  break;
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
