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

package com.churchmutual.core.model.impl;

import com.churchmutual.core.model.CMICUser;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CMICUser in entity cache.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICUserCacheModel
	implements CacheModel<CMICUser>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CMICUserCacheModel)) {
			return false;
		}

		CMICUserCacheModel cmicUserCacheModel = (CMICUserCacheModel)obj;

		if (cmicUserId == cmicUserCacheModel.cmicUserId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cmicUserId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{cmicUserId=");
		sb.append(cmicUserId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CMICUser toEntityModel() {
		CMICUserImpl cmicUserImpl = new CMICUserImpl();

		cmicUserImpl.setCmicUserId(cmicUserId);
		cmicUserImpl.setUserId(userId);

		cmicUserImpl.resetOriginalValues();

		return cmicUserImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cmicUserId = objectInput.readLong();

		userId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cmicUserId);

		objectOutput.writeLong(userId);
	}

	public long cmicUserId;
	public long userId;

}