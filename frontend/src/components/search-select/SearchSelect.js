import React from 'react';
import TextField from 'material-ui/TextField';
import SearchSelectResults from './SearchSelectResults';
import settings from 'config/settings';
import classNames from 'classnames';
import './SearchSelect.css';

class SearchSelect extends React.Component {
    state = {
        searchValue: '',
        results: {},
        selectedItem: undefined,
        derivedSelectedItem: undefined,
        highlightedIndex: 0,
        resultsVisible: false
    };

    requestThresholdTimer = null;

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.selectedItem !== prevState.derivedSelectedItem) {
            return {
                derivedSelectedItem: nextProps.selectedItem,
                selectedItem: nextProps.selectedItem,
                searchValue: nextProps.selectedItem.name
            };
        }

        return null;
    }

    searchSuccess = (data) => {
        this.setState({results: data});
    };

    static searchFailure(error) {
        console.log(error)
    }

    handleTextChange = (event) => {
        let value = event.target.value;
        if (this.props.onTextChange) {
            this.props.onTextChange(value);
        }
        this.setState({searchValue: value});
        if (this.requestThresholdTimer) {
            clearTimeout(this.requestThresholdTimer);
        }

        if (this.state.selectedItem) {
            this.invalidateSelection();
        }

        this.requestThresholdTimer = setTimeout(() => {
                if (value === '') {
                    this.setState({results: {}});
                } else {
                    this.props.getSearchResults(value, this.searchSuccess, this.searchFailure);
                }
            },
            settings.searchRequestThreshold
        );
    };

    highlightNext() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.state.results.items
            || this.state.results.items.length === 0
            || currentlyHighlighted + 1 >= this.state.results.items.length
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted + 1});

        return true;
    }

    highlightPrev() {
        let currentlyHighlighted = this.state.highlightedIndex;
        if (!this.state.results.items
            || this.state.results.items.length === 0
            || currentlyHighlighted <= 0
        ) {
            return false;
        }
        this.setState({highlightedIndex: currentlyHighlighted - 1});

        return true;
    }

    selectHighlighted() {
        this.handleSelection(this.state.results.items[this.state.highlightedIndex]);
    }

    invalidateSelection = () => {
        if (this.props.onChange) {
            this.props.onChange(undefined);
        }
        this.setState({
            selectedItem: undefined,
            resultsVisible: true,
        });
    };

    handleSelection = (item) => {
        if (this.props.onChange) {
            this.props.onChange(item.id);
        }
        this.setState({
            searchValue: item.name,
            selectedItem: item,
            resultsVisible: false
        });
    };

    showResults = () => {
        if (!this.state.selectedItem) {
            this.setState({resultsVisible: true});
        }
    };

    hideResults = () => {
        this.setState({resultsVisible: false});
    };

    handleInputKeyDown = (event) => {
        let key = event.key;
        if (!this.state.resultsVisible) {
            return;
        }
        let preventionNeeded = true;
        switch (key) {
            case 'ArrowDown':
                this.highlightNext();
                break;
            case 'ArrowUp':
                this.highlightPrev();
                break;
            case 'Enter':
                this.selectHighlighted();
                break;
            default:
                preventionNeeded = false;
        }
        if (preventionNeeded) {
            event.preventDefault();
        }
    };

    render() {
        return (
            <div className={classNames(
                'search-select',
                {'search-select_picker-hidden': !this.state.resultsVisible}
            )}>
                <TextField
                    error={Boolean(this.props.error)}
                    fullWidth
                    className="search-select__input"
                    label={this.props.label}
                    placeholder="Начните вводить название"
                    value={this.state.searchValue}
                    onChange={this.handleTextChange}
                    onKeyDown={this.handleInputKeyDown}
                    onFocus={this.showResults}
                    onBlur={this.hideResults}
                    helperText={this.props.error ? this.props.error : undefined}
                />
                <SearchSelectResults
                    onSelect={this.handleSelection}
                    items={this.state.results ? this.state.results.items : undefined}
                    highlightedIndex={this.state.highlightedIndex}
                    selectedItem={this.state.selectedItem}
                    controls={this.props.controls}
                />
            </div>
        );
    }
}

export default SearchSelect;
