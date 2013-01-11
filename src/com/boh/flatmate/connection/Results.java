package com.boh.flatmate.connection;

public class Results {
	private Geometry geometry;
	private OpeningHours opening_hours;
	private Name name;
	
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

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}