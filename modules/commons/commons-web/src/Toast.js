import React, { useState, useEffect } from 'react';
import ClayAlert from '@clayui/alert';

const Toast = (props) => {

    let initialState = [];

    if (props.message) {
        initialState.push(props.message);
    }

    let [toastItems, setToastItems] = useState(initialState);

    useEffect(() => {
        if (props.message && !toastItems.includes(props.message)) {
            setToastItems([
                ...toastItems,
                props.message
            ]);
        }
    }, [props.message]);

    const onClose = (value) => {
        setToastItems(
            toastItems.filter(i => i !== value)
        );

        if (props.onClose) {
            props.onClose();
        }
    };

    return (
        <ClayAlert.ToastContainer>
            {toastItems.map((value, i) => (
                <ClayAlert
                    autoClose={5000}
                    key={i}
                    displayType={props.displayType}
                    onClose={() => onClose(value)}
                    spritemap={Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg"}
                    title={props.title}>
                    {Liferay.Language.get(value)}
                </ClayAlert>
            ))}
        </ClayAlert.ToastContainer>
    )
};

export default Toast;