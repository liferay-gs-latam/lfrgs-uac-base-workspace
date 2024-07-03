import React from 'react';
import ClayIcon from '@clayui/icon';
import {Popover, navigate} from 'com.churchmutual.commons.web';

const cmicspritemap = themeDisplay.getPathThemeImages() + '/cmic/icons.svg';

export const TableHeadings = [
  {
    text: Liferay.Language.get('name'),
    sortBy: 'accountName',
    sortable: true,
    props: {
      expanded: true,
    },
  },
  {
    text: Liferay.Language.get('producer-org'),
    sortBy: 'producerName',
    sortable: true,
    props: {
      expanded: true,
    },
  },
  {
    text: Liferay.Language.get('in-force-policies'),
    sortBy: 'numInForcePolicies',
    sortable: true,
    props: {
      expanded: true,
      align: 'center',
      className: 'table-cell-ws-nowrap',
    },
  },
  {
    text: Liferay.Language.get('future-policies'),
    sortBy: 'numFuturePolicies',
    sortable: true,
    props: {
      expanded: true,
      align: 'center',
      className: 'table-cell-ws-nowrap'
    },
  },
  {
    text: Liferay.Language.get('expired-policies'),
    sortBy: 'numExpiredPolicies',
    sortable: true,
    props: {
      expanded: true,
      align: 'center',
      className: 'table-cell-ws-nowrap'
    },
  },
  {
    text: Liferay.Language.get('account-premium'),
    sortBy: 'totalBilledPremiumSortable',
    sortable: true,
    popover: () => {
      return (
        <Popover message={Liferay.Language.get('this-value-is-an-estimate-and-may-not-reflect-the-exact-account-premium-costs')} />
      );
    },
    props: {
      expanded: true,
      align: 'right',
      className: 'table-cell-ws-nowrap'
    },
  },
];
export const TableSort = ({setSort, sortColumn, sortDirection, sortable, sortBy, children}) => {
  return (sortable
      ? <span
        className="toggle-sort cursor-pointer"
        onClick={() => setSort(sortBy)}
      >
        {children}
        {sortColumn === sortBy
          ? sortDirection === 'desc'
            ? <ClayIcon symbol={"sort-descending"} spritemap={cmicspritemap} className="text-primary" />
            : <ClayIcon symbol={"sort-ascending"} spritemap={cmicspritemap} className="text-primary" />
          : <ClayIcon symbol={"sort"} spritemap={cmicspritemap} className="text-muted" />
        }
      </span>
      : children
  );
};
export function Render(text) {
  return (
    typeof text === 'string'
      ? text
      : typeof text === 'function'
      ? text()
      : ''
  );
}
export function Navigate(accountNumber, companyNumber) {
  navigate('account-details?accountNumber=' + accountNumber + '&companyNumber=' + companyNumber);
}