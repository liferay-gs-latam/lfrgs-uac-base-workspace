package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=30", service = ScreenNavigationEntry.class
)
public class CMICProducerServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_PRODUCER_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor getContactsDescriptor = new HarnessDescriptor(
			"Get all contacts for a producer.", _GET_CONTACTS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter producerId = new HarnessDescriptor.Parameter(
			"producerId", "producerId", true, 4498L, Long.class.getName());

		getContactsDescriptor.addQueryParameters(producerId);

		HarnessDescriptor getPrimaryContactDescriptor = new HarnessDescriptor(
			"Get primary contact info for the producer.", _GET_PRIMARY_CONTACT_ENDPOINT, Http.Method.GET);

		getPrimaryContactDescriptor.addQueryParameters(producerId);

		HarnessDescriptor getProducerByIdDescriptor = new HarnessDescriptor(
			"Get a producer by its id.", _GET_PRODUCER_BY_ID_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter id = new HarnessDescriptor.Parameter("id", "id", true, 4498L, Long.class.getName());

		getProducerByIdDescriptor.addPathParameters(id);

		HarnessDescriptor getProducersDescriptor = new HarnessDescriptor(
			"Get a list of producers optionally filtered by any combination of division, agent, name, and whether or " +
				"not they are paid out of CDMS.",
			_GET_PRODUCERS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter agent = new HarnessDescriptor.Parameter(
			"agent", "agent", false, "123", String.class.getName());

		HarnessDescriptor.Parameter division = new HarnessDescriptor.Parameter(
			"division", "division", false, "12", String.class.getName());

		HarnessDescriptor.Parameter name = new HarnessDescriptor.Parameter(
			"name", "name", false, "Test Producer", String.class.getName());

		HarnessDescriptor.Parameter payOutOfCdms = new HarnessDescriptor.Parameter(
			"payOutOfCdms", "payOutOfCdms", false, false, Boolean.class.getName());

		getProducersDescriptor.addQueryParameters(agent, division, name, payOutOfCdms);

		HarnessDescriptor getRoleAssignmentsDescriptor = new HarnessDescriptor(
			"Gets a list of all the role assignments on a producer.", _GET_ROLE_ASSIGNMENTS_ENDPOINT, Http.Method.GET);

		getRoleAssignmentsDescriptor.addQueryParameters(producerId);

		return ListUtil.fromArray(
			getContactsDescriptor, getPrimaryContactDescriptor, getProducerByIdDescriptor, getProducersDescriptor,
			getRoleAssignmentsDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "producer-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		try {
			if (_GET_CONTACTS_ENDPOINT.equals(endpoint)) {
				long producerId = ParamUtil.getLong(portletRequest, "producerId");

				List<CMICContactDTO> contacts = _producerWebService.getContacts(producerId);

				contacts.forEach(contact -> response.put(contact.toJSONObject()));
			}
			else if (_GET_PRIMARY_CONTACT_ENDPOINT.equals(endpoint)) {
				long producerId = ParamUtil.getLong(portletRequest, "producerId");

				CMICContactDTO contact = _producerWebService.getPrimaryContact(producerId);

				response.put(contact.toJSONObject());
			}
			else if (_GET_PRODUCER_BY_ID_ENDPOINT.equals(endpoint)) {
				long id = ParamUtil.getLong(portletRequest, "id");

				CMICProducerDTO producer = _producerWebService.getProducerById(id);

				response.put(producer.toJSONObject());
			}
			else if (_GET_PRODUCERS_ENDPOINT.equals(endpoint)) {
				String agent = ParamUtil.getString(portletRequest, "agent");
				String division = ParamUtil.getString(portletRequest, "division");
				String name = ParamUtil.getString(portletRequest, "name");
				Boolean payOutOfCdms = ParamUtil.getBoolean(portletRequest, "payOutOfCdms");

				List<CMICProducerDTO> producers = _producerWebService.getProducers(agent, division, name, payOutOfCdms);

				producers.forEach(producer -> response.put(producer.toJSONObject()));
			}
			else if (_GET_ROLE_ASSIGNMENTS_ENDPOINT.equals(endpoint)) {
				long producerId = ParamUtil.getLong(portletRequest, "producerId");

				List<CMICRoleAssignmentDTO> roleAssignments = _producerWebService.getRoleAssignments(producerId);

				roleAssignments.forEach(roleAssignment -> response.put(roleAssignment.toJSONObject()));
			}
		}
		catch (Exception pe) {
			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}
		}

		return response.toString();
	}

	private static final String _GET_CONTACTS_ENDPOINT = "/v1/contacts";

	private static final String _GET_PRIMARY_CONTACT_ENDPOINT = "/v1/contacts/with-assignment";

	private static final String _GET_PRODUCER_BY_ID_ENDPOINT = "/v1/producers/{id}";

	private static final String _GET_PRODUCERS_ENDPOINT = "/v1/producers";

	private static final String _GET_ROLE_ASSIGNMENTS_ENDPOINT = "/v1/role-assignments";

	private static final Log _log = LogFactoryUtil.getLog(CMICProducerServiceScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private ProducerWebService _producerWebService;

}