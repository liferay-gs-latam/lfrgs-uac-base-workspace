import React from 'react';
import ClayBreadcrumb from '@clayui/breadcrumb';
import Ellipsis from '@clayui/breadcrumb/lib/Ellipsis';
import {navigate, CardPageHeader} from 'com.churchmutual.commons.web';

const Navigate = (accountNumber, companyNumber) => {
  navigate('account-details?accountNumber=' + accountNumber + '&companyNumber=' + companyNumber);
}

const AccountInfo = ({policy, userIsProducer}) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

  return (
    userIsProducer ? (
      <React.Fragment>
        <CardPageHeader>
          <div>
            <div className="flex-container align-items-center">
              <h3 className="mb-0 data-hj-suppress">{policy.accountName}</h3>
              <small className="font-weight-bold text-muted ml-4 data-hj-suppress">{policy.accountNumber}</small>
            </div>
            <ClayBreadcrumb
              items={[
                {
                  href: 'accounts',
                  label: `${Liferay.Language.get('accounts')}`
                },
                {
                  label: <div className={"data-hj-suppress"}>{policy.accountName}</div>,
                    onClick: () => Navigate(policy.accountNumber, policy.companyNumber)
                },
                {
                  label: <Ellipsis spritemap={spritemap} items={[{label: `${policy.accountName}`, onClick: () => Navigate(policy.accountNumber, policy.companyNumber)}]} />
                },
                {
                  active: true,
                  label: <div>{policy.productName}</div>
                }
              ]}
              spritemap={spritemap}
              className="mb-sm-0 pb-0 policy-details-breadcrumbs"
            />
          </div>
          <div className="well">
            <h6 className="well-title">{Liferay.Language.get('producer-organization')}</h6>
            <div className="well-subtitle data-hj-suppress">{policy.producerEntity}</div>
            <div className="small font-weight-bold text-muted data-hj-suppress">{policy.producerEntityCode}</div>
          </div>
        </CardPageHeader>
      </React.Fragment>
    ) : (
      <React.Fragment>
        <div>
          <ClayBreadcrumb
            items={[
              {
                href: 'policies',
                label: `${Liferay.Language.get('my-policies')}`
              },
              {
                active: true,
                label: <span>{policy.productName} <span className="neutral-4 data-hj-suppress">(#{policy.policyNumber})</span></span>
              }
            ]}
            spritemap={spritemap}
            className="mb-sm-0 pb-0"
          />
        </div>
      </React.Fragment>
    )
  );
};

export default AccountInfo;