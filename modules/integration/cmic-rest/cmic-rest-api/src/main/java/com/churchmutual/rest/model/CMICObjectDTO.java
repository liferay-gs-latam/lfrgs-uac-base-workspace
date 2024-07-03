package com.churchmutual.rest.model;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;

/**
 * @author Kayleen Lim
 */
public abstract class CMICObjectDTO {

	public JSONObject toJSONObject() {
		try {
			return JSONFactoryUtil.createJSONObject(toString());
		}
		catch (JSONException jsone) {
			return JSONFactoryUtil.createJSONObject();
		}
	}

	public String toString() {
		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		return jsonSerializer.serializeDeep(this);
	}

	protected static final String DEFAULT_COMPANY_NUMBER = "1";

}