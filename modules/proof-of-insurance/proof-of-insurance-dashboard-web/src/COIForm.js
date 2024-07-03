import React, {forwardRef, useImperativeHandle, useEffect, useState} from 'react';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {AsteriskIcon, DropDown, Input, isAndroid, Popover, Select} from 'com.churchmutual.commons.web';
import groupBy from 'lodash.groupby';
import {download} from './Download';
import EventTypeAdditionalQuestion from './EventTypeAdditionalQuestion';
import RequestSuccess from './RequestSuccess';
import RequestError from './RequestError';

const COIForm = forwardRef((props, ref) => {
  const [wouldYouLikeToAddAnInsuredToYourPolicy, setWouldYouLikeToAddAnInsuredToYourPolicy] = useState(false);
  const [selectedAccount, setSelectedAccount] = useState('');
  const [policiesAndLimits, setPoliciesAndLimits] = useState(null);
  const [policyDropDownItems, setPolicyDropDownItems] = useState([]);
  const [selectedPolicies, setSelectedPolicies] = useState([]);
  const [eventType, setEventType] = useState('');
  const [showContactCustomerService, setShowContactCustomerService] = useState(false);
  const [hasHNALimits, setHasHNALimits] = useState(false);
  const [hasSMLimits, setHasSMLimits] = useState(false);
  const [displayLimits, setDisplayLimits] = useState('dontDisplayLimits');
  const [certificateHolderName, setCertificateHolderName] = useState('');
  const [certificateHolderAddress1, setCertificateHolderAddress1] = useState('');
  const [certificateHolderAddress2, setCertificateHolderAddress2] = useState('');
  const [certificateHolderCity, setCertificateHolderCity] = useState('');
  const [certificateHolderState, setCertificateHolderState] = useState('');
  const [certificateHolderZipCode, setCertificateHolderZipCode] = useState('');

  const [willAlcoholBePresent, setWillAlcoholBePresent] = useState(false);
  const [newLocationOrExisting, setNewLocationOrExisting] = useState(false);
  const [isThisListedOnThePolicy, setIsThisListedOnThePolicy] = useState(true);
  const [anySalesOccurring, setAnySalesOccurring] = useState(false);
  const [willInflatablesBePresent, setWillInflatablesBePresent] = useState(false);

  const resetEventResponses = () => {
    setWillAlcoholBePresent(false);
    setNewLocationOrExisting(false);
    setIsThisListedOnThePolicy(true);
    setAnySalesOccurring(false);
    setWillInflatablesBePresent(false);
  }

  const handleEventTypeChange = (fieldName, fieldValue) => {
    setEventType(fieldValue);

    resetEventResponses();

    let hasErrors = ['afterSchoolProgram', 'athletic5kMarathons', 'retreatCamp'].includes(fieldValue);

    setShowContactCustomerService(hasErrors);
  }

  const policiesCallback = (data) => {
    try {
      const grouped = groupBy(data, policy => policy.policyNumber.split(' ')[0]);

      setPoliciesAndLimits(grouped);
    } catch (err) {
      props.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-list-of-policies'))
    }
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
      '/cmic.cmiccertificateofinsurance/download-coi-document',
      {
        address: certificateHolderAddress1,
        address2: certificateHolderAddress2,
        city: certificateHolderCity,
        fullName: certificateHolderName,
        postalCode: certificateHolderZipCode,
        state: certificateHolderState,
        policyNumbers: selectedPolicies.map(policy => selectedAccount + ' ' + policy.id),
        showHiredAndNonOwnedAutoLimits: displayLimits === 'displayHiredAndNonOwnedAutoLimits',
        showSMLimits: displayLimits === 'displaySexualMisconductLimits'
      },
      callback,
      errCallback
    );
  }

  useImperativeHandle(ref, () => ({ onClickDownload }));

  useEffect(() => {
      Liferay.Service(
        '/cmic.cmicqspolicy/get-coi-eligible-policies',
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
      setPolicyDropDownItems(policiesAndLimits[account].map((policy) => ({id: policy.policyNumber.substring(policy.policyNumber.indexOf(' ') + 1), type: policy.productName})));
    }
  }

  const onChangePolicies = (policies) => {
    setSelectedPolicies(policies);

    let selectedPoliciesAndLimits = policies.map(x => policiesAndLimits[selectedAccount].find(y => y.policyNumber === selectedAccount + ' ' + x.id));

    setHasHNALimits(selectedPoliciesAndLimits.findIndex(x => x.hasHiredAndNonOwnedAutoLimits) > -1);

    setHasSMLimits(selectedPoliciesAndLimits.findIndex(x => x.hasSexualMisconductLimits) > -1);
  }

  const formIsInvalid = () => {
    return selectedAccount === '' || selectedPolicies.length === 0 || wouldYouLikeToAddAnInsuredToYourPolicy ||
      eventType === '' ||
      eventType === 'afterSchoolProgram' ||
      eventType === 'athletic5kMarathons' ||
      eventType === 'churchPicnic' && willAlcoholBePresent ||
      eventType === 'churchService' && newLocationOrExisting ||
      eventType === 'daycareMothersDayOut' && !isThisListedOnThePolicy ||
      eventType === 'informationalBooth' && anySalesOccurring ||
      eventType === 'mortgagee' && !isThisListedOnThePolicy ||
      eventType === 'musicFestival' && (willAlcoholBePresent || willInflatablesBePresent) ||
      eventType === 'retreatCamp' ||
      certificateHolderName === '' || certificateHolderAddress1 === '' || certificateHolderCity === '' ||
      certificateHolderState === '' || certificateHolderZipCode === '';
  }

  useEffect(() => {
    props.setFormIsInvalid(formIsInvalid());
  });

  return (
    props.downloadStatus === 'success' ? <RequestSuccess isLoading={props.isLoading} onClickDownload={onClickDownload} text={Liferay.Language.get('your-certificate-of-insurance-should-begin-downloading-shortly')} /> :
    props.downloadStatus === 'error' ? <RequestError /> :
    <div className="proof-of-insurance-coi-form">
      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("1-select-the-account-for-this-certificate-of-insurance")}
        </div>
        <div className="proof-of-insurance-question__response">
          <Select
            handleFieldChange={onChangeAccount}
            fieldName={Liferay.Language.get('account-number')}
            label={Liferay.Language.get('account-number')}
            options={policiesAndLimits ? [{label: '', value: ''}].concat(Object.keys(policiesAndLimits).sort((a, b) => a - b).map((key) => ({label: key, value: key}))) : []}
            required={true}
            containsSensitiveInformation={true}
          />
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("2-select-the-policy-ies-for-this-certificate-of-insurance")}
        </div>
        <div className="proof-of-insurance-question__response">
          <div><label className="policy-selection-help-text">{Liferay.Language.get('each-policy-must-be-a-different-policy-type')}</label></div>

          <div>
            <label className="policy-selection-help-text">
              {Liferay.Language.get('only-one-of-certain-policy-types-can-be-selected')}
              <Popover
                alignPosition="top"
                bodyClassName="mw-100"
                className="popover-inner-pre"
                closeOnModalScroll={true}
                dynamic={true}
                message={Liferay.Language.get('tooltip-policy-types')} />
            </label>
          </div>

          <div className="pb-4">
            <label className="policy-selection-help-text">{Liferay.Language.get('maximum-of-4-policies-per-COI-request')}</label>
          </div>

          <DropDown
            captionDefault={Liferay.Language.get('policy-number-s')}
            closeOnModalScroll={true}
            dropDownItems={policyDropDownItems}
            disableByType={true}
            exclusionGroup={['02', '08', '21', '25']}
            limit={4}
            required={true}
            selected={selectedPolicies}
            setDropDownItems={setPolicyDropDownItems}
            setSelected={onChangePolicies}
            showLabels={true}
            containsSensitiveInformation={true}
          />
          <div className="pt-2"><label>{Liferay.Language.get('dont-see-your-policy')}</label></div>
        </div>
      </div>

      {hasHNALimits || hasSMLimits ?
        <div className="proof-of-insurance-question">
          <div className="proof-of-insurance-question__response">
            <ClayRadioGroup
              name="displayLimits"
              onSelectedValueChange={setDisplayLimits}
              selectedValue={displayLimits}
            >
              <ClayRadio
                label={Liferay.Language.get("dont-display-limits")}
                value={'dontDisplayLimits'}
              />
              {hasHNALimits ?
                <ClayRadio
                  label={Liferay.Language.get("display-hired-and-non-owned-auto-limits")}
                  value={'displayHiredAndNonOwnedAutoLimits'}
                /> : <React.Fragment />}
              {hasSMLimits ?
                <ClayRadio
                  label={Liferay.Language.get("display-sexual-misconduct-limits")}
                  value={'displaySexualMisconductLimits'}
                /> : <React.Fragment />}
            </ClayRadioGroup>
          </div>
        </div> : ''}

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("3-would-you-like-to-add-an-insured-to-your-policy")}<AsteriskIcon />
        </div>
        <div className="proof-of-insurance-question__response">
          <ClayRadioGroup
            name="wouldYouLikeToAddAnInsuredToYourPolicy"
            onSelectedValueChange={wouldYouLikeToAddAnInsuredToYourPolicy => setWouldYouLikeToAddAnInsuredToYourPolicy(wouldYouLikeToAddAnInsuredToYourPolicy)}
            selectedValue={wouldYouLikeToAddAnInsuredToYourPolicy ? wouldYouLikeToAddAnInsuredToYourPolicy : false}
          >

            <ClayRadio
              label={Liferay.Language.get("no")}
              value={false}
            />

            <ClayRadio
              label={Liferay.Language.get("yes")}
              value={true}
            />
          </ClayRadioGroup>
        </div>
        <div
          className={`proof-of-insurance-question__error pt-3 ${wouldYouLikeToAddAnInsuredToYourPolicy ? '' : 'hide'}`}
        >
          {Liferay.Language.get('to-add-an-insured-please-contact-customer-service')}
        </div>
      </div>

      <div className="proof-of-insurance-question">
        <div className="pb-4 proof-of-insurance-question__title">
          {Liferay.Language.get("4-why-are-you-requesting-a-certificate-of-insurance")}
        </div>
        <div className="proof-of-insurance-question__response">
          <Select
            handleFieldChange={handleEventTypeChange}
            fieldName={Liferay.Language.get('select-an-event')}
            label={Liferay.Language.get('select-an-event')}
            options={[
              {label: '', value: ''},
              {label: Liferay.Language.get('after-school-program'), value: 'afterSchoolProgram'},
              {label: Liferay.Language.get('athletic-5k-marathons'), value: 'athletic5kMarathons'},
              {label: Liferay.Language.get('bible-study'), value: 'bibleStudy'},
              {label: Liferay.Language.get('church-picnic'), value: 'churchPicnic'},
              {label: Liferay.Language.get('church-service'), value: 'churchService'},
              {label: Liferay.Language.get('conferences-key-note-speaker-and-food'), value: 'conferencesKeyNoteSpeakerAndFood'},
              {label: Liferay.Language.get('daycare-mothers-day-out'), value: 'daycareMothersDayOut'},
              {label: Liferay.Language.get('fundraisers'), value: 'fundraisers'},
              {label: Liferay.Language.get('grant'), value: 'grant'},
              {label: Liferay.Language.get('informational-booth'), value: 'informationalBooth'},
              {label: Liferay.Language.get('mortgagee'), value: 'mortgagee'},
              {label: Liferay.Language.get('music-festival'), value: 'musicFestival'},
              {label: Liferay.Language.get('parade'), value: 'parade'},
              {label: Liferay.Language.get('parking-lot'), value: 'parkingLot'},
              {label: Liferay.Language.get('retreat-camp'), value: 'retreatCamp'}
            ]}
            required={true}
          />

          <EventTypeAdditionalQuestion
            title={Liferay.Language.get('will-alcohol-be-present')}
            showAdditionalQuestion={eventType === 'churchPicnic' || eventType === 'musicFestival'}
            options={[{label: 'no', value: false}, {label: 'yes',  value: true}]}
            value={willAlcoholBePresent}
            errorValue={true}
            onChangeValue={setWillAlcoholBePresent}
          />

          <EventTypeAdditionalQuestion
            title={Liferay.Language.get('new-location-or-existing')}
            showAdditionalQuestion={eventType === 'churchService'}
            options={[{label: Liferay.Language.get('existing'), value: false}, {label: 'new',  value: true}]}
            value={newLocationOrExisting}
            errorValue={true}
            onChangeValue={setNewLocationOrExisting}
          />

          <EventTypeAdditionalQuestion
            title={Liferay.Language.get('is-this-listed-on-the-policy')}
            showAdditionalQuestion={eventType === 'daycareMothersDayOut' || eventType === 'mortgagee'}
            options={[{label: 'yes', value: true}, {label: 'no',  value: false}]}
            value={isThisListedOnThePolicy}
            errorValue={false}
            onChangeValue={setIsThisListedOnThePolicy}
          />

          <EventTypeAdditionalQuestion
            title={Liferay.Language.get('any-sales-occurring')}
            showAdditionalQuestion={eventType === 'informationalBooth'}
            options={[{label: 'no', value: false}, {label: 'yes',  value: true}]}
            value={anySalesOccurring}
            errorValue={true}
            onChangeValue={setAnySalesOccurring}
          />

          <EventTypeAdditionalQuestion
            title={Liferay.Language.get('will-inflatables-be-present')}
            showAdditionalQuestion={eventType === 'musicFestival'}
            options={[{label: 'no', value: false}, {label: 'yes',  value: true}]}
            value={willInflatablesBePresent}
            errorValue={true}
            onChangeValue={setWillInflatablesBePresent}
          />

          <div
            className={`proof-of-insurance-question__error pl-0 pt-3 ${showContactCustomerService ? '' : 'hide'}`}
          >
            {Liferay.Language.get('please-contact-customer-service-to-complete-your-form-request')}
          </div>

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
          </div>
        </div>
    </div>
  )
});

export default COIForm;
