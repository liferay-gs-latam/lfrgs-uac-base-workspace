import React, {forwardRef, useImperativeHandle, useEffect, useState} from 'react';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {AsteriskIcon, DropDown, Input, isAndroid, Popover, Select} from 'com.churchmutual.commons.web';
import groupBy from 'lodash.groupby';
import {download} from './Download';
import RequestSuccess from './RequestSuccess';
import RequestError from './RequestError';

const NYWCForm = forwardRef((props, ref) => {
  const [selectedAccount, setSelectedAccount] = useState('');
  const [policiesAndLimits, setPoliciesAndLimits] = useState(null);
  const [policyDropDownItems, setPolicyDropDownItems] = useState([]);
  const [selectedPolicies, setSelectedPolicies] = useState([]);
  const [certificateHolderName, setCertificateHolderName] = useState('');
  const [certificateHolderAddress1, setCertificateHolderAddress1] = useState('');
  const [certificateHolderAddress2, setCertificateHolderAddress2] = useState('');
  const [certificateHolderCity, setCertificateHolderCity] = useState('');
  const [certificateHolderState, setCertificateHolderState] = useState('');
  const [certificateHolderZipCode, setCertificateHolderZipCode] = useState('');
  const [businessPhoneNumber, setBusinessPhoneNumber] = useState('');

  const policiesCallback = (data) => {
    try {
      const grouped = groupBy(data, policyNumber => policyNumber.split(' ')[0]);

      setPoliciesAndLimits(grouped);
    } catch (err) {
      props.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-list-of-policies'))
    }
  }
  const formatPhoneNumber=(value)=> {
	let number ='';
    if (!value) return value;

    const phoneNumber = value.replace(/[^\d]/g, "");

    const phoneNumberLength = phoneNumber.length;

    if (phoneNumberLength < 4) number= phoneNumber;

    if (phoneNumberLength < 7) {
      number= `(${phoneNumber.slice(0, 3)}) ${phoneNumber.slice(3)}`;
    }

    number= `(${phoneNumber.slice(0, 3)}) ${phoneNumber.slice(
      3,
      6
    )}-${phoneNumber.slice(6, 10)}`;

	  setBusinessPhoneNumber(number);
  }


  const onClickDownload = () => {
    if (formIsInvalid()) {
      return;
    }

    props.setIsLoading(true);

    let callback = (data) => {
      props.setDownloadStatus('success');
      props.setCurrentPage('Download');
      props.setIsLoading(false);

      download(data.bytes, data.fileName, props.displaySuccessMessage);
    }

    let errCallback = () => {
      props.setDownloadStatus('error');
      props.setCurrentPage('Download');
      props.setIsLoading(false);
    }

    Liferay.Service(
      '/cmic.cmiccertificateofinsurance/download-nywc-document',
      {
        address: certificateHolderAddress1,
        address2: certificateHolderAddress2,
        city: certificateHolderCity,
        fullName: certificateHolderName,
        postalCode: certificateHolderZipCode,
        state: certificateHolderState,
        policyNumber: selectedAccount + ' ' + selectedPolicies[0].id,
		telephoneNumber: businessPhoneNumber
        
      },
      callback,
      errCallback
    );
  }

  useImperativeHandle(ref, () => ({ onClickDownload }));

  useEffect(() => {
      Liferay.Service(
        '/cmic.cmicqspolicy/get-nywc-eligible-policies',

        policiesCallback
      );
    },
    []);

  const onChangeAccount = (fieldName, fieldValue) => {
    let account = fieldValue;

    setSelectedAccount(account);

    if (account === '') {
      setSelectedPolicies([]);
      setPolicyDropDownItems([]);
    }
    else if (policiesAndLimits && policiesAndLimits[account]) {
      setSelectedPolicies([]);
      setPolicyDropDownItems(policiesAndLimits[account].map((policyNumber) => ({id: policyNumber.substring(policyNumber.indexOf(' ') + 1), type: "WORKER'S COMPENSATION"})));
    }
  }

  const onChangePolicies = (policies) => {
    setSelectedPolicies(policies);
  }

  const formIsInvalid = () => {
    return selectedAccount === '' || selectedPolicies.length === 0 ||
      certificateHolderName === '' || certificateHolderAddress1 === '' || certificateHolderCity === '' ||
      certificateHolderState === '' || certificateHolderZipCode === '' || businessPhoneNumber==='';
  }

  useEffect(() => {
    props.setFormIsInvalid(formIsInvalid());
  });

  return (
    props.downloadStatus === 'success' ? <RequestSuccess isLoading={props.isLoading} onClickDownload={onClickDownload} text={Liferay.Language.get('your-new-york-workers-compensation-should-begin-downloading-shortly')} /> :
    props.downloadStatus === 'error' ? <RequestError /> :
    <div className="proof-of-insurance-coi-form">
      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("1-select-the-account-for-this-new-york-workers-compensation")}
        </div>
        <div className="proof-of-insurance-question__response">
          <Select
            handleFieldChange={onChangeAccount}
            fieldName={Liferay.Language.get('account-number')}
            label={Liferay.Language.get('account-number')}
            options={policiesAndLimits ? [{label: '', value: ''}].concat(Object.keys(policiesAndLimits).sort((a, b) => a - b).map((key) => ({label: key, value: key}))) : []}
            required={true}
          />
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("2-select-the-policy-ies-for-this-new-york-workers-compensation")}
        </div>
        <div className="proof-of-insurance-question__response">
        
          <DropDown
            captionDefault={Liferay.Language.get('policy-number-s')}
            closeOnModalScroll={true}
            dropDownItems={policyDropDownItems}
            disableByType={true}
            exclusionGroup={['02', '08', '21', '25']}
            limit={2}
            required={true}
            selected={selectedPolicies}
            setDropDownItems={setPolicyDropDownItems}
            setSelected={onChangePolicies}
            showLabels={true}
          >
            <div className="pt-2"><label>{Liferay.Language.get('dont-see-your-policy')}</label></div>
          </DropDown>
        </div>
      </div>

        <div className="proof-of-insurance-question">
          <div className="pb-4 proof-of-insurance-question__title">
            {Liferay.Language.get("5-certificate-holder-information")}
          </div>
          <div className="proof-of-insurance-question__response certificate-holder-question">
            <Input
              fieldName="certificateHolderName"
              handleFieldChange={(fieldName, fieldValue) => setCertificateHolderName(fieldValue)}
              label={Liferay.Language.get("name")}
              required={true}
              value={certificateHolderName}
            />
            <Input
              fieldName="certificateHolderAddress1"
              handleFieldChange={(fieldName, fieldValue) => setCertificateHolderAddress1(fieldValue)}
              label={Liferay.Language.get("address-line-1")}
              required={true}
              value={certificateHolderAddress1}
            />
            <Input
              fieldName="certificateHolderAddress2"
              handleFieldChange={(fieldName, fieldValue) => setCertificateHolderAddress2(fieldValue)}
              label={Liferay.Language.get("address-line-2")}
              required={false}
              value={certificateHolderAddress2}
            />
            <Input
              fieldName="certificateHolderCity"
              handleFieldChange={(fieldName, fieldValue) => setCertificateHolderCity(fieldValue)}
              label={Liferay.Language.get("city")}
              required={true}
              value={certificateHolderCity}
            />
            <div className="d-flex">
              <Input
                fieldName="certificateHolderState"
                handleFieldChange={(fieldName, fieldValue) => setCertificateHolderState(fieldValue)}
                label={Liferay.Language.get("state")}
                required={true}
                value={certificateHolderState}
              />

              <div className="flex-grow-1">
                <Input
                  fieldName="certificateHolderZipCode"
                  handleFieldChange={(fieldName, fieldValue) => setCertificateHolderZipCode(fieldValue)}
                  label={Liferay.Language.get("zip")}
                  required={true}
                  value={certificateHolderZipCode}
                />
              </div>
            </div>
 		  <Input
				
              	fieldName="businessPhoneNumber"
              	handleFieldChange={(fieldName, fieldValue) => formatPhoneNumber(fieldValue)}
              	label={Liferay.Language.get("phone-number")}
              	required={true}
              	value={businessPhoneNumber}
            />
          </div>
        </div>
    </div>
  )
});

export default NYWCForm;