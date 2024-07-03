package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockAccountWebServiceClient.class)
public class MockAccountWebServiceClient {

	public CMICAccountDTO getAccounts(String accountNumber) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAccounts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAccountDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICAccountDTO.class);
	}

	public List<CMICAccountDTO> getAccountsSearchByProducer(String[] producerCode) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAccountsSearchByProducer.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAccountDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICAccountDTO[] cmicAccountDTOS = jsonDeserializer.deserialize(fileContent, CMICAccountDTO[].class);

		return ListUtil.fromArray(cmicAccountDTOS);
	}

	public CMICAddressDTO getAddressAccount(String accountNumber) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAddressAccount.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAddressDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICAddressDTO.class);
	}

	public CMICAddressDTO getMailingAddressAccount() {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getMailingAddressAccount.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAddressDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICAddressDTO.class);
	}

	private static final String _ACCOUNT_WEB_SERVICE_DIR = "account-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}