package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;

public class CMICPolicyDocumentDisplay {

	public CMICPolicyDocumentDisplay(CMICPolicyDocumentDTO cmicPolicyDocumentDTO) {
		_bytes = cmicPolicyDocumentDTO.getBytes();
		_mimeType = "application/pdf";
		_name = cmicPolicyDocumentDTO.getFileName();
	}

	public String getBytes() {
		return _bytes;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getName() {
		return _name;
	}

	private String _bytes;
	private String _mimeType;
	private String _name;

}