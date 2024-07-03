package com.churchmutual.test.harness.model;

import com.liferay.portal.kernel.util.Http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eric Chin
 */
public class HarnessDescriptor {

	public HarnessDescriptor(String description, String endpoint, Http.Method method) {
		_description = description;
		_endpoint = endpoint;
		_method = method;
		_bodyParameters = new ArrayList<>();
		_pathParameters = new ArrayList<>();
		_queryParameters = new ArrayList<>();
	}

	public void addBodyParameters(Parameter... parameters) {
		Collections.addAll(_bodyParameters, parameters);
	}

	public void addPathParameters(Parameter... parameters) {
		Collections.addAll(_pathParameters, parameters);
	}

	public void addQueryParameters(Parameter... parameters) {
		Collections.addAll(_queryParameters, parameters);
	}

	public List<Parameter> getBodyParameters() {
		return _bodyParameters;
	}

	public String getDescription() {
		return _description;
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public Http.Method getMethod() {
		return _method;
	}

	public List<Parameter> getPathParameters() {
		return _pathParameters;
	}

	public List<Parameter> getQueryParameters() {
		return _queryParameters;
	}

	public void setBodyParameters(List<Parameter> parameters) {
		_bodyParameters = parameters;
	}

	public void setPathParameters(List<Parameter> parameters) {
		_pathParameters = parameters;
	}

	public void setQueryParameters(List<Parameter> parameters) {
		_queryParameters = parameters;
	}

	public static class Parameter {

		public Parameter(String description, String name, boolean required, Object sampleValue, String type) {
			_description = description;
			_name = name;
			_required = required;
			_sampleValue = sampleValue;
			_type = type;
		}

		public Parameter(String description, String name, boolean required, String type) {
			_description = description;
			_name = name;
			_required = required;
			_type = type;
		}

		public String getDescription() {
			return _description;
		}

		public String getName() {
			return _name;
		}

		public Object getSampleValue() {
			return _sampleValue;
		}

		public String getType() {
			return _type;
		}

		public boolean isRequired() {
			return _required;
		}

		private String _description;
		private String _name;
		private boolean _required;
		private Object _sampleValue;
		private String _type;

	}

	private List<Parameter> _bodyParameters;
	private String _description;
	private String _endpoint;
	private Http.Method _method;
	private List<Parameter> _pathParameters;
	private List<Parameter> _queryParameters;

}