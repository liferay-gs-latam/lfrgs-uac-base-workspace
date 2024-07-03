package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICCompanyDTO;
import com.churchmutual.rest.model.CMICEventTypeDTO;
import com.churchmutual.rest.model.CMICProductDTO;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public interface EnterpriseReferenceValuesWebService {

	public List<CMICCompanyDTO> getCompanies(boolean includeInactive) throws PortalException;

	public List<CMICEventTypeDTO> getEventTypes(boolean includeInactive) throws PortalException;

	public List<CMICProductDTO> getProducts(boolean includeInactive) throws PortalException;

}
