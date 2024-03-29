package com.boh.flatmate.connection;

public class Results {
	private Geometry geometry;
	private OpeningHours opening_hours;
	private String name;
	
	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	public OpeningHours getOpening_Hours() {
		return opening_hours;
	}
	
	public void setOpening_Hours(OpeningHours oh) {
		opening_hours = oh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}