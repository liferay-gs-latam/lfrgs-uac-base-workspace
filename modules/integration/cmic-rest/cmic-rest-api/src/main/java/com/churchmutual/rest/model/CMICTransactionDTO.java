package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICTransactionDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getEventType() {
		return _eventType;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getProduct() {
		return _product;
	}

	public int getSequenceNumber() {
		return _sequenceNumber;
	}

	public String getTransactionCommitTimestamp() {
		return _transactionCommitTimestamp;
	}

	public String getTransactionEffectiveDate() {
		return _transactionEffectiveDate;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setEventType(String eventType) {
		_eventType = eventType;
	}

	public void setPolicyNumber(String policyNumber) {
		_policyNumber = policyNumber;
	}

	public void setProduct(String product) {
		_product = product;
	}

	public void setSequenceNumber(int sequenceNumber) {
		_sequenceNumber = sequenceNumber;
	}

	public void setTransactionCommitTimestamp(String transactionCommitTimestamp) {
		_transactionCommitTimestamp = transactionCommitTimestamp;
	}

	public void setTransactionEffectiveDate(String transactionEffectiveDate) {
		_transactionEffectiveDate = transactionEffectiveDate;
	}

	private String _accountNumber;
	private String _eventType;
	private String _policyNumber;
	private String _product;
	private int _sequenceNumber;
	private String _transactionCommitTimestamp;
	private String _transactionEffectiveDate;

}