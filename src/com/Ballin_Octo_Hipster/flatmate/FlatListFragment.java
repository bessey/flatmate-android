package com.Ballin_Octo_Hipster.flatmate;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.Ballin_Octo_Hipster.flatmate.FlatMate.FlatDataExchanger;

public class FlatListFragment extends ListFragment {

	private ListView mListView;
	private FlatMateRowAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mAdapter = new FlatMateRowAdapter(getActivity(), android.R.id.list, FlatDataExchanger.flatData.getFlatMates());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v1 = inflater.inflate(R.layout.flat_list, container, false);
		mListView = (ListView) v1.findViewById(android.R.id.list);
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setAdapter(mAdapter);
	}
}