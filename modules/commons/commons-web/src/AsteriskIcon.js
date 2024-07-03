import React from 'react';
import ClayIcon from "@clayui/icon";

const AsteriskIcon = () => {
    let spritemap = Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg";

    return (
        <span className="reference-mark"><ClayIcon className="pl-1" symbol="asterisk" spritemap={spritemap} /></span>
    )
};

export default AsteriskIcon;