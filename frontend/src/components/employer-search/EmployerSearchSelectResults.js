import React from 'react';
import ReactDOM from 'react-dom';
import List, { ListItem, ListItemText } from 'material-ui/List';

class EmployerSearchSelectResults extends React.Component {
    state = {
        visible: false,
        selectedIndex: null,
        highlightedIndex: null,
        listScrollPosition: null
    };

    domNode = undefined;

    constructor(props, context) {
        super(props, context);

        if (!this.props.data) {
            this.props.data = {};
        }
    }

    componentDidMount() {
        this.domNode = ReactDOM.findDOMNode(this)
    }

    componentWillUnmount() {
        this.domNode = undefined;
    }

    handleSelection(itemIndex) {
        this.setState({
            selectedIndex: itemIndex,
            highlightedIndex: itemIndex
        });
        if (this.props.onSelect) {
            this.props.onSelect(this.props.data.items[itemIndex]);
        }
    }

    invalidateSelection() {
        this.setState({
            selectedIndex: null,
            highlightedIndex: null,
        });
    }

    highlightNext() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.props.data.items
            || this.props.data.items.length === 0
            || currentlyHighlighted + 1 >= this.props.data.items.length
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted + 1});
        this.scrollToHighlighted(currentlyHighlighted + 1);

        return true;
    }

    highlightPrev() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.props.data.items
            || this.props.data.items.length === 0
            || currentlyHighlighted <= 0
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted - 1});
        this.scrollToHighlighted(currentlyHighlighted - 1);

        return true;
    }

    scrollToHighlighted(highlightedIndex) {
        if (!this.props.data.items || this.props.data.items.length === 0) {
            return false;
        }
        let listNode = this.domNode.childNodes[0];
        if (!listNode) {
            return false;
        }
        let listItemNode = listNode.childNodes[0];
        if (!listItemNode) {
            return false;
        }
        let itemHeight = listItemNode.offsetHeight,
            maxScrollPosition = this.domNode.scrollHeight,
            currentScrollPosition = this.domNode.scrollTop,
            nodeHeight = this.domNode.offsetHeight,
            itemTop = highlightedIndex * itemHeight,
            itemBottom = itemTop + itemHeight,
            listPadding = maxScrollPosition - (itemHeight * this.props.data.items.length),
            scrollPadding = Math.floor(itemHeight / 3);
        if (Math.max(itemTop - scrollPadding, 0) < currentScrollPosition) {
            this.setState({
                listScrollPosition: Math.max(itemTop - scrollPadding, 0)
            });
            return;
        }
        if (Math.min(maxScrollPosition, itemBottom + scrollPadding) > (currentScrollPosition + nodeHeight)) {
            this.setState({
                listScrollPosition: Math.min(itemBottom - nodeHeight + (scrollPadding + listPadding), maxScrollPosition)
            });
        }
    }

    selectHighlighted() {
        this.handleSelection(this.state.highlightedIndex);
        this.hide();
    }

    componentDidUpdate() {
        this.domNode.scrollTop = this.state.listScrollPosition;
    }

    renderResultsList() {
        if (!this.props.data.items) {
            return '';
        }

        if (this.props.data.items.length === 0) {
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
                {this.props.data.items.map((item, index) => (
                    <ListItem
                        button
                        className={
                            (index === this.state.selectedIndex ? 'employer-search-select_active' : '')
                            + (index === this.state.highlightedIndex ? 'employer-search-select_highlighted' : '')
                        }
                        key={index}
                        onClick={this.handleSelection.bind(this, index)}
                    >
                        <ListItemText primary={item.name} />
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
                {this.renderResultsList()}
            </div>
        );
    }
}

export default EmployerSearchSelectResults;
