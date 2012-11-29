package com.Ballin_Octo_Hipster.flatmate;

import com.Ballin_Octo_Hipster.flatmate.FlatMate.ShoppingDataExchanger;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShoppingListFragment extends ListFragment {

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
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setAdapter(mAdapter);
	}

}
