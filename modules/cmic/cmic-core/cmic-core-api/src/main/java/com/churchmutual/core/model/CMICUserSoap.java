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
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICUserServiceSoap}.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICUserSoap implements Serializable {

	public static CMICUserSoap toSoapModel(CMICUser model) {
		CMICUserSoap soapModel = new CMICUserSoap();

		soapModel.setCmicUserId(model.getCmicUserId());
		soapModel.setUserId(model.getUserId());

		return soapModel;
	}

	public static CMICUserSoap[] toSoapModels(CMICUser[] models) {
		CMICUserSoap[] soapModels = new CMICUserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICUserSoap[][] toSoapModels(CMICUser[][] models) {
		CMICUserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CMICUserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICUserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICUserSoap[] toSoapModels(List<CMICUser> models) {
		List<CMICUserSoap> soapModels = new ArrayList<CMICUserSoap>(
			models.size());

		for (CMICUser model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICUserSoap[soapModels.size()]);
	}

	public CMICUserSoap() {
	}

	public long getPrimaryKey() {
		return _cmicUserId;
	}

	public void setPrimaryKey(long pk) {
		setCmicUserId(pk);
	}

	public long getCmicUserId() {
		return _cmicUserId;
	}

	public void setCmicUserId(long cmicUserId) {
		_cmicUserId = cmicUserId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _cmicUserId;
	private long _userId;

}