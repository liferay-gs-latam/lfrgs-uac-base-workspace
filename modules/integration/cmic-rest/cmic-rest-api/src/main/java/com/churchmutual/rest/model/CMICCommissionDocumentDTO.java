package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICCommissionDocumentDTO extends CMICObjectDTO {

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getDocumentType() {
		return _documentType;
	}

	public String getId() {
		return _id;
	}

	public String getStatementDate() {
		return _statementDate;
	}

	public String getStatementId() {
		return _statementId;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public void setDocumentType(String documentType) {
		_documentType = documentType;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setStatementDate(String statementDate) {
		_statementDate = statementDate;
	}

	public void setStatementId(String statementId) {
		_statementId = statementId;
	}

	private String _agentNumber;
	private String _divisionNumber;
	private String _documentType;
	private String _id;
	private String _statementDate;
	private String _statementId;

}