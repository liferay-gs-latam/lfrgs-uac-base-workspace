package com.churchmutual.test.harness.helper;

import com.churchmutual.test.harness.api.TestHarnessInvoker;
import com.churchmutual.test.harness.constants.TestHarnessConstants;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationRegistry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eric Chin
 */
@Component(immediate = true, service = TestHarnessHelper.class)
public class TestHarnessHelper {

	public TestHarnessInvoker getTestHarnessInvoker(PortletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		String screenNavigationCategoryKey = ParamUtil.getString(request, "screenNavigationCategoryKey");

		String screenNavigationEntryKey = ParamUtil.getString(request, "screenNavigationEntryKey");

		List<ScreenNavigationCategory> screenNavigationCategories =
			_screenNavigationRegistry.getScreenNavigationCategories(
				TestHarnessConstants.SCREEN_NAVIGATION_KEY, themeDisplay.getUser(), null);

		Stream<ScreenNavigationCategory> screenNavigationCategoryStream = screenNavigationCategories.stream();

		Optional<ScreenNavigationCategory> screenNavigationCategoryOptional = screenNavigationCategoryStream.filter(
			screenNavigationCategory -> StringUtil.equals(
				screenNavigationCategoryKey, screenNavigationCategory.getCategoryKey())
		).findFirst();

		if (screenNavigationCategoryOptional.isPresent()) {
			List<ScreenNavigationEntry> screenNavigationEntries = _screenNavigationRegistry.getScreenNavigationEntries(
				screenNavigationCategoryOptional.get(), themeDisplay.getUser(), null);

			Stream<ScreenNavigationEntry> screenNavigationEntryStream = screenNavigationEntries.stream();

			Optional<ScreenNavigationEntry> screenNavigationEntryOptional = screenNavigationEntryStream.filter(
				screenNavigationEntry -> StringUtil.equals(
					screenNavigationEntryKey, screenNavigationEntry.getEntryKey())
			).findFirst();

			if (screenNavigationEntryOptional.isPresent()) {
				ScreenNavigationEntry screenNavigationEntry = screenNavigationEntryOptional.get();

				return (TestHarnessInvoker)screenNavigationEntry;
			}
		}

		return null;
	}

	@Reference
	private ScreenNavigationRegistry _screenNavigationRegistry;

}