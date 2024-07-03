import React from 'react';
import ReactBigList from 'react-big-list';
import NumberFormat from 'react-number-format';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {AccountName, getFriendlyURL, navigate} from 'com.churchmutual.commons.web';
import {Render, TableHeadings, TableSort} from './PolicyTableHelpers';

const formatDate = (dateString) => {
	return dateString.replace(/^0*([1-9]\d*)\/0*([1-9]\d*)/, "$1/$2");
}

const Navigate = (policy) => {
	navigate('policy-details?accountNumber=' + policy.accountNumber + '&companyNumber=' + policy.companyNumber + '&policyNumber=' + policy.policyNumber);
}

const PolicyList = (props) => {
	if (props.isLoading) {
		return (<ClayLoadingIndicator />);
	}
	else if (props.policiesList.length == 0) {
		return (<div className="mt-2">{Liferay.Language.get('no-policy-information-is-available')}</div>);
	}
	else {
		return (
			<div className="card-body fade-table scrollable-table mobile-table-padding">
				<ReactBigList members={props.policiesList}>
					{({
							displayedMembers,
							setSort,
							sortColumn,
							sortDirection,
						}) => (
					<React.Fragment>
						<ClayTable>
							<ClayTable.Head>
								<ClayTable.Row>
									{TableHeadings.map((heading, i) => (
										<ClayTable.Cell key={i} headingCell className="table-cell-ws-nowrap" {...heading.props}>
											{Render(heading.popover)}
											<TableSort
												sortable={heading.sortable}
												sortBy={heading.sortBy}
												setSort={setSort}
												sortColumn={sortColumn}
												sortDirection={sortDirection}
											>
												{Render(heading.text)}
											</TableSort>
										</ClayTable.Cell>
									))}
								</ClayTable.Row>
							</ClayTable.Head>
							<ClayTable.Body>
								{displayedMembers.map((policy, index) => (
									<ClayTable.Row
										key={index}
										className="cursor-pointer"
										onClick={() => Navigate(policy)}>
										<ClayTable.Cell>
											<h5 className="font-weight-bold mb-0">{policy.productName}</h5>
											<small className="text-muted data-hj-suppress">#{policy.policyNumber}</small>
										</ClayTable.Cell>

										<ClayTable.Cell className={"data-hj-suppress"}><AccountName accountName={policy.accountName}/><div className="subtitle-label">#{policy.accountNumber}</div></ClayTable.Cell>

										<ClayTable.Cell align="right" className={`text-nowrap ${policy.isExpired ? "expired-text" : ""}`}><div>{formatDate(policy.effectiveDate) + ' - ' + formatDate(policy.expirationDate)}{policy.isExpired && (<span className="parent-expired-label"><span className="expired-label">Expired</span></span>)}</div></ClayTable.Cell>

										<ClayTable.Cell align="right" className="h3 font-weight-bold">
											<NumberFormat decimalScale={2} displayType={'text'} fixedDecimalScale={true} prefix={'$'} thousandSeparator={true} value={policy.totalBilledPremium} />
										</ClayTable.Cell>
									</ClayTable.Row>
								))}
							</ClayTable.Body>
						</ClayTable>
						<div className="pt-2 px-2"><a href={getFriendlyURL('policies')} className="link-action">{Liferay.Language.get('view-all-policies')}</a></div>
					</React.Fragment>
				)}
				</ReactBigList>
			</div>
		);
	}
};

export default PolicyList;