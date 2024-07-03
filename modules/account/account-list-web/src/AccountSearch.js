import React from 'react';
import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Popover} from 'com.churchmutual.commons.web';

export const AccountSearch = ({activePage, filterCount, filterVisible, queryString, setFilterVisible, setPageNumber, setQueryString}) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

  return (
    <ClayInput.Group>
      <ClayInput.GroupItem shrink className="align-items-center text-secondary">
        <Popover
          alignPosition="bottom-left"
          className="popover-inner-pre"
          message={Liferay.Language.get('search-by-account-name-producer-code')}
        />
      </ClayInput.GroupItem>
      <ClayInput.GroupItem>
        <ClayInput
          aria-label="Search"
          className="input-group-inset input-group-inset-after"
          onChange={e => {
            setQueryString(e.target.value);
            if (activePage != 1) {
              setPageNumber(1);
            }
          }}
          placeholder={Liferay.Language.get('search')}
          type="search"
          value={queryString}
        />
        <ClayInput.GroupInsetItem after tag="span">
          <ClayIcon
            className={`icon-monospaced ${queryString ? "text-primary" : "text-secondary"}`}
            symbol="search"
            spritemap={spritemap}
          />
        </ClayInput.GroupInsetItem>
      </ClayInput.GroupItem>
      <ClayInput.GroupItem shrink>
        <ClayButton
          className={`align-self-center ${(filterVisible || filterCount > 0) ? "bg-primary-light text-primary" : "text-secondary"}`}
          displayType="unstyled"
          monospaced={true}
          onClick={() => setFilterVisible(!filterVisible)}
        >
          <ClayIcon spritemap={spritemap} symbol="filter" />
          {filterCount > 0 &&
            <ClayBadge displayType="danger" label={filterCount} className="position-absolute" />
          }
        </ClayButton>
      </ClayInput.GroupItem>
    </ClayInput.Group>
  );
};

export const AccountSearchFilter = (member, queryString) => {
  queryString = queryString.trim().toLowerCase();

  return (
    member.accountName.toLowerCase().includes(queryString) ||
    member.accountNumber.includes(queryString) ||
    member.producerCodeFormatted.includes(queryString) ||
    member.policyNumbers.includes(queryString)
  );
};
