package com.boh.flatmate.connection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateUtils;

import com.boh.flatmate.FlatMate.ConnectionExchanger;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;

public class ShopItem {
	private String id;
	private String flat_id;
	private String user_want_id;	//used when working out who owes who money
	private String user_bought_id;	//used with above, also when this stops being null, don't display item in shopping list
	private String updated_at;
	private String price;
	private String name;		//name of item, maybe implement getting this from list (to help with image recog)
	private String paid_back;	//when this becomes true the item gets deleted
	
	ShopItem() {
		//do nothing
	}
	
	public ShopItem(String itemName) {
		setUserWantId(FlatDataExchanger.flatData.getCurrentUserId());
		setFlatId(FlatDataExchanger.flatData.getId());
		name = itemName;
	}
		
	public String toHTTPString() {
		String result = "shop_item[name]=" + name;
		if (user_want_id != null) result += "&shop_item[user_want_id]=" + user_want_id;
		if (user_bought_id != null) result += "&shop_item[user_bought_id]=" + user_bought_id;
		if (price != null) result += "&shop_item[price]=" + price;
		//if (paid_back != null) result += "&shop_item[paid_back]=" + paid_back;
		return result;
	}
	
	public int isBought(){
		if(user_bought_id != null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public void addItem(){
		ConnectionExchanger.connection.addItem(this);		
	}
	
	public void setBoughtToday(double boughtPrice){
		int id = FlatDataExchanger.flatData.getCurrentUserId();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		
		user_bought_id = ""+id;
		price = ""+boughtPrice;
		updated_at = ""+dateString;
		ConnectionExchanger.connection.updateItem(this);
	}

	public int getFlatId() {
		return Integer.parseInt(flat_id);
	}

	public void setFlatId(int flat_id) {
		this.flat_id = Integer.toString(flat_id);
	}

	public int getUserWantId() {
		return Integer.parseInt(user_want_id);
	}

	public void setUserWantId(int user_want_id) {
		this.user_want_id = Integer.toString(user_want_id);
	}

	public int getUserBoughtId() {
		return Integer.parseInt(user_bought_id);
	}

	public void setUserBoughtId(int user_bought_id) {
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

	public boolean isPaidBack() {
		return Boolean.parseBoolean(paid_back);
	}

	public void setPaidBack(boolean paid_back) {
		this.paid_back = Boolean.toString(paid_back);
	}

	public int getId() {
		return Integer.parseInt(id);
	}

	public void setId(int id) {
		this.id = Integer.toString(id);
	}

	public String getUpdatedAt() {
		return updated_at;
	}
	
	public String getUpdatedAtPretty() {
		// TODO: use relative dates 
		// DateUtils.getRelativeTimeSpanString(Context, long, boolean)
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.ENGLISH).parse(updated_at);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CharSequence str = DateUtils.getRelativeDateTimeString(
		        contextExchanger.context, // Suppose you are in an activity or other Context subclass
		        date.getTime(), // The time to display
		        DateUtils.SECOND_IN_MILLIS, // The resolution. This will display only minutes 
		                          // (no "3 seconds ago"
		        DateUtils.YEAR_IN_MILLIS, // The maximum resolution at which the time will switch 
		                         // to default date instead of spans. This will not 
		                         // display "3 weeks ago" but a full date instead
		        0); // Eventual flags
		String[] result = ((String)str).split(",");
		return result[0];
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
}
