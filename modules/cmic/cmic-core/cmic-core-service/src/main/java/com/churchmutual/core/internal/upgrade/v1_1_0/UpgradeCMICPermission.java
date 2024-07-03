package com.churchmutual.core.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

public class UpgradeCMICPermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(UpgradeCMICPermission.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

}