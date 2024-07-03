package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICInsuredUserDTO;
import com.churchmutual.rest.model.CMICProducerUserDTO;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
	immediate = true, property = "screen.navigation.entry.order:Integer=20", service = ScreenNavigationEntry.class
)
public class CMICPortalUserServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_PORTAL_USER_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor deleteInsuredEntityUserDescriptor = new HarnessDescriptor(
			"Remove an existing user from insured account", _DELETE_INSURED_ENTITY_USER_URL, Http.Method.PUT);

		HarnessDescriptor.Parameter accountNumber = new HarnessDescriptor.Parameter(
			"accountNumber", "accountNumber", true, "0318444", "String");

		HarnessDescriptor.Parameter lossRunsAccountNumber = new HarnessDescriptor.Parameter(
			"accountNumber", "lossRunsAccountNumber", true, "0222340", "String");

		HarnessDescriptor.Parameter requesterFirstName = new HarnessDescriptor.Parameter(
			"requesterFirstName", "requesterFirstName", true, "John", "String");

		HarnessDescriptor.Parameter requesterLastName = new HarnessDescriptor.Parameter(
			"requesterLastName", "requesterLastName", true, "Doe", "String");

		HarnessDescriptor.Parameter claimHistoryEndDate = new HarnessDescriptor.Parameter(
			"ClaimHistoryEndDate", "claimHistoryEndDate", true, "2020-01-01", "String");

		HarnessDescriptor.Parameter claimHistoryStartDate = new HarnessDescriptor.Parameter(
			"ClaimHistoryStartDate", "claimHistoryStartDate", true, "2000-01-01", "String");

		HarnessDescriptor.Parameter requestReason = new HarnessDescriptor.Parameter(
			"RequestReason", "requestReason", true, "Shopping around for a new insurer", "String");

		HarnessDescriptor.Parameter showClaimantInformation = new HarnessDescriptor.Parameter(
			"ShowClaimantInformation", "showClaimantInformation", true, false, Boolean.class.toString());

		HarnessDescriptor.Parameter showReserveInformation = new HarnessDescriptor.Parameter(
			"ShowReserveInformation", "showReserveInformation", true, false, Boolean.class.toString());

		HarnessDescriptor generateLossRunReportDescriptor = new HarnessDescriptor(
			"Generate PDF Report", _GENERATE_LOSS_RUN_REPORT_URL, Http.Method.GET);

		HarnessDescriptor.Parameter companyNumber = new HarnessDescriptor.Parameter(
			"companyNumber", "companyNumber", true, "1", "String");

		HarnessDescriptor provisionExistingInsuredUserDescriptor = new HarnessDescriptor(
			"Producer or Insured invites existing insured user", _PROVISION_EXISTING_INSURED_USER_URL, Http.Method.PUT);

		HarnessDescriptor provisionNewInsuredUserDescriptor = new HarnessDescriptor(
			"Producer user or Insured user invites new insured user", _PROVISION_NEW_INSURED_USER_URL,
			Http.Method.POST);

		HarnessDescriptor updateInsuredUserRoleDescriptor = new HarnessDescriptor(
			"Insured owner/admin updates insured user role of registered user", _UPDATE_INSURED_USER_ROLE_URL,
			Http.Method.PUT);

		HarnessDescriptor validateInsuredUserRegistrationDescriptor = new HarnessDescriptor(
			"Validate and update a insured user's registration with uuid", _VALIDATE_INSURED_USER_REGISTRATION_URL,
			Http.Method.PUT);

		HarnessDescriptor deleteProducerEntityUserDescriptor = new HarnessDescriptor(
			"delete Producer user from Producer Organization with producerID.", _DELETE_PRODUCER_ENTITY_USER_URL,
			Http.Method.PUT);

		HarnessDescriptor.Parameter callerUserId = new HarnessDescriptor.Parameter(
			"callerUserId", "callerUserId", true, 1038L, Long.class.getName());

		HarnessDescriptor getAccountEntityUsersDescriptor = new HarnessDescriptor(
			"get List of Insured users by accountNumber.", _GET_ACCOUNT_ENTITY_USERS_URL, Http.Method.GET);

		HarnessDescriptor getProducerEntityUsersDescriptor = new HarnessDescriptor(
			"get List of Producer users by producerID.", _GET_PRODUCER_ENTITY_USERS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter producerId = new HarnessDescriptor.Parameter(
			"producer id", "producerID", true, 4159L, Long.class.getName());

		HarnessDescriptor.Parameter accountProducerId = new HarnessDescriptor.Parameter(
			"producer id", "producerID", true, 4321L, Long.class.getName());

		HarnessDescriptor getUserDetailsDescriptor = new HarnessDescriptor(
			"load user details by id after login.", _GET_USER_DETAILS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter uuid = new HarnessDescriptor.Parameter(
			"uuid", "uuid", true, "77985eaa-6dd4-4a5c-8004-17bde0a5bd73", "String");

		HarnessDescriptor.Parameter userId = new HarnessDescriptor.Parameter(
			"user id", "id", true, 1098L, Long.class.getName());

		HarnessDescriptor.Parameter emails = new HarnessDescriptor.Parameter(
			"email addresses", "emails", true, "johndoe@churchmutual.com,janedoe@churchmutual.com", "String");

		HarnessDescriptor provisionExistingProducerUserDescriptor = new HarnessDescriptor(
			"self provision an existing producer user", _PROVISION_EXISTING_PRODUCER_USER_URL, Http.Method.PUT);

		HarnessDescriptor provisionNewProducerUserDescriptor = new HarnessDescriptor(
			"self provision a new producer user", _PROVISION_NEW_PRODUCER_USER_URL, Http.Method.POST);

		HarnessDescriptor.Parameter inviterUserId = new HarnessDescriptor.Parameter(
			"inviterUserId", "inviterUserId", true, 1038L, Long.class.getName());

		HarnessDescriptor updateProducerUserRoleDescriptor = new HarnessDescriptor(
			"update a producer user's role", _UPDATE_PRODUCER_USER_ROLE_URL, Http.Method.PUT);

		HarnessDescriptor.Parameter role = new HarnessDescriptor.Parameter("role", "role", true, "admin", "String");

		HarnessDescriptor validateProducerUserRegistrationDescriptor = new HarnessDescriptor(
			"validate registration of a producer user", _VALIDATE_PRODUCER_USER_REGISTRATION_URL, Http.Method.PUT);

		HarnessDescriptor isUserRegisteredDescriptor = new HarnessDescriptor(
			"Find out if a user is registered using ID. False at first", _IS_USER_REGISTERED_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter agentNumber = new HarnessDescriptor.Parameter(
			"agentNumber", "agentNumber", true, "376", "String");

		HarnessDescriptor.Parameter divisionNumber = new HarnessDescriptor.Parameter(
			"divisionNumber", "divisionNumber", true, "35", "String");

		HarnessDescriptor.Parameter zipCode = new HarnessDescriptor.Parameter(
			"zipCode", "zipCode", true, "54452", "String");

		HarnessDescriptor.Parameter registrationCode = new HarnessDescriptor.Parameter(
			"registrationCode", "registrationCode", true, "abODgecc", "String");

		HarnessDescriptor validateUserDescriptor = new HarnessDescriptor(
			"validate a User with registration code", _VALIDATE_USER_ENDPOINT, Http.Method.GET);

		deleteInsuredEntityUserDescriptor.addQueryParameters(accountNumber, callerUserId, companyNumber);
		deleteInsuredEntityUserDescriptor.addPathParameters(userId);
		provisionExistingInsuredUserDescriptor.addPathParameters(userId);
		provisionExistingInsuredUserDescriptor.addBodyParameters(
			accountNumber, companyNumber, accountProducerId, emails, inviterUserId);
		provisionNewInsuredUserDescriptor.addBodyParameters(
			accountNumber, companyNumber, accountProducerId, emails, inviterUserId);
		updateInsuredUserRoleDescriptor.addPathParameters(userId);
		updateInsuredUserRoleDescriptor.addBodyParameters(accountNumber, callerUserId, companyNumber, role);
		validateInsuredUserRegistrationDescriptor.addPathParameters(userId);
		validateInsuredUserRegistrationDescriptor.addBodyParameters(
			accountNumber, companyNumber, registrationCode, uuid, zipCode);
		deleteProducerEntityUserDescriptor.addQueryParameters(callerUserId, producerId);
		deleteProducerEntityUserDescriptor.addPathParameters(userId);
		generateLossRunReportDescriptor.addQueryParameters(
			lossRunsAccountNumber, claimHistoryEndDate, claimHistoryStartDate, requestReason, showClaimantInformation,
			showReserveInformation, userId, requesterFirstName, requesterLastName);
		getAccountEntityUsersDescriptor.addQueryParameters(accountNumber);
		getProducerEntityUsersDescriptor.addQueryParameters(producerId);
		getUserDetailsDescriptor.addQueryParameters(userId);
		isUserRegisteredDescriptor.addQueryParameters(userId);
		provisionExistingProducerUserDescriptor.addPathParameters(userId);
		provisionExistingProducerUserDescriptor.addBodyParameters(emails, inviterUserId, producerId);
		provisionNewProducerUserDescriptor.addBodyParameters(producerId, inviterUserId, emails);
		updateProducerUserRoleDescriptor.addPathParameters(userId);
		updateProducerUserRoleDescriptor.addBodyParameters(callerUserId, producerId, role);
		validateProducerUserRegistrationDescriptor.addPathParameters(userId);
		validateProducerUserRegistrationDescriptor.addBodyParameters(
			agentNumber, divisionNumber, registrationCode, uuid, zipCode);
		validateUserDescriptor.addQueryParameters(registrationCode);

		return ListUtil.fromArray(
			deleteInsuredEntityUserDescriptor, provisionExistingInsuredUserDescriptor,
			provisionNewInsuredUserDescriptor, updateInsuredUserRoleDescriptor,
			validateInsuredUserRegistrationDescriptor, deleteProducerEntityUserDescriptor,
			generateLossRunReportDescriptor, getAccountEntityUsersDescriptor, getProducerEntityUsersDescriptor, getUserDetailsDescriptor,
			isUserRegisteredDescriptor, provisionExistingProducerUserDescriptor, provisionNewProducerUserDescriptor,
			updateProducerUserRoleDescriptor, validateProducerUserRegistrationDescriptor, validateUserDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "portal-user-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");
		String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");
		long producerId = ParamUtil.getLong(portletRequest, "producerID");
		long callerUserId = ParamUtil.getLong(portletRequest, "callerUserId");
		long id = ParamUtil.getLong(portletRequest, "id");
		long inviterUserId = ParamUtil.getLong(portletRequest, "inviterUserId");
		String uuid = ParamUtil.getString(portletRequest, "uuid");
		String agentNumber = ParamUtil.getString(portletRequest, "agentNumber");
		String companyNumber = ParamUtil.getString(portletRequest, "companyNumber");
		String divisionNumber = ParamUtil.getString(portletRequest, "divisionNumber");
		String registrationCode = ParamUtil.getString(portletRequest, "registrationCode");
		String role = ParamUtil.getString(portletRequest, "role");
		String emails = ParamUtil.getString(portletRequest, "emails");
		String zipCode = ParamUtil.getString(portletRequest, "zipCode");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		try {
			if (_DELETE_INSURED_ENTITY_USER_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.deleteInsuredEntityUser(
					callerUserId, id, accountNumber, companyNumber);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_PROVISION_EXISTING_INSURED_USER_URL.equals(endpoint)) {
				CMICInsuredUserDTO cmicInsuredUserDTO = _portalUserWebService.provisionExistingInsuredUser(
					callerUserId, id, accountNumber, companyNumber, producerId, emails);

				response.put(cmicInsuredUserDTO.toJSONObject());
			}
			else if (_PROVISION_NEW_INSURED_USER_URL.equals(endpoint)) {
				String[] emailArray = emails.replaceAll(
					"[|]", StringPool.BLANK
				).split(
					StringPool.COMMA
				);

				List<CMICUserDTO> users = _portalUserWebService.provisionNewInsuredUsers(
					callerUserId, accountNumber, companyNumber, producerId, ListUtil.fromArray(emailArray));

				users.forEach(user -> response.put(user.toJSONObject()));
			}
			else if (_UPDATE_INSURED_USER_ROLE_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.updateInsuredUserRole(
					callerUserId, id, accountNumber, companyNumber, role);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_VALIDATE_INSURED_USER_REGISTRATION_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.validateInsuredUserRegistration(
					id, accountNumber, companyNumber, registrationCode, uuid, zipCode);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_DELETE_PRODUCER_ENTITY_USER_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.deleteProducerEntityUser(callerUserId, id, producerId);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_GENERATE_LOSS_RUN_REPORT_URL.equals(endpoint)) {
				String lossRunsAccountNumber = ParamUtil.getString(portletRequest, "lossRunsAccountNumber");
				String claimHistoryEndDate = ParamUtil.getString(portletRequest, "claimHistoryEndDate");
				String claimHistoryStartDate = ParamUtil.getString(portletRequest, "claimHistoryStartDate");
				String requestReason = ParamUtil.getString(portletRequest, "requestReason");
				boolean showClaimantInformation = ParamUtil.getBoolean(portletRequest, "showClaimantInformation");
				boolean showReserveInformation = ParamUtil.getBoolean(portletRequest, "showReserveInformation");
				String requesterFirstName = ParamUtil.getString(portletRequest, "requesterFirstName");
				String requesterLastName = ParamUtil.getString(portletRequest, "requesterLastName");

				String pdf = _portalUserWebService.generateLossRunReport(
					lossRunsAccountNumber, id, requesterFirstName, requesterLastName, claimHistoryEndDate,
					claimHistoryStartDate, requestReason, showClaimantInformation, showReserveInformation);

				response.put(pdf);
			}
			else if (_GET_ACCOUNT_ENTITY_USERS_URL.equals(endpoint)) {
				List<CMICUserDTO> users = _portalUserWebService.getAccountEntityUsers(accountNumber);

				users.forEach(user -> response.put(user.toJSONObject()));
			}
			else if (_GET_PRODUCER_ENTITY_USERS_ENDPOINT.equals(endpoint)) {
				List<CMICUserDTO> users = _portalUserWebService.getProducerEntityUsers(producerId);

				users.forEach(user -> response.put(user.toJSONObject()));
			}
			else if (_GET_USER_DETAILS_ENDPOINT.equals(endpoint)) {
				CMICUserDTO user = _portalUserWebService.getUserDetails(id);

				response.put(user.toJSONObject());
			}
			else if (_IS_USER_REGISTERED_ENDPOINT.equals(endpoint)) {
				boolean userRegistered = _portalUserWebService.isUserRegistered(id);

				response.put(userRegistered);
			}
			else if (_PROVISION_EXISTING_PRODUCER_USER_URL.equals(endpoint)) {
				CMICProducerUserDTO cmicProducerUserDTO = _portalUserWebService.provisionExistingProducerUser(
					inviterUserId, id, producerId, emails);

				response.put(cmicProducerUserDTO.toJSONObject());
			}
			else if (_PROVISION_NEW_PRODUCER_USER_URL.equals(endpoint)) {
				String[] emailArray = emails.replaceAll(
					"[|]", StringPool.BLANK
				).split(
					StringPool.COMMA
				);

				List<CMICUserDTO> users = _portalUserWebService.provisionNewProducerUsers(
					inviterUserId, producerId, ListUtil.fromArray(emailArray));

				users.forEach(user -> response.put(user.toJSONObject()));
			}
			else if (_UPDATE_PRODUCER_USER_ROLE_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.updateProducerUserRole(
					callerUserId, id, producerId, role);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_VALIDATE_PRODUCER_USER_REGISTRATION_URL.equals(endpoint)) {
				CMICUserDTO cmicUserDTO = _portalUserWebService.validateProducerUserRegistration(
					id, agentNumber, divisionNumber, registrationCode, uuid, zipCode);

				response.put(cmicUserDTO.toJSONObject());
			}
			else if (_VALIDATE_USER_ENDPOINT.equals(endpoint)) {
				CMICUserDTO user = _portalUserWebService.validateUserRegistrationCode(registrationCode);

				response.put(user.toJSONObject());
			}
		}
		catch (PortalException pe) {
			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}
		}

		return response.toString();
	}

	private static final String _DELETE_INSURED_ENTITY_USER_URL = "/v2/insured-users/{targetUserId}";

	private static final String _DELETE_PRODUCER_ENTITY_USER_URL = "/v2/producer-users/{targetUserId}";

	private static final String _GENERATE_LOSS_RUN_REPORT_URL = "/v1/loss-run/for-account/pdf";

	private static final String _GET_ACCOUNT_ENTITY_USERS_URL = "/v2/insureds/{accountNumber}/users";

	private static final String _GET_PRODUCER_ENTITY_USERS_ENDPOINT = "/v2/producers/{producerID}/users";

	private static final String _GET_USER_DETAILS_ENDPOINT = "/v2/users/{userId}";

	private static final String _IS_USER_REGISTERED_ENDPOINT = "/v2/users/{userId}/is-user-registered";

	private static final String _PROVISION_EXISTING_INSURED_USER_URL =
		"/v2/insured-users/{inviteeUserId}/self-provision-existing";

	private static final String _PROVISION_EXISTING_PRODUCER_USER_URL =
		"/v2/producer-users/{inviteeUserId}/self-provision-existing";

	private static final String _PROVISION_NEW_INSURED_USER_URL = "/v2/insured-users/self-provision-new";

	private static final String _PROVISION_NEW_PRODUCER_USER_URL = "/v2/producer-users/self-provision-new";

	private static final String _UPDATE_INSURED_USER_ROLE_URL = "/v2/insured-users/{targetUserId}/role";

	private static final String _UPDATE_PRODUCER_USER_ROLE_URL = "/v2/producer-users/{targetUserId}/role";

	private static final String _VALIDATE_INSURED_USER_REGISTRATION_URL = "/v2/insured-users/{userId}/registration";

	private static final String _VALIDATE_PRODUCER_USER_REGISTRATION_URL = "/v2/producer-users/{userId}/registration";

	private static final String _VALIDATE_USER_ENDPOINT = "/v2/users/registration";

	private static final Log _log = LogFactoryUtil.getLog(CMICPortalUserServiceScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PortalUserWebService _portalUserWebService;

}