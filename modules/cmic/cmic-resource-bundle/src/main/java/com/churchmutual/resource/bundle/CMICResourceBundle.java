package com.churchmutual.resource.bundle;

import com.liferay.portal.kernel.language.UTF8Control;
import org.osgi.service.component.annotations.Component;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {
		"language.id=en_US"
	},
	service = ResourceBundle.class
)
public class CMICResourceBundle extends ResourceBundle {

	@Override
	public Enumeration<String> getKeys() {
		return _resourceBundle.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		return _resourceBundle.getObject(key);
	}

	private final ResourceBundle _resourceBundle =
		ResourceBundle.getBundle("content.Language", UTF8Control.INSTANCE);

}