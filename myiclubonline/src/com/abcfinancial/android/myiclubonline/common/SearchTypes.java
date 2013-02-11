package com.abcfinancial.android.myiclubonline.common;

public enum SearchTypes {
	PAYMENT(0), PURCHASE(1), CHECK_IN(2);
	
	private int id;

	SearchTypes(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
