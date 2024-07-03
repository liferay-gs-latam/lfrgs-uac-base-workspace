package com.churchmutual.portal.ws.commons.client.internal.executor;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequest;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutor;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutorFactory;

import org.apache.http.client.HttpClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = WebServiceRequestExecutorFactory.class)
public class DefaultWebServiceRequestExecutorFactoryImpl implements WebServiceRequestExecutorFactory {

	@Override
	public WebServiceRequestExecutor<String> createWebServiceRequestExecutor(WebServiceRequest webServiceRequest) {
		return new DefaultWebServiceRequestExecutor(httpClient, webServiceRequest);
	}

	@Reference(target = "(name=defaultHttpClient)")
	protected HttpClient httpClient;

}