import React, {useState, useContext} from 'react';

export const ChangesTrackerContext = React.createContext({});

export const ChangesTrackerProvider = ({children}) => {
    const initialState = {
        clearChanges: () => {
            setState((previousState) => ({
                ...previousState,
                unsavedChanges: false
            }))
        },
        onChange: () => {
            setState((previousState) => ({
                ...previousState,
                unsavedChanges: true
            }))
        },
        unsavedChanges: false
    };

    let [state, setState] = useState(initialState);

    return (
        <ChangesTrackerContext.Provider value={state}>
            {children}
        </ChangesTrackerContext.Provider>
    )
};

export const useStateValue = () => useContext(ChangesTrackerContext);