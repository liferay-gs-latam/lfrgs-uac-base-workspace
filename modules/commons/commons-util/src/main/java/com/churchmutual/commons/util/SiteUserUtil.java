package com.churchmutual.commons.util;

import com.churchmutual.commons.enums.CMICSite;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

public class SiteUserUtil {

    public static boolean isInsuredSiteUser(long userId) throws PortalException {
        User user = UserLocalServiceUtil.getUser(userId);

        Group producerGroup = GroupLocalServiceUtil.getFriendlyURLGroup(
            user.getCompanyId(), CMICSite.INSURED.getFriendlyURL());

        return UserLocalServiceUtil.hasGroupUser(producerGroup.getGroupId(), userId);
    }

    public static boolean isProducerSiteUser(long userId) throws PortalException {
        User user = UserLocalServiceUtil.getUser(userId);

        Group producerGroup = GroupLocalServiceUtil.getFriendlyURLGroup(
            user.getCompanyId(), CMICSite.PRODUCER.getFriendlyURL());

        return UserLocalServiceUtil.hasGroupUser(producerGroup.getGroupId(), userId);
    }

}
