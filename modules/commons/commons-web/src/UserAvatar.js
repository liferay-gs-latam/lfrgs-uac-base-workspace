import React from 'react';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';

const UserAvatar = ({shape = 'circle', size = 'lg', displayType = 'light', elevation, className, index, image, firstName, lastName, onClick}) => {
	const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
	const variations = ['blue', 'green', 'orange'];
	const display = image ? displayType : (index !== undefined ? variations[index % variations.length] : displayType);
	const avatarClassName = getCN(
		{
			[`elevation-${elevation}`]: elevation
		},
		className
	);

	return (
		<ClaySticker shape={shape} displayType={display} size={size} className={`${avatarClassName} data-hj-suppress`} onClick={onClick}>
			{image ? (
				<img className="sticker-img" src={image} />
			) : (
				(firstName && lastName) ? (
					`${firstName.substring(0, 1)}${lastName.substring(0, 1)}`
				) : (
					<ClayIcon symbol="user" spritemap={spritemap} />
				)
			)}
		</ClaySticker>
	)
};

export default UserAvatar;
