package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface AccountWebService {

	public CMICAccountDTO getAccounts(String accountNumber) throws PortalException;

	public List<CMICAccountDTO> getAccountsSearchByProducer(String[] producerCode) throws PortalException;

	public CMICAddressDTO getAddressAccount(String accountNumber) throws PortalException;

	public CMICAddressDTO getMailingAddressAccount(String accountNumber) throws PortalException;

}