import React, {useState} from 'react';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {AsteriskIcon} from 'com.churchmutual.commons.web';

const EventTypeAdditionalQuestion = (props) => {

	const handleChange = (selectedValue) => {
		if (props.onChangeValue) {
			props.onChangeValue(selectedValue);
		}
	}

	return (
		<div className={`${props.showAdditionalQuestion ? '' : 'hide '}proof-of-insurance-question`}>
			<div className="pb-4 proof-of-insurance-question__title">
				{props.title}<AsteriskIcon />
			</div>
			<div className="proof-of-insurance-question__response">
				<ClayRadioGroup
					onSelectedValueChange={handleChange}
					selectedValue={props.value}
				>
					{props.options.map((option, i) => (
						<ClayRadio
							key={i}
							label={Liferay.Language.get(option.label)}
							value={option.value}
						/>
					))}
				</ClayRadioGroup>
			</div>
			<div
				className={`proof-of-insurance-question__error pt-3 ${props.value === props.errorValue ? '' : 'hide'}`}
			>
				{Liferay.Language.get('please-contact-customer-service-to-complete-your-form-request')}
			</div>
		</div>
	);
}

export default EventTypeAdditionalQuestion;