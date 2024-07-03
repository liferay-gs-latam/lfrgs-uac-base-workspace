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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICAccountEntryServiceSoap}.
 *
 * @author Matthew Chan
 * @generated
 */
public class CMICAccountEntryDisplaySoap implements Serializable {

	public static CMICAccountEntryDisplaySoap toSoapModel(CMICAccountEntryDisplay model) {
		CMICAccountEntryDisplaySoap soapModel = new CMICAccountEntryDisplaySoap();

		soapModel.setId(model.getAccountId());
		soapModel.setAccountNumber(model.getAccountNumber());
		soapModel.setCompanyNumber(model.getCompanyNumber());
		soapModel.setNumExpiredPolicies(model.getNumExpiredPolicies());
		soapModel.setNumFuturePolicies(model.getNumFuturePolicies());
		soapModel.setNumInForcePolicies(model.getNumInForcePolicies());
		soapModel.setTotalBilledPremium(model.getTotalBilledPremium());

		return soapModel;
	}

	public static CMICAccountEntryDisplaySoap[] toSoapModels(
		CMICAccountEntryDisplay[] models) {

		CMICAccountEntryDisplaySoap[] soapModels =
			new CMICAccountEntryDisplaySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICAccountEntryDisplaySoap[][] toSoapModels(
		CMICAccountEntryDisplay[][] models) {

		CMICAccountEntryDisplaySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CMICAccountEntryDisplaySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICAccountEntryDisplaySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICAccountEntryDisplaySoap[] toSoapModels(
		List<CMICAccountEntryDisplay> models) {

		List<CMICAccountEntryDisplaySoap> soapModels =
			new ArrayList<CMICAccountEntryDisplaySoap>(models.size());

		for (CMICAccountEntryDisplay model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICAccountEntryDisplaySoap[soapModels.size()]);
	}

	public CMICAccountEntryDisplaySoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		_companyNumber = companyNumber;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public int getNumExpiredPolicies() {
		return _numExpiredPolicies;
	}

	public void setNumExpiredPolicies(int numExpiredPolicies) {
		_numExpiredPolicies = numExpiredPolicies;
	}

	public int getNumFuturePolicies() {
		return _numFuturePolicies;
	}

	public void setNumFuturePolicies(int numFuturePolicies) {
		_numFuturePolicies = numFuturePolicies;
	}

	public int getNumInForcePolicies() {
		return _numInForcePolicies;
	}

	public void setNumInForcePolicies(int numInForcePolicies) {
		_numInForcePolicies = numInForcePolicies;
	}

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void setTotalBilledPremium(String totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private String _accountNumber;
	private String _companyNumber;
	private long _id;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _totalBilledPremium;

}