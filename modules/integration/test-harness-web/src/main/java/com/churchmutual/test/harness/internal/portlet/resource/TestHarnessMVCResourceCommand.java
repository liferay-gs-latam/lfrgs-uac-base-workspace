package com.churchmutual.test.harness.internal.portlet.resource;

import com.churchmutual.test.harness.api.TestHarnessInvoker;
import com.churchmutual.test.harness.constants.TestHarnessPortletKeys;
import com.churchmutual.test.harness.helper.TestHarnessHelper;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + TestHarnessPortletKeys.TEST_HARNESS, "mvc.command.name=/invoke"},
	service = MVCResourceCommand.class
)
public class TestHarnessMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		TestHarnessInvoker invoker = _testHarnessHelper.getTestHarnessInvoker(resourceRequest);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(invoker.invoke(resourceRequest));

		JSONPortletResponseUtil.writeJSON(resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private TestHarnessHelper _testHarnessHelper;

}