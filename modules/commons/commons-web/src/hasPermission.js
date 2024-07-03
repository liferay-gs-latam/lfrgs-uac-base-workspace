export function hasPermission(cmicBusinessKey, actionId, callback) {
    Liferay.Service(
        '/cmic.cmicpermission/has-permission',
        {
            cmicBusinessKey: cmicBusinessKey,
            actionId: actionId
        },
        callback
    );
}

export function hasProducerPermission(producerId, actionId, callback) {
    let cmicBusinessKey = JSON.stringify({
        producerId: producerId
    });

    hasPermission(cmicBusinessKey, actionId, callback);
}

export function hasAccountPermission(accountNumber, companyNumber, actionId, callback) {
    let cmicBusinessKey = JSON.stringify({
        accountNumber: accountNumber,
        companyNumber: companyNumber
    });

    hasPermission(cmicBusinessKey, actionId, callback);
}