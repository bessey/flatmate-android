package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.ShoppingDataExchanger;
import com.boh.flatmate.data.ShoppingItem_data;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListFragment extends ListFragment {

	private ViewGroup c;
	private ListView mListView;
	private ShoppingRowAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mAdapter = new ShoppingRowAdapter(getActivity(), android.R.id.list, ShoppingDataExchanger.shoppingData.getShoppingList());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v1 = inflater.inflate(R.layout.shopping_list, container, false);
		mListView = (ListView) v1.findViewById(android.R.id.list);
		c = container;
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setAdapter(mAdapter);
		ImageButton addShoppingButton = (ImageButton)c.findViewById(R.id.AddListItem);
		addShoppingButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				EditText textInput = (EditText)c.findViewById(R.id.addShoppingItem);
				Editable inputText = textInput.getText();
				//if(priceText.toString() == null || priceText.toString().length() == 0){
					//Toast toast = Toast.makeText(getContext(), "Please enter valid item!", Toast.LENGTH_SHORT);
					//toast.show();
				//}else{
					//ShoppingDataExchanger.shoppingData.addItem("test");
					System.out.println("Item Added - "+ inputText);
					textInput.setText("");
					mAdapter.notifyDataSetChanged();
				//}
			}
		});
	}

}
