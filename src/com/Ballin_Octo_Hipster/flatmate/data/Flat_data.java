package com.Ballin_Octo_Hipster.flatmate.data;

import java.util.ArrayList;

public class Flat_data {
	
	private ArrayList<FlatMate_data> FlatMates = new ArrayList<FlatMate_data>();
	private int    FlatID;
	private String FlatName;
	private String PostCode;
	private String Address1;
	private float Lat;
	private float Long;
	
	public Flat_data(){
		FlatID = 1;
		FlatName = "Our Flat";
		PostCode = "BS8 2LG";
		Address1 = "20 Whiteladies Rd";
		Lat = 51.460291f;
		Long = -2.608701f;
			
		FlatMate_data temp_FlatMate = new FlatMate_data(1,"James Grant","07944652549",51.460291f,-2.608701f);
		FlatMates.add(temp_FlatMate);
		
		temp_FlatMate = new FlatMate_data(2,"Matt Bessey","07957151787",51.455752f,-2.602839f);
		FlatMates.add(temp_FlatMate);
		
		temp_FlatMate = new FlatMate_data(3,"Adam Coales","07539419348",51.460291f,-2.608701f);
		FlatMates.add(temp_FlatMate);
	}
	
	public ArrayList<FlatMate_data> getFlatMates(){
		return FlatMates;
	}
	
	public int getFlatMatesNo(){
		return FlatMates.size();
	}
	
	public FlatMate_data getFlatMateAtPosition(int position){
		return FlatMates.get(position);
	}
	
	public float getFlatLat(){
		return Lat;
	}
	
	public float getFlatLong(){
		return Long;
	}

	public String getFlatName() {
		return FlatName;
	}
}
