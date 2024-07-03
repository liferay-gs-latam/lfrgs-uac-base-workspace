package com.churchmutual.core.internal.upgrade;

import com.churchmutual.core.internal.upgrade.v1_1_0.UpgradeCMICPermission;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class CMICCoreUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(UpgradeStepRegistrator.Registry registry) {
		registry.register("1.0.0", "1.1.0", new UpgradeCMICPermission());
	}

}