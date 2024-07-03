package com.churchmutual.login.hook;

import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;

@Component(immediate = true, property = "key=login.events.post", service = LifecycleAction.class)
public class LoginPostAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) {
		HttpServletRequest request = lifecycleEvent.getRequest();

		long userId = GetterUtil.getLong(request.getRemoteUser());

		_log.debug("Pulling down latest data from CMIC services for userId: " + userId);
	}

	private Log _log = LogFactoryUtil.getLog(LoginPostAction.class.getName());

}