import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import groupBy from 'lodash.groupby';
import map from 'lodash.map';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

export class PolicyFilter extends React.Component {

	constructor(props) {
		super(props);

		this.clearLabels = this.clearLabels.bind(this);
		this.toggleLabel = this.toggleLabel.bind(this);

		this.state = {
			accountNameItems: [],
			policyNumberItems: [],
			members: [],
			filters: [],
			labels: [],
			productNameItems: [],
		};
	}

	componentDidMount() {
		this.setMembers();
	}

	componentDidUpdate(prevProps, prevState) {
		if (prevState.labels !== this.state.labels) {
			this.setFilters();
			this.props.setFilterCount(this.state.labels.length);
		}

		if (prevProps.filteredMembers !== this.props.filteredMembers) {
			this.setMembers();
		}

		if(prevState.filters !== this.state.filters) {
			this.setFilteredMembers();
		}
	}

	clearLabels() {
		this.setState({
			labels: [],
		});
	}

	toggleLabel(label) {
		let labels = [...this.state.labels];

		if (labels.some(item => item.value === label.value)) {
			labels = labels.filter(item => item.value !== label.value);
		}
		else {
			labels.push(label);
		}

		this.setState({
			labels: labels,
		});
	}

	setFilteredMembers() {
		const filters = this.state.filters;
		const originalMembers = this.props.originalMembers;
		let filteredMembers;

		if (filters.length) {
			filteredMembers = originalMembers.filter(item => {
				return filters.some(filter => {
					return filter.value.includes(item[filter.id]);
				});
			});
		}
		else {
			filteredMembers = originalMembers;
		}

		this.props.setFilteredMembers(filteredMembers);
	}

	setFilters() {
		const grouped = groupBy(this.state.labels, label => label.ref);

		const filters = map(grouped, (value, prop) => {
			value = value.map(v => v.value);
			return ({id: prop, value})
		});

		this.setState({
			filters: filters,
		});
	}

	setMembers() {
		this.setState({
			members: this.props.queryString ? this.props.filteredMembers : this.props.originalMembers
		}, this.setDropDownItems);
	}

	setDropDownItems() {
		let accountNameItems = [];
		let policyNumberItems = [];
		let productNameItems = [];

		this.state.members.map(member => {
			if (this.props.userIsProducer) {
				policyNumberItems.push({
					label: member.policyNumber,
					onClick: () => {
						this.toggleLabel({
							langKey: 'policy-number',
							ref: 'policyNumber',
							value: member.policyNumber,
						});
					}
				});
			}
			else {
				let hasAccountName = accountNameItems.findIndex(item => item.label == member.accountName) != -1;

				if (!hasAccountName) {
					accountNameItems.push({
						label: member.accountName,
						onClick: () => {
							this.toggleLabel({
								langKey: 'account',
								ref: 'accountName',
								value: member.accountName,
							});
						}
					});
				}
			}

			let hasProductName = productNameItems.findIndex(item => item.label == member.productName) != -1;

			if (!hasProductName) {
				productNameItems.push({
					label: member.productName,
					onClick: () => {
						this.toggleLabel({
							langKey: 'policy-type',
							ref: 'productName',
							value: member.productName,
						});
					}
				});
			}
		});

		accountNameItems.sort((a, b) => {return a.label == b.label ? 0 : a.label > b.label ? 1 : -1;});

		policyNumberItems.sort((a, b) => {return a.label == b.label ? 0 : a.label > b.label ? 1 : -1;});

		productNameItems.sort((a, b) => {return a.label == b.label ? 0 : a.label > b.label ? 1 : -1;});

		this.setState({
			accountNameItems: accountNameItems,
			policyNumberItems: policyNumberItems,
			productNameItems: productNameItems
		});
	}

