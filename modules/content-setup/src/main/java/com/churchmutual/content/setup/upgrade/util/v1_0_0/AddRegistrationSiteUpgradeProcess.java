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

package com.churchmutual.content.setup.upgrade.util.v1_0_0;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.common.ProducerUpgradeConstants;
import com.churchmutual.content.setup.upgrade.util.registration.RegistrationDeactivatedPage;
import com.churchmutual.content.setup.upgrade.util.registration.RegistrationUserRegistrationPage;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Kayleen Lim
 */
public class AddRegistrationSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	public AddRegistrationSiteUpgradeProcess(
			AssetEntryLocalService assetEntryLocalService, CMICUserLocalService cmicUserLocalService,
			CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService,
			DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService, DLFileEntryLocalService dlFileEntryLocalService,
			DLFileEntryTypeLocalService dlFileEntryTypeLocalService, ExpandoColumnLocalService expandoColumnLocalService,
			ExpandoTableLocalService expandoTableLocalService, GroupLocalService groupLocalService,
			JournalArticleLocalService journalArticleLocalService, LayoutSetLocalService layoutSetLocalService,
			PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
			StorageEngineManager storageEngineManager, UserLocalService userLocalService,
			VirtualHostLocalService virtualHostLocalService) {

		super(
			assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService,
			ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService, expandoColumnLocalService,
			expandoTableLocalService, groupLocalService, journalArticleLocalService, layoutSetLocalService,
			permissionCheckerFactory, portal, roleLocalService, storageEngineManager, userLocalService,
			virtualHostLocalService);

		this.portal = portal;
		this.userLocalService = userLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long groupId = addOrUpdatePortalSite(
			companyId, CMICSite.REGISTRATION.getName(), CMICSite.REGISTRATION.getFriendlyURL(),
			LayoutURLKeyConstants.THEME_ID_CMIC_INSURED);

		long userId = userLocalService.getDefaultUserId(companyId);

		addJournalArticle(groupId, ProducerUpgradeConstants.DEACTIVATED_WEB_CONTENT_TITLE);

		_addPrivatePages(companyId, userId, groupId);
	}

	protected Portal portal;
	protected UserLocalService userLocalService;

	private void _addPrivatePages(long companyId, long userId, long groupId) throws Exception {
		RegistrationUserRegistrationPage.addPage(companyId, userId, groupId);
		RegistrationDeactivatedPage.addPage(companyId, userId, groupId);
	}

}