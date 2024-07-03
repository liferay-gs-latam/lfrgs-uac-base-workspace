package com.churchmutual.notification.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matthew Chan
 */
@ExtendedObjectClassDefinition(category = "cmic")
@Meta.OCD(
	id = "com.churchmutual.notification.configuration.NotificationConfiguration", localization = "content/Language",
	name = "notifications"
)
public interface NotificationConfiguration {

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to download a Certificate of Insurance or Evidence of Property Insurance for  [$ENTITY_NAME$].",
		description = "Set the notification message to be received when a user has been granted permission to download a Certificate of Insurance and Evidence of Property Insurance. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantDownloadCOIEOPPermission();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to download Loss Runs reports for [$ENTITY_NAME$].",
		description = "Set the notification message to be received when a user has been granted permission to download Loss Runs reports. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantDownloadLossRunsPermission();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to download policy documents for [$ENTITY_NAME$].",
		description = "Set the notification message to be received when a user has been granted permission to download policy and transaction documents. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantDownloadPolicyAndTransactionDocumentsPermission();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to manage user permissions for [$ENTITY_NAME$]. You can manage user permissions by visiting your profile page.",
		description = "Set the notification message to be received when a user has been granted permission to manage permissions. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantManagePermissionsPermission();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to manage users for [$ENTITY_NAME$]. You can manage users by visiting your profile page.",
		description = "Set the notification message to be received when a user has been granted permission to manage users. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantManageUsersPermission();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has given you access to view and download commission statements for [$ENTITY_NAME$].",
		description = "Set the notification message to be received a user has been granted permission to view and download commission statements. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationGrantViewAndDownloadCommissionStatementsPermission();

	@Meta.AD(
		deflt = "You have been added to [$ENTITY_NAME$] by [$CALLER_USER_FULL_NAME$].",
		description = "Set the notification message to be received by users invited to an Account. [$ENTITY_NAME$] = Account Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action", required = false
	)
	public String notificationInviteToAccount();

	@Meta.AD(
		deflt = "You have been added to [$ENTITY_NAME$] by [$CALLER_USER_FULL_NAME$].",
		description = "Set the notification message to be received by users invited to a Producer. [$ENTITY_NAME$] = Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action", required = false
	)
	public String notificationInviteToProducer();

	@Meta.AD(
		deflt = "[$CALLER_USER_FULL_NAME$] has updated your portal role to Owner of [$ENTITY_NAME$]. You are now the primary portal user.",
		description = "Set the notification message to be received when a user's role has been changed to Owner. [$ENTITY_NAME$] = Account or Producer Name, [$CALLER_USER_FULL_NAME$] = Full Name of User who performed action",
		required = false
	)
	public String notificationUpdateRoleToOwner();

}