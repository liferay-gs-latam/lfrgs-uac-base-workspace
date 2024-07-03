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
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICOrganizationServiceSoap}.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICOrganizationDisplaySoap implements Serializable {

	public static CMICOrganizationDisplaySoap toSoapModel(CMICOrganizationDisplay model) {
		CMICOrganizationDisplaySoap soapModel = new CMICOrganizationDisplaySoap();

		soapModel.setAgentNumber(model.getAgentNumber());
		soapModel.setDivisionNumber(model.getDivisionNumber());
		soapModel.setProducerId(model.getProducerId());
		soapModel.setProducerType(model.getProducerType());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static CMICOrganizationDisplaySoap[] toSoapModels(
		CMICOrganizationDisplay[] models) {

		CMICOrganizationDisplaySoap[] soapModels =
			new CMICOrganizationDisplaySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICOrganizationDisplaySoap[][] toSoapModels(
		CMICOrganizationDisplay[][] models) {

		CMICOrganizationDisplaySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CMICOrganizationDisplaySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICOrganizationDisplaySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICOrganizationDisplaySoap[] toSoapModels(
		List<CMICOrganizationDisplay> models) {

		List<CMICOrganizationDisplaySoap> soapModels =
			new ArrayList<CMICOrganizationDisplaySoap>(models.size());

		for (CMICOrganizationDisplay model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICOrganizationDisplaySoap[soapModels.size()]);
	}

	public CMICOrganizationDisplaySoap() {
	}

	public long getPrimaryKey() {
		return _producerId;
	}

	public void setPrimaryKey(long pk) {
		setProducerId(pk);
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public ProducerType getProducerType() {
		return _producerType;
	}

	public void setProducerType(ProducerType producerType) {
		_producerType = producerType;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private String _agentNumber;
	private String _divisionNumber;
	private long _producerId;
	private ProducerType _producerType;
	private boolean _active;

}