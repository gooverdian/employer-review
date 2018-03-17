import React from 'react';
import List from 'react-toolbox/lib/list/List';
import ListItem from 'react-toolbox/lib/list/ListItem';

class EmployerSearchSelectResults extends React.Component {
    state = {
        data: {},
        visible: false,
        selectedIndex: null
    };

    componentDidMount() {
        if (this.props.onReference()) {
            this.props.onReference(this);
        }
    }

    componentWillUnmount() {
        if (this.props.onReference()) {
            this.props.onReference(undefined);
        }
    }

    handleSelection(itemIndex) {
        this.setState({selectedIndex: itemIndex});
        if (this.props.onSelect) {
            this.props.onSelect(this.state.data.items[itemIndex]);
        }
    }

    invalidateSelection() {
        this.setState({selectedIndex: null, data: {}});
    }

    generateResultsList() {
        if (!this.state.data.items) {
            return '';
        }

        if (this.state.data.items.length === 0) {
            return (
                <List>
                    <ListItem
                        className="nothing-found"
                        caption="По Вашему запросу компаний не найдено"
                    />
                </List>
            );
        }

        return (
            <List ripple>
                {this.state.data.items.map((item, index) => (
                    <ListItem
                        selectable
                        className={index === this.state.selectedIndex ? 'active' : ''}
                        key={index}
                        caption={String(item.name)}
                        onMouseDown={this.handleSelection.bind(this, index)}
                    >
                    </ListItem>
                ))}
            </List>
        );
    }

    show() {
        this.setState({visible: true});
    }

    hide() {
        this.setState({visible: false});
    }

    render() {
        return (
            <div className={"employer-search-select-results" + (this.state.visible ? "" : " hidden")}>
                {this.generateResultsList()}
            </div>
        );
    }
}

export default EmployerSearchSelectResults;
