/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.core.model;

import com.churchmutual.core.constants.ProducerType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services
 *
 * @author Kayleen Lim
 */
public class CMICBusinessDisplaySoap implements Serializable {

	public static CMICBusinessDisplaySoap toSoapModel(CMICBusinessDisplay model) {
		CMICBusinessDisplaySoap soapModel = new CMICBusinessDisplaySoap();

		if (model instanceof CMICAccountEntryDisplay) {
			CMICAccountEntryDisplay cmicAccountEntryDisplay = (CMICAccountEntryDisplay)model;

			soapModel.setId(cmicAccountEntryDisplay.getAccountId());
			soapModel.setAccountNumber(cmicAccountEntryDisplay.getAccountNumber());
			soapModel.setCompanyNumber(cmicAccountEntryDisplay.getCompanyNumber());
			soapModel.setNumExpiredPolicies(cmicAccountEntryDisplay.getNumExpiredPolicies());
			soapModel.setNumFuturePolicies(cmicAccountEntryDisplay.getNumFuturePolicies());
			soapModel.setNumInForcePolicies(cmicAccountEntryDisplay.getNumInForcePolicies());
			soapModel.setTotalBilledPremium(cmicAccountEntryDisplay.getTotalBilledPremium());
		}

		if (model instanceof CMICOrganizationDisplay) {
			CMICOrganizationDisplay cmicOrganizationDisplay = (CMICOrganizationDisplay)model;

			soapModel.setAgentNumber(cmicOrganizationDisplay.getAgentNumber());
			soapModel.setDivisionNumber(cmicOrganizationDisplay.getDivisionNumber());
			soapModel.setProducerId(cmicOrganizationDisplay.getProducerId());
			soapModel.setProducerType(cmicOrganizationDisplay.getProducerType());
			soapModel.setActive(cmicOrganizationDisplay.isActive());
		}

		return soapModel;
	}

	public static CMICBusinessDisplaySoap[] toSoapModels(CMICBusinessDisplay[] models) {
		CMICBusinessDisplaySoap[] soapModels = new CMICBusinessDisplaySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICBusinessDisplaySoap[][] toSoapModels(CMICBusinessDisplay[][] models) {
		CMICBusinessDisplaySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CMICBusinessDisplaySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICBusinessDisplaySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICBusinessDisplaySoap[] toSoapModels(List<CMICBusinessDisplay> models) {
		List<CMICBusinessDisplaySoap> soapModels = new ArrayList<>(models.size());

		for (CMICBusinessDisplay model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICBusinessDisplaySoap[0]);
	}

	public CMICBusinessDisplaySoap() {
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public boolean getActive() {
		return _active;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public long getId() {
		return _id;
	}

	public int getNumExpiredPolicies() {
		return _numExpiredPolicies;
	}

	public int getNumFuturePolicies() {
		return _numFuturePolicies;
	}

	public int getNumInForcePolicies() {
		return _numInForcePolicies;
	}

	public long getPrimaryKey() {
		return _id;
	}

	public long getProducerId() {
		return _producerId;
	}

	public ProducerType getProducerType() {
		return _producerType;
	}

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public boolean isActive() {
		return _active;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setNumExpiredPolicies(int numExpiredPolicies) {
		_numExpiredPolicies = numExpiredPolicies;
	}

	public void setNumFuturePolicies(int numFuturePolicies) {
		_numFuturePolicies = numFuturePolicies;
	}

	public void setNumInForcePolicies(int numInForcePolicies) {
		_numInForcePolicies = numInForcePolicies;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public void setProducerType(ProducerType producerType) {
		_producerType = producerType;
	}

	public void setTotalBilledPremium(String totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private String _accountNumber;
	private boolean _active;
	private String _agentNumber;
	private String _companyNumber;
	private String _divisionNumber;
	private long _id;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private long _producerId;
	private ProducerType _producerType;
	private String _totalBilledPremium;

}