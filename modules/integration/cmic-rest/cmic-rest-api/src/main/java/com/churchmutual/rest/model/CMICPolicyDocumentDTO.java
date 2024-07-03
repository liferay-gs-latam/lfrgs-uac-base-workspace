package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICPolicyDocumentDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getBytes() {
		return _bytes;
	}

	public String getComment() {
		return _comment;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getItemId() {
		return _itemId;
	}

	public String getItemName() {
		return _itemName;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getPolicyType() {
		return _policyType;
	}

	public String getSequenceNumber() {
		return _sequenceNumber;
	}

	public String getUrl() {
		return _url;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setBytes(String bytes) {
		_bytes = bytes;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public void setItemName(String itemName) {
		_itemName = itemName;
	}

	public void setPolicyNumber(String policyNumber) {
		_policyNumber = policyNumber;
	}

	public void setPolicyType(String policyType) {
		_policyType = policyType;
	}

	public void setSequenceNumber(String sequenceNumber) {
		_sequenceNumber = sequenceNumber;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _accountNumber;
	private String _bytes;
	private String _comment;
	private String _fileName;
	private String _itemId;
	private String _itemName;
	private String _policyNumber;
	private String _policyType;
	private String _sequenceNumber;
	private String _url;

}