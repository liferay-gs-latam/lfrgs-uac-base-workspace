package com.churchmutual.portal.ws.commons.client.executor;

/**
 * @author Michael C. Han
 */
public interface WebServiceRequestExecutorFactory {

	public WebServiceRequestExecutor<String> createWebServiceRequestExecutor(WebServiceRequest webServiceRequest);

}