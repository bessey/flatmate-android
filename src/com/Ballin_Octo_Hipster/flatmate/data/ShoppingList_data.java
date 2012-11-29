package com.Ballin_Octo_Hipster.flatmate.data;

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
	
	public int totalToBuy(){
		
		return 2;
	}
}
