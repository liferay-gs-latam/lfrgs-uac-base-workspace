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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CMICPermission service. Represents a row in the &quot;cmic_CMICPermission&quot; database table, with each column mapped to a property of this class.
 *
 * @author Kayleen Lim
 * @see CMICPermissionModel
 * @generated
 */
@ImplementationClassName("com.churchmutual.core.model.impl.CMICPermissionImpl")
@ProviderType
public interface CMICPermission extends CMICPermissionModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.churchmutual.core.model.impl.CMICPermissionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CMICPermission, Long> USER_ID_ACCESSOR =
		new Accessor<CMICPermission, Long>() {

			@Override
			public Long get(CMICPermission cmicPermission) {
				return cmicPermission.getUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CMICPermission> getTypeClass() {
				return CMICPermission.class;
			}

		};
	public static final Accessor<CMICPermission, String>
		CMIC_BUSINESS_KEY_ACCESSOR = new Accessor<CMICPermission, String>() {

			@Override
			public String get(CMICPermission cmicPermission) {
				return cmicPermission.getCmicBusinessKey();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<CMICPermission> getTypeClass() {
				return CMICPermission.class;
			}

		};
	public static final Accessor<CMICPermission, String> ACTION_ID_ACCESSOR =
		new Accessor<CMICPermission, String>() {

			@Override
			public String get(CMICPermission cmicPermission) {
				return cmicPermission.getActionId();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<CMICPermission> getTypeClass() {
				return CMICPermission.class;
			}

		};

}