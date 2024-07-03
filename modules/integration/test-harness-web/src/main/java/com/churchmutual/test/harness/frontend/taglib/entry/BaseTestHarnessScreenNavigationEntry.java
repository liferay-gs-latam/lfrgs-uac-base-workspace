package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.test.harness.api.TestHarnessInvoker;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eric Chin
 */
public abstract class BaseTestHarnessScreenNavigationEntry
	implements ScreenNavigationEntry<HarnessDescriptor>, TestHarnessInvoker {

	public void doRender(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	}

	public abstract JSPRenderer getJSPRenderer();

	@Override
	public void render(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {

		doRender(httpServletRequest, httpServletResponse);

		httpServletRequest.setAttribute("screenNavigationCategoryKey", getCategoryKey());

		httpServletRequest.setAttribute("screenNavigationEntryKey", getEntryKey());

		httpServletRequest.setAttribute("harnessDescriptors", getHarnessDescriptors());

		getJSPRenderer().renderJSP(httpServletRequest, httpServletResponse, "/api/view.jsp");
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());

		return new AggregateResourceBundle(resourceBundle, PortalUtil.getResourceBundle(locale));
	}

}