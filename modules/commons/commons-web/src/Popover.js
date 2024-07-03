import React, {useEffect, useRef, useState} from 'react';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {parseAsHTML} from './parseAsHTML';
import {useEventListener} from './useEventListener';

const Popover = (props) => {
	const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

	const { message, bodyClassName, className, closeOnModalScroll, dynamic, alignPosition = 'bottom' } = props;

	const [alignPositionRef, setAlignPositionRef] = useState(alignPosition);

	const infoCircleRef = useRef(null);

	const [showPopover, setShowPopover] = useState(false);

	const handleScroll = () => { setShowPopover(false); }

	const handleClickOutside = event => {
		if (infoCircleRef.current && !infoCircleRef.current.contains(event.target)) {
			setShowPopover(false);
		}
	}

	if (closeOnModalScroll) {
		useEventListener('scroll', handleScroll, document.getElementsByClassName("modal-body")[0]);
	}

	if (dynamic) {
		useEffect(() => {
			if (infoCircleRef.current != null) {
				const windowWidth = window.innerWidth;
				const xpos = infoCircleRef.current.getBoundingClientRect().x;

				let prefix = '';

				if (alignPosition.includes('bottom') || windowWidth < 576) {
					prefix = "bottom";
				}
				else if (alignPosition.includes('top')) {
					prefix = "top";
				}

				if (xpos < windowWidth / 3) {
					setAlignPositionRef(prefix ? prefix + '-left' : 'left');
				}
				else if (xpos > 2 * windowWidth / 3) {
					setAlignPositionRef(prefix ? prefix + '-right' : 'right');
				}
				else {
					setAlignPositionRef(prefix ? prefix : alignPosition);
				}
			}
		}, [alignPositionRef]);
	}

	useEffect(() => {
		document.addEventListener('click', handleClickOutside);

		return () => {
			document.removeEventListener('click', handleClickOutside)
		};
	}, [infoCircleRef]);

	return (
		<ClayPopover
			className={`popover-custom ${bodyClassName ? bodyClassName : ''}`}
			alignPosition={alignPositionRef}
			show={showPopover}
			onShowChange={(isShown) => setShowPopover(isShown)}
			trigger={
				<span className="p-2 cursor-pointer">
					<ClayIcon
						className="pointer-events-all"
						data-tooltip-align="bottom-right"
						symbol="info-circle-open"
						spritemap={spritemap}
						ref={infoCircleRef}
					/>
				</span>
			}
		>
			<span className={className ? className : ''}>{parseAsHTML(message)}</span>
		</ClayPopover>
	);
}

export default Popover;