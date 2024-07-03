package com.churchmutual.rest.service;

import com.churchmutual.commons.constants.CacheConstants;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.QsPdsPolicyWebService;
import com.churchmutual.rest.configuration.MockQsPdsPolicyWebServiceConfiguration;
import com.churchmutual.rest.model.*;
import com.churchmutual.rest.service.mock.MockQsPdsPolicyWebServiceClient;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component(
        configurationPid = "com.churchmutual.rest.configuration.MockQsPdsPolicyServiceConfiguration", immediate = true,
        service = QsPdsPolicyWebService.class
)
public class QsPdsPolicyWebServiceImpl implements QsPdsPolicyWebService {
    @Deactivate
    public void deactivate() {
        _singleVMPool.removePortalCache(_GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_YOUNG);
        _singleVMPool.removePortalCache(_GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_OLD);
        _singleVMPool.removePortalCache(_GET_TRANSACTION_CACHE_NAME);
        _singleVMPool.removePortalCache(_GET_TRANSACTIONS_ON_POLICY_CACHE_NAME);
        _singleVMPool.removePortalCache(_GET_POLICIES_BY_POLICY_NUMBERS_CACHE_NAME);
        _singleVMPool.removePortalCache(_GET_POLICIES_ON_ACCOUNT_CACHE_NAME);
    }

    @Override
    public CMICPolicyAccountSummaryDTO fetchPolicyAccountSummaryByAccount(String accountNumber) throws PortalException {
        List<CMICPolicyAccountSummaryDTO> cmicPolicyAccountSummaryDTOS = getPolicyAccountSummariesByAccounts(
                new String[] {accountNumber});

        return CollectionsUtil.getFirst(cmicPolicyAccountSummaryDTOS);
    }

