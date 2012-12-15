package com.boh.flatmate.connection;

public class Message {
	private int from_id;
	private int to_id;		//either send to an individual and flat_id is null
	private int flat_id;	//or send to whole flat and to_id is null
	private String contents;
	private boolean received;
	private String context;		//eg, receive when get into flat, receive upon leaving flat...
	Message () {
		//do nothing
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public int getFlat_id() {
		return flat_id;
	}
	public void setFlat_id(int flat_id) {
		this.flat_id = flat_id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
