package com.boh.flatmate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
		User flatMate = FlatDataExchanger.flatData.getCurrentUser();

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
		
		final ImageButton button1 = (ImageButton) v1.findViewById(R.id.button1);
		final ImageButton button2 = (ImageButton) v1.findViewById(R.id.button2);
		final ImageButton button3 = (ImageButton) v1.findViewById(R.id.button3);
		final ImageButton button4 = (ImageButton) v1.findViewById(R.id.button4);
		final ImageButton button5 = (ImageButton) v1.findViewById(R.id.button5);
		final ImageButton button6 = (ImageButton) v1.findViewById(R.id.button6);
		final ImageButton button7 = (ImageButton) v1.findViewById(R.id.button7);
		final ImageButton button8 = (ImageButton) v1.findViewById(R.id.button8);
		
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)0);
				button1.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)1);
				button2.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)2);
				button3.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)3);
				button4.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)4);
				button5.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)5);
				button6.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)6);
				button7.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)7);
				button8.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		if(flatMate.getColour_Id() == 0) {
			button1.setImageResource(R.drawable.emptytick);
		} else if(flatMate.getColour_Id() == 1) {
			button2.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 2) {
			button3.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 3) {
			button4.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 4) {
			button5.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 5) {
			button6.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 6) {
			button7.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 7) {
			button8.setImageResource(R.drawable.emptytick);
		} else {
			button8.setImageResource(R.drawable.emptytick);
		}
		
		ToggleButton toggleShops = (ToggleButton) v1.findViewById(R.id.toggleShops);
		toggleShops.setChecked(showShopsB);
		toggleShops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	showShopsB = true;
		        } else {
		        	showShopsB = false;
		        }
		        showButtonsPressed();
		    }
		});
		
		ToggleButton toggleFlatmates = (ToggleButton) v1.findViewById(R.id.toggleFlatMates);
		toggleFlatmates.setChecked(showFlatmatesB);
		toggleFlatmates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	showFlatmatesB = true;
		        } else {
		        	showFlatmatesB = false;
		        }
		        showButtonsPressed();
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

	public void UpdateColors(){
		
		User flatMate = FlatMate.FlatDataExchanger.flatData.getCurrentUser();
		
		final ImageButton button1 = (ImageButton) v1.findViewById(R.id.button1);
		final ImageButton button2 = (ImageButton) v1.findViewById(R.id.button2);
		final ImageButton button3 = (ImageButton) v1.findViewById(R.id.button3);
		final ImageButton button4 = (ImageButton) v1.findViewById(R.id.button4);
		final ImageButton button5 = (ImageButton) v1.findViewById(R.id.button5);
		final ImageButton button6 = (ImageButton) v1.findViewById(R.id.button6);
		final ImageButton button7 = (ImageButton) v1.findViewById(R.id.button7);
		final ImageButton button8 = (ImageButton) v1.findViewById(R.id.button8);
		
		button1.setImageResource(R.drawable.empty);
		button2.setImageResource(R.drawable.empty);
		button3.setImageResource(R.drawable.empty);
		button4.setImageResource(R.drawable.empty);
		button5.setImageResource(R.drawable.empty);
		button6.setImageResource(R.drawable.empty);
		button7.setImageResource(R.drawable.empty);
		button8.setImageResource(R.drawable.empty);
		
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)0);
				button1.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)1);
				button2.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)2);
				button3.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)3);
				button4.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)4);
				button5.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)5);
				button6.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)6);
				button7.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		button8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new updateColor().execute((Integer)7);
				button8.setImageResource(R.drawable.emptyrefresh);
			}
		});
		
		if(flatMate.getColour_Id() == 0) {
			button1.setImageResource(R.drawable.emptytick);
		} else if(flatMate.getColour_Id() == 1) {
			button2.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 2) {
			button3.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 3) {
			button4.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 4) {
			button5.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 5) {
			button6.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 6) {
			button7.setImageResource(R.drawable.emptytick);
		} else if (flatMate.getColour_Id() == 7) {
			button8.setImageResource(R.drawable.emptytick);
		} else {
			button8.setImageResource(R.drawable.emptytick);
		}
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
	
	public class updateColor extends AsyncTask<Integer,Void,Void> {
		protected Void doInBackground(Integer... id) {
			User me = FlatDataExchanger.flatData.getCurrentUser();
			me.setColour_Id(id[0]);
			FlatMate.ConnectionExchanger.connection.updateUser(me);
			FlatDataExchanger.flatData.updateFlatData(ConnectionExchanger.connection.getMyFlat());
			return null;
		}

		protected void onPostExecute(Void result) {
			UpdateColors();
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
		SharedPreferences settings = contextExchanger.context.getSharedPreferences("Map", 0);
		showShopsB = settings.getBoolean("showShops", true);
		showFlatmatesB = settings.getBoolean("showFlatmates", true);
	}

	public void showButtonsPressed() {
		SharedPreferences settings = contextExchanger.context.getSharedPreferences("Map", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("showShops", showShopsB);
		editor.putBoolean("showFlatmates", showFlatmatesB);
		editor.commit();
	}
}
