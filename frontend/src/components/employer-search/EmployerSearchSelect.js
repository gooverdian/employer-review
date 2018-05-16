import React from 'react';
import TextField from 'material-ui/TextField';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import EmployerSearchSelectResults from './EmployerSearchSelectResults';
import settings from 'config/settings';
import classNames from 'classnames';
import './EmployerSearchSelect.css';

class EmployerSearchSelect extends React.Component {
    state = {
        searchValue: '',
        results: {},
        selectedItem: undefined,
        highlightedIndex: 0,
        resultsVisible: false
    };

    requestThresholdTimer = null;

    componentDidMount() {
        if (this.props.employerId) {
            ExchangeInterface.getEmployer(this.props.employerId).then((data) => {
                this.handleSelection(data);
            }, (error) => {
                console.log(error);
            });
        }
    }

    handleTextChange = (event) => {
        let value = event.target.value;
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
                    ExchangeInterface.employerSearch(value, 0, settings.selectPageSize).then((data) => {
                        this.setState({results: data});
                    }, (error) => {
                        console.log(error);
                    });
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
                'employer-search-select',
                {'employer-search-select_results-hidden': !this.state.resultsVisible}
            )}>
                <TextField
                    error={Boolean(this.props.error)}
                    fullWidth
                    className="employer-search-select-input"
                    label="Выберите компанию"
                    placeholder="Начните вводить название"
                    value={this.state.searchValue}
                    onChange={this.handleTextChange}
                    onKeyDown={this.handleInputKeyDown}
                    onFocus={this.showResults}
                    onBlur={this.hideResults}
                    helperText={this.props.error ? this.props.error : undefined}
                />
                <EmployerSearchSelectResults
                    onSelect={this.handleSelection}
                    items={this.state.results ? this.state.results.items : undefined}
                    highlightedIndex={this.state.highlightedIndex}
                    selectedItem={this.state.selectedItem}
                />
            </div>
        );
    }
}

export default EmployerSearchSelect;
