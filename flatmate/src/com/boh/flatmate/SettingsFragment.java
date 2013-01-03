package com.boh.flatmate;

import com.boh.flatmate.R;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SettingsFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.emptylayout, container, false);
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	}

