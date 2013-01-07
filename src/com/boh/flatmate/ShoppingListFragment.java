package com.boh.flatmate;

import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.R;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShoppingListFragment extends ListFragment {

	@SuppressWarnings("unused")
	private ViewGroup c;
	private ListView mListView;
	public static ShoppingRowAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mAdapter = new ShoppingRowAdapter(getActivity(), android.R.id.list, FlatDataExchanger.flatData.getShopItems());
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
	}

}
