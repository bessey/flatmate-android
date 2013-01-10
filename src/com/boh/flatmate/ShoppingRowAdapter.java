package com.boh.flatmate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.connection.ShopItem;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingRowAdapter extends ArrayAdapter<ShopItem> {

	private Context context;

	public ShoppingRowAdapter(Context c, int textViewResourceId, ShopItem[] data) {
		super(c, textViewResourceId, data);
		context = c;
	}
	
	@Override
	public int getCount(){
		return FlatDataExchanger.flatData.getShopItemsLength();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ShopItem shoppingItem = FlatDataExchanger.flatData.getShopItem(position);
		
		if(shoppingItem.isBought() == 1) {
			if(v == null || v.getTag() == "notBought"){
				LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.shopping_row_bought, null);
				v.setTag("bought");
			}
		} else if (v == null || v.getTag() == "bought") {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.shopping_row, null);
			v.setTag("notBought");
		}
		v.setBackgroundResource(R.drawable.boxg);

		String name = shoppingItem.getName();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		
		int wantID = shoppingItem.getUserWantId();
		String itemForText = "";
		if(wantID == -1){
			itemForText = "For the Flat";
		}else{
			itemForText = "For "+FlatDataExchanger.flatData.getUserName(wantID);
		}
		TextView wantsTextView = (TextView) v.findViewById(R.id.wantsTextView);
		if (wantsTextView != null) {
			wantsTextView.setText(itemForText);
		}
		
		if(shoppingItem.isBought() == 1) {
			TextView priceTextView = (TextView) v.findViewById(R.id.Price);
			Double priceDouble = shoppingItem.getPrice();
			String priceString = String.format("%.2f",priceDouble);
			priceTextView.setText("£"+priceString);
			TextView flatMateTextView = (TextView) v.findViewById(R.id.boughtName);

			int buyerID = shoppingItem.getUserBoughtId();
			flatMateTextView.setText(FlatDataExchanger.flatData.getUserName(buyerID));

			int colourID = FlatDataExchanger.flatData.getUserColourID(buyerID);
			if(colourID == 0) {
				v.setBackgroundResource(R.drawable.box1);
			} else if(colourID == 1) {
				v.setBackgroundResource(R.drawable.box2);
			} else if (colourID == 2) {
				v.setBackgroundResource(R.drawable.box3);
			} else if (colourID == 3) {
				v.setBackgroundResource(R.drawable.box4);
			} else if (colourID == 4) {
				v.setBackgroundResource(R.drawable.box5);
			} else if (colourID == 5) {
				v.setBackgroundResource(R.drawable.box6);
			} else if (colourID == 6) {
				v.setBackgroundResource(R.drawable.box7);
			} else if (colourID == 7) {
				v.setBackgroundResource(R.drawable.box8);
			}else {
				v.setBackgroundResource(R.drawable.box8);
			}

			TextView dateTextView = (TextView) v.findViewById(R.id.dateBought);
			dateTextView.setText(shoppingItem.getUpdatedAtPretty());
		} else {
			final ImageButton setPriceButton = (ImageButton)v.findViewById(R.id.saveButton);
			final EditText priceInput = (EditText)v.findViewById(R.id.priceInput);
			if(priceInput != null){
				priceInput.setFilters(new InputFilter[] {new InputFilter(){
					Pattern mPattern;			    
					@Override
					public CharSequence filter(CharSequence source, int start,
							int end, Spanned dest, int dstart, int dend) {
						mPattern=Pattern.compile("[0-9]{0," + (2) + "}+((\\.[0-9]{0," + (1) + "})?)||(\\.)?");
						Matcher matcher=mPattern.matcher(dest);       
						if(!matcher.matches())
							return "";
						return null;
					}
				}});
				priceInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(final View view, boolean hasFocus) {
						if(hasFocus){
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								public void run() {
									if (view.findViewById(R.id.priceInput) != null) {
										view.findViewById(R.id.priceInput).requestFocus();
									}
								}
							}, 100);
						}
					}
				});
			}
			if(setPriceButton != null){
				setPriceButton.setImageResource(R.drawable.tick);
				setPriceButton.setActivated(true);
				setPriceButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View view) {
						Editable priceText = priceInput.getText();
						if(priceText.toString() == null || priceText.toString().length() == 0){
							Toast toast = Toast.makeText(getContext(), "Please enter valid price!", Toast.LENGTH_SHORT);
							toast.show();
						}else{
							double price = Double.valueOf(priceText.toString());
							setPriceButton.setImageResource(R.drawable.navigation_refresh);
							setPriceButton.setActivated(false);
							FlatDataExchanger.flatData.getShopItem(position).setBoughtToday(price);
							System.out.println("Item Bought for "+ price);
						}
					}
				});
			}
		}
		final ImageButton deleteButton = (ImageButton)v.findViewById(R.id.removeButton);
		deleteButton.setImageResource(R.drawable.content_discard);
		deleteButton.setActivated(true);
		deleteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				FlatDataExchanger.flatData.getShopItem(position).deleteItem();
				deleteButton.setImageResource(R.drawable.navigation_refresh);
				deleteButton.setActivated(false);
			}
		});
		return v;
	}
}