import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useEventListener} from './useEventListener';
import AsteriskIcon from './AsteriskIcon';

const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

const DropDown = ({captionDefault, children, closeOnModalScroll, dropDownItems, disableByType, exclusionGroup, limit, required, selected, setSelected, setDropDownItems, showLabels, containsSensitiveInformation}) => {

	const [active, setActive] = useState(false);

	const [hasExclusiveType, setHasExclusiveType] = useState(false);

	const handleScroll = () => { setActive(false); }

	if (closeOnModalScroll) {
		useEventListener('scroll', handleScroll, document.getElementsByClassName("modal-body")[0]);
	}

	const isSelected = (option) => {
		return selected.some(selectedItem => selectedItem.id === option.id);
	}

	const isDisabled = (option) => {
		if (!disableByType || isSelected(option)) {
			return false;
		}

		let isOfSelectedType = selected.findIndex((item) => item.type === option.type) > -1;

		let isInExclusiveGroup = exclusionGroup && hasExclusiveType && exclusionGroup.includes(option.id.substring(0, 2));

		// Disable all unselected items when limit is reached

		let limitReached = (limit > 1 && selected.length === limit);

		return selected.length > 0 && (isOfSelectedType || isInExclusiveGroup || limitReached);
	}

	const removeLabel = (option) => {
		let modifiedItems = [...dropDownItems];

		updateSelected(modifiedItems, option, null);

		setDropDownItems(modifiedItems);
	}

	const removeAllLabels = () => {
		setSelected([]);

		setHasExclusiveType(false);
	}

	const updateSelected = (arr, option, value) => {
		if (limit === 1) {
			setSelected([option]);

			setActive(false);

			return;
		}

		let modifiedSelected = [...selected];

		if (value === true) {
			modifiedSelected.push(option);
		}
		else {
			let index = modifiedSelected.findIndex(item => item.id === option.id);

			if (index > -1) {
				modifiedSelected.splice(index, 1);
			}
		}

		setSelected(modifiedSelected);

		if (exclusionGroup && exclusionGroup.includes(option.id.substring(0, 2))) {
			setHasExclusiveType(value);
		}
	}

	const onClickOption = (option) => {
		let modifiedItems = [...dropDownItems];

		if (!isSelected(option) && selected.length < limit) {
			updateSelected(modifiedItems, option, true);

			setDropDownItems(modifiedItems);
		}
		else {
			updateSelected(modifiedItems, option, null);

			setDropDownItems(modifiedItems);
		}
	}

	const getCaptionText = () => {
		if (limit === 1) {
			return selected.length === 0 ? captionDefault : <span className="color-black">{selected[0].id}</span>;
		}

		return selected.length === 0 ? captionDefault : selected.length === 1 ? Liferay.Language.get('one-policy-selected') : Liferay.Util.sub(Liferay.Language.get('x-policies-selected'), selected.length);
	}

	return (
		<React.Fragment>
			<ClayDropDown
				active={active}
				hasRightSymbols={true}
				onActiveChange={setActive}
				trigger={
					<ClayButton displayType="outline-secondary" small>
						<span>{getCaptionText()}{required && selected.length === 0 ? <AsteriskIcon /> : ''}</span>
						<ClayIcon spritemap={spritemap} symbol="caret-double-l" />
					</ClayButton>
				}
				className={`${containsSensitiveInformation ? "data-hj-suppress" : ""}`}
			>
				<div>
					<ClayDropDown.ItemList>
						{dropDownItems
							.map((item, i) => (
								<ClayDropDown.Item
									active={isSelected(item)}
									disabled={isDisabled(item)}
									key={i}
									onClick={() => onClickOption(item)}
									spritemap={spritemap}
									symbolRight="check">
									<div>
										<div className={`${containsSensitiveInformation ? "data-hj-suppress" : ""}`}>{item.id}</div>
										<div className={`dropdown-subitem ${containsSensitiveInformation ? "data-hj-suppress" : ""}`}>{item.type}</div>
									</div>
								</ClayDropDown.Item>
							))
						}
					</ClayDropDown.ItemList>
				</div>
			</ClayDropDown>

			{children}

			{showLabels && selected.length > 0 &&
				<div className="mt-4">
					<table>
						<tbody>
							{selected
								.map((item, i) => (
									<tr key={i} className="pb-3">
										<td className={`pr-3 ${containsSensitiveInformation ? "data-hj-suppress" : ""}`}>{item.id}</td>
										<td className={`pr-3 ${containsSensitiveInformation ? "data-hj-suppress" : ""}`}><span className="neutral-3 small">({item.type})</span></td>
										<td><ClayIcon className="cursor-pointer" spritemap={spritemap} symbol="times" onClick={() => removeLabel(item)}/></td>
									</tr>
								))}
							<tr className="pt-3">
								<td colSpan={3}>
									<ClayButton
										borderless="true"
										className="float-right link-action"
										onClick={removeAllLabels}
										small
									>
										{Liferay.Language.get('remove-all')}
									</ClayButton>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			}
		</React.Fragment>
	);
}

export default DropDown;