package com.boh.flatmate.connection;

import java.util.ArrayList;

import com.boh.flatmate.FlatMate.contextExchanger;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

public class Flat {
	private String geocode_lat = "100";
	private String geocode_long = "100";
	private String id = "9001";
	private String nickname = "dummy";	//for displaying purposes, so don't just display postcode
	private String postcode = "DUM MY55";
	private User[] users;
	private User currentUser;
	private ShopItem[] shop_items;

	public String getUserName(int id){
		System.out.println("USER BOUGHT ID "+id);
		for (User u : users){
			System.out.println("USER ID "+u.getId());
			if(u.getId() == id){
				return u.getFirst_name();
			}
		}
		return "";
	}

	public void setCurrentUser(User u){
		currentUser = u;
		
		// Remove us from the users array
		ArrayList<User> usersWithoutMe = new ArrayList<User>();
		for(int i = 0; i < users.length; i++){
			if(users[i].getId() != currentUser.getId()){
				usersWithoutMe.add(users[i]);
			}
		}
		users = usersWithoutMe.toArray(new User[usersWithoutMe.size()]);
	}

	public User getCurrentUser(){
		return currentUser;
	}

	public int getCurrentUserId(){
		return currentUser.getId();
	}

	public int getNoAtFlat(){
		int count = 0;
		for (User u : users){
			count += u.isHome();
		}
		return count;
	}

	public String toHTTPString() {
		String result = "flat[nickname]=" + nickname;
		if (geocode_lat != null) result += "&flat[geocode_lat]=" + geocode_lat;
		if (geocode_long != null) result += "&flat[geocode_long]=" + geocode_long;
		if (postcode != null) result += "&flat[price]=" + postcode;
		return result;
	}

	public float getGeocode_lat() {
		if(geocode_lat != null){
			return Float.parseFloat(geocode_lat);
		}else return 0.0f;
	}
	public void setGeocode_lat(float geocode_lat) {
		this.geocode_lat = Float.toString(geocode_lat);
	}
	public float getGeocode_long() {
		if(geocode_long != null){
			return Float.parseFloat(geocode_long);
		}else return 0.0f;
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
	
	public ShopItem getShopItem(int position) {
		return shop_items[position];
	}

	public ShopItem[] getShopItems() {
		return shop_items;
	}

	public OnClickListener messageListener = new OnClickListener(){ // the book's action
		@Override
		public void onClick(View v) {
			Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
			messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			String numbers = "";
			boolean first = true;
			for (User u : users) {
				if (first) first = false;
				else numbers += ";";
				numbers += u.getPhone_number();
			}
			messageIntent.setData(Uri.parse("sms:"+numbers));
			contextExchanger.context.startActivity(messageIntent);
		}
	};
}
