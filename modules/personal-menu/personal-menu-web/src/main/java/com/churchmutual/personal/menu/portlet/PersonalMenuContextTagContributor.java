package com.churchmutual.personal.menu.portlet;

import com.liferay.frontend.taglib.clay.servlet.taglib.contributor.ClayTagContextContributor;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.util.SoyRawData;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

@Component(
	immediate = true,
	property = "clay.tag.context.contributor.key=PersonalMenuKey",
	service = ClayTagContextContributor.class
)
public class PersonalMenuContextTagContributor implements ClayTagContextContributor {

	@Override
	public void populate(Map<String, Object> context) {
		SoyRawData soyRawData = soyDataFactory.createSoyRawData((String)context.get("label"));

		context.replace("label", soyRawData.getValue());
	}

	@Reference
	private SoyDataFactory soyDataFactory;
}
