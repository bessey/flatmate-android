package com.boh.flatmate.data;

import java.util.ArrayList;

public class ShoppingList_data {
	
	private ArrayList<ShoppingItem_data> ShoppingList = new ArrayList<ShoppingItem_data>();

	public ShoppingList_data() {
		ShoppingItem_data item = new ShoppingItem_data("Cheese");
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Bread");
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Pizza",1.99,"05/11/12",1);
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Toilet Roll");
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Ham");
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Chips");
		ShoppingList.add(item);
		
		item = new ShoppingItem_data("Beans");
		ShoppingList.add(item);
	}
	
	public ArrayList<ShoppingItem_data> getShoppingList(){
		return ShoppingList;
	}
	
	public void addItem(String itemName){
		ShoppingList.add(0,new ShoppingItem_data(itemName));
	}
	
	public int totalToBuy(){
		return 2;
	}

	public void boughtToday(int position, double price) {
		ShoppingItem_data item = ShoppingList.get(position);
		item.boughtToday(price);
	}
}
