package com.churchmutual.personal.menu.portlet.command;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.personal.menu.constants.PersonalMenuPortletKeys;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PersonalMenuPortletKeys.PERSONAL_MENU_PORTLET, "mvc.command.name=/"},
	service = MVCRenderCommand.class
)
public class PersonalMenuMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		try {
			renderRequest.setAttribute("portraitImageURL", _getPortraitImageURL(renderRequest));
			renderRequest.setAttribute("profilePageRedirect", _getProfilePageRedirect(renderRequest));
			renderRequest.setAttribute("signInRedirect", _getSignInRedirect(renderRequest));

			return "/view.jsp";
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			_log.error(e, e);

			throw new PortletException(e);
		}
	}

	private String _getPortraitImageURL(RenderRequest renderRequest) throws PortalException {
		long userId = GetterUtil.getLong(renderRequest.getAttribute(WebKeys.USER_ID));

		if (Validator.isNull(userId)) {
			return StringPool.BLANK;
		}

		return _cmicUserLocalService.getPortraitImageURL(userId);
	}

	private String _getProfilePageRedirect(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		String groupFURL = group.getFriendlyURL();

		// Only add the profile page redirect for Producer and Insured sites; Otherwise clicking the avatar icon has no action

		if (!groupFURL.equals(BusinessPortalType.PRODUCER.getFriendlyURL()) &&
			!groupFURL.equals(BusinessPortalType.INSURED.getFriendlyURL())) {

			return StringPool.BLANK;
		}

		String profilePageRedirect = _portal.getPathFriendlyURLPrivateGroup() + groupFURL + _PROFILE_PAGE_FURL;

		if (themeDisplay.isImpersonated()) {
			return profilePageRedirect + "?doAsUserId=" + HtmlUtil.escapeURL(themeDisplay.getDoAsUserId());
		}

		return profilePageRedirect;
	}

	private String _getSignInRedirect(RenderRequest renderRequest) {
		String portalURL = PortalUtil.getPortalURL(renderRequest);

		return portalURL + "/c/portal/login";
	}

	private static final String _PROFILE_PAGE_FURL = "/profile";

	private static final Log _log = LogFactoryUtil.getLog(PersonalMenuMVCRenderCommand.class);

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

	@Reference
	private Portal _portal;

}