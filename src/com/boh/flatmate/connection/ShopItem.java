package com.boh.flatmate.connection;

public class ShopItem {
	private String id;
	private String flat_id;
	private String user_want_id;	//used when working out who owes who money
	private String user_bought_id;	//used with above, also when this stops being null, don't display item in shopping list
	private String price;
	private String name;		//name of item, maybe implement getting this from list (to help with image recog)
	private String paid_back;	//when this becomes true the item gets deleted
	
	ShopItem() {
		//do nothing
	}
	
	public String toHTTPString() {
		String result = "shop_item[name]=" + name;
		if (user_want_id != null) result += "&shop_item[user_want_id]" + user_want_id;
		if (user_bought_id != null) result += "&shop_item[user_bought_id]" + user_bought_id;
		if (price != null) result += "&shop_item[price]" + price;
		if (paid_back != null) result += "&shop_item[paid_back]" + paid_back;
		return result;
	}

	public String getFlat_Id() {
		return flat_id;
	}

	public void setFlat_Id(String flat_id) {
		this.flat_id = flat_id;
	}

	public String getUser_want_id() {
		return user_want_id;
	}

	public void setUser_want_id(String user_want_id) {
		this.user_want_id = user_want_id;
	}

	public String getUser_bought_id() {
		return user_bought_id;
	}

	public void setUser_bought_id(String user_bought_id) {
		this.user_bought_id = user_bought_id;
	}

	public String getPrice() {
		return price;
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

	public String isPaid_back() {
		return paid_back;
	}

	public void setPaid_back(String paid_back) {
		this.paid_back = paid_back;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
