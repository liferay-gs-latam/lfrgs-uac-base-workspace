package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICRoleAssignmentDTO;
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
@Component(immediate = true, service = MockProducerWebServiceClient.class)
public class MockProducerWebServiceClient {

	public List<CMICContactDTO> getContacts(long producerId) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getContacts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICContactDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICContactDTO[] cmicContactDTOS = jsonDeserializer.deserialize(fileContent, CMICContactDTO[].class);

		return ListUtil.fromArray(cmicContactDTOS);
	}

	public CMICContactDTO getPrimaryContact(long producerId) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getPrimaryContact.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICContactDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICContactDTO.class);
	}

	public CMICProducerDTO getProducerById(long id) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getProducerById.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICProducerDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICProducerDTO.class);
	}

	public List<CMICProducerDTO> getProducers(String agent, String division, String name, Boolean payOutOfCdms) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getProducers.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICProducerDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICProducerDTO[] cmicProducerDTOS = jsonDeserializer.deserialize(fileContent, CMICProducerDTO[].class);

		return ListUtil.fromArray(cmicProducerDTOS);
	}

	public List<CMICRoleAssignmentDTO> getRoleAssignments(long producerId) {
		String fileName = _PRODUCER_WEB_SERVICE_DIR + "getRoleAssignmentsTerritoryManagers.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICRoleAssignmentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICRoleAssignmentDTO[] cmicRoleAssignmentDTOS = jsonDeserializer.deserialize(
			fileContent, CMICRoleAssignmentDTO[].class);

		return ListUtil.fromArray(cmicRoleAssignmentDTOS);
	}

	private static final String _PRODUCER_WEB_SERVICE_DIR = "producer-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}