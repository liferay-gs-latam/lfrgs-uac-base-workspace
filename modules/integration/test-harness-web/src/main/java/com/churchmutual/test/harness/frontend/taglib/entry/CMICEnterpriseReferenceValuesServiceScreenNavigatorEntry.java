package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.EnterpriseReferenceValuesWebService;
import com.churchmutual.rest.model.CMICCompanyDTO;
import com.churchmutual.rest.model.CMICEventTypeDTO;
import com.churchmutual.rest.model.CMICProductDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=80", service = ScreenNavigationEntry.class
)
public class CMICEnterpriseReferenceValuesServiceScreenNavigatorEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_ENTERPRISE_REFERENCE_VALUES_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor getCompaniesDescriptor = new HarnessDescriptor(
			"Get a list of all companies", _GET_COMPANIES_ENDPOINT, Http.Method.GET);

		HarnessDescriptor getEventTypesDescriptor = new HarnessDescriptor(
			"Get a list of all event types", _GET_EVENT_TYPES_ENDPOINT, Http.Method.GET);

		HarnessDescriptor getProductsDescriptor = new HarnessDescriptor(
			"Get a list of all products", _GET_PRODUCTS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter includeInactive = new HarnessDescriptor.Parameter(
			"includeInactive", "includeInactive", true, Boolean.FALSE.toString(), Boolean.class.getName());

		getCompaniesDescriptor.addQueryParameters(includeInactive);
		getEventTypesDescriptor.addQueryParameters(includeInactive);
		getProductsDescriptor.addQueryParameters(includeInactive);

		return ListUtil.fromArray(getCompaniesDescriptor, getEventTypesDescriptor, getProductsDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "enterprise-reference-values-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		try {
			if (_GET_COMPANIES_ENDPOINT.equals(endpoint)) {
				Boolean includeInactive = ParamUtil.getBoolean(portletRequest, "includeInactive");

				List<CMICCompanyDTO> cmicCompanyDTOs = _enterpriseReferenceValuesWebService.getCompanies(
					includeInactive);

				JSONArray jsonArray = _jsonFactory.createJSONArray(cmicCompanyDTOs);

				return jsonArray.toString();
			}
			else if (_GET_EVENT_TYPES_ENDPOINT.equals(endpoint)) {
				Boolean includeInactive = ParamUtil.getBoolean(portletRequest, "includeInactive");

				List<CMICEventTypeDTO> cmicEventTypeDTOs = _enterpriseReferenceValuesWebService.getEventTypes(
					includeInactive);

				JSONArray jsonArray = _jsonFactory.createJSONArray(cmicEventTypeDTOs);

				return jsonArray.toString();
			}
			else if (_GET_PRODUCTS_ENDPOINT.equals(endpoint)) {
				Boolean includeInactive = ParamUtil.getBoolean(portletRequest, "includeInactive");

				List<CMICProductDTO> cmicProductDTOs = _enterpriseReferenceValuesWebService.getProducts(
					includeInactive);

				JSONArray jsonArray = _jsonFactory.createJSONArray(cmicProductDTOs);

				return jsonArray.toString();
			}
			else {
				return StringPool.OPEN_BRACKET + StringPool.CLOSE_BRACKET;
			}
		}
		catch (Exception pe) {
			JSONArray response = _jsonFactory.createJSONArray();

			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}

			return response.toString();
		}
	}

	private static final String _GET_COMPANIES_ENDPOINT = "/companies/v1/";

	private static final String _GET_EVENT_TYPES_ENDPOINT = "/event-types/v1/";

	private static final String _GET_PRODUCTS_ENDPOINT = "/products/v1/";

	private static final Log _log = LogFactoryUtil.getLog(
		CMICEnterpriseReferenceValuesServiceScreenNavigatorEntry.class);

	@Reference
	private EnterpriseReferenceValuesWebService _enterpriseReferenceValuesWebService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

}