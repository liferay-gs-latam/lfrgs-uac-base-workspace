import React, {useState} from 'react';
import ReactBigList from 'react-big-list';
import NumberFormat from 'react-number-format';
import ClayButton from '@clayui/button';
import ClayCard from "@clayui/card";
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {AccountName, navigate, PaginationBar} from 'com.churchmutual.commons.web';
import {downloadLatestTransaction} from './downloadLatestTransaction';
import EmptyState from './EmptyState';
import {PolicyFilter} from './PolicyFilter';
import {PolicySearch, PolicySearchFilter} from './PolicySearch';
import {Render, InsuredTableHeadings, ProducerTableHeadings, TableSort} from './PolicyTableHelpers';

const formatDate = (dateString) => {
  return dateString.replace(/^0*([1-9]\d*)\/0*([1-9]\d*)/, "$1/$2");
}

const PolicyList = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';

  const [pageSize, setPageSize] = useState(10);
  const [filterCount, setFilterCount] = useState(0);
  const [filterVisible, setFilterVisible] = useState(false);
  const [data, setData] = useState(props.policyList);
  const headings = props.userIsProducer ? ProducerTableHeadings : InsuredTableHeadings;

  const Navigate = (e, policy) => {
    let accountNumber = props.accountNumber;

    if (!props.userIsProducer) {
      accountNumber = policy.accountNumber;
    }

    navigate('policy-details?accountNumber=' + accountNumber + '&companyNumber=' + policy.companyNumber + '&policyNumber=' + policy.policyNumber);
  }

  return (
      <ReactBigList
          members={data}
          paginationProps={{pageSize}}
          queryStringFilter={(member, queryString) => PolicySearchFilter(member, queryString, props.userIsProducer)}>
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
                <ClayCard.Description displayType="title">{Liferay.Language.get('policies')} ({filteredCount})</ClayCard.Description>
                <div className="mt-4 mt-md-0">
                  <PolicySearch
                      activePage={activePage}
                      filterCount={filterCount}
                      filterVisible={filterVisible}
                      queryString={queryString}
                      setFilterVisible={setFilterVisible}
                      setPageNumber={setPageNumber}
                      setQueryString={setQueryString}
                      userIsProducer={props.userIsProducer}
                  />
                </div>
              </div>

              <PolicyFilter
                  filteredCount={filteredCount}
                  filteredMembers={filteredMembers}
                  filterVisible={filterVisible}
                  members={data}
                  originalMembers={props.policyList}
                  queryString={queryString}
                  setFilterCount={setFilterCount}
                  setFilteredMembers={setData}
                  userIsProducer={props.userIsProducer}
              />

              <ClayCard.Body className="fade-table mobile-table-padding scrollable-table">
                {!filteredCount && (queryString || filterCount) ? (
                    <EmptyState
                        userIsProducer={props.userIsProducer}
                    />
                ) : (
                    <React.Fragment>
                      <ClayTable className="table-heading-nowrap">
                        <ClayTable.Head>
                          <ClayTable.Row>
                            {headings.map((heading, i) => (
                                <ClayTable.Cell className={heading.customClassName} key={i} headingCell {...heading.props}>
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
                            {props.hasViewPolicyDocuments && <ClayTable.Cell /> || !props.hasViewPolicyDocuments && <ClayTable.Cell />}
                          </ClayTable.Row>
                        </ClayTable.Head>

                        <ClayTable.Body>
                          {displayedMembers.map((policy, i) => (
                              <ClayTable.Row
                                  key={i}
                                  className="cursor-pointer">
                                <ClayTable.Cell onClick={(e) => Navigate(e, policy)}>
                                  <h5 className="font-weight-bold mb-0">{policy.productName}</h5>
                                  <small className="text-muted data-hj-suppress">#{policy.policyNumber}</small>
                                </ClayTable.Cell>

                                {props.userIsProducer ?
                                    null : <ClayTable.Cell onClick={(e) => Navigate(e, policy)} className={"data-hj-suppress"}><AccountName accountName={policy.accountName} /><div className="subtitle-label">#{policy.accountNumber}</div></ClayTable.Cell>
                                }

                                <ClayTable.Cell align="right" className={`text-nowrap ${policy.isExpired ? "expired-text" : ""}`} onClick={(e) => Navigate(e, policy)}><div>{formatDate(policy.effectiveDate) + ' - ' + formatDate(policy.expirationDate)}{policy.isExpired && (<span className="parent-expired-label"><span className="expired-label">Expired</span></span>)}</div></ClayTable.Cell>

                                <ClayTable.Cell align="right" onClick={(e) => Navigate(e, policy)}>{policy.numTransactions}</ClayTable.Cell>

                                <ClayTable.Cell align="right" className="h3 font-weight-bold" onClick={(e) => Navigate(e, policy)}>
                                  <NumberFormat decimalScale={2} displayType={'text'} fixedDecimalScale={true} prefix={'$'} thousandSeparator={true} value={policy.totalBilledPremium} />
                                </ClayTable.Cell>
                                {
                                  policy.hasViewPolicyDocument ?
                                    <ClayTable.Cell align="right">
                                      <ClayButton displayType="unstyled" monospaced className="text-primary pt-0 pb-0"
                                                  onClick={(e) => downloadLatestTransaction(e, displayedMembers[i], policy.accountNumber, props.displayErrorMessage, props.displaySuccessMessage)}>
                                        <span className="download-icon">
                                          <ClayIcon symbol={"download"} spritemap={spritemap} className="lexicon-icon-lg"/>
                                        </span>
                                        <span className="loading-icon d-none inline-item">
                                          <ClayLoadingIndicator/>
                                        </span>
                                      </ClayButton>
                                    </ClayTable.Cell>
                                  :
                                    <ClayTable.Cell align="right">
                                      <ClayButton title={Liferay.Language.get('download.disabled')}
                                                  displayType="unstyled" monospaced className="text-primary pt-0 pb-0 disabled">
                                        <span className="download-icon">
                                          <ClayIcon symbol={"download"} spritemap={spritemap} className="lexicon-icon-lg" color={'gray'}/>
                                        </span>
                                      </ClayButton>
                                    </ClayTable.Cell>
                                }
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

export default PolicyList;