	render() {
		const filterVisible = this.props.filterVisible;
		const filteredCount = this.props.filteredCount;

		return (
			<React.Fragment>
				{filterVisible && filteredCount
					? (
						<React.Fragment>
							<ClayForm.Group className="filter">
								<ClayInput.Group>
									{this.props.userIsProducer ? (
										<div className="d-flex flex-wrap justify-content-start">
											<ClayInput.GroupItem shrink className="mb-4 mb-sm-1 mr-2">
												<DropDown
													labels={this.state.labels}
													caption={Liferay.Language.get('policy-type')}
													items={this.state.productNameItems}
													containsSensitiveInformation={false}
												/>
											</ClayInput.GroupItem>
											<ClayInput.GroupItem shrink className="mb-4 mb-sm-1 ml-0 mr-2">
												<DropDown
													labels={this.state.labels}
													caption={Liferay.Language.get('policy-number')}
													items={this.state.policyNumberItems}
													containsSensitiveInformation={true}
												/>
											</ClayInput.GroupItem>
										</div>
									) : (
										<div className="d-flex flex-wrap justify-content-start">
											<ClayInput.GroupItem shrink className="mb-4 mb-sm-1 ml-0 mr-2">
												<DropDown
													labels={this.state.labels}
													caption={Liferay.Language.get('account-name')}
													items={this.state.accountNameItems}
													containsSensitiveInformation={true}
												/>
											</ClayInput.GroupItem>
											<ClayInput.GroupItem shrink className="mb-4 mb-sm-1 mr-2">
												<DropDown
													labels={this.state.labels}
													caption={Liferay.Language.get('policy-type')}
													items={this.state.productNameItems}
													containsSensitiveInformation={false}
												/>
											</ClayInput.GroupItem>
										</div>
									)}

									<ClayInput.GroupItem shrink className="ml-auto">
										{this.state.labels.length > 0 &&
										<ClayButton
											displayType="outline-secondary"
											onClick={this.clearLabels}
											small
										>
											<ClayIcon spritemap={spritemap} symbol="times-circle" className="mr-1" />
											{Liferay.Language.get('clear-all')}
										</ClayButton>
										}
									</ClayInput.GroupItem>
								</ClayInput.Group>
							</ClayForm.Group>

							{this.state.labels.length > 0 &&
							<ClayForm.Group className="filter-labels">
								<ActiveLabels
									items={this.state.labels}
									toggleLabel={this.toggleLabel}
								/>
							</ClayForm.Group>
							}
						</React.Fragment>
					)
					: ''
				}
			</React.Fragment>
		);
	}
}

const DropDown = ({labels, caption, items, containsSensitiveInformation}) => {
	const [search, setSearch] = useState('');
	const [active, setActive] = useState(false);

	return(
		<ClayDropDown
			active={active}
			hasRightSymbols={true}
			onActiveChange={setActive}
			trigger={
				<ClayButton displayType="outline-secondary" small>
					{caption}
					<ClayIcon spritemap={spritemap} symbol="caret-double-l" />
				</ClayButton>
			}
		>
			<ClayDropDown.Search
				onChange={(event) => setSearch(event.target.value)}
				spritemap={spritemap}
				value={search}
			/>
			<div className="inline-scroller">
				<ClayDropDown.ItemList>
					{items
						.filter(({label}) =>
							label.toLowerCase().match(search.toLowerCase())
						)
						.map((item, i) => (
							<ClayDropDown.Item
								active={labels.some(label => label.value === item.label)}
								key={i}
								onClick={item.onClick}
								spritemap={spritemap}
								symbolRight="check"
								className={`${containsSensitiveInformation ? "data-hj-suppress" : ""}`}>
								{item.label}
							</ClayDropDown.Item>
						))
					}
				</ClayDropDown.ItemList>
			</div>
		</ClayDropDown>
	);
};

const ActiveLabels = ({items, toggleLabel}) => {
	return items.map((item, i) => (
		<ClayLabel
			closeButtonProps={{
				onClick: () => {
					toggleLabel(item);
				}
			}}
			displayType="secondary"
			innerElementProps={{
				className: "flex-row",
			}}
			key={i}
			large={true}
			spritemap={spritemap}
			className={"data-hj-suppress"}
		>
			<div><strong className="mr-1">{Liferay.Language.get(item.langKey)}:</strong>{item.value}</div>
		</ClayLabel>
	));
};
