package com.boh.flatmate.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShoppingItem_data {
	
	private int bought = 0;
	private String name;
	private double price;
	private String date;
	private int flatMateId;

	public ShoppingItem_data(String Name) {
		this.name = Name;
	}
	
	public ShoppingItem_data(String Name, double Price, String Date, int FlatMateId) {
		this.name = Name;
		this.price = Price;
		this.date = Date;
		this.flatMateId = FlatMateId;
		this.bought = 1;
	}
	
	public void boughtToday(double price){
		this.price = price;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		this.date = sdf.format(new Date());
		// TODO get real flatMateId
		this.flatMateId = 1;
		this.bought = 1;
	}
	
	public int isBought(){
		return bought;
	}
	
	public String itemName(){
		return name;
	}
	
	public double itemPrice(){
		return price;
	}
	
	public String itemDate(){
		return date;
	}
	
	public int itemBoughtBy(){
		return flatMateId;
	}

}
