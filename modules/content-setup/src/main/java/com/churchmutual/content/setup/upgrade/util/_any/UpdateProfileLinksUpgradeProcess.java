package com.churchmutual.content.setup.upgrade.util._any;

import com.churchmutual.commons.enums.CMICSite;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.churchmutual.content.setup.upgrade.util.producer.ProfileLinks;
import com.churchmutual.core.service.CMICUserLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.*;
import com.liferay.portal.kernel.util.Portal;

public class UpdateProfileLinksUpgradeProcess extends BaseSiteUpgradeProcess {
    public UpdateProfileLinksUpgradeProcess(AssetEntryLocalService assetEntryLocalService, CMICUserLocalService cmicUserLocalService, CompanyLocalService companyLocalService, DDM ddm, DDMStructureLocalService ddmStructureLocalService, DDMTemplateLocalService ddmTemplateLocalService, DLAppService dlAppService, DLFileEntryLocalService dlFileEntryLocalService, DLFileEntryTypeLocalService dlFileEntryTypeLocalService, ExpandoColumnLocalService expandoColumnLocalService, ExpandoTableLocalService expandoTableLocalService, GroupLocalService groupLocalService, JournalArticleLocalService journalArticleLocalService, LayoutSetLocalService layoutSetLocalService, PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService, StorageEngineManager storageEngineManager, UserLocalService userLocalService, VirtualHostLocalService virtualHostLocalService) {
        super(assetEntryLocalService, cmicUserLocalService, companyLocalService, ddm, ddmStructureLocalService, ddmTemplateLocalService, dlAppService, dlFileEntryLocalService, dlFileEntryTypeLocalService, expandoColumnLocalService, expandoTableLocalService, groupLocalService, journalArticleLocalService, layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService, storageEngineManager, userLocalService, virtualHostLocalService);
    }

    @Override
    public void doUpgradeAsAdmin() throws Exception {
        _upgradeInsuredLinks();
        _upgradeProducerLinks();
    }

    private void _upgradeProducerLinks() throws Exception {
        long companyId = portal.getDefaultCompanyId();
        long userId = userLocalService.getDefaultUserId(companyId);
        Group portalGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.PRODUCER.getFriendlyURL());
        long groupId = portalGroup.getGroupId();

        ProfileLinks.addLinks(companyId, userId, groupId);
    }

    private void _upgradeInsuredLinks() throws Exception {
        long companyId = portal.getDefaultCompanyId();
        long userId = userLocalService.getDefaultUserId(companyId);
        Group portalGroup = groupLocalService.getFriendlyURLGroup(companyId, CMICSite.INSURED.getFriendlyURL());
        long groupId = portalGroup.getGroupId();

        ProfileLinks.addLinks(companyId, userId, groupId);
    }
}
