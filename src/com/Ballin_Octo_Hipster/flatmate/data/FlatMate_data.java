package com.Ballin_Octo_Hipster.flatmate.data;

import com.Ballin_Octo_Hipster.flatmate.FlatMate.FlatDataExchanger;
import com.Ballin_Octo_Hipster.flatmate.FlatMate.contextExchanger;

import android.content.Intent;
import android.net.Uri;
import android.util.FloatMath;
import android.view.View;
import android.view.View.OnClickListener;

public class FlatMate_data {
	
	private int    FlatMateID;
	private String Name;
	private String PhoneNumber;
	private float Lat;
	private float Long;
	
	private int currentUser = 0;
	
	public FlatMate_data(int ID, String N, String P, float Latitude, float Longitude){
		FlatMateID = ID;
		Name = N;
		PhoneNumber = P;
		Lat = Latitude;
		Long = Longitude;
		
		if(Name == "James Grant"){
			currentUser = 1;
		}
	}
	
	public String getName(){
		return Name;
	}
	
	public int isCurrentUser(){
		return currentUser;
	}
	
	public String getPhoneNumber(){
		return PhoneNumber;
	}
	
	public int isHome(){
		double distanceFromHome = gps2m(Lat,Long,FlatDataExchanger.flatData.getFlatLat(),FlatDataExchanger.flatData.getFlatLong());
		if(distanceFromHome > 50){
			return 0;
		}
		return 1;
	}
	
	private double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
	    float pk = (float) (180/3.14169);

	    float a1 = lat_a / pk;
	    float a2 = lng_a / pk;
	    float b1 = lat_b / pk;
	    float b2 = lng_b / pk;

	    float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
	    float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
	    float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
	    double tt = Math.acos(t1 + t2 + t3);
	   
	    return 6366000*tt;
	}
	
	public OnClickListener phoneListener = new OnClickListener(){ // the book's action
        @Override
        public void onClick(View v) {
        	Intent callIntent = new Intent(Intent.ACTION_VIEW);
        	callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	callIntent.setData(Uri.parse("tel:"+getPhoneNumber()));
        	contextExchanger.context.startActivity(callIntent);
        }
    };
    
    public OnClickListener messageListener = new OnClickListener(){ // the book's action
        @Override
        public void onClick(View v) {
        	Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
        	messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	messageIntent.setData(Uri.parse("sms:"+getPhoneNumber()));
        	contextExchanger.context.startActivity(messageIntent);
        }
    };
}
