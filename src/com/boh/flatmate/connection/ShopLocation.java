package com.boh.flatmate.connection;

public class ShopLocation {
	private double lat;
	private double lng;
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public boolean isEquals(ShopLocation loc){
		if(lat == loc.getLat() && lng == loc.getLng()){
			return true;
		}else return false;
	}
}
