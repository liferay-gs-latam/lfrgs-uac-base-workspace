package com.churchmutual.test.harness.internal.portlet;

import com.churchmutual.test.harness.constants.TestHarnessPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=test-harness-web",
		"com.liferay.portlet.display-category=category.hidden", "com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false", "javax.portlet.display-name=Test Harness",
		"javax.portlet.init-param.template-path=/", "javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + TestHarnessPortletKeys.TEST_HARNESS, "javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TestHarnessPortlet extends MVCPortlet {
}