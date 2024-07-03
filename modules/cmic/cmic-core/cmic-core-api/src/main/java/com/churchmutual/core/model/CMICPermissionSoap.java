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

import com.churchmutual.core.service.persistence.CMICPermissionPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICPermissionServiceSoap}.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICPermissionSoap implements Serializable {

	public static CMICPermissionSoap toSoapModel(CMICPermission model) {
		CMICPermissionSoap soapModel = new CMICPermissionSoap();

		soapModel.setUserId(model.getUserId());
		soapModel.setCmicBusinessKey(model.getCmicBusinessKey());
		soapModel.setActionId(model.getActionId());

		return soapModel;
	}

	public static CMICPermissionSoap[] toSoapModels(CMICPermission[] models) {
		CMICPermissionSoap[] soapModels = new CMICPermissionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICPermissionSoap[][] toSoapModels(
		CMICPermission[][] models) {

		CMICPermissionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CMICPermissionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICPermissionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICPermissionSoap[] toSoapModels(
		List<CMICPermission> models) {

		List<CMICPermissionSoap> soapModels = new ArrayList<CMICPermissionSoap>(
			models.size());

		for (CMICPermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICPermissionSoap[soapModels.size()]);
	}

	public CMICPermissionSoap() {
	}

	public CMICPermissionPK getPrimaryKey() {
		return new CMICPermissionPK(_userId, _cmicBusinessKey, _actionId);
	}

	public void setPrimaryKey(CMICPermissionPK pk) {
		setUserId(pk.userId);
		setCmicBusinessKey(pk.cmicBusinessKey);
		setActionId(pk.actionId);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getCmicBusinessKey() {
		return _cmicBusinessKey;
	}

	public void setCmicBusinessKey(String cmicBusinessKey) {
		_cmicBusinessKey = cmicBusinessKey;
	}

	public String getActionId() {
		return _actionId;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	private long _userId;
	private String _cmicBusinessKey;
	private String _actionId;

}