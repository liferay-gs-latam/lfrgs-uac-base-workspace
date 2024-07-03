import React from 'react';
import {ClaySelect} from '@clayui/form';

const RoleSelect = (props) => {

	const options = props.roleTypes;

	function getOptions() {
		if (props.user.statusKey === 'invited') {
			return options.filter(role => role.label == 'member')
		}

		if (props.user.role === 'member') {
			return options.filter(role => role.label !== 'admin')
		}

		if (props.currentUser.role === 'owner') {
			return options;
		}

		let ret = [...options];

		ret.shift();

		return ret;
	}

	return (
		<React.Fragment>
			{props.value !== 'owner' ?
				<ClaySelect aria-label="Select Role"
						onChange={(e) => props.handleFieldChange(props.user, e.target.value)} value={props.value}>
					{getOptions().map(item => (
						<ClaySelect.Option
							key={item.label}
							label={Liferay.Language.get(item.label)}
							value={item.label}
						/>
					))}
				</ClaySelect> : Liferay.Language.get('owner')
			}
		</React.Fragment>
	);
};

export {RoleSelect};