package com.boh.flatmate.connection;

import java.util.ArrayList;

import com.boh.flatmate.FlatMate.contextExchanger;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Flat {
	private String nickname;	//for displaying purposes, so don't just display postcode
	private String geocode_lat;
	private String geocode_long;
	private String id;
	private String postcode;
	private User[] users;
	private User currentUser;
	private ShopItem[] shop_items;
	private Debt[] debts;
	
	public void updateData(Flat newFlat){
		shop_items = newFlat.getShopItems();
	}

	public String getUserName(int id){
		for (User u : users){
			if(u.getId() == id){
				return u.getFirst_name();
			}
		}
		// If the name isn't in the list, it must be you
		return "You";
	}
	
	public int getUserColourID(int id) {
		for (User u : users){
			if(u.getId() == id){
				return u.getColour_Id();
			}
		}
		// If the name isn't in the list, it must be you
		return currentUser.getColour_Id();
	}
	
	public void updateDebts() {
		debts = new Debt[users.length];
		for (int i = 0; i < users.length; i++) {
			debts[i] = new Debt(users[i].getId(), 0.0);
		}
		int uid = currentUser.getId();
		for (ShopItem si : shop_items) {
			int ub = si.getUserBoughtId();
			int uw = si.getUserWantId();
			if (ub != -1) {
				if (uw == uid && ub != uid) {
					for (Debt d : debts) {
						if (d.getId() == ub) {
							d.add(si.getPrice());
							break;
						}
					}
				} else if (ub == uid && uw != uid) {
					for (Debt d : debts) {
						if (d.getId() == uw) {
							d.sub(si.getPrice());
							break;
						}
					}
				} else if (uw == -1) {
					for (Debt d : debts) {
						if (d.getId() != ub) {
							d.add(si.getPrice()/debts.length+1);
						}
					}
				}
			}
		}
	}
	
	public Debt[] getDebts() {
		return debts;
	}
	
	public String getDebt(int id) {
		double debt = 0.0;
		System.out.println("*****************---*********************");
		for (Debt d : debts) {
			System.out.println("***********************************************");
			System.out.println(d.getId() + ":" + id);
			System.out.println("***********************************************");
			if (d.getId() == id) {
				debt = d.getDebt();
				break;
			}
		}
		String debtS = "£" + String.format("%.2f",debt);
		if (debt < 0.0) {
			return "Owes you: " + debtS;
		}
		else return "You owe: " + debtS;
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
		String result = "flat[nickname]=" + TextUtils.htmlEncode(nickname);
		if (geocode_lat != null) result += "&flat[geocode_lat]=" + geocode_lat;
		if (geocode_long != null) result += "&flat[geocode_long]=" + geocode_long;
		if (postcode != null) result += "&flat[postcode]=" + TextUtils.htmlEncode(postcode);
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
		if (id < 0) this.id = null;
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
	public User[] getApprovedUsers() {
		ArrayList<User> l = new ArrayList<User>();
		for(User u : users){
			Log.v("FLAT",u.getFlatApproved());
			if(u.getFlatApproved().equals("true")) l.add(u);
		}
		return (User[]) l.toArray(new User[l.size()]);
	}
	public User[] getUnapprovedUsers() {
		ArrayList<User> l = new ArrayList<User>();
		for(User u : users){
			if(u.getFlatApproved().equals("false")) l.add(u);
		}
		return (User[]) l.toArray(new User[l.size()]);
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
