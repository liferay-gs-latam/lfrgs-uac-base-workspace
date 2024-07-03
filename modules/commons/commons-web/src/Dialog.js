import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useRef} from 'react';
import ClayButton from "@clayui/button";
import ClayLoadingIndicator from '@clayui/loading-indicator';

const Dialog = ({
    children,
    className,
    closeOnConfirm = true,
    confirmButtonText = 'Confirm',
    disableCancel = false,
    disableConfirm = false,
    hideCancel,
    hideFooter,
    loading = false,
    onClickCancel,
    onClickConfirm,
    size = 'lg',
    scrollable,
    status,
    title,
    setVisible,
    visible }) => {

    const removeOutsideClickEvent = (ref) => {
        useEffect(() => {
            const handleClickOutside = event => {
                if (ref.current && !ref.current.contains(event.target)) {
                    event.preventDefault();
                }
            };

            document.addEventListener('mouseup', handleClickOutside);

            return () => {
                document.removeEventListener('mouseup', handleClickOutside)
            };
        }, [ref]);
    }

    const wrapperRef = useRef(null);
    removeOutsideClickEvent(wrapperRef);

    const { observer, onClose } = useModal({
        onClose: () => {
            if (setVisible) {
                setVisible(false);
            }

            if (onClickCancel) {
                onClickCancel();
            }
        }
    });

    const onConfirm = () => {
        if (onClickConfirm) {
            onClickConfirm();
        }

        if (closeOnConfirm) {
            let modalOpenNode = document.querySelector('.modal-open');

            if (modalOpenNode) {
                modalOpenNode.classList.remove('modal-open')
            }

            setVisible(false);
        }
    };

    return visible && (
        <ClayModal
            observer={observer}
            className={className}
            size={size}
            spritemap={Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg"}
            status={status ? status : null}
        >
            <div ref={wrapperRef}>
            <ClayModal.Header>{title}</ClayModal.Header>
            <ClayModal.Body
              scrollable={scrollable}
            >
                {children}
            </ClayModal.Body>
            {!hideFooter && <ClayModal.Footer
                last={
                    <React.Fragment>
                        <ClayButton.Group spaced>
                            {!hideCancel && <ClayButton displayType="outline-secondary" small onClick={onClose} className={disableCancel ? "disabled" : ""}>
                                {Liferay.Language.get("cancel")}
                            </ClayButton>}
                            <ClayButton displayType={status ? status : "primary"} small className={`${loading ? "d-none" : ""} ${disableConfirm ? "disabled" : ""}`} onClick={onConfirm}>
                                {confirmButtonText}
                            </ClayButton>
                            <span className={loading ? "inline-item ml-2 mr-3 px-3" : "d-none"}>
                                <ClayLoadingIndicator />
                            </span>
                        </ClayButton.Group>
                    </React.Fragment>
                }
            />
            }
            </div>
        </ClayModal>);
}

export default Dialog;