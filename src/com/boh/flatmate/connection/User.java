package com.boh.flatmate.connection;

import android.content.Intent;
import android.net.Uri;
import android.util.FloatMath;
import android.view.View;
import android.view.View.OnClickListener;

import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;

public class User {
	private String email = "dummy@dum.my";
	private String password = "dummy";
	private String first_name = "DUM";
	private String flat_id = "9001";
	private String geocode_lat = "99";
	private String geocode_long = "99";
	private String id = "17";
	private String last_name = "MY";
	private String phone_number = "0987654321";
	User() {
		//does nothing
	}
	public String toHTTPString() {
		String result = "";
		result = "user[email]=" + email;
		if (flat_id != null) result += "&user[flat_id]=" + flat_id;
		if (geocode_lat != null) result += "&user[geocode_lat]=" + geocode_lat;
		if (geocode_long != null) result += "&user[geocode_long]=" + geocode_long;
		if (first_name != null) result += "&user[first_name]=" + first_name;
		if (last_name != null) result += "&user[last_name]=" + last_name;
		if (phone_number != null) result += "&user[phone_number]=" + phone_number;
		if (password != null) result += "&user[password]=" + password;
		return result;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getFlat_id() {
		return flat_id;
	}
	public void setFlat_id(String flat_id) {
		this.flat_id = flat_id;
	}
	public float getGeocode_lat() {
		float geoLat;
		try {
			geoLat = Float.parseFloat(geocode_lat);
		} catch(Exception e) {
			return 0.0f;
		}
		return geoLat;
	}
	public void setGeocode_lat(float geocode_lat) {
		this.geocode_lat = Float.toString(geocode_lat);
	}
	public float getGeocode_long() {
		float geoLong;
		try {
			geoLong = Float.parseFloat(geocode_long);
		} catch(Exception e) {
			return 0.0f;
		}
		return geoLong;
	}
	public void setGeocode_long(float geocode_long) {
		this.geocode_long = Float.toString(geocode_long);
	}
	public int getId() {
		return Integer.parseInt(id);
	}
	public void setId(int id) {
		this.id = Integer.toString(id);
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int isHome(){
		float FlatMate_Lat = FlatDataExchanger.flatData.getGeocode_lat();
		float FlatMate_Long = FlatDataExchanger.flatData.getGeocode_long();
		double distanceFromHome = gps2m(this.getGeocode_lat(),this.getGeocode_long(),FlatMate_Lat,FlatMate_Long);
		if(distanceFromHome > 50){
			return 0;
		}
		return 1;
	}

	public double distanceFromHome(){
		float FlatMate_Lat = FlatDataExchanger.flatData.getGeocode_lat();
		float FlatMate_Long = FlatDataExchanger.flatData.getGeocode_long();
		double distanceFromHome = gps2m(this.getGeocode_lat(),this.getGeocode_long(),FlatMate_Lat,FlatMate_Long);
		return distanceFromHome*0.00062137;
	}

	public OnClickListener phoneListener = new OnClickListener(){ // the book's action
		@Override
		public void onClick(View v) {
			Intent callIntent = new Intent(Intent.ACTION_VIEW);
			callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			callIntent.setData(Uri.parse("tel:"+getPhone_number()));
			contextExchanger.context.startActivity(callIntent);
		}
	};

	public OnClickListener messageListener = new OnClickListener(){ // the book's action
		@Override
		public void onClick(View v) {
			Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
			messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			messageIntent.setData(Uri.parse("sms:"+getPhone_number()));
			contextExchanger.context.startActivity(messageIntent);
		}
	};

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
}
