package com.boh.flatmate.connection;


public class Shops {
	private Results[] results;

	public Results[] getResults() {
		return results;
	}

	public void setResults(Results[] results) {
		this.results = results;
	}
	
	public Location getLocation(int i) {
		if (results[i].getGeometry().getLocation() != null) {
			return results[i].getGeometry().getLocation();
		} else return null;
	}
	
	public boolean isOpen(int i) {
		return results[i].getOpening_Hours().isOpen_now();
	}
}

class Results {
	private Geometry geometry;
	private OpeningHours opening_hours;

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
}
