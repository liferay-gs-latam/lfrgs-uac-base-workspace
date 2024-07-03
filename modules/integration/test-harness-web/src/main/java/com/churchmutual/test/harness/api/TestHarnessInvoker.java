package com.churchmutual.test.harness.api;

import com.churchmutual.test.harness.model.HarnessDescriptor;

import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Eric Chin
 */
public interface TestHarnessInvoker {

	public List<HarnessDescriptor> getHarnessDescriptors();

	public String invoke(PortletRequest portletRequest);

}