package com.example.themotivator.misc;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;

import com.example.themotivator.MyActivityManager;

public class BlockTries extends Activity{
	

	 //amKillProcess("com.facebook.katana");
	  
//	  Toast.makeText(getApplicationContext(),
//		      "Killing facebook process..." + (killPackageProcesses("com.facebook.katana") ? "Killed":"Not killed"), 
//		      Toast.LENGTH_LONG).show();
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

}
