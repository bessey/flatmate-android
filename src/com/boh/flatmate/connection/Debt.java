package com.boh.flatmate.connection;

public class Debt {
	private int id;
	private double debt;
	
	public Debt (int id, double debt) {
		this.id = id;
		this.debt = debt;
	}
	
	public int getId() {
		return id;
	}
	
	public void setDebterId(int id) {
		this.id = id;
	}
	
	public double getDebt() {
		return debt;
	}
	
	public void setDebt(double debt) {
		this.debt = debt;
	}
	
	public void add(double amt) {
		this.debt += amt;
	}
	
	public void sub(double amt) {
		this.debt -= amt;
	}
}
