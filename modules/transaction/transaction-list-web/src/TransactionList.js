import React, {useState} from 'react';
import ReactBigList from 'react-big-list';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import {PaginationBar} from 'com.churchmutual.commons.web';
import {downloadTransaction} from './downloadTransaction';
import ClayCard from "@clayui/card";

const TransactionList = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';
  const [pageSize, setPageSize] = useState(10);

  return (
    <ReactBigList
      members={props.transactionList}
      paginationProps={{pageSize}}>
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
          <div className="card-header">
            <ClayCard.Description displayType="title">{props.productName} - {Liferay.Language.get('transactions')} ({filteredCount})</ClayCard.Description>
          </div>
          <ClayCard.Body className="fade-table mobile-table-padding scrollable-table">
            {props.transactionList.length > 0 ?
              <div>
                <ClayTable className="table-heading-nowrap">
                  <ClayTable.Head>
                    <ClayTable.Row>
                      <ClayTable.Cell expanded headingCell>{Liferay.Language.get('transaction-date')}</ClayTable.Cell>
                      <ClayTable.Cell expanded headingCell>{Liferay.Language.get('effective-date')}</ClayTable.Cell>
                      <ClayTable.Cell expanded headingCell>{Liferay.Language.get('transaction-type')}</ClayTable.Cell>
                      {props.hasViewPolicyDocuments &&
                        <ClayTable.Cell headingCell align="center">{Liferay.Language.get('download')}</ClayTable.Cell>
                      }
                    </ClayTable.Row>
                  </ClayTable.Head>
                  <ClayTable.Body>
                    {displayedMembers.map((transaction, index) => (
                      <ClayTable.Row key={index}>
                        <ClayTable.Cell>{transaction.transactionCommitTimestamp}</ClayTable.Cell>
                        <ClayTable.Cell>{transaction.transactionEffectiveDate}</ClayTable.Cell>
                        <ClayTable.Cell>{transaction.eventType}</ClayTable.Cell>
                        {props.hasViewPolicyDocuments &&
                          <ClayTable.Cell align="center">
                            <ClayButton
                                displayType="unstyled"
                                monospaced
                                className="text-primary py-0"
                                onClick={(e) => downloadTransaction(e, props.policy.accountNumber, transaction.sequenceNumber, transaction.productType, props.displayErrorMessage, props.displaySuccessMessage)}>
                                  <span className="download-icon">
                                    <ClayIcon symbol={"download"} spritemap={spritemap} className="lexicon-icon-lg"/>
                                  </span>
                              <span className="loading-icon d-none inline-item">
                                      <ClayLoadingIndicator/>
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
              </div>
              : Liferay.Language.get('there-are-no-transactions-for-this-policy')
            }
          </ClayCard.Body>
        </React.Fragment>
      )}
    </ReactBigList>
  );
};

export default TransactionList;