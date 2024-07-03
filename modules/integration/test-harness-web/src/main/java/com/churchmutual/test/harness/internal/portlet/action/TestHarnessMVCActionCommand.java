package com.churchmutual.test.harness.internal.portlet.action;

import com.churchmutual.test.harness.api.TestHarnessInvoker;
import com.churchmutual.test.harness.constants.TestHarnessPortletKeys;
import com.churchmutual.test.harness.helper.TestHarnessHelper;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationRegistry;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + TestHarnessPortletKeys.TEST_HARNESS,
		"mvc.command.name=" + TestHarnessMVCActionCommand.MVC_COMMAND_NAME
	},
	service = MVCActionCommand.class
)
public class TestHarnessMVCActionCommand extends BaseMVCActionCommand {

	public static final String MVC_COMMAND_NAME = "invoke";

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		TestHarnessInvoker invoker = _testHarnessHelper.getTestHarnessInvoker(actionRequest);

		String response = invoker.invoke(actionRequest);

		actionRequest.setAttribute("response", response);
	}

	@Reference
	private ScreenNavigationRegistry _screenNavigationRegistry;

	@Reference
	private TestHarnessHelper _testHarnessHelper;

}