    public List<CMICAdditionalInterestDTO> getAdditionalInterestsOnBuilding(
            Long additionalInterestTypeReferenceId, String policyNumber, String buildingNumber,
            String locationPremisesNumber)
            throws PortalException {

        if (_mockPolicyWebServiceConfiguration.enableMockGetAdditionalInterestsOnBuilding()) {
            return _mockPolicyWebServiceClient.getAdditionalInterestsOnBuilding();
        }

        Map<String, String> queryParameters = new HashMap<>();

        if (Validator.isNotNull(additionalInterestTypeReferenceId)) {
            queryParameters.put("additionalInterestTypeReferenceId", String.valueOf(additionalInterestTypeReferenceId));
        }

        queryParameters.put("combinedPolicyNumber", policyNumber);
        queryParameters.put("buildingNumber", buildingNumber);
        queryParameters.put("locationPremisesNumber", locationPremisesNumber);

        String response = _webServiceExecutor.executeGet(_GET_ADDITIONAL_INTERESTS_ON_BUILDING_URL, queryParameters);

        JSONDeserializer<CMICAdditionalInterestDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        List<CMICAdditionalInterestDTO> list = new ArrayList();

        try {
            CMICAdditionalInterestDTO[] results = jsonDeserializer.deserialize(
                    response, CMICAdditionalInterestDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        return list;
    }

    @Override
    public List<CMICBuildingDTO> getBuildingsOnPolicy(String policyNumber) throws PortalException {
        if (_mockPolicyWebServiceConfiguration.enableMockGetBuildingsOnPolicy()) {
            return _mockPolicyWebServiceClient.getBuildingsOnPolicy();
        }

        Map<String, String> queryParameters = new HashMap<>();

        queryParameters.put("combinedPolicyNumber", policyNumber);

        String response = _webServiceExecutor.executeGet(_GET_BUILDINGS_ON_POLICY_URL, queryParameters);

        JSONDeserializer<CMICBuildingDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        List<CMICBuildingDTO> list = new ArrayList();

        try {
            CMICBuildingDTO[] results = jsonDeserializer.deserialize(response, CMICBuildingDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        return list;
    }

    @Override
    public List<CMICPolicyDTO> getPoliciesByPolicyNumbers(String[] policyNumbers) throws PortalException {
        if (_mockPolicyWebServiceConfiguration.enableMockGetPoliciesByPolicyNumbers()) {
            return _mockPolicyWebServiceClient.getPoliciesByPolicyNumbers(policyNumbers);
        }

        QsPdsPolicyWebServiceImpl.PolicyNumberKey key = new QsPdsPolicyWebServiceImpl.PolicyNumberKey(policyNumbers);

        List<CMICPolicyDTO> cache = _getPoliciesByPolicyNumbersPortalCache.get(key);

        if (cache != null) {
            return cache;
        }

        JSONArray bodyParameters = _jsonFactory.createJSONArray(policyNumbers);

        String response = _webServiceExecutor.executePost(
                _GET_POLICIES_BY_POLICY_NUMBERS_URL, new HashMap<>(), bodyParameters.toString());

        JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        List<CMICPolicyDTO> list = new ArrayList();

        try {
            CMICPolicyDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        _getPoliciesByPolicyNumbersPortalCache.put(key, list);

        return list;
    }

    @Override
    public List<CMICPolicyDTO> getPoliciesOnAccount(String accountNumber) throws PortalException {
        if (_mockPolicyWebServiceConfiguration.enableMockGetPoliciesOnAccount()) {
            return _mockPolicyWebServiceClient.getPoliciesOnAccount(accountNumber);
        }

        QsPdsPolicyWebServiceImpl.AccountNumbersKey key = new QsPdsPolicyWebServiceImpl.AccountNumbersKey(accountNumber);

        List<CMICPolicyDTO> cache = _getPoliciesOnAccountPortalCache.get(key);

        if (cache != null) {
            return cache;
        }

        Map<String, String> queryParameters = new HashMap<>();

        queryParameters.put("accountNumber", accountNumber);

        String response = _webServiceExecutor.executeGet(_GET_POLICIES_ON_ACCOUNT_URL, queryParameters);

        JSONDeserializer<CMICPolicyDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        List<CMICPolicyDTO> list = new ArrayList();

        try {
            CMICPolicyDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        _getPoliciesOnAccountPortalCache.put(key, list);

        return list;
    }

    private Map<Integer, Object> lockMap = new ConcurrentHashMap<>();

    @Override
    public List<CMICPolicyAccountSummaryDTO> getPolicyAccountSummariesByAccounts(String[] accountNumbers)
            throws PortalException {

        try {
            String randomId = StringUtil.randomId();

            QsPdsPolicyWebServiceImpl.AccountNumbersKey key = new QsPdsPolicyWebServiceImpl.AccountNumbersKey(accountNumbers);

            if (_log.isDebugEnabled()) {
                _log.debug(
                        String.format(
                                "[%s] Getting policyAccountSummaries for %d accountNumbers: %s", randomId,
                                accountNumbers.length, StringUtil.shorten(StringUtil.merge(accountNumbers))));
                _log.debug(
                        String.format(
                                "[%s] In Young Cache [%s], In Old Cache [%s]", randomId, inYoungCache(key), inOldCache(key)));
            }

            List<CMICPolicyAccountSummaryDTO> cache = _getPolicyAccountSummariesByAccountsPortalCacheYoung.get(key);

            if (cache != null) {
                return cache;
            }

            cache = _getPolicyAccountSummariesByAccountsPortalCacheOld.get(key);

            if (cache != null) {
                return cache;
            }

            if (_log.isDebugEnabled()) {
                _log.debug(String.format("[%s] Not found in any caches, calling API to refresh cache", randomId));
            }
        }
        catch (Exception e) {
            _log.error(e);

            throw new PortalException(e);
        }

        return refreshPolicyAccountSummariesByAccountsCache(accountNumbers);
    }

    @Override
    public String getPolicyAccountSummariesByAccountsCacheStatus(String[] accountNumbers) throws PortalException {
        QsPdsPolicyWebServiceImpl.AccountNumbersKey key = new QsPdsPolicyWebServiceImpl.AccountNumbersKey(accountNumbers);

        List<CMICPolicyAccountSummaryDTO> cache = _getPolicyAccountSummariesByAccountsPortalCacheYoung.get(key);

        if (_log.isDebugEnabled()) {
            _log.debug(String.format("In Young Cache [%s], In Old Cache [%s]", inYoungCache(key), inOldCache(key)));
        }

        if (cache != null) {
            return CacheConstants.IN_YOUNG_CACHE;
        }

        cache = _getPolicyAccountSummariesByAccountsPortalCacheOld.get(key);

        if (cache != null) {
            return CacheConstants.IN_OLD_CACHE;
        }

        return CacheConstants.NOT_IN_CACHE;
    }

    @Override
    public CMICTransactionDTO getTransaction(String policyNumber, int sequenceNumber) throws PortalException {
        if (_mockPolicyWebServiceConfiguration.enableMockGetTransaction()) {
            return _mockPolicyWebServiceClient.getTransaction(policyNumber, sequenceNumber);
        }

        QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey key = new QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey(
                policyNumber, sequenceNumber);

        CMICTransactionDTO cache = _getTransactionPortalCache.get(key);

        if (cache != null) {
            return cache;
        }

        Map<String, String> queryParameters = new HashMap<>();

        queryParameters.put("combinedPolicyNumber", policyNumber);
        queryParameters.put("sequenceNumber", String.valueOf(sequenceNumber));

        String response = _webServiceExecutor.executeGet(_GET_TRANSACTION_URL, queryParameters);

        JSONDeserializer<CMICTransactionDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        CMICTransactionDTO transaction = null;

        try {
            transaction = jsonDeserializer.deserialize(response, CMICTransactionDTO.class);
        }
        catch (Exception e) {
            throw new PortalException(
                    String.format(
                            "Transaction with combinedPolicyNumber %s and sequenceNumber %s could not be found",
                            policyNumber, sequenceNumber),
                    e);
        }

        _getTransactionPortalCache.put(key, transaction);

        return transaction;
    }

    @Override
    public List<CMICTransactionDTO> getTransactionsOnPolicy(String policyNumber) throws PortalException {
        if (_mockPolicyWebServiceConfiguration.enableMockGetTransactionsOnPolicy()) {
            return _mockPolicyWebServiceClient.getTransactionsOnPolicy(policyNumber);
        }

        QsPdsPolicyWebServiceImpl.PolicyNumberKey key = new QsPdsPolicyWebServiceImpl.PolicyNumberKey(policyNumber);

        List<CMICTransactionDTO> cache = _getTransactionsOnPolicyPortalCache.get(key);

        if (cache != null) {
            return cache;
        }

        Map<String, String> queryParameters = new HashMap<>();

        queryParameters.put("combinedPolicyNumber", policyNumber);

        String response = _webServiceExecutor.executeGet(_GET_TRANSACTIONS_ON_POLICY_URL, queryParameters);

        JSONDeserializer<CMICTransactionDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        List<CMICTransactionDTO> list = new ArrayList();

        try {
            CMICTransactionDTO[] results = jsonDeserializer.deserialize(response, CMICTransactionDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        _getTransactionsOnPolicyPortalCache.put(key, list);

        return list;
    }

    /**
     * Refresh young cache for policy account summaries, only if needed.
     */
    public List<CMICPolicyAccountSummaryDTO> refreshPolicyAccountSummariesByAccountsCache(String[] accountNumbers)
            throws PortalException {

        String randomId = StringUtil.randomId();

        if (_log.isDebugEnabled()) {
            _log.debug(
                    String.format(
                            "[%s] Refreshing policyAccountSummaries for %d accountNumbers: %s", randomId, accountNumbers.length,
                            StringUtil.shorten(StringUtil.merge(accountNumbers))));
        }

        QsPdsPolicyWebServiceImpl.AccountNumbersKey key = new QsPdsPolicyWebServiceImpl.AccountNumbersKey(accountNumbers);

        if (_log.isDebugEnabled()) {
            _log.debug(
                    String.format(
                            "[%s] In Young Cache [%s], In Old Cache [%s]", randomId, inYoungCache(key), inOldCache(key)));
        }

        final Integer id = new Integer(key.hashCode());

        Object lock = null;

        synchronized (this) {
            lock = lockMap.get(id);

            if (lock == null) {
                lock = new Object();

                lockMap.put(id, lock);
            }
        }

        List<CMICPolicyAccountSummaryDTO> list = new ArrayList();

        if (_log.isDebugEnabled()) {
            _log.debug(String.format("[%s] Refreshing policyAccountSummaries - BEFORE lock", randomId));
        }

        synchronized (lock) {
            if (_log.isDebugEnabled()) {
                _log.debug(String.format("[%s] Refreshing policyAccountSummaries - IN lock", randomId));
            }

            // This check is needed since threads will be queuing up, and when it's finally their turn, the queued up
            // threads should find the data in the young cache

            List<CMICPolicyAccountSummaryDTO> cache = _getPolicyAccountSummariesByAccountsPortalCacheYoung.get(key);

            if (cache != null) {
                if (_log.isDebugEnabled()) {
                    _log.debug(
                            String.format(
                                    "[%s] Refreshing policyAccountSummaries - found in cache, no refresh needed", randomId));
                }

                return cache;
            }

            if (_log.isDebugEnabled()) {
                _log.debug(
                        String.format(
                                "[%s] Refreshing policyAccountSummaries - not found in cache, getting data", randomId));
            }

            int accountsSize = accountNumbers.length;

            int startIndex = 0;

            do {
                int endIndex = startIndex + _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_BATCH_SIZE;

                if (accountsSize < endIndex) {
                    endIndex = accountsSize;
                }

                String[] accountNumbersSublist = ArrayUtil.subset(accountNumbers, startIndex, endIndex);

                list.addAll(_getPolicyAccountSummariesByAccounts(accountNumbersSublist));

                startIndex = startIndex + _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_BATCH_SIZE;
            }
            while (startIndex < accountsSize);

            _getPolicyAccountSummariesByAccountsPortalCacheYoung.remove(key);
            _getPolicyAccountSummariesByAccountsPortalCacheYoung.put(key, list);
            _getPolicyAccountSummariesByAccountsPortalCacheOld.remove(key);
            _getPolicyAccountSummariesByAccountsPortalCacheOld.put(key, list);

            if (_log.isDebugEnabled()) {
                _log.debug(
                        String.format(
                                "[%s] Refreshing policyAccountSummaries - EXIT lock - added %d account summaries to cache",
                                randomId, accountNumbers.length));
            }
        }

        return list;

    }

    @Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
        _mockPolicyWebServiceConfiguration = ConfigurableUtil.createConfigurable(
                MockQsPdsPolicyWebServiceConfiguration.class, properties);

        _getPolicyAccountSummariesByAccountsPortalCacheYoung =
                (PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyAccountSummaryDTO>>)_singleVMPool.getPortalCache(
                        _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_YOUNG);
        _getPolicyAccountSummariesByAccountsPortalCacheOld =
                (PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyAccountSummaryDTO>>)_singleVMPool.getPortalCache(
                        _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_OLD);
        _getTransactionsOnPolicyPortalCache =
                (PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberKey, List<CMICTransactionDTO>>)_singleVMPool.getPortalCache(
                        _GET_TRANSACTIONS_ON_POLICY_CACHE_NAME);
        _getPoliciesByPolicyNumbersPortalCache =
                (PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberKey, List<CMICPolicyDTO>>)_singleVMPool.getPortalCache(
                        _GET_POLICIES_BY_POLICY_NUMBERS_CACHE_NAME);
        _getPoliciesOnAccountPortalCache =
                (PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyDTO>>)_singleVMPool.getPortalCache(
                        _GET_POLICIES_ON_ACCOUNT_CACHE_NAME);
        _getTransactionPortalCache =
                (PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey, CMICTransactionDTO>)_singleVMPool.getPortalCache(
                        _GET_TRANSACTION_CACHE_NAME);
    }

    private List<CMICPolicyAccountSummaryDTO> _getPolicyAccountSummariesByAccounts(String[] accountNumbers)
            throws PortalException {

        List<CMICPolicyAccountSummaryDTO> list = new ArrayList<>();

        JSONArray bodyParameters = _jsonFactory.createJSONArray(accountNumbers);

        String response = _webServiceExecutor.executePost(
                _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_URL, new HashMap<>(), bodyParameters.toString());

        JSONDeserializer<CMICPolicyAccountSummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

        try {
            CMICPolicyAccountSummaryDTO[] results = jsonDeserializer.deserialize(
                    response, CMICPolicyAccountSummaryDTO[].class);

            Collections.addAll(list, results);
        }
        catch (Exception e) {
        }

        return list;
    }

    private boolean inYoungCache(QsPdsPolicyWebServiceImpl.AccountNumbersKey key) {
        List<CMICPolicyAccountSummaryDTO> cache = _getPolicyAccountSummariesByAccountsPortalCacheYoung.get(key);

        if (cache != null) {
            return true;
        }

        return false;
    }

    private boolean inOldCache(QsPdsPolicyWebServiceImpl.AccountNumbersKey key) {
        List<CMICPolicyAccountSummaryDTO> cache = _getPolicyAccountSummariesByAccountsPortalCacheOld.get(key);

        if (cache != null) {
            return true;
        }

        return false;
    }

    private static final String _GET_ADDITIONAL_INTERESTS_ON_BUILDING_URL =
            "/qs-pds-policy-service/v1/additional-interests/on-building";

    private static final String _GET_BUILDINGS_ON_POLICY_URL =
            "/qs-pds-policy-service/v1/insurable-objects/buildings/on-policy";

    private static final String _GET_POLICIES_BY_POLICY_NUMBERS_CACHE_NAME =
            QsPdsPolicyWebServiceImpl.class.getName() + "_GET_POLICIES_BY_POLICY_NUMBERS";

    private static final String _GET_POLICIES_BY_POLICY_NUMBERS_URL = "/qs-pds-policy-service/v1/policies";

    private static final String _GET_POLICIES_ON_ACCOUNT_CACHE_NAME =
            QsPdsPolicyWebServiceImpl.class.getName() + "_GET_POLICIES_ON_ACCOUNT";

    private static final String _GET_POLICIES_ON_ACCOUNT_URL = "/qs-pds-policy-service/v1/policies/on-account";

    private static final int _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_BATCH_SIZE = 2000;

    private static final String _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_YOUNG =
            QsPdsPolicyWebServiceImpl.class.getName() + "_GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_YOUNG";

    private static final String _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_CACHE_NAME_OLD =
            QsPdsPolicyWebServiceImpl.class.getName() + "_GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_OLD";

    private static final String _GET_POLICY_ACCOUNT_SUMMARIES_BY_ACCOUNTS_URL =
            "/qs-pds-policy-service/v1/policy-summaries/on-account";

    private static final String _GET_TRANSACTION_CACHE_NAME = QsPdsPolicyWebServiceImpl.class.getName() + "_GET_TRANSACTION";

    private static final String _GET_TRANSACTION_URL = "/qs-pds-policy-service/v1/transactions";

    private static final String _GET_TRANSACTIONS_ON_POLICY_CACHE_NAME =
            QsPdsPolicyWebServiceImpl.class.getName() + "_GET_TRANSACTIONS_ON_POLICY";

    private static final String _GET_TRANSACTIONS_ON_POLICY_URL = "/qs-pds-policy-service/v1/transactions/on-policy";

    private PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberKey, List<CMICPolicyDTO>> _getPoliciesByPolicyNumbersPortalCache;
    private PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyDTO>> _getPoliciesOnAccountPortalCache;
    private PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyAccountSummaryDTO>> _getPolicyAccountSummariesByAccountsPortalCacheYoung;
    private PortalCache<QsPdsPolicyWebServiceImpl.AccountNumbersKey, List<CMICPolicyAccountSummaryDTO>> _getPolicyAccountSummariesByAccountsPortalCacheOld;
    private PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey, CMICTransactionDTO> _getTransactionPortalCache;
    private PortalCache<QsPdsPolicyWebServiceImpl.PolicyNumberKey, List<CMICTransactionDTO>> _getTransactionsOnPolicyPortalCache;

    @Reference
    private JSONFactory _jsonFactory;

    @Reference
    private MockQsPdsPolicyWebServiceClient _mockPolicyWebServiceClient;

    private MockQsPdsPolicyWebServiceConfiguration _mockPolicyWebServiceConfiguration;

    @Reference
    private SingleVMPool _singleVMPool;

    @Reference
    private WebServiceExecutor _webServiceExecutor;

    private static class AccountNumbersKey implements Serializable {

        @Override
        public boolean equals(Object obj) {
            QsPdsPolicyWebServiceImpl.AccountNumbersKey key = (QsPdsPolicyWebServiceImpl.AccountNumbersKey)obj;

            if (Objects.equals(key._accountNumbers, _accountNumbers)) {
                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return HashUtil.hash(0, _accountNumbers);
        }

        private AccountNumbersKey(String accountNumber) {
            _accountNumbers = ListUtil.toList(accountNumber);
        }

        private AccountNumbersKey(String[] accountNumbers) {
            _accountNumbers = ListUtil.fromArray(accountNumbers);
        }

        private static final long serialVersionUID = 1L;

        private final List<String> _accountNumbers;

    }

    private static class PolicyNumberAndSequenceNumberKey extends QsPdsPolicyWebServiceImpl.PolicyNumberKey {

        @Override
        public boolean equals(Object obj) {
            QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey key = (QsPdsPolicyWebServiceImpl.PolicyNumberAndSequenceNumberKey)obj;

            if (Objects.equals(key._policyNumbers, super._policyNumbers) &&
                    (key._sequenceNumber == _sequenceNumber)) {

                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            int hashCode = HashUtil.hash(0, super._policyNumbers);

            return HashUtil.hash(hashCode, _sequenceNumber);
        }

        private PolicyNumberAndSequenceNumberKey(String policyNumber, int sequenceNumber) {
            super(policyNumber);

            _sequenceNumber = sequenceNumber;
        }

        private static final long serialVersionUID = 1L;

        private final int _sequenceNumber;

    }

    private static class PolicyNumberKey implements Serializable {

        @Override
        public boolean equals(Object obj) {
            QsPdsPolicyWebServiceImpl.PolicyNumberKey key = (QsPdsPolicyWebServiceImpl.PolicyNumberKey)obj;

            if (Objects.equals(key._policyNumbers, _policyNumbers)) {
                return true;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return HashUtil.hash(0, _policyNumbers);
        }

        protected final List<String> _policyNumbers;

        private PolicyNumberKey(String policyNumber) {
            _policyNumbers = ListUtil.toList(policyNumber);
        }

        private PolicyNumberKey(String[] policyNumbers) {
            _policyNumbers = ListUtil.fromArray(policyNumbers);
        }

        private static final long serialVersionUID = 1L;

    }

    private static final Log _log = LogFactoryUtil.getLog(QsPdsPolicyWebServiceImpl.class);
}
