package com.churchmutual.content.setup.upgrade.util.insured;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.util.common.BaseSitePage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;

public class InsuredResourcesPage extends BaseSitePage {

	public static final String[][] RESOURCE_SUBPAGES = {
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_ALICE, "Alice", "alice"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_SEC, "SEC", "sec"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_INSIGHTWW, "Insight Worldwide", "insightww"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_MINISTRYSAFE, "MinistrySafe", "ministrysafe"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_TRUSTED_EMPLOYEES, "Trusted Employees", "trusted-employees"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_RELIAS, "Relias", "relias"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_MEDCOR, "Medcor", "medcor"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_BEAZLEY, "Beazley", "beazley"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_IBHS, "IBHS", "ibhs"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_ROADMASTER, "Roadmaster", "roadmaster"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_AEDSUPERSTORE, "AEDSuperstore", "aedsuperstore"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_SAFELYYOU, "SafelyYou", "safelyyou"},
		{LayoutURLKeyConstants.LAYOUT_FURL_INSURED_CONCENTRA, "Concentra", "concentra"}
	};

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		Layout layout = LayoutHelper.addLayout(
			userId, groupId, 0, "Resources", "Resources", true, false,
			LayoutURLKeyConstants.LAYOUT_FURL_INSURED_RESOURCES, LayoutConstants.TYPE_CONTENT);

		_publishLayout(layout);

		for (String[] resourceSubpage : RESOURCE_SUBPAGES) {
			Layout childLayout = LayoutHelper.addLayout(
				userId, groupId, layout.getLayoutId(), resourceSubpage[1], resourceSubpage[1], true, false,
				resourceSubpage[0], LayoutConstants.TYPE_CONTENT);

			_publishLayout(childLayout);
		}
	}

	private static Layout _publishLayout(Layout layout) throws PortalException {
		UnicodeProperties props = layout.getTypeSettingsProperties();

		props.setProperty("published", "true");

		layout = LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), props.toString());

		// Force publishDate != createDate

		Date publishDate = layout.getPublishDate();

		layout.setPublishDate(new Date(publishDate.getTime() + 1000));

		return LayoutLocalServiceUtil.updateLayout(layout);
	}

}