package com.boh.flatmate;

import com.boh.flatmate.FlatMate.ConnectionExchanger;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;
import com.boh.flatmate.connection.ShopItem;
import com.boh.flatmate.connection.User;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FlatSettingsFragment extends Fragment {

	private ViewGroup c;
	private View v1;
	private FlatListFragment flatList;
	private UFlatMateRowAdapter mAdapter;
	private ListView mListView;	
	private LayoutInflater inflate;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v1 = inflater.inflate(R.layout.flat_settings, container, false);
		inflate = inflater;
		c = container;

        EditText flatNickname = (EditText) v1.findViewById(R.id.flatNickname);
        flatNickname.setText(FlatMate.FlatDataExchanger.flatData.getNickname());
        
        EditText flatPostcode = (EditText) v1.findViewById(R.id.flatPostcode);
        flatPostcode.setText(FlatMate.FlatDataExchanger.flatData.getPostcode());
        
        Button saveButton = (Button) v1.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveButtonPressed();
			}
		});

		//mAdapter = new UFlatMateRowAdapter(getActivity(), R.id.unList, FlatDataExchanger.flatData.getUnapprovedUsers());		
		//mListView = (ListView) v1.findViewById(R.id.unList);
		
		User[] users = FlatDataExchanger.flatData.getUnapprovedUsers();
		LinearLayout list = (LinearLayout)v1.findViewById(R.id.unList);
		for (int i=0; i<users.length; i++) {
		  User user = users[i];
		  View vi = inflater.inflate(R.layout.uflat_row, null);
		  String name = user.getFirst_name() +" "+ user.getLast_name();
			if (name != null) {
				TextView tt = (TextView) vi.findViewById(R.id.name);
				if (tt != null) {
					tt.setText(name);
				}
			}
			
			Button approve = (Button) vi.findViewById(R.id.approveButton);
			Button ignore = (Button) vi.findViewById(R.id.ignoreButton);
			final View approvalButtons = vi.findViewById(R.id.approvalButtons);
			final View approveSpinner = vi.findViewById(R.id.approveSpinner);
			final int flatmateId = user.getId();
			
			approve.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new approveFlatmatePressed().execute((Integer)flatmateId);
					approvalButtons.setVisibility(View.GONE);
					approveSpinner.setVisibility(View.VISIBLE);
				}
			});
			ignore.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new ignoreFlatmatePressed().execute(flatmateId);
					approvalButtons.setVisibility(View.GONE);
					approveSpinner.setVisibility(View.VISIBLE);
				}
			});
		  list.addView(vi);
		}
		
		return v1;
	}
	
	public void UpdateList(){
		User[] users = FlatDataExchanger.flatData.getUnapprovedUsers();
		LinearLayout list = (LinearLayout)v1.findViewById(R.id.unList);
		list.removeAllViews();
		for (int i=0; i<users.length; i++) {
		  User user = users[i];
		  View vi = inflate.inflate(R.layout.uflat_row, null);
		  String name = user.getFirst_name() +" "+ user.getLast_name();
			if (name != null) {
				TextView tt = (TextView) vi.findViewById(R.id.name);
				if (tt != null) {
					tt.setText(name);
				}
			}
			
			Button approve = (Button) vi.findViewById(R.id.approveButton);
			Button ignore = (Button) vi.findViewById(R.id.ignoreButton);
			final View approvalButtons = vi.findViewById(R.id.approvalButtons);
			final View approveSpinner = vi.findViewById(R.id.approveSpinner);
			final int flatmateId = user.getId();
			
			approve.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new approveFlatmatePressed().execute((Integer)flatmateId);
					approvalButtons.setVisibility(View.GONE);
					approveSpinner.setVisibility(View.VISIBLE);
				}
			});
			ignore.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new ignoreFlatmatePressed().execute(flatmateId);
					approvalButtons.setVisibility(View.GONE);
					approveSpinner.setVisibility(View.VISIBLE);
				}
			});
		  list.addView(vi);
		}
	}
	
	public class approveFlatmatePressed extends AsyncTask<Integer,Void,String> {
		protected String doInBackground(Integer... id) {
			String result = FlatMate.ConnectionExchanger.connection.approveMember(id[0]);
			FlatDataExchanger.flatData.updateData(ConnectionExchanger.connection.getMyFlat());
			return result;
		}

		protected void onPostExecute(String result) {
			UpdateList();
			if(result == "failed" || result == "invalid" || result == "connection"){
				Toast.makeText(contextExchanger.context, "An error occured, could not approve user", Toast.LENGTH_SHORT).show();
			}else Toast.makeText(contextExchanger.context, "User Approved", Toast.LENGTH_SHORT).show();
		}
	}
	
	public class ignoreFlatmatePressed extends AsyncTask<Integer,Void,String> {
		protected String doInBackground(Integer... id) {
			String result = FlatMate.ConnectionExchanger.connection.ignoreMember(id[0]);
			FlatDataExchanger.flatData.updateData(ConnectionExchanger.connection.getMyFlat());
			return result;
		}

		protected void onPostExecute(String result) {
			UpdateList();
			if(result == "failed" || result == "invalid" || result == "connection"){
				Toast.makeText(contextExchanger.context, "An error occured, could not ignore user", Toast.LENGTH_SHORT).show();
			}else Toast.makeText(contextExchanger.context, "User Ignored", Toast.LENGTH_SHORT).show();
		}
	}
	
	/*public void approveFlatmatePressed(int id){
		FlatMate.ConnectionExchanger.connection.approveMember(id);
	}
	
	public void ignoreFlatmatePressed(int id){
		FlatMate.ConnectionExchanger.connection.ignoreMember(id);
	}*/

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//mListView.setAdapter(mAdapter);
	}
	
	public void saveButtonPressed(){
		EditText flatNicknameBox = (EditText) v1.findViewById(R.id.flatNickname);
		EditText flatPostcodeBox = (EditText) v1.findViewById(R.id.flatPostcode);
		FlatMate.FlatDataExchanger.flatData.setNickname(flatNicknameBox.getText().toString());
		FlatMate.FlatDataExchanger.flatData.setPostcode(flatPostcodeBox.getText().toString());
		
		FlatMate.ConnectionExchanger.connection.updateMyFlat(FlatMate.FlatDataExchanger.flatData);
	}
}
