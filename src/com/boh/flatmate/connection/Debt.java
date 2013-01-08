package com.boh.flatmate.connection;

public class Debt {
	private int userId;
	private int debterId;
	private double debt;
	
	public Debt (int uid, int did, double debt) {
		userId = uid;
		debterId = did;
		this.debt = debt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDebterId() {
		return debterId;
	}
	public void setDebterId(int debterId) {
		this.debterId = debterId;
	}
	public double getDebt() {
		return debt;
	}
	public void setDebt(double debt) {
		this.debt = debt;
	}
}
