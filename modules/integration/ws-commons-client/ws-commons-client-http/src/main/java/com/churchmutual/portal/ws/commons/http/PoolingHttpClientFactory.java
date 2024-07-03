package com.churchmutual.portal.ws.commons.http;

import com.churchmutual.portal.ws.commons.http.configuration.PoolingHttpClientFactoryConfiguration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.churchmutual.portal.ws.commons.http.configuration.PoolingHttpClientFactoryConfiguration",
	immediate = true, service = PoolingHttpClientFactory.class
)
public class PoolingHttpClientFactory {

	@Activate
	protected synchronized void activate(BundleContext bundleContext, Map<String, Object> properties) {
		PoolingHttpClientFactoryConfiguration poolingHttpClientFactoryConfiguration =
			ConfigurableUtil.createConfigurable(PoolingHttpClientFactoryConfiguration.class, properties);

		_poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

		_poolingHttpClientConnectionManager.setDefaultMaxPerRoute(
			poolingHttpClientFactoryConfiguration.defaultMaxConnectionsPerRoute());

		_poolingHttpClientConnectionManager.setMaxTotal(poolingHttpClientFactoryConfiguration.maxTotalConnections());

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		httpClientBuilder.setConnectionManager(_poolingHttpClientConnectionManager);

		RequestConfig requestConfig = RequestConfig.copy(
			RequestConfig.DEFAULT
		).setConnectionRequestTimeout(
			poolingHttpClientFactoryConfiguration.connectionRequestTimeout()
		).build();

		httpClientBuilder.setDefaultRequestConfig(requestConfig);

		if (!poolingHttpClientFactoryConfiguration.contentCompressionEnabled()) {
			httpClientBuilder.disableContentCompression();
		}

		_closeableHttpClient = httpClientBuilder.build();

		Dictionary<String, String> httpClientProperties = new HashMapDictionary<>();

		httpClientProperties.put("name", "defaultHttpClient");

		_httpClientServiceRegistration = bundleContext.registerService(
			HttpClient.class, _closeableHttpClient, httpClientProperties);
	}

	@Deactivate
	protected synchronized void deactivate() {
		if (_log.isDebugEnabled()) {
			_log.debug("Shut down");
		}

		if (_httpClientServiceRegistration != null) {
			_httpClientServiceRegistration.unregister();
		}

		if (_poolingHttpClientConnectionManager == null) {
			return;
		}

		int retry = 0;

		while (retry < _MAX_RETRIES) {
			PoolStats poolStats = _poolingHttpClientConnectionManager.getTotalStats();

			int availableConnections = poolStats.getAvailable();

			if (availableConnections <= 0) {
				break;
			}

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(_DEBUG_LOG_SIZE);

				sb.append(toString());
				sb.append(" is waiting on ");
				sb.append(String.valueOf(availableConnections));
				sb.append(" connections");

				_log.debug(sb.toString());
			}

			_poolingHttpClientConnectionManager.closeIdleConnections(
				_CONNECTION_MANAGER_CLOSE_TIMEOUT, TimeUnit.MILLISECONDS);

			try {
				synchronized (this) {
					wait(_SHUTDOWN_WAIT_TIMEOUT);
				}
			}
			catch (InterruptedException ie) {
				if (_log.isDebugEnabled()) {
					_log.debug(ie, ie);
				}
			}

			retry++;
		}

		_poolingHttpClientConnectionManager.shutdown();

		_poolingHttpClientConnectionManager = null;

		if (_closeableHttpClient != null) {
			try {
				_closeableHttpClient.close();
			}
			catch (IOException ioe) {
				if (_log.isInfoEnabled()) {
					_log.info(ioe, ioe);
				}
			}
		}

		_closeableHttpClient = null;

		if (_log.isDebugEnabled()) {
			_log.debug(toString() + " was shut down");
		}
	}

	@Modified
	protected synchronized void modified(BundleContext bundleContext, Map<String, Object> properties) {
		deactivate();
		activate(bundleContext, properties);
	}

	private static final int _CONNECTION_MANAGER_CLOSE_TIMEOUT = 200;

	private static final int _DEBUG_LOG_SIZE = 4;

	private static final int _MAX_RETRIES = 10;

	private static final int _SHUTDOWN_WAIT_TIMEOUT = 500;

	private static final Log _log = LogFactoryUtil.getLog(PoolingHttpClientFactory.class);

	private CloseableHttpClient _closeableHttpClient;
	private ServiceRegistration<HttpClient> _httpClientServiceRegistration;
	private PoolingHttpClientConnectionManager _poolingHttpClientConnectionManager;

}