package com.boh.flatmate.connection;

import java.util.ArrayList;

import com.boh.flatmate.FlatMate.ConnectionExchanger;
import com.boh.flatmate.FlatMate.FlatDataExchanger;

public class ShoppingList {
	
	private ArrayList<ShopItem> shoppingList = new ArrayList<ShopItem>();

	public ShoppingList() {}
	
	public ShoppingList(ShopItem[] items) {
		for (ShopItem s : items) {  
			shoppingList.add(s);  
		} 
	}
	
	public ArrayList<ShopItem> getShoppingList(){
		return shoppingList;
	}
	
	public int listSize(){
		return shoppingList.size();
	}
	
	public void addItem(String itemName){
		int user_id = FlatDataExchanger.flatData.getCurrentUserId();
		int flat_id = FlatDataExchanger.flatData.getId();
		ShopItem item = new ShopItem(itemName, user_id, flat_id);
		shoppingList.add(item);
		ConnectionExchanger.connection.addItem(item);
	}
	
	public int totalToBuy(){
		return 2;
	}

	public void boughtToday(int position, double price) {
		ShopItem item = shoppingList.get(position);
		//item.boughtToday(price);
	}
}
