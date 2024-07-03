import React, {useRef, useState, useEffect} from 'react';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import COIForm from "./COIForm";
import NYWCForm from "./NYWCForm";
import {Dialog, Toast} from "com.churchmutual.commons.web";
import EOPForm from "./EOPForm";

const ProofOfInsuranceTypeSelect = (props) => {
  const [formIsInvalid, setFormIsInvalid] = useState(false);
  const [currentPage, setCurrentPage] = useState('');
  const [proofOfInsuranceTypeField, setProofOfInsuranceTypeField] = useState('');
  const [nywcAccountDisplay, setNYWCAccountDisplay] = useState(false);
  const [downloadStatus, setDownloadStatus] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [toastDisplayType, setToastDisplayType] = useState('');
  const [toastMessage, setToastMessage] = useState('');
  const [toastTitle, setToastTitle] = useState('');

  const coiFormRef = useRef(null);

 const accountCallback = (data) => {
    try {
		if(data.length>0){
			setNYWCAccountDisplay(true);
		}else{
			setNYWCAccountDisplay(false);
		}
      
    } catch (err) {
      props.displayErrorMessage(Liferay.Language.get('error.unable-to-retrieve-list-of-policies'))
    }
  }

  const displayErrorMessage = (msg) => {
    setToastDisplayType('danger');
    setToastMessage(msg);
    setToastTitle(Liferay.Language.get('error-colon'))
  }

  const displaySuccessMessage = (msg) => {
    setToastDisplayType('success');
    setToastMessage(msg);
  }

  const hasSubmittedForm = () => {
    return currentPage === 'Download';
  }

  const onToastClosed = () => {
    setToastDisplayType('');
    setToastMessage('');
    setToastTitle('');
  }

  const onCancel = () => {
    props.showRequestDialog(false);
    setFormIsInvalid(false);
    setCurrentPage('');
    setProofOfInsuranceTypeField('');
    setDownloadStatus('');
    setIsLoading(false);
  }

 const modalTitleChange = ()=>{
	if(proofOfInsuranceTypeField === 'new-york-workers-compensation'){
		return Liferay.Language.get('new-york-workers-compensation-proof-of-insurance');
	}else{
		return Liferay.Language.get('request-a-proof-of-insurance');
	}
}

  useEffect(() => {
      Liferay.Service(
        '/cmic.cmicqspolicy/get-nywc-display',
        accountCallback
      );
    },
    []);

  const nextPage = () => {
    if (currentPage === '' && proofOfInsuranceTypeField !== '') {
      setCurrentPage('Form');
    }
    else if (currentPage === 'Form' && !formIsInvalid) {
      coiFormRef.current.onClickDownload();
    }
    else if (currentPage === 'Download') {
      onCancel();
    }
  }

  return (
    <React.Fragment>
      <Dialog
        className={`proof-of-insurance-portlet ${currentPage === 'Form' ? 'modal-expanded' : currentPage === 'Download' ? 'modal-shrink' : ''}`}
        scrollable="true"
        title={hasSubmittedForm() ? Liferay.Language.get('download-your-proof-of-insurance') : modalTitleChange()}
        confirmButtonText={currentPage === '' ? Liferay.Language.get('next') : currentPage === 'Form' ? Liferay.Language.get('download') : Liferay.Language.get('done')}
        disableConfirm={currentPage === '' ? proofOfInsuranceTypeField === '' : currentPage === 'Form' ? formIsInvalid : false}
        closeOnConfirm={downloadStatus === 'success' || downloadStatus === 'error'}
        onClickCancel={onCancel}
        onClickConfirm={nextPage}
        hideCancel={hasSubmittedForm()}
        loading={downloadStatus === 'success' || downloadStatus === 'error' ? false : isLoading}
        visible={props.visible}
        setVisible={(show) => props.showRequestDialog(show)}
      >
        {currentPage === '' ?
          <div className="proof-of-insurance-type-select-wrapper">
            <div className="proof-of-insurance-subtitle">
              {Liferay.Language.get('which-proof-of-insurance-do-you-need')}
            </div>
            <div className="proof-of-insurance-type-select">
              <ClayRadioGroup
                className="proof-of-insurance-radio-group"
                inline="true"
                name="proofOfInsuranceType"
                onSelectedValueChange={proofOfInsuranceTypeField => setProofOfInsuranceTypeField(proofOfInsuranceTypeField)}
                selectedValue={proofOfInsuranceTypeField}
              >
                <ClayRadio
                  label={Liferay.Language.get("evidence-of-property-insurance")}
                  value="evidence-of-property-insurance"
                />

                <div className={`proof-of-insurance-help eop-help-text-mobile ${proofOfInsuranceTypeField === 'evidence-of-property-insurance' ? '' : 'hide'}`}>
                  {Liferay.Language.get("evidence-of-property-insurance-help-text")}
                </div>

                <ClayRadio
                  label={Liferay.Language.get("certificate-of-insurance")}
                  value="certificate-of-insurance"
                />

				{nywcAccountDisplay && currentPage === '' ? 
				
            	
				<ClayRadio 
                  		label={Liferay.Language.get("new-york-workers-compensation")}
                  		value="new-york-workers-compensation"
                	/>
			 :<div className="hide-it"><ClayRadio /></div>}
				
              </ClayRadioGroup>

              <div className="d-flex justify-content-center flex-wrap">
                <div className={`proof-of-insurance-help eop-help-text-desktop ${proofOfInsuranceTypeField === 'evidence-of-property-insurance' ? '' : 'hide'}`}>
                  {Liferay.Language.get("evidence-of-property-insurance-help-text")}
                </div>

				<div className={`proof-of-insurance-help ${proofOfInsuranceTypeField === 'new-york-workers-compensation' ? '' : 'hide'}`}>
                  {Liferay.Language.get("new-york-workers-compensation-help-text")}
                </div>

                <div className={`proof-of-insurance-help ${proofOfInsuranceTypeField === 'certificate-of-insurance' ? '' : 'hide'}`}>
                  {Liferay.Language.get("certificate-of-insurance-help-text")}
                </div>
              </div>
            </div>
          </div> :
          (currentPage === 'Form' || currentPage === 'Download') && proofOfInsuranceTypeField === 'certificate-of-insurance' ?
            <COIForm
              displayErrorMessage={(msg) => displayErrorMessage(msg)}
              displaySuccessMessage={(msg) => displaySuccessMessage(msg)}
              setFormIsInvalid={setFormIsInvalid}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              downloadStatus={downloadStatus}
              setDownloadStatus={setDownloadStatus}
              isLoading={isLoading}
              setIsLoading={setIsLoading}
              ref={coiFormRef} /> :
          (currentPage === 'Form' || currentPage === 'Download') && proofOfInsuranceTypeField === 'evidence-of-property-insurance' ?
            <EOPForm
              displayErrorMessage={(msg) => displayErrorMessage(msg)}
              displaySuccessMessage={(msg) => displaySuccessMessage(msg)}
              setFormIsInvalid={setFormIsInvalid}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              downloadStatus={downloadStatus}
              setDownloadStatus={setDownloadStatus}
              isLoading={isLoading}
              setIsLoading={setIsLoading}
              ref={coiFormRef} /> : 
		(currentPage === 'Form' || currentPage === 'Download') && proofOfInsuranceTypeField === 'new-york-workers-compensation' ?
            <NYWCForm
              displayErrorMessage={(msg) => displayErrorMessage(msg)}
              displaySuccessMessage={(msg) => displaySuccessMessage(msg)}
              setFormIsInvalid={setFormIsInvalid}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
              downloadStatus={downloadStatus}
              setDownloadStatus={setDownloadStatus}
              isLoading={isLoading}
              setIsLoading={setIsLoading}
              ref={coiFormRef} />: ''}
      </Dialog>

      <Toast
        displayType={toastDisplayType}
        message={toastMessage}
        onClose={() => onToastClosed()}
        title={toastTitle} />
    </React.Fragment>
  )
};

export default ProofOfInsuranceTypeSelect;