package com.churchmutual.notification;

import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.notification.configuration.NotificationConfiguration;
import com.churchmutual.notification.constants.NotificationConstants;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true, property = "javax.portlet.name=" + NotificationConstants.NOTIFICATION_PORTLET,
	service = NotificationEventSender.class
)
public class NotificationEventSender {

	public void sendNotification(
			int notificationType, long recipientUserId, long callerUserId, String entityName)
		throws PortalException {

		User callerUser = userLocalService.getUser(callerUserId);

		String callerUserFullName = callerUser.getFullName();

		String entityType = getEntityType(recipientUserId);

		String notificationTemplate = getNotificationTemplate(notificationType);

		String message = StringUtil.replace(
			notificationTemplate, _MESSAGE_REPLACEMENTS, new String[] {callerUserFullName, entityName, entityType});

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", message);

		userNotificationEventLocalService.sendUserNotificationEvents(
			recipientUserId, NotificationConstants.NOTIFICATION_PORTLET,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, jsonObject);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_notificationConfiguration = ConfigurableUtil.createConfigurable(NotificationConfiguration.class, properties);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected UserLocalService userLocalService;

	@Reference
	protected UserNotificationEventLocalService userNotificationEventLocalService;

	private String getEntityType(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		Group insuredGroup = groupLocalService.getFriendlyURLGroup(
			user.getCompanyId(), CMICSite.INSURED.getFriendlyURL());

		Group producerGroup = groupLocalService.getFriendlyURLGroup(
			user.getCompanyId(), CMICSite.PRODUCER.getFriendlyURL());

		if (userLocalService.hasGroupUser(insuredGroup.getGroupId(), userId)) {
			return "account";
		}
		else if (userLocalService.hasGroupUser(producerGroup.getGroupId(), userId)) {
			return "organization";
		}

		throw new PortalException("Could not find user entity type");
	}

	private String getNotificationTemplate(int notificationType) throws PortalException {
		switch (notificationType) {
			case NotificationConstants.TYPE_GRANT_DOWNLOAD_COI_EOP_PERMISSION:
				return _notificationConfiguration.notificationGrantDownloadCOIEOPPermission();
			case NotificationConstants.TYPE_GRANT_DOWNLOAD_LOSS_RUNS_PERMISSION:
				return _notificationConfiguration.notificationGrantDownloadLossRunsPermission();
			case NotificationConstants.TYPE_GRANT_DOWNLOAD_POLICY_TRANSACTION_DOCUMENTS_PERMISSION:
				return _notificationConfiguration.notificationGrantDownloadPolicyAndTransactionDocumentsPermission();
			case NotificationConstants.TYPE_GRANT_MANAGE_PERMISSIONS_PERMISSION:
				return _notificationConfiguration.notificationGrantManagePermissionsPermission();
			case NotificationConstants.TYPE_GRANT_MANAGE_USERS_PERMISSION:
				return _notificationConfiguration.notificationGrantManageUsersPermission();
			case NotificationConstants.TYPE_GRANT_VIEW_DOWNLOAD_COMMISSION_STATEMENTS_PERMISSION:
				return _notificationConfiguration.notificationGrantViewAndDownloadCommissionStatementsPermission();
			case NotificationConstants.TYPE_INVITE_TO_ACCOUNT:
				return _notificationConfiguration.notificationInviteToAccount();
			case NotificationConstants.TYPE_INVITE_TO_PRODUCER:
				return _notificationConfiguration.notificationInviteToProducer();
			case NotificationConstants.TYPE_UPDATE_ROLE_TO_OWNER:
				return _notificationConfiguration.notificationUpdateRoleToOwner();
			default:
				throw new PortalException("Could not find notification type");
		}
	}

	private static final String[] _MESSAGE_REPLACEMENTS = {
		"[$CALLER_USER_FULL_NAME$]", "[$ENTITY_NAME$]", "[$ENTITY_TYPE$]"
	};

	private NotificationConfiguration _notificationConfiguration;

}