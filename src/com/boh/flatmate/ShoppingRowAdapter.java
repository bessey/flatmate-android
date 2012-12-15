package com.boh.flatmate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.boh.flatmate.R;
import com.boh.flatmate.data.ShoppingItem_data;

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

public class ShoppingRowAdapter extends ArrayAdapter<ShoppingItem_data> {

	private Context context;
	private ArrayList<ShoppingItem_data> ShoppingData;
	private int BoxWithFocus = -1;

	public ShoppingRowAdapter(Context c, int textViewResourceId, ArrayList<ShoppingItem_data> data) {
		super(c, textViewResourceId, data);
		ShoppingData = data;
		context = c;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final ShoppingItem_data shoppingItem = ShoppingData.get(position);
		if (v == null || shoppingItem.isBought() == 1) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if(shoppingItem.isBought() == 1) v = vi.inflate(R.layout.shopping_row_bought, null);
			else v = vi.inflate(R.layout.shopping_row, null);
		}		
		String name = shoppingItem.itemName();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		if(shoppingItem.isBought() == 1){
			TextView priceTextView = (TextView) v.findViewById(R.id.Price);
			System.out.println(name);
			Double priceDouble = shoppingItem.itemPrice();
			String priceString = String.format("%.2f",priceDouble);
			priceTextView.setText("£"+priceString);
			TextView flatMateTextView = (TextView) v.findViewById(R.id.boughtName);
			
			// TODO flatmate name get()
			flatMateTextView.setText("James Grant");
			
			TextView dateTextView = (TextView) v.findViewById(R.id.dateBought);
			dateTextView.setText(shoppingItem.itemDate());
		}else{
			ImageButton setPriceButton = (ImageButton)v.findViewById(R.id.saveButton);
			EditText priceInput = (EditText)v.findViewById(R.id.priceInput);
			final Editable priceText = priceInput.getText();
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
			setPriceButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {
						if(priceText.toString() == null || priceText.toString().length() == 0){
							Toast toast = Toast.makeText(getContext(), "Please enter valid price!", Toast.LENGTH_SHORT);
							toast.show();
						}else{
							double price = Double.valueOf(priceText.toString());
							shoppingItem.boughtToday(price);
							System.out.println("Item Bought for "+ price);
							notifyDataSetChanged();
						}
				}
			});
		}
		return v;
	}
}