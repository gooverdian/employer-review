import React from 'react';
import ReactDOM from 'react-dom';
import List, { ListItem, ListItemText } from 'material-ui/List';
import classNames from 'classnames';

class SearchSelectResults extends React.Component {
    state = {
        listScrollPosition: undefined
    };

    domNode = undefined;

    componentDidMount() {
        this.domNode = ReactDOM.findDOMNode(this)
    }

    componentWillUnmount() {
        this.domNode = undefined;
    }

    handleSelection(itemIndex) {
        if (this.props.onSelect) {
            this.props.onSelect(this.props.items[itemIndex]);
        }
    }

    scrollToHighlighted(highlightedIndex) {
        if (!this.props.items || this.props.items.length === 0) {
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
            listPadding = maxScrollPosition - (itemHeight * this.props.items.length),
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

    componentDidUpdate() {
        this.domNode.scrollTop = this.state.listScrollPosition;
    }

    renderResultsList() {
        if (!this.props.items) {
            return '';
        }

        this.scrollToHighlighted(this.props.highlightedIndex);

        if (this.props.items.length === 0) {
            return (
                <List>
                    <ListItem className="search-select-item search-select-item_nothing-found">
                        <ListItemText
                            classes={{primary: 'search-select-item__caption'}}
                            primary="Ничего не найдено"
                        />
                    </ListItem>
                </List>
            );
        }

        return (
            <List>
                {this.props.items.map((item, index) => (
                    <ListItem
                        key={index}
                        button
                        className={
                            classNames('search-select-item', {
                                'search-select-item_active': index === this.props.selectedIndex,
                                'search-select-item_highlighted': index === this.props.highlightedIndex
                            })
                        }
                        onMouseDown={this.handleSelection.bind(this, index)}
                    >
                        <ListItemText classes={{primary: 'search-select-item__caption'}} primary={item.name} />
                    </ListItem>
                ))}
            </List>
        );
    }

    render() {
        return (
            <div className="search-select__picker">
                <div className="search-select__results">
                    {this.renderResultsList()}
                </div>
                {
                    this.props.controls
                        ? <div className="search-select__controls">{this.props.controls}</div>
                        : ''
                }
            </div>
        );
    }
}

export default SearchSelectResults;
