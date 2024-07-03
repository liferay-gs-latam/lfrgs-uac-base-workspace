import React from 'react';
import ClayIcon from '@clayui/icon';
import {Popover} from 'com.churchmutual.commons.web';

const cmicspritemap = themeDisplay.getPathThemeImages() + '/cmic/icons.svg';

export const InsuredTableHeadings = [
  {
    text: Liferay.Language.get('type'),
    sortBy: 'productName',
    sortable: true,
    props: {
      expanded: true,
    },
  },
  {
    text: Liferay.Language.get('account'),
    sortBy: 'accountName',
    sortable: true,
    props: {
      expanded: false,
    }
  },
  {
    text: Liferay.Language.get('effective-date'),
    sortBy: 'effectiveDateSortable',
    sortable: true,
    props: {
      align: 'right',
    },
  },
  {
    customClassName: 'transactions-header',
    text: Liferay.Language.get('transactions'),
    sortBy: 'numTransactions',
    sortable: true,
    props: {
      align: 'right',
    },
  },
  {
    text: Liferay.Language.get('policy-premium'),
    sortBy: 'totalBilledPremium',
    sortable: true,
    popover: () => {
      return (
        <Popover message={Liferay.Language.get('this-value-is-an-estimate-and-may-not-reflect-the-exact-policy-premium-costs')} />
      );
    },
    props: {
      align: 'right',
    },
  }
];
export const ProducerTableHeadings = [
  {
    text: Liferay.Language.get('type'),
    sortBy: 'productName',
    sortable: true,
    props: {
      expanded: true,
    },
  },
  {
    text: Liferay.Language.get('effective-date'),
    sortBy: 'effectiveDateSortable',
    sortable: true,
    props: {
      align: 'right',
    },
  },
  {
    text: Liferay.Language.get('transactions'),
    sortBy: 'numTransactions',
    sortable: true,
    props: {
      align: 'right',
    },
  },
  {
    text: Liferay.Language.get('policy-premium'),
    sortBy: 'totalBilledPremium',
    sortable: true,
    popover: () => {
      return (
        <Popover message={Liferay.Language.get('this-value-is-an-estimate-and-may-not-reflect-the-exact-policy-premium-costs')} />
      );
    },
    props: {
      align: 'right',
    },
  }
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