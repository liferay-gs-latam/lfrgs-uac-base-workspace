package com.churchmutual.portal.ws.commons.client.executor;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michael C. Han
 */
public interface WebServiceRequestExecutor<T> {

	public T execute() throws PortalException;

}