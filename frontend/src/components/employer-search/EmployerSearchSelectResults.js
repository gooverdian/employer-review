import React from 'react';
import ReactDOM from 'react-dom';
import List from 'react-toolbox/lib/list/List';
import ListItem from 'react-toolbox/lib/list/ListItem';

class EmployerSearchSelectResults extends React.Component {
    state = {
        data: {},
        visible: false,
        selectedIndex: null,
        highlightedIndex: null,
        listScrollPosition: null
    };

    componentDidMount() {
        if (this.props.onReference) {
            this.props.onReference(this);
        }
    }

    componentWillUnmount() {
        if (this.props.onReference) {
            this.props.onReference(undefined);
        }
    }

    handleSelection(itemIndex) {
        this.setState({
            selectedIndex: itemIndex,
            highlightedIndex: itemIndex
        });
        if (this.props.onSelect) {
            this.props.onSelect(this.state.data.items[itemIndex]);
        }
    }

    invalidateSelection() {
        this.setState({
            selectedIndex: null,
            highlightedIndex: null,
            data: {}
        });
    }

    highlightNext() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.state.data.items
            || this.state.data.items.length === 0
            || currentlyHighlighted + 1 >= this.state.data.items.length
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted + 1});
        this.scrollToHighlighted(currentlyHighlighted + 1);

        return true;
    }

    highlightPrev() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.state.data.items
            || this.state.data.items.length === 0
            || currentlyHighlighted <= 0
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted - 1});
        this.scrollToHighlighted(currentlyHighlighted - 1);

        return true;
    }

    scrollToHighlighted(highlightedIndex) {
        if (!this.state.data.items || this.state.data.items.length === 0) {
            return false;
        }
        let resultsNode = ReactDOM.findDOMNode(this),
            listNode = resultsNode.childNodes[0];
        if (!listNode) {
            return false;
        }
        let listItemNode = listNode.childNodes[0];
        if (!listItemNode) {
            return false;
        }
        let itemHeight = listItemNode.offsetHeight,
            maxScrollPosition = resultsNode.scrollHeight,
            currentScrollPosition = resultsNode.scrollTop,
            nodeHeight = resultsNode.offsetHeight,
            itemTop = highlightedIndex * itemHeight,
            itemBottom = itemTop + itemHeight,
            listPadding = maxScrollPosition - (itemHeight * this.state.data.items.length);
        if (Math.max(itemTop - 15, 0) < currentScrollPosition) {
            this.setState({
                listScrollPosition: Math.max(itemTop - 15, 0)
            });
            return;
        }
        if (Math.min(maxScrollPosition, itemBottom + 15) > (currentScrollPosition + nodeHeight)) {
            this.setState({
                listScrollPosition: Math.min(itemBottom - nodeHeight + (15 + listPadding), maxScrollPosition)
            });
        }
    }

    selectHighlighted() {
        this.handleSelection(this.state.highlightedIndex);
        this.hide();
    }

    componentDidUpdate() {
        let listNode = ReactDOM.findDOMNode(this);
        listNode.scrollTop = this.state.listScrollPosition;
    }


    generateResultsList() {
        if (!this.state.data.items) {
            return '';
        }

        if (this.state.data.items.length === 0) {
            return (
                <List>
                    <ListItem
                        className="employer-search-select_nothing-found"
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
                        className={
                            (index === this.state.selectedIndex ? 'employer-search-select_active' : '')
                            + (index === this.state.highlightedIndex ? 'employer-search-select_highlighted' : '')
                        }
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
        this.setState({
            visible: true,
            highlightedIndex: 0
        });
    }

    hide() {
        this.setState({visible: false});
    }

    render() {
        return (
            <div className={"employer-search-select__results" + (this.state.visible ? "" : " hidden")}>
                {this.generateResultsList()}
            </div>
        );
    }
}

export default EmployerSearchSelectResults;
