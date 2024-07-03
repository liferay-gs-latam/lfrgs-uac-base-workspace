import React, {useState, useEffect} from 'react';
import ClayButton from '@clayui/button';
import ClayDropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import groupBy from 'lodash.groupby';
import map from 'lodash.map';
import ReactListify, { withCustomFilters } from 'react-big-list';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

export class AccountFilter extends React.Component {

  constructor(props) {
    super(props);

    this.clearLabels = this.clearLabels.bind(this);
    this.toggleLabel = this.toggleLabel.bind(this);

    this.state = {
      accountNameItems: [],
      members: [],
      filters: [],
      labels: [],
      producerNameItems: [],
    };
  }

  componentDidMount() {
    this.setMembers();
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.labels !== this.state.labels) {
      this.setFilters();
      this.props.setFilterCount(this.state.labels.length);
    }

    if (prevProps.filteredMembers !== this.props.filteredMembers) {
      this.setMembers();
    }

    if(prevState.filters !== this.state.filters) {
      this.setFilteredMembers();
    }
  }

  clearLabels() {
    this.setState({
      labels: [],
    });
  }

  toggleLabel(label) {
    let labels = [...this.state.labels];

    if (labels.some(item => item.value === label.value)) {
      labels = labels.filter(item => item.value !== label.value);
    }
    else {
      labels.push(label);
    }

    this.setState({
      labels: labels,
    });
  }

  setFilteredMembers() {
    const filters = this.state.filters;
    const originalMembers = this.props.originalMembers;
    let filteredMembers;

    if (filters.length) {
      filteredMembers = originalMembers.filter(item => {
        return filters.some(filter => {
          return filter.value.includes(item[filter.id]);
        });
      });
    }
    else {
      filteredMembers = originalMembers;
    }

    this.props.setFilteredMembers(filteredMembers);
  }

  setFilters() {
    const grouped = groupBy(this.state.labels, label => label.ref);

    const filters = map(grouped, (value, prop) => {
      value = value.map(v => v.value);
      return ({id: prop, value})
    });

    this.setState({
      filters: filters,
    });
  }

  setMembers() {
    this.setState({
      members: this.props.queryString ? this.props.filteredMembers : this.props.originalMembers
    }, this.setDropDownItems);
  }

  setDropDownItems() {
    let accountNameItems = [];
    let producerNameItems = [];

    this.state.members.map(member => {
      accountNameItems.push({
        label: member.accountName,
        onClick: () => {
          this.toggleLabel({
            langKey: 'account-name',
            ref: 'accountName',
            value: member.accountName,
          });
        }
      });

      let hasProducer = producerNameItems.findIndex(item => item.label == member.producerName) != -1;

      if (!hasProducer) {
        producerNameItems.push({
          label: member.producerName,
          onClick: () => {
            this.toggleLabel({
              langKey: 'producer-org',
              ref: 'producerName',
              value: member.producerName,
            });
          }
        });
      }
    });

    producerNameItems.sort((a, b) => {return a.label == b.label ? 0 : a.label > b.label ? 1 : -1;});

    this.setState({
      accountNameItems: accountNameItems,
      producerNameItems: producerNameItems,
    });
  }

  render() {
    const filterVisible = this.props.filterVisible;
    const filteredCount = this.props.filteredCount;

    return (
      <React.Fragment>
        {filterVisible && filteredCount
          ? (
            <React.Fragment>
              <ClayForm.Group>
                <ClayInput.Group>
                  <ClayInput.GroupItem shrink className="mb-4 mb-sm-1 ">
                    <DropDown
                      labels={this.state.labels}
                      caption={Liferay.Language.get('account-name')}
                      items={this.state.accountNameItems}
                    />
                  </ClayInput.GroupItem>
                  <ClayInput.GroupItem shrink className="filter-mobile-margin mb-4 mb-sm-1 mr-auto">
                    <DropDown
                      labels={this.state.labels}
                      caption={Liferay.Language.get('producer-org')}
                      items={this.state.producerNameItems}
                    />
                  </ClayInput.GroupItem>
                  <ClayInput.GroupItem shrink className="mb-sm-1 ml-auto">
                    {this.state.labels.length > 0 &&
                      <ClayButton
                        displayType="outline-secondary"
                        onClick={this.clearLabels}
                        small
                      >
                        <ClayIcon spritemap={spritemap} symbol="times-circle" className="mr-1" />
                        {Liferay.Language.get('clear-all')}
                      </ClayButton>
                    }
                  </ClayInput.GroupItem>
                </ClayInput.Group>
              </ClayForm.Group>

              {this.state.labels.length > 0 &&
                <ClayForm.Group>
                  <ActiveLabels
                    items={this.state.labels}
                    toggleLabel={this.toggleLabel}
                  />
                </ClayForm.Group>
              }
            </React.Fragment>
          )
          : ''
        }
      </React.Fragment>
    );
  }
}

const DropDown = ({labels, caption, items}) => {
  const [search, setSearch] = useState('');
  const [active, setActive] = useState(false);

  return(
    <ClayDropDown
      active={active}
      hasRightSymbols={true}
      onActiveChange={setActive}
      trigger={
        <ClayButton displayType="outline-secondary" small>
          {caption}
          <ClayIcon spritemap={spritemap} symbol="caret-double-l" />
        </ClayButton>
      }
    >
      <ClayDropDown.Search
        onChange={(event) => setSearch(event.target.value)}
        spritemap={spritemap}
        value={search}
      />
      <div className="inline-scroller">
        <ClayDropDown.ItemList>
          {items
            .filter(({label}) =>
              label.toLowerCase().match(search.toLowerCase())
            )
            .map((item, i) => (
              <ClayDropDown.Item
                active={labels.some(label => label.value === item.label)}
                key={i}
                onClick={item.onClick}
                spritemap={spritemap}
                symbolRight="check"
                className={"data-hj-suppress"}>
                {item.label}
              </ClayDropDown.Item>
            ))
          }
        </ClayDropDown.ItemList>
      </div>
    </ClayDropDown>
  );
};

const ActiveLabels = ({items, toggleLabel}) => {
  return items.map((item, i) => (
    <ClayLabel
      closeButtonProps={{
        onClick: () => {
          toggleLabel(item);
        }
      }}
      displayType="secondary"
      innerElementProps={{
        className: "flex-row",
      }}
      key={i}
      large={true}
      spritemap={spritemap}
      className={"data-hj-suppress"}
    >
      <strong className="mr-1">{Liferay.Language.get(item.langKey)}:</strong>
      {item.value}
    </ClayLabel>
  ));
};
