import React from 'react';
import ClayLoadingIndicator from '@clayui/loading-indicator';

const ProducerOrgList = (props) => {

  const onShowAllClick = (e) => {
    let showAllLink = e.currentTarget;
    let showAllDiv = showAllLink.parentElement;

    showAllDiv.classList.add("d-none");

    let container = document.querySelector(".organization-profile-portlet");
    let additionalOrganizations = container.querySelector(".additional-organizations");

    additionalOrganizations.classList.remove("d-none");
  }

  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        <div className="initial-organizations">
          {props.producerOrgList.slice(0, 3).map((producer, index) => (
            <div className="well well-lg" key={index}>
              <div>
                <h2 className="well-title data-hj-suppress">{producer.name}</h2>
                <div className="small font-weight-bold text-muted text-uppercase data-hj-suppress">{Liferay.Language.get('producer-code')}: {producer.divisionNumber}-{producer.agentNumber}</div>
              </div>
              <div>
                <React.Fragment>
                  <div className={"data-hj-suppress"}>{producer.addressLine1}</div>
                  <div className={"data-hj-suppress"}>{producer.addressLine2}</div>
                  <div className={"data-hj-suppress"}>{producer.city}, {producer.state} {producer.postalCode}</div>
                </React.Fragment>
                {producer.phoneNumber && producer.phoneNumberURL &&
                  <div className={"data-hj-suppress"}><a href={producer.phoneNumberURL}>{producer.phoneNumber}</a></div>
                }
              </div>
            </div>
          ))}
        </div>

        <div className="additional-organizations d-none">
          {props.producerOrgList.slice(3).map((producer, index) => (
            <div className="well well-lg" key={index}>
              <div>
                <h2 className="well-title data-hj-suppress">{producer.name}</h2>
                <div className="small font-weight-bold text-muted text-uppercase data-hj-suppress">{Liferay.Language.get('producer-code')}: {producer.divisionNumber}-{producer.agentNumber}</div>
              </div>
              <div>
                <React.Fragment>
                  <div className={"data-hj-suppress"}>{producer.addressLine1}</div>
                  <div className={"data-hj-suppress"}>{producer.addressLine2}</div>
                  <div className={"data-hj-suppress"}>{producer.city}, {producer.state} {producer.postalCode}</div>
                </React.Fragment>
                {producer.phoneNumber && producer.phoneNumberURL &&
                <div className={"data-hj-suppress"}><a href={producer.phoneNumberURL}>{producer.phoneNumber}</a></div>
                }
              </div>
            </div>
          ))}
        </div>

        {props.producerOrgList.length > 3 &&
          <div className="bg-fade">
            <a className="cursor-pointer link-action" onClick={(e) => onShowAllClick(e)} tabIndex="0">{Liferay.Language.get('show-all')}</a>
          </div>
        }

        {props.producerOrgList.length == 0 &&
          <div className="well well-lg justify-content-around">
            <div className="empty-state">
              <h3 className="text-muted">
                {Liferay.Language.get('error.you-are-currently-not-associated-with-any-producer-organizations')}
              </h3>
              <div>
                {Liferay.Language.get('contact-your-organizations-administrator-to-be-invited-to-the-organization')}
              </div>
            </div>
          </div>
        }
      </React.Fragment>
    );
  }
};

export default ProducerOrgList;