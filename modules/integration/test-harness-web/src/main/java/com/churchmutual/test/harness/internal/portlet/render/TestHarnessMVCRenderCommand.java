package com.churchmutual.test.harness.internal.portlet.render;

import com.churchmutual.test.harness.constants.TestHarnessPortletKeys;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestHarnessPortletKeys.TEST_HARNESS, "mvc.command.name=/", "mvc.command.name=/view"
	},
	service = MVCRenderCommand.class
)
public class TestHarnessMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String resolvedModuleName = _npmResolver.resolveModuleName("test-harness/js/TestHarness.es");

		renderRequest.setAttribute("resolvedModuleName", resolvedModuleName + " as TestHarness");

		return "/view.jsp";
	}

	@Reference
	private NPMResolver _npmResolver;

}