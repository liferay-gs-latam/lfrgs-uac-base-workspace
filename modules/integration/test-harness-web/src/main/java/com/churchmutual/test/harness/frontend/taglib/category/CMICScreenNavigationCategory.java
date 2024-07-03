package com.churchmutual.test.harness.frontend.taglib.category;

import com.churchmutual.test.harness.constants.TestHarnessConstants;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(
	immediate = true, property = "screen.navigation.category.order:Integer=10", service = ScreenNavigationCategory.class
)
public class CMICScreenNavigationCategory implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL);
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

}