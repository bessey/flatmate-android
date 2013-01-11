package com.boh.flatmate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.boh.flatmate.FlatMate.ConnectionExchanger;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;
import com.boh.flatmate.connection.User;

import android.content.Context;
import android.content.SharedPreferences;
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
	private boolean showShopsB = true;
	private boolean showFlatmatesB = true;

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
		pointerDisplayUpdate();

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

		/*Button showShops = (Button) v1.findViewById(R.id.showShopsButton);
		showShops.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showShopsB = !showShopsB;
				showButtonsPressed();
			}
		});

		Button showFlatmates = (Button) v1.findViewById(R.id.showFlatmatesButton);
		showFlatmates.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showShopsB = !showShopsB;
				showButtonsPressed();
			}
		});*/

		//mAdapter = new UFlatMateRowAdapter(getActivity(), R.id.unList, FlatDataExchanger.flatData.getUnapprovedUsers());		
		//mListView = (ListView) v1.findViewById(R.id.unList);

		User[] users = FlatDataExchanger.flatData.getUnapprovedUsers();
		if(users.length > 0){
			TextView t = (TextView) v1.findViewById(R.id.noPendingMembers);
			t.setVisibility(View.GONE);
		}
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
			FlatDataExchanger.flatData.updateFlatData(ConnectionExchanger.connection.getMyFlat());
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
			FlatDataExchanger.flatData.updateFlatData(ConnectionExchanger.connection.getMyFlat());
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

	public void pointerDisplayUpdate() {
		File file = new File("displayPointers");
		FileInputStream fis = null;
		String result = "";
		try {
			fis = new FileInputStream(file);
			// if file doesnt exists, then stop
			if (file.exists()) {
				int i;
				while ((i = fis.read()) != -1) {
					result += Integer.toString(i);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		String[] results = result.split(",");
		showShopsB = Boolean.parseBoolean(results[0]);
		showFlatmatesB = Boolean.parseBoolean(results[1]);
	}

	public void showButtonsPressed() {
		//TODO !boolean for show shops on map
		FileOutputStream fos = null;
		File file;
		String content = showShopsB + "," + showFlatmatesB;
		boolean read = true;
		try {
			file = new File("displayPointers");
			fos = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
			fos.write(contentInBytes);
			fos.flush();
			fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
