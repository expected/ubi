package com.example.themotivator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

public class LockService extends IntentService {
	
	private Object object = new Object();
	
	String[] appsToLock = {"com.facebook"};
	
	private boolean running = false;
	
    public LockService() {
		super("LockService");
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void onHandleIntent(Intent workIntent) {
		while(isRunning()) {
			ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
	        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
	        String activityOnTop=ar.topActivity.getClassName();
	        Log.d("DEBUG ACTIVITY_ON_TOP", activityOnTop);
	        
	        for (String appToLock : appsToLock){
	        	if (activityOnTop.contains(appToLock)){
	        		lockApp();
	        	}
	        }
	        
	        
	        // Sleep	        
	        synchronized (object) {
	            try {
	                object.wait(1000); // If you want a timed wait or else you can just use object.wait()
	            } catch (InterruptedException e) {
	                Log.e("Message", "Interrupted Exception while getting lock" + e.getMessage());
	            }
	        }
	    }
    }
	
	@Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        setRunning(true);
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		setRunning(false);
	}
	
	public void lockApp(){
  	    Intent lockIntent = new Intent(getBaseContext(), MainActivity.class);
  	    lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  	    startActivity(lockIntent);
  	    
  	    Toast.makeText(getApplicationContext(),
		      "Please show us your results, before you can use blocked apps.", Toast.LENGTH_LONG)
		      .show();
	}
	
	protected List<String> getAllRelevantApps(){
		PackageManager packageManager = getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		List<String> apps = new ArrayList<String>();
		List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
		Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			ApplicationInfo a = p.applicationInfo;
			// skip system apps if they shall not be included
			if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
				continue;
			}
			apps.add(p.packageName);
		}
		
		return apps;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
