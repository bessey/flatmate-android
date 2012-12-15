package com.boh.flatmate.data;

public class Flat_data {
	
	private String geocode_lat;
	private String geocode_long;
	private String id;
	private String nickname;	//for displaying purposes, so don't just display postcode
	private String postcode;
	private FlatMate_data[] users;
	
	public Flat_data(){
		setId("1");
		setNickname("Our Flat");
		setPostcode("BS8 2LG");
		setGeocode_lat("51.460291");
		setGeocode_long("-2.608701");
		
		FlatMate_data[] tempFlatMates = {new FlatMate_data(1,"James Grant","07944652549",51.460291f,-2.608701f),
										 new FlatMate_data(2,"Matt Bessey","07957151787",51.455752f,-2.602839f),
										 new FlatMate_data(3,"Adam Coales","07539419348",51.460291f,-2.608701f)};
		users = tempFlatMates;
	}
	
	public String toHTTPString() {
		String result = "flat[nickname]=" + nickname;
		if (geocode_lat != null) result += "&flat[geocode_lat]" + geocode_lat;
		if (geocode_long != null) result += "&flat[geocode_long]" + geocode_long;
		if (postcode != null) result += "&flat[price]" + postcode;
		return result;
	}
	
	public String getGeocode_lat() {
		return geocode_lat;
	}
	public void setGeocode_lat(String geocode_lat) {
		this.geocode_lat = geocode_lat;
	}
	public String getGeocode_long() {
		return geocode_long;
	}
	public void setGeocode_long(String geocode_long) {
		this.geocode_long = geocode_long;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public FlatMate_data[] getUsers() {
		return users;
	}
	public void setUsers(FlatMate_data[] users) {
		this.users = users;
	}
}
