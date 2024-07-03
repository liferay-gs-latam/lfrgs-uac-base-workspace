import React from 'react';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {AccountName} from 'com.churchmutual.commons.web';

const InsuredAccountsList = (props) => {

	const onShowAllClick = (e) => {
		let showAllLink = e.currentTarget;
		let showAllDiv = showAllLink.parentElement;

		showAllDiv.classList.add("d-none");

		let container = document.querySelector(".account-profile-portlet");
		let additionalOrganizations = container.querySelector(".additional-accounts");

		additionalOrganizations.classList.remove("d-none");
	}

	if (props.isLoading) {
		return (<ClayLoadingIndicator />);
	}
	else {
		return (
			<React.Fragment>
				<div className="initial-organizations">
					{props.insuredAccountsList.slice(0, 3).map((account, index) => (
						<div className="well well-lg" key={index}>
							<div>
								<h2 className="well-title data-hj-suppress"><AccountName accountName={account.name} /></h2>
								<div className="small font-weight-bold text-muted text-uppercase data-hj-suppress">{Liferay.Language.get('acct-#')}: {account.accountNumber}</div>
							</div>
							<div>
								<React.Fragment>
									<div className={"data-hj-suppress"}>{account.addressLine1}</div>
									<div className={"data-hj-suppress"}>{account.city}, {account.state} {account.zipCode}</div>
									<div className={"data-hj-suppress"}>{account.addressLine3}</div>
								</React.Fragment>
							</div>
						</div>
					))}
				</div>

				<div className="additional-accounts d-none">
					{props.insuredAccountsList.slice(3).map((account, index) => (
						<div className="well well-lg" key={index}>
							<div>
								<h2 className="well-title data-hj-suppress"><AccountName accountName={account.name} /></h2>
								<div className="small font-weight-bold text-muted text-uppercase data-hj-suppress">{Liferay.Language.get('acct-#')}: {account.accountNumber}</div>
							</div>
							<div>
								<React.Fragment>
									<div className={"data-hj-suppress"}>{account.addressLine1}</div>
									<div className={"data-hj-suppress"}>{account.city}, {account.state} {account.zipCode}</div>
									<div className={"data-hj-suppress"}>{account.addressLine3}</div>
								</React.Fragment>
							</div>
						</div>
					))}
				</div>

				{props.insuredAccountsList.length > 3 &&
				<div className="bg-fade">
					<a className="cursor-pointer link-action" onClick={(e) => onShowAllClick(e)} tabIndex="0">{Liferay.Language.get('show-all')}</a>
				</div>
				}

				{props.insuredAccountsList.length == 0 &&
				<div className="well well-lg justify-content-around">
					<div className="empty-state">
						<h3 className="text-muted">
							{Liferay.Language.get('error.you-are-currently-not-associated-with-any-producer-organizations')}
						</h3>
						<div>
							{Liferay.Language.get('contact-your-organizations-administrator-to-be-invited-to-the-organization')}
						</div>
					</div>
				</div>
				}
			</React.Fragment>
		);
	}

}

export default InsuredAccountsList;