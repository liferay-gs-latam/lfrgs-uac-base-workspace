package com.churchmutual.commons.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Luiz Marins
 */
public class PortalHostUtil {

    private PortalHostUtil() {}

    public static String getHost(long groupId) throws PortalException {
        Group group = GroupLocalServiceUtil.getGroup(groupId);

        Company company = CompanyLocalServiceUtil.getCompany(group.getCompanyId());

        String host = company.getPortalURL(groupId);

        if (host.contains("localhost") && group.hasPrivateLayouts()) {
            LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, true);

            if (Validator.isNotNull(layoutSet.getVirtualHostname())) {
                String portalURL = PortalUtil.getPortalURL(
                    layoutSet.getVirtualHostname(), PortalUtil.getPortalServerPort(false), false);

                if (Validator.isNotNull(portalURL)) {
                    host = portalURL;
                }
            }
        }

        return URLBuilderUtil.trimPortNumber(host);
    }
}
