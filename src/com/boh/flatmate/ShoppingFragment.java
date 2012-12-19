package com.boh.flatmate;

import com.boh.flatmate.FlatMate.ShoppingDataExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShoppingFragment extends Fragment {
	
	private ViewGroup c;
	ShoppingListFragment shoppingList;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v1 = inflater.inflate(R.layout.shopping_page, container, false);
		c = container;
		return v1;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
		shoppingList = new ShoppingListFragment();
		ft.add(R.id.list2, shoppingList,"shopping_fragment");
		//ft.addToBackStack(null);
		ft.commit();
		ImageButton addShoppingButton = (ImageButton)c.findViewById(R.id.AddListItem);
		addShoppingButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				EditText textInput = (EditText)c.findViewById(R.id.addShoppingItem);
				Editable inputText = textInput.getText();
				if(inputText.toString() == null || inputText.toString().length() == 0){
					Toast toast = Toast.makeText(contextExchanger.context, "Please enter valid item!", Toast.LENGTH_SHORT);
					toast.show();
				}else{
					ShoppingDataExchanger.shoppingData.addItem(inputText.toString());
					System.out.println("Item Added - "+ inputText);
					textInput.setText("");
					//getFragmentManager().
					//mAdapter.notifyDataSetChanged();
				}
			}
		});
	}

}
