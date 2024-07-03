import React from 'react';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {AccountName, getFriendlyURL, navigate, Popover} from 'com.churchmutual.commons.web';
import NumberFormat from 'react-number-format';

const Navigate = (accountNumber, companyNumber) => {
  navigate('account-details?accountNumber=' + accountNumber + '&companyNumber=' + companyNumber);
}

const AccountList = (props) => {
  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        <ClayTable>
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell expanded headingCell>
                {Liferay.Language.get('name')}
              </ClayTable.Cell>

              <ClayTable.Cell expanded headingCell align="center">
                {Liferay.Language.get('in-force-policies')}
              </ClayTable.Cell>

              <ClayTable.Cell headingCell align="right" className="table-cell-ws-nowrap">
                <Popover message={Liferay.Language.get('this-value-is-an-estimate-and-may-not-reflect-the-exact-account-premium-costs')} />
                {Liferay.Language.get('account-premium')}
              </ClayTable.Cell>

            </ClayTable.Row>
          </ClayTable.Head>
          <ClayTable.Body>
            {props.accountsList.map((account, index) => (
              <ClayTable.Row
                key={index}
                className="cursor-pointer"
                onClick={() => Navigate(account.accountNumber, account.companyNumber)}>
                <ClayTable.Cell>
                  <h5 className="font-weight-bold mb-0 data-hj-suppress"><AccountName accountName={account.accountName} /></h5>
                  <small className="text-muted data-hj-suppress">#{account.accountNumber}</small>
                </ClayTable.Cell>
                <ClayTable.Cell align="center">{account.numInForcePolicies}</ClayTable.Cell>
                <ClayTable.Cell align="right" className="h3 font-weight-bold">
                  <NumberFormat decimalScale={2} displayType={'text'} fixedDecimalScale={true} prefix={'$'} thousandSeparator={true} value={account.totalBilledPremium} />
                </ClayTable.Cell>
              </ClayTable.Row>
            ))}
          </ClayTable.Body>
        </ClayTable>
        <div className="pt-2 px-2"><a href={getFriendlyURL('accounts')} className="link-action">{Liferay.Language.get('see-all-accounts')}</a></div>
      </React.Fragment>
    );
  }
};

export default AccountList;