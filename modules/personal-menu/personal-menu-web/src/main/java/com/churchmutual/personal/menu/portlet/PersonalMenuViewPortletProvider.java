package com.churchmutual.personal.menu.portlet;

import com.liferay.admin.kernel.util.PortalUserPersonalBarApplicationType;
import com.churchmutual.personal.menu.constants.PersonalMenuPortletKeys;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=" + PortalUserPersonalBarApplicationType.UserPersonalBar.CLASS_NAME,
		"service.ranking:Integer=10"
	},
	service = ViewPortletProvider.class
)
public class PersonalMenuViewPortletProvider extends BasePortletProvider implements ViewPortletProvider {

	@Override
	public String getPortletName() {
		return PersonalMenuPortletKeys.PERSONAL_MENU_PORTLET;
	}

}