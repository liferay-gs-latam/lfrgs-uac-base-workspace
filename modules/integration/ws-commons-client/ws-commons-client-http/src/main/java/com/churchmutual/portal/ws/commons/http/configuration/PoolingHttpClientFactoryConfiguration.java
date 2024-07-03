package com.churchmutual.portal.ws.commons.http.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.portal.ws.commons.http.configuration.PoolingHttpClientFactoryConfiguration",
	localization = "content/Language", name = "pooling-http-client-factory"
)
public interface PoolingHttpClientFactoryConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean contentCompressionEnabled();

	@Meta.AD(deflt = "2000", required = false)
	public int connectionRequestTimeout();

	@Meta.AD(deflt = "50", required = false)
	public int defaultMaxConnectionsPerRoute();

	@Meta.AD(deflt = "300", required = false)
	public int maxTotalConnections();

}