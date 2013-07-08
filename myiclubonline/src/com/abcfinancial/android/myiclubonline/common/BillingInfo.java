package com.abcfinancial.android.myiclubonline.common;

public class BillingInfo {
	private String club;
	private String memberNumber;
	private String firstName;
	private String lastName;
	private Enums.PaymentMethods paymentMethod;
	private String bankName;
	private String bankAccountNumber;
	private Enums.BankAccountTypes bankAccountType;
	private String bankRoutingNumber;
	private String creditCardNumber;
	private Enums.CreditCardTypes creditCardType;
	private String creditCardExpMonth;
	private String creditCardExpYear;
	private String nextDueDate;
	private String nextPaymentAmount;
	private String lateFeeAmount;
	private String latePaymentAmount;
	private String totalPastDue;

	public BillingInfo() {
	}

	public BillingInfo(String inlineData) {
		String[] list = inlineData.split(",");
		this.club = list[0];
		this.memberNumber = list[0];
		this.firstName = list[2];
		this.lastName = list[3];
		this.paymentMethod = Enums.PaymentMethods.fromName(list[4]);
		this.bankAccountNumber = list[5];
		this.bankAccountType = Enums.BankAccountTypes.fromName(list[6]);
		this.bankRoutingNumber = list[7];
		this.bankName = list[8];
		this.creditCardNumber = list[9];
		this.creditCardType = Enums.CreditCardTypes.fromName(list[10]);
		this.creditCardExpMonth = list[11];
		this.creditCardExpYear = list[12];
		this.nextDueDate = list[13];
		this.nextPaymentAmount = list[14];
		this.lateFeeAmount = list[15];
		this.latePaymentAmount = list[16];
		this.totalPastDue = list[17];
	}

	public String toString() {
		return club + "," + memberNumber + "," + firstName + "," + lastName
				+ "," + paymentMethod.getName() + "," + bankAccountNumber + ","
				+ bankAccountType.getName() + "," + bankRoutingNumber + ","
				+ bankName + "," + creditCardNumber + ","
				+ creditCardType.getName() + "," + creditCardExpMonth + ","
				+ creditCardExpYear + "," + nextDueDate + ","
				+ nextPaymentAmount + "," + lateFeeAmount + ","
				+ latePaymentAmount + "," + totalPastDue;
	}

	public String getCreditCardExpirationForDisplay() {
		return creditCardExpMonth + "/" + creditCardExpYear;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}

	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardExpMonth() {
		return creditCardExpMonth;
	}

	public void setCreditCardExpMonth(String creditCardExpMonth) {
		this.creditCardExpMonth = creditCardExpMonth;
	}

	public String getCreditCardExpYear() {
		return creditCardExpYear;
	}

	public void setCreditCardExpYear(String creditCardExpYear) {
		this.creditCardExpYear = creditCardExpYear;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getNextPaymentAmount() {
		return nextPaymentAmount;
	}

	public void setNextPaymentAmount(String nextPaymentAmount) {
		this.nextPaymentAmount = nextPaymentAmount;
	}

	public String getLateFeeAmount() {
		return lateFeeAmount;
	}

	public void setLateFeeAmount(String lateFeeAmount) {
		this.lateFeeAmount = lateFeeAmount;
	}

	public String getLatePaymentAmount() {
		return latePaymentAmount;
	}

	public void setLatePaymentAmount(String latePaymentAmount) {
		this.latePaymentAmount = latePaymentAmount;
	}

	public String getTotalPastDue() {
		return totalPastDue;
	}

	public void setTotalPastDue(String totalPastDue) {
		this.totalPastDue = totalPastDue;
	}

	public Enums.PaymentMethods getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Enums.PaymentMethods paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Enums.BankAccountTypes getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Enums.BankAccountTypes bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public Enums.CreditCardTypes getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(Enums.CreditCardTypes creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}