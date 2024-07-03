import React from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

const PaginationBar = ({activePage, displayingFrom, displayingTo, filteredCount, numPages, pageSize, setPageNumber, setPageSize}) => {
  const deltas = [10, 20, 30, 40, 50];

  const items = deltas.map(delta => {
    return {
      label: Liferay.Util.sub(Liferay.Language.get('x-items'), delta),
      onClick: () => setPageSize(delta)
    };
  });

  return (
    <ClayPaginationBar size="sm">
      <ClayPaginationBar.DropDown
        items={items}
        trigger={
          <ClayButton displayType="unstyled">
            {Liferay.Util.sub(Liferay.Language.get('x-items'), pageSize)}
            <ClayIcon spritemap={spritemap} symbol="caret-double-l" />
          </ClayButton>
        }
      />

      <ClayPaginationBar.Results>
        {Liferay.Util.sub(Liferay.Language.get('showing-x-to-x-of-x'), [displayingFrom, displayingTo, filteredCount])}
      </ClayPaginationBar.Results>

      <ClayPaginationWithBasicItems
        activePage={activePage}
        ellipsisBuffer={1}
        onPageChange={(page) => setPageNumber(page)}
        spritemap={spritemap}
        totalPages={numPages}
      />
    </ClayPaginationBar>
  );
};

export default PaginationBar;