package com.boh.flatmate.connection;

public class Flat {
	private String geocode_lat;
	private String geocode_long;
	private String id;
	private String nickname;	//for displaying purposes, so don't just display postcode
	private String postcode;
	private User[] users;
	Flat() {
		//do nothing
	}
	
	public String toHTTPString() {
		String result = "flat[nickname]=" + nickname;
		if (geocode_lat != null) result += "&flat[geocode_lat]" + geocode_lat;
		if (geocode_long != null) result += "&flat[geocode_long]" + geocode_long;
		if (postcode != null) result += "&flat[price]" + postcode;
		return result;
	}
	
	public float getGeocode_lat() {
		return Float.parseFloat(geocode_lat);
	}
	public void setGeocode_lat(float geocode_lat) {
		this.geocode_lat = Float.toString(geocode_lat);
	}
	public float getGeocode_long() {
		return Float.parseFloat(geocode_long);
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public User[] getUsers() {
		return users;
	}
	public void setUsers(User[] users) {
		this.users = users;
	}
}
