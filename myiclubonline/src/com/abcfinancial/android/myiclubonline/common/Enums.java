package com.abcfinancial.android.myiclubonline.common;

public class Enums {
	public enum RequestMethod {
		GET, POST
	};

	public enum PaymentMethods {
		CC("Credit Card"), EFT("EFT");

		private String name;

		PaymentMethods(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	};

	public enum PaymentActions {
		MAKE_PAYMENT, UPDATE_PAYMENT;
	};

	public enum SearchTypes {
		PAYMENT(0), PURCHASE(1), CHECK_IN(2);

		private int id;

		SearchTypes(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	};
}
