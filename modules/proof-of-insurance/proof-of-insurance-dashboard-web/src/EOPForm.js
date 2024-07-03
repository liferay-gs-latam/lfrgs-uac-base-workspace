import React, {forwardRef, useImperativeHandle, useEffect, useState} from 'react';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {AsteriskIcon, DropDown, isAndroid, Select} from 'com.churchmutual.commons.web';
import groupBy from 'lodash.groupby';
import {download} from "./Download";
import RequestSuccess from "./RequestSuccess";
import RequestError from "./RequestError";

const EOPForm = forwardRef((props, ref) => {
  const [isThisFormForAnExistingOrNewProperty, setIsThisFormForAnExistingOrNewProperty] = useState(false);
  const [selectedAccount, setSelectedAccount] = useState('');
  const [policies, setPolicies] = useState(null);
  const [policyDropDownItems, setPolicyDropDownItems] = useState([]);
  const [selectedPolicy, setSelectedPolicy] = useState([]);
  const [location, setLocation] = useState('');
  const [locationList, setLocationList] = useState([]);
  const [additionalInterestType, setAdditionalInterestType] = useState('no-additional-interest');
  const [additionalInterest, setAdditionalInterest] = useState('');
  const [additionalInterestList, setAdditionalInterestList] = useState([]);

  const formIsInvalid = () => {
    return selectedAccount === '' || selectedPolicy.length === 0 || location === '' ||
        isThisFormForAnExistingOrNewProperty ||
        additionalInterestType === 'additional-insured' ||
        additionalInterestType === 'lenders-loss-payable' ||
        ((additionalInterestType === 'loss-payee' || additionalInterestType === 'mortgagee') && (additionalInterestList.length == 0 || (additionalInterestList.length > 0 && additionalInterest === '')));
  }

  useEffect(() => {
    props.setFormIsInvalid(formIsInvalid());
  });

  const onClickDownload = () => {
    if (formIsInvalid()) {
      return;
    }

    props.setIsLoading(true);

    let cmicAdditionalInterestDisplay = {};

    if (additionalInterest != null && additionalInterest !== '') {
      cmicAdditionalInterestDisplay = JSON.parse(additionalInterest);
    }

    cmicAdditionalInterestDisplay.mortgagee = (additionalInterestType === 'mortgagee');

    const callback = (data) => {
      props.setDownloadStatus('success');
      props.setCurrentPage('Download');
      props.setIsLoading(false);

      download(data.bytes, data.fileName, props.displaySuccessMessage);
    }

    const errCallback = () => {
      props.setDownloadStatus('error');
      props.setCurrentPage('Download');
      props.setIsLoading(false);
    }

    Liferay.Service(
      '/cmic.cmiccertificateofinsurance/download-eop-document',
      {
        cmicAdditionalInterestDisplay: JSON.stringify(cmicAdditionalInterestDisplay),
        policyNumber: selectedAccount + ' ' + selectedPolicy[0].id,
        cmicBuildingDisplay: location
      },
      callback,
      errCallback
    );
  }

  useImperativeHandle(ref, () => ({ onClickDownload }));

  const policiesCallback = (data) => {
    try {
      const grouped = groupBy(data, policy => policy.policyNumber.split(' ')[0]);

      setPolicies(grouped);
    } catch (err) {
      props.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-list-of-policies'))
    }
  }

  useEffect(() => {
    Liferay.Service(
      '/cmic.cmicqspolicy/get-eop-eligible-policies',
      policiesCallback
    );
  },
  []);

  const getAdditionalInterestList = (additionalInterestType) => {
    let callback = (data) => {
      if (data.length == 0) {
        setAdditionalInterestList([]);
        return;
      }

      let list = [{label: '', value: ''}];

      data.forEach(
        item => list.push(
          {
            label: `${item.name}\n${item.addressLine1} ${item.addressLine2 ? item.addressLine2 : ''} ${item.city}, ${item.state} ${item.postalCode}`,
            value: JSON.stringify(item)
          }
        )
      );

      setAdditionalInterestList(list);
    }

    let additionalInterestTypeReferenceId = additionalInterestType === 'loss-payee' ?  1 : additionalInterestType === 'mortgagee' ? 2 : 0;

    let locationJSON = JSON.parse(location);

    Liferay.Service(
      '/cmic.cmicqspolicy/get-additional-interests-by-buildings',
      {
        additionalInterestTypeReferenceId: additionalInterestTypeReferenceId,
        policyNumber: selectedAccount + ' ' + selectedPolicy[0].id,
        buildingNumber: locationJSON.buildingNumber,
        locationPremisesNumber: locationJSON.locationPremisesNumber
      },
      callback
    );
  }

  const getLocationsList = (policyNumber) => {
    let callback = (data) => {
      let list = [{label: '', value: ''}];

      data.forEach(
        item => list.push(
          {
            label: `${item.addressLine1} ${item.addressLine2 ? item.addressLine2 : ''} ${item.city}, ${item.state} ${item.postalCode}\n
             Premise #${item.locationPremisesNumber} - Building #${item.buildingNumber}`,
            value: JSON.stringify(item)
          }
          )
      );

      setLocationList(list);
    }

    Liferay.Service(
      '/cmic.cmicqspolicy/get-buildings-by-policy',
      {
        policyNumber: policyNumber
      },
      callback
    );
  }

  const setAdditionalInterestTypeValue = (additionalInterestType) => {
    setAdditionalInterestType(additionalInterestType);

    setAdditionalInterest('');

    if (selectedAccount !== '' && selectedPolicy.length !== 0 && location && location !== '' && (additionalInterestType === 'loss-payee' || additionalInterestType === 'mortgagee')) {
      getAdditionalInterestList(additionalInterestType);
    }
  }

  const setLocationValue = (fieldName, fieldValue) => {
    setLocation(fieldValue);

    setAdditionalInterestType('no-additional-interest');
    setAdditionalInterest('');
  }

  const setPolicyValue = (policy) => {
    setSelectedPolicy(policy);

    setIsThisFormForAnExistingOrNewProperty(false);
    setLocation('');
    setLocationList([]);
    setAdditionalInterestType('no-additional-interest');
    setAdditionalInterest('');

    if (selectedAccount !== '' && policy.length !== 0) {
      getLocationsList(selectedAccount + ' ' + policy[0].id);
    }
  }

  const onChangeAccount = (fieldName, fieldValue) => {
    let account = fieldValue;

    setSelectedAccount(account);
    setSelectedPolicy([]);
    setIsThisFormForAnExistingOrNewProperty(false);
    setLocation('');
    setLocationList([]);
    setAdditionalInterestType('no-additional-interest');
    setAdditionalInterest('');

    if (account === '') {
      setPolicyDropDownItems([]);
    }
    else if (policies && policies[account]) {
      setPolicyDropDownItems(policies[account].map((policy) => ({id: policy.policyNumber.substring(policy.policyNumber.indexOf(' ') + 1), type: policy.productName})));
    }
  }

  return (
    props.downloadStatus === 'success' ? <RequestSuccess isLoading={props.isLoading} onClickDownload={onClickDownload} text={Liferay.Language.get('your-evidence-of-property-insurance-should-begin-downloading-shortly')}/> :
    props.downloadStatus === 'error' ? <RequestError /> :
    <div className="proof-of-insurance-eop-form">
      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("1-select-the-account-for-this-evidence-of-property-insurance")}
        </div>
        <div className="proof-of-insurance-question__response">
          <Select
            handleFieldChange={onChangeAccount}
            fieldName={Liferay.Language.get('account-number')}
            label={Liferay.Language.get('account-number')}
            options={policies ? [{label: '', value: ''}].concat(Object.keys(policies).sort((a, b) => a - b).map((key) => ({label: key, value: key}))) : []}
            required={true}
            containsSensitiveInformation={true}
          />
          <div className="proof-of-insurance-question__help">
            {Liferay.Language.get('dont-see-your-account')}
          </div>
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("2-select-the-policy-for-this-evidence-of-property-insurance")}
        </div>
        <div className="proof-of-insurance-question__response">
          <DropDown
            captionDefault={Liferay.Language.get('policy-number')}
            closeOnModalScroll={true}
            dropDownItems={policyDropDownItems}
            limit={1}
            required={true}
            selected={selectedPolicy}
            setDropDownItems={setPolicyDropDownItems}
            setSelected={(policy) => setPolicyValue(policy)}
            containsSensitiveInformation={true}
          />
          <div className="pt-4 proof-of-insurance-question__help">
            {Liferay.Language.get('dont-see-your-policy')}
          </div>
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("3-is-this-form-for-an-existing-or-new-property")}<AsteriskIcon />
        </div>
        <div className="proof-of-insurance-question__response">
          <ClayRadioGroup
            name="isThisFormForAnExistingOrNewProperty"
            onSelectedValueChange={isThisFormForAnExistingOrNewProperty => setIsThisFormForAnExistingOrNewProperty(isThisFormForAnExistingOrNewProperty)}
            selectedValue={isThisFormForAnExistingOrNewProperty ? isThisFormForAnExistingOrNewProperty : false}
          >

            <ClayRadio
              label={Liferay.Language.get("existing-property")}
              value={false}
            />

            <ClayRadio
              label={Liferay.Language.get("new-property")}
              value={true}
            />
          </ClayRadioGroup>
        </div>
        <div
          className={`proof-of-insurance-question__error pt-3 ${isThisFormForAnExistingOrNewProperty ? '' : 'hide'}`}
        >
          {Liferay.Language.get('if-youd-like-to-add-new-property-to-an-evidence-of-property-insurance-form-please-contact-customer-service')}
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("4-where-does-the-property-currently-reside")}
        </div>
        <div className="proof-of-insurance-question__response">
          <Select
            handleFieldChange={(fieldName, fieldValue) => setLocationValue(fieldName, fieldValue)}
            fieldName='location'
            label={Liferay.Language.get('location-s')}
            options={locationList}
            required={true}
            containsSensitiveInformation={true}
          />
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("5-would-you-like-to-include-an-additional-interest-from-your-existing-policy")}<AsteriskIcon />
        </div>
        <div className="proof-of-insurance-question__response">
          <ClayRadioGroup
            name="additionalInterestType"
            onSelectedValueChange={additionalInterestType => setAdditionalInterestTypeValue(additionalInterestType)}
            selectedValue={additionalInterestType ? additionalInterestType : 'no-additional-interest'}
          >

            <ClayRadio
              label={Liferay.Language.get("no-additional-interest")}
              value="no-additional-interest"
            />

            <ClayRadio
              label={Liferay.Language.get("additional-insured")}
              value="additional-insured"
            />

            <ClayRadio
              label={Liferay.Language.get("lenders-loss-payable")}
              value="lenders-loss-payable"
            />

            <ClayRadio
              label={Liferay.Language.get("loss-payee")}
              value="loss-payee"
            />

            <ClayRadio
              label={Liferay.Language.get("mortgagee")}
              value="mortgagee"
            />
          </ClayRadioGroup>
        </div>
        {(additionalInterestType == 'lenders-loss-payable' || additionalInterestType == 'additional-insured') &&
          <div className='proof-of-insurance-question__error pt-3'>
            {Liferay.Language.get('please-contact-customer-service-to-complete-your-form-request')}
          </div>
        }

        {(additionalInterestType == 'loss-payee' || additionalInterestType == 'mortgagee') && additionalInterestList.length == 0 &&
          <div className='proof-of-insurance-question__error pt-3'>
            {Liferay.Util.sub(Liferay.Language.get('there-are-no-x-additional-interests-for-the-selected-property'), Liferay.Language.get(additionalInterestType))}
          </div>
        }
      </div>

      {(additionalInterestType == 'loss-payee' || additionalInterestType == 'mortgagee') &&
        <div className="proof-of-insurance-question">
          {additionalInterestList.length > 0 &&
            <React.Fragment>
              <div className="pb-4 proof-of-insurance-question__title">
                {Liferay.Language.get("select-the-additional-interest")}
              </div>
              <div className="proof-of-insurance-question__response">
                <Select
                  handleFieldChange={(fieldName, fieldValue) => setAdditionalInterest(fieldValue)}
                  fieldName='additionalInterest'
                  label={Liferay.Language.get('additional-interest')}
                  options={additionalInterestList}
                  required={true}
                />
              </div>
            </React.Fragment>
          }
        </div>
      }
    </div>
  )
});

export default EOPForm;