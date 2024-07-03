package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface ProducerWebService {

	public List<CMICContactDTO> getContacts(long producerId) throws PortalException;

	public CMICContactDTO getPrimaryContact(long producerId) throws PortalException;

	public CMICProducerDTO getProducerById(long id) throws PortalException;

	public List<CMICProducerDTO> getProducers(String agent, String division, String name, Boolean payOutOfCdms)
		throws PortalException;

	/**
	 * Get producer role assignments (Territory Manager)
	 */
	public List<CMICRoleAssignmentDTO> getRoleAssignments(long producerId) throws PortalException;

}