package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface CommissionDocumentWebService {

	public List<CMICFileDTO> downloadDocuments(String[] ids, boolean includeBytes) throws PortalException;

	public List<CMICCommissionDocumentDTO> searchDocuments(
			String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
			String minimumStatementDate)
		throws PortalException;

}