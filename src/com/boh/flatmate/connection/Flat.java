package com.boh.flatmate.connection;

import java.util.ArrayList;

import com.boh.flatmate.FlatMate.contextExchanger;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
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
		this.shop_items = newFlat.getShopItems();
		this.orderShopItems();
	}
	
	public void updateFlatData(Flat myFlat) {
		this.setUsers(myFlat.getUsers());
		this.setCurrentUser(this.getCurrentUser());
		this.setNickname(myFlat.getNickname());
	}

	public String getUserName(int id){
		for (User u : users){
			if(u.getId() == id){
				return u.getFirst_name() +" "+ u.getLast_name().charAt(0);
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
		int cuid = currentUser.getId();
		for (ShopItem si : shop_items) {
			int ub = si.getUserBoughtId();
			int uw = si.getUserWantId();
			if (ub != -1) {
				if (uw == cuid && ub != cuid) {
					for (Debt d : debts) {
						if (d.getId() == ub) {
							d.add(si.getPrice());
							break;
						}
					}
				} else if (ub == cuid && uw != cuid && uw != -1) {
					for (Debt d : debts) {
						if (d.getId() == uw) {
							d.sub(si.getPrice());
							break;
						}
					}
				} else if (uw == -1) {
					if (ub != cuid) {
						for (Debt d : debts) {
							if (d.getId() == ub) {
								d.add(si.getPrice()/(debts.length+1));
							}
						}
					} else {
						for (Debt d : debts) {
							d.sub(si.getPrice()/(debts.length+1));
						}
					}
				}
			}
		}
	}

	public Debt[] getDebts() {
		return debts;
	}

	public int shopItemsToBuy(){
		int total = 0;
		for (ShopItem si : shop_items) {
			if(si.isBought() == 0){
				total++;
			}
		}
		return total;
	}

	public String getDebt(int id) {
		double debt = 0.0;
		for (Debt d : debts) {
			if (d.getId() == id) {
				debt = d.getDebt();
				break;
			}
		}
		if (debt < 0.0) {
			String debtS = "£" + String.format("%.2f",-debt);
			return "Owes you: " + debtS;
		}
		else {
			String debtS = "£" + String.format("%.2f",debt);
			return "You owe: " + debtS;
		}
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
			if(u.isHome() == 1){
				count++;
			}
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
			//Log.v("FLAT",u.getFlatApproved());
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

	public int getShopItemsLength(){
		return shop_items.length;
	}

	public ShopItem[] getShopItems() {
		return shop_items;
	}

	public void orderShopItems(){
		ShopItem[] temp = new ShopItem[shop_items.length];
		int i = 0;
		for(ShopItem s : shop_items){
			if(s.isBought() == 0){
				temp[i] = s;
				i++;
			}
		}
		for(ShopItem s : shop_items){
			if(s.isBought() == 1){
				temp[i] = s;
				i++;
			}
		}
		shop_items = temp;
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
