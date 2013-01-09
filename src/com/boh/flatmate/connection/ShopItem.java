package com.boh.flatmate.connection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.AsyncTask;
import android.text.format.DateUtils;

import com.boh.flatmate.FlatMate;
import com.boh.flatmate.ShoppingListFragment;
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
	private String paid_back = "false";	//when this becomes true the item gets deleted
	
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
		if (paid_back != null) result += "&shop_item[paid_back]=" + paid_back;
		return result;
	}
	
	public String toHTTPStringBought() {
		String result = "shop_item[user_bought_id]=" + user_bought_id;
		if (price != null) result += "&shop_item[price]=" + price;
		return result;
	}
	
	public int isBought(){
		if(user_bought_id != null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public void deleteItem(){
		new serverDeleteItem().execute(this);
	}
	
	public void addItem(){
		new serverAddItem().execute(this);
	}
	
	public void setBoughtToday(double boughtPrice){
		int userId = FlatDataExchanger.flatData.getCurrentUserId();
		
		ShopItem updateItem = new ShopItem();
		updateItem = this;
		updateItem.setUserBoughtId(userId);
		updateItem.setPrice(""+boughtPrice);
		
		new serverUpdateItem().execute(updateItem);
	}

	public int getFlatId() {
		return Integer.parseInt(flat_id);
	}

	public void setFlatId(int flat_id) {
		this.flat_id = Integer.toString(flat_id);
	}

	public int getUserWantId() {
		if (user_want_id != null) return Integer.parseInt(user_want_id);
		else return -1;
	}

	public void setUserWantId(int user_want_id) {
		this.user_want_id = Integer.toString(user_want_id);
	}

	public int getUserBoughtId() {
		if (user_bought_id == null) {
			return -1;
		} else return Integer.parseInt(user_bought_id);
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
		CharSequence str = DateUtils.getRelativeDateTimeString (
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
	
	private class serverDeleteItem extends AsyncTask<ShopItem,Void,Void> {
		protected Void doInBackground(ShopItem... item) {
			ConnectionExchanger.connection.deleteItem(item[0].getFlatId()+"",item[0].getId()+"");
			FlatDataExchanger.flatData.updateData(ConnectionExchanger.connection.getMyFlat());
			return null;
		}

		protected void onPostExecute(Void result) {
			ShoppingListFragment.mAdapter.notifyDataSetChanged();
		}
	}
	
	private class serverAddItem extends AsyncTask<ShopItem,Void,Void> {
		protected Void doInBackground(ShopItem... item) {
			ConnectionExchanger.connection.addItem(item[0]);
			FlatDataExchanger.flatData.updateData(ConnectionExchanger.connection.getMyFlat());
			return null;
		}

		protected void onPostExecute(Void result) {
			ShoppingListFragment.mAdapter.notifyDataSetChanged();
		}
	}
	
	private class serverUpdateItem extends AsyncTask<ShopItem,Void,Void> {
		protected Void doInBackground(ShopItem... item) {
			ConnectionExchanger.connection.updateItem(item[0]);
			FlatDataExchanger.flatData.updateData(ConnectionExchanger.connection.getMyFlat());
			return null;
		}

		protected void onPostExecute(Void result) {
			ShoppingListFragment.mAdapter.notifyDataSetChanged();
		}
	}
}
