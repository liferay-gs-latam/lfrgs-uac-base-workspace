import React from 'react';
import ClayBreadcrumb from '@clayui/breadcrumb';
import {AccountName} from 'com.churchmutual.commons.web';

const AccountInfo = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

  if (!props.isLoading) {
    return (
      <React.Fragment>
        <div>
          <div className="flex-container align-items-center">
            <h3 className="mb-0 data-hj-suppress"><AccountName accountName={props.account.accountName} /></h3>
            <small className="font-weight-bold text-muted ml-4 data-hj-suppress">{props.account.accountNumber}</small>
          </div>
          <ClayBreadcrumb
            items={[
              {
                href: 'accounts',
                label: 'Accounts'
              },
              {
                active: true,
                label:
                  <div className={"data-hj-suppress"}>
                    <AccountName accountName={props.account.accountName}/>
                  </div>
              }
            ]}
            spritemap={spritemap}
            className="mb-sm-0 pb-0"
          />
        </div>
        <div className="well">
          <h6 className="well-title">{Liferay.Language.get('producer-organization')}</h6>
          <div className="well-subtitle data-hj-suppress">{props.account.producerEntity}</div>
          <div className="small font-weight-bold text-muted data-hj-suppress">{props.account.producerEntityCode}</div>
        </div>
      </React.Fragment>
    );
  }
  else {
    return '';
  }
};

export default AccountInfo;