package com.abcfinancial.android.myiclubonline.common;

public class Enums {
	public enum RequestMethod {
		GET, POST
	}

	public enum PaymentMethods {
		CC("CreditCard"), EFT("EFT");

		private String name;

		PaymentMethods(String name) {
			this.name = name;
		}

		public static PaymentMethods fromName(String name) {
			for (PaymentMethods method : PaymentMethods.values()) {
		        if (method.name.equalsIgnoreCase(name)) {
		            return method;
		        }
		    }
		    return null;			
		}		
		
		public String getName() {
			return name;
		}
	}

	public enum PaymentActions {
		MAKE_PAYMENT, UPDATE_PAYMENT;
	}

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

	public enum BankAccountTypes {
		CHECKING("Checking", 0), SAVINGS("Savings", 1);

		private String name;
		private int order;

		BankAccountTypes(String name, int order) {
			this.name = name;
			this.order = order;
		}

		public static BankAccountTypes fromName(String name) {
			for (BankAccountTypes type : BankAccountTypes.values()) {
		        if (type.name.equalsIgnoreCase(name)) {
		            return type;
		        }
		    }
		    return null;			
		}		
		
		public String getName() {
			return name;
		}
		
		public int getOrder() {
			return order;
		}
	}
	
	public enum CreditCardTypes {
		VISA("Visa"), MASTERCARD("Master Card"), AMERICAN_EXPRESS("American Express"), DISCOVERY("Discovery");
		private String name;

		CreditCardTypes(String name) {
			this.name = name;
		}

		public static CreditCardTypes fromName(String name) {
			for (CreditCardTypes type : CreditCardTypes.values()) {
		        if (type.name.equalsIgnoreCase(name)) {
		            return type;
		        }
		    }
		    return null;			
		}
		
		public String getName() {
			return name;
		}		
	}	
}
