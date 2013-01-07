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
import android.text.format.Time;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ShopItem shoppingItem = FlatDataExchanger.flatData.getShopItem(position);
		System.out.println(shoppingItem.getName() + " - " + shoppingItem.isBought());

		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(shoppingItem.isBought() == 1) v = vi.inflate(R.layout.shopping_row_bought, null);
		else v = vi.inflate(R.layout.shopping_row, null);

		String name = shoppingItem.getName();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		if(shoppingItem.isBought() == 1) {
			TextView priceTextView = (TextView) v.findViewById(R.id.Price);
			//System.out.println(name);
			Double priceDouble = shoppingItem.getPrice();
			String priceString = String.format("%.2f",priceDouble);
			priceTextView.setText("�"+priceString);
			TextView flatMateTextView = (TextView) v.findViewById(R.id.boughtName);

			flatMateTextView.setText(FlatDataExchanger.flatData.getUserName(shoppingItem.getUserBoughtId()));

			TextView dateTextView = (TextView) v.findViewById(R.id.dateBought);
			dateTextView.setText(shoppingItem.getUpdatedAt());
		} else {
			ImageButton setPriceButton = (ImageButton)v.findViewById(R.id.saveButton);
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
				setPriceButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View view) {
						Editable priceText = priceInput.getText();
						if(priceText.toString() == null || priceText.toString().length() == 0){
							Toast toast = Toast.makeText(getContext(), "Please enter valid price!", Toast.LENGTH_SHORT);
							toast.show();
						}else{
							double price = Double.valueOf(priceText.toString());
							Time now = new Time();
							now.setToNow();
							FlatDataExchanger.flatData.getShopItem(position).setBoughtToday(price);
							System.out.println("Item Bought for "+ price);
							notifyDataSetChanged();
						}
					}
				});
			}
		}
		return v;
	}
}