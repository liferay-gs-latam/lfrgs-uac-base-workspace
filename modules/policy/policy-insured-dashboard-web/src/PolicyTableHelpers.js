import React from 'react';
import ClayIcon from '@clayui/icon';
import {Popover} from 'com.churchmutual.commons.web';

const cmicspritemap = themeDisplay.getPathThemeImages() + '/cmic/icons.svg';

export const TableHeadings = [
  {
    text: Liferay.Language.get('type'),
    sortBy: 'productName',
    sortable: false,
    props: {
      expanded: true,
    },
  },
  {
    text: Liferay.Language.get('account'),
    sortBy: 'accountName',
    sortable: false
  },
  {
    text: Liferay.Language.get('effective-date'),
    sortBy: 'effectiveDateSortable',
    sortable: false,
    props: {
      align: 'right',
    },
  },
  {
    text: Liferay.Language.get('policy-premium'),
    sortBy: 'totalBilledPremium',
    sortable: false,
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