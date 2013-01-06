package com.boh.flatmate.connection;

public class ShopItem {
	private String id;
	private String flat_id;
	private String user_want_id;	//used when working out who owes who money
	private String user_bought_id;	//used with above, also when this stops being null, don't display item in shopping list
	private String bought_date;
	private String price;
	private String name;		//name of item, maybe implement getting this from list (to help with image recog)
	private String paid_back;	//when this becomes true the item gets deleted
	
	ShopItem() {
		//do nothing
	}
	
	public ShopItem(String itemName, int user_id, int current_flat_id) {
		name = itemName;
		user_want_id = ""+user_id;
		flat_id = ""+current_flat_id;
	}
		
	public String toHTTPString() {
		String result = "shop_item[name]=" + name;
		if (user_want_id != null) result += "&shop_item[user_want_id]" + user_want_id;
		if (user_bought_id != null) result += "&shop_item[user_bought_id]" + user_bought_id;
		if (price != null) result += "&shop_item[price]" + price;
		//if (paid_back != null) result += "&shop_item[paid_back]" + paid_back;
		return result;
	}
	
	public int isBought(){
		if(user_bought_id != null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public void boughtToday(double boughtPrice, int id, String date){
		user_bought_id = ""+id;
		price = ""+boughtPrice;
		bought_date = ""+date;
	}

	public int getFlat_Id() {
		return Integer.parseInt(flat_id);
	}

	public void setFlat_Id(int flat_id) {
		this.flat_id = Integer.toString(flat_id);
	}

	public int getUser_want_id() {
		return Integer.parseInt(user_want_id);
	}

	public void setUser_want_id(int user_want_id) {
		this.user_want_id = Integer.toString(user_want_id);
	}

	public int getUser_bought_id() {
		return Integer.parseInt(user_bought_id);
	}

	public void setUser_bought_id(int user_bought_id) {
		this.user_bought_id = Integer.toString(user_bought_id);
	}

	public Double getPrice() {
		return Double.parseDouble(price);
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPaid_back() {
		return Boolean.parseBoolean(paid_back);
	}

	public void setPaid_back(boolean paid_back) {
		this.paid_back = Boolean.toString(paid_back);
	}

	public int getId() {
		return Integer.parseInt(id);
	}

	public void setId(int id) {
		this.id = Integer.toString(id);
	}

	public String getBought_date() {
		return bought_date;
	}

	public void setBought_date(String bought_date) {
		this.bought_date = bought_date;
	}
}
