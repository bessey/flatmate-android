package com.boh.flatmate.connection;

public class User {
	private String email;
	private String password;
	private String first_name;
	private String flat_id;
	private String geocode_lat;
	private String geocode_long;
	private String id;
	private String last_name;
	private String phone_number;
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
}
