import React from 'react';
import {Dialog} from 'com.churchmutual.commons.web';

function PolicyChangeRequestModal(props) {

  return <Dialog
    title={Liferay.Language.get('policy-change-request')}
    confirmButtonText={Liferay.Language.get('continue')}
    onClickCancel={() => props.onClickCancel()}
    onClickConfirm={() => props.onClickContinue()}
    setVisible={props.setVisible}
    visible={props.visible}
    className="policy-change-request"
  >
    <div>
      <div className="first policy-change-request-step">
        <span className="title">{Liferay.Language.get('step-1')}</span>
        <span className="text">{Liferay.Language.get('request-changes-to-your-policy-via-email')}</span>
      </div>

      <div className="policy-change-request-step">
        <span className="title">{Liferay.Language.get('step-2')}</span>
        <span className="text">{Liferay.Language.get('you-will-be-connected-with-a-licensed-agent')}</span>
      </div>

      <div className="policy-change-request-step">
        <span className="title">{Liferay.Language.get('step-3')}</span>
        <span className="text">{Liferay.Language.get('you-and-your-agent-will-finalize-policy-updates')}</span>
      </div>

      <div className="disclaimer-text">{Liferay.Language.get('please-be-advised')}</div>
    </div>
  </Dialog>;
}

export {PolicyChangeRequestModal};