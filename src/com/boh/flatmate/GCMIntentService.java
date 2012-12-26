package com.boh.flatmate;

import android.content.Context;
import android.content.Intent;

public class GCMIntentService {
	
	/*
	 * Called after a registration intent is received, passes the registration ID assigned by 
	 * GCM to that device/application pair as parameter. Typically, you should send the regid 
	 * to your server so it can use it to send messages to this device.
	 */
	void onRegistered(Context context, String regId){
		
	}
	
	/*
	 * Called after the device has been unregistered from GCM. Typically, you should send the 
	 * regid to the server so it unregisters the device.
	 */
	void onUnregistered(Context context, String regId){
		
	}
	
	/*
	 * Called when your server sends a message to GCM, and GCM delivers it to the device. If 
	 * the message has a payload, its contents are available as extras in the intent.
	 */
	void onMessage(Context context, Intent intent){
		
	}
	
	/*
	 * Called when the device tries to register or unregister, but GCM returned an error. 
	 * Typically, there is nothing to be done other than evaluating the error (returned by 
	 * errorId) and trying to fix the problem.
	 */
	void onError(Context context, String errorId){
		
	}
	
	/*
	 * Called when the device tries to register or unregister, but the GCM servers are 
	 * unavailable. The GCM library will retry the operation using exponential backup, 
	 * unless this method is overridden and returns false. This method is optional and should 
	 * be overridden only if you want to display the message to the user or cancel the retry 
	 * attempts.
	 */
	void onRecoverableError(Context context, String errorId){
		
	}
}
