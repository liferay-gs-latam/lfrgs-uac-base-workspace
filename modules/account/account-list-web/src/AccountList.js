import React, {useState} from 'react';
import ClayCard from '@clayui/card';
import ClayTable from '@clayui/table';
import NumberFormat from 'react-number-format';
import ReactBigList from 'react-big-list';
import {AccountName, PaginationBar} from 'com.churchmutual.commons.web';
import {AccountFilter, customFilterMap} from './AccountFilter';
import {AccountSearch, AccountSearchFilter} from './AccountSearch';
import {Navigate, Render, TableHeadings, TableSort} from './AccountTableHelpers';
import EmptyState from './EmptyState';

const AccountList = (props) => {
  const [pageSize, setPageSize] = useState(10);
  const [filterCount, setFilterCount] = useState(0);
  const [filterVisible, setFilterVisible] = useState(false);
  const [data, setData] = useState(props.accountsList);

  return (
    <ReactBigList
      members={data}
      paginationProps={{pageSize}}
      queryStringFilter={AccountSearchFilter}>
      {({
          activePage,
          displayedCount,
          displayedMembers,
          displayingFrom,
          displayingTo,
          filteredCount,
          filteredMembers,
          initialCount,
          members,
          numPages,
          queryString,
          sortColumn,
          sortDirection,
          setPageNumber,
          setQueryString,
          setSort,
        }) => (
        <React.Fragment>
          <div className="align-items-md-center card-header flex-container flex-container-stacked-xs justify-content-between mobile-table-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('accounts')} ({filteredCount})</ClayCard.Description>
            <div className="mt-4 mt-md-0">
              <AccountSearch
                activePage={activePage}
                filterCount={filterCount}
                filterVisible={filterVisible}
                queryString={queryString}
                setFilterVisible={setFilterVisible}
                setPageNumber={setPageNumber}
                setQueryString={setQueryString}
              />
            </div>
          </div>

          <AccountFilter
              filteredCount={filteredCount}
              filteredMembers={filteredMembers}
              filterVisible={filterVisible}
              members={data}
              originalMembers={props.accountsList}
              queryString={queryString}
              setFilterCount={setFilterCount}
              setFilteredMembers={setData}
            />

          <ClayCard.Body className="fade-table mobile-table-padding scrollable-table">
            {!filteredCount && (queryString || filterCount) ? (
              <EmptyState />
            ) : (
              <React.Fragment>
                <ClayTable>
                  <ClayTable.Head>
                    <ClayTable.Row>
                      {TableHeadings.map((heading, i) => (
                        <ClayTable.Cell key={i} headingCell {...heading.props}>
                          {Render(heading.popover)}
                          <TableSort
                            sortable={heading.sortable}
                            sortBy={heading.sortBy}
                            setSort={setSort}
                            sortColumn={sortColumn}
                            sortDirection={sortDirection}
                          >
                            {Render(heading.text)}
                          </TableSort>
                        </ClayTable.Cell>
                      ))}
                    </ClayTable.Row>
                  </ClayTable.Head>

                  <ClayTable.Body>
                    {displayedMembers.map((account, i) => (
                      <ClayTable.Row
                        className="cursor-pointer"
                        key={i}
                        onClick={() => Navigate(account.accountNumber, account.companyNumber)}
                      >
                        <ClayTable.Cell className="table-cell-expand-small">
                          <h5 className="font-weight-bold mb-0 data-hj-suppress"><AccountName accountName={account.accountName} /></h5>
                          <small className="text-muted data-hj-suppress">#{account.accountNumber}</small>
                        </ClayTable.Cell>

                        <ClayTable.Cell>
                          <h5 className="font-weight-normal mb-0 data-hj-suppress">{account.producerName}</h5>
                          <small className="text-muted data-hj-suppress">{account.producerCodeFormatted}</small>
                        </ClayTable.Cell>

                        <ClayTable.Cell align="center">{account.numInForcePolicies}</ClayTable.Cell>
                        <ClayTable.Cell align="center">{account.numFuturePolicies}</ClayTable.Cell>
                        <ClayTable.Cell align="center">{account.numExpiredPolicies}</ClayTable.Cell>

                        <ClayTable.Cell align="right" className="h3 font-weight-bold">
                          <NumberFormat decimalScale={2} displayType={'text'} fixedDecimalScale={true} prefix={'$'} thousandSeparator={true} value={account.totalBilledPremium} />
                        </ClayTable.Cell>
                      </ClayTable.Row>
                    ))}
                  </ClayTable.Body>
                </ClayTable>
                <hr/>
                <PaginationBar
                  activePage={activePage}
                  displayingFrom={displayingFrom}
                  displayingTo={displayingTo}
                  filteredCount={filteredCount}
                  numPages={numPages}
                  pageSize={pageSize}
                  setPageNumber={setPageNumber}
                  setPageSize={setPageSize}
                />
              </React.Fragment>
            )}
          </ClayCard.Body>
        </React.Fragment>
      )}
    </ReactBigList>
  );
};

export default AccountList;