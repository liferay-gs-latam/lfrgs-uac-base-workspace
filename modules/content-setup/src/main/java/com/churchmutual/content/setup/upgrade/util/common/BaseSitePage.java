package com.churchmutual.content.setup.upgrade.util.common;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

public class BaseSitePage {

	public static void setAssetPublisher(
			long companyId, long plid, String portletId, String dlFileTypeKey, String ddmTemplateKey,
			String ddmStructureFieldName, String ddmStructureFieldValue)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
			layout, portletId, StringPool.BLANK);

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(DLFileEntry.class);

		portletPreferences.setValue("anyAssetType", String.valueOf(classNameId));
		portletPreferences.setValue("classNameIds", String.valueOf(classNameId));

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		Group globalGroup = company.getGroup();

		long globalGroupId = globalGroup.getGroupId();

		DLFileEntryType fileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(globalGroupId, dlFileTypeKey);

		long fileEntryTypeId = fileEntryType.getFileEntryTypeId();

		portletPreferences.setValue("anyClassTypeDLFileEntryAssetRendererFactory", String.valueOf(fileEntryTypeId));
		portletPreferences.setValue("classTypeIds", String.valueOf(fileEntryTypeId));

		portletPreferences.setValue("paginationType", "regular");
		portletPreferences.setValue("delta", "10");
		portletPreferences.setValue("displayStyle", String.format("ddmTemplate_%s", ddmTemplateKey));
		portletPreferences.setValue("displayStyleGroupId", String.valueOf(globalGroupId));
		portletPreferences.setValue("showAddContentButton", Boolean.FALSE.toString());

		if (!Validator.isBlank(ddmStructureFieldName) && !Validator.isBlank(ddmStructureFieldValue)) {
			portletPreferences.setValue("anyAssetType", DLFileEntry.class.getName());
			portletPreferences.setValue("subtypeFieldsFilterEnabled", Boolean.TRUE.toString());
			portletPreferences.setValue("subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory", Boolean.TRUE.toString());
			portletPreferences.setValue("subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory", Boolean.TRUE.toString());
			portletPreferences.setValue("ddmStructureFieldName", ddmStructureFieldName);
			portletPreferences.setValues("ddmStructureFieldValue", String.format("[\"%s\"]", ddmStructureFieldValue));
			portletPreferences.setValue("ddmStructureDisplayFieldValue", ddmStructureFieldValue);
		}

		portletPreferences.store();
	}

	public static void setJournalArticle(long groupId, long plid, String portletId, String title) throws Exception {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
			layout, portletId, StringPool.BLANK);

		portletPreferences.setValue("groupId", String.valueOf(groupId));

		JournalArticle article = JournalArticleLocalServiceUtil.getArticleByUrlTitle(groupId, title);

		portletPreferences.setValue("articleId", article.getArticleId());

		portletPreferences.store();
	}

}