package com.churchmutual.test.harness.internal.application.list;

import com.churchmutual.test.harness.constants.TestHarnessPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true,
	property = {"panel.app.order:Integer=1", "panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_CONFIGURATION},
	service = PanelApp.class
)
public class TestHarnessPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return TestHarnessPortletKeys.TEST_HARNESS;
	}

	@Override
	@Reference(target = "(javax.portlet.name=" + TestHarnessPortletKeys.TEST_HARNESS + ")", unbind = "-")
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}