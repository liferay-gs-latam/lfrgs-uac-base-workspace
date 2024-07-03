package com.churchmutual.theme.context.contributor;

import com.churchmutual.core.service.CMICAnnouncementAndNotificationLocalService;
import com.churchmutual.core.service.CMICUserLocalService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "type=" + TemplateContextContributor.TYPE_THEME,
	service = TemplateContextContributor.class
)
public class CMICThemeContextContributor implements TemplateContextContributor {

	@Override
	public void prepare(Map<String, Object> contextObjects, HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		try {
			long companyId = themeDisplay.getCompanyId();

			long defaultUserId = _userLocalService.getDefaultUserId(companyId);

			if (defaultUserId == themeDisplay.getUserId()) {
				return;
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return;
		}

		try {
			contextObjects.put("deactivated", _cmicUserLocalService.isDeactivated(themeDisplay.getUserId()));
		}
		catch (PortalException pe) {
			contextObjects.put("deactivated", false);

			_log.error(pe, pe);
		}

		try {
			contextObjects.put(
				"notification_and_announcements_count",
				_cmicAnnouncementAndNotificationsLocalService.getAnnouncementsAndNotificationsCount(
					themeDisplay.getUserId()));
		}
		catch (PortalException pe) {
			contextObjects.put("notification_and_announcements_count", 0);

			_log.error(pe, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICThemeContextContributor.class);

	@Reference
	private CMICAnnouncementAndNotificationLocalService _cmicAnnouncementAndNotificationsLocalService;

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

	@Reference
	private UserLocalService _userLocalService;

}