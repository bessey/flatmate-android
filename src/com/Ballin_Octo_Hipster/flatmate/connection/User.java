package com.Ballin_Octo_Hipster.flatmate.connection;

public class User {
	private String email;
	private String first_name;
	private String flat_id;
	private float geocode_lat;
	private float geocode_long;
	private int id;
	private String last_name;
	private String phone_number;
	User() {
		//does nothing
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
		return geocode_lat;
	}
	public void setGeocode_lat(float geocode_lat) {
		this.geocode_lat = geocode_lat;
	}
	public float getGeocode_long() {
		return geocode_long;
	}
	public void setGeocode_long(float geocode_long) {
		this.geocode_long = geocode_long;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
}
