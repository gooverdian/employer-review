import React from 'react';
import TextField from 'material-ui/TextField';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import EmployerSearchSelectResults from './EmployerSearchSelectResults';
import settings from 'config/settings';
import './EmployerSearchSelect.css';

class EmployerSearchSelect extends React.Component {
    state = {
        searchValue: '',
        selectedItem: null,
        results: {}
    };

    requestThresholdTimer = null;

    constructor(props, context) {
        super(props, context);
        if (props.employerId) {
            ExchangeInterface.getEmployer(props.employerId).then((data) => {
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

        this.requestThresholdTimer = setTimeout(
            () => {
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

    invalidateSelection = () => {
        if (this.props.onChange) {
            this.props.onChange(null);
        }
        this.setState({selectedItem: null});
        this.resultsComponent.invalidateSelection();
        this.resultsComponent.show();
    };

    handleSelection = (item) => {
        if (this.props.onChange) {
            this.props.onChange(item.id);
        }
        this.setState({searchValue: item.name, selectedItem: item});
    };

    showResults = () => {
        if (!this.state.selectedItem) {
            this.resultsComponent.show();
        }
    };

    hideResults = () => {
        this.resultsComponent.hide();
    };

    handleInputKeyDown = (eventProxy) => {
        let key = eventProxy.key;
        if (!this.resultsComponent.state.visible) {
            return;
        }
        let preventionNeeded = true;
        switch (key) {
            case 'ArrowDown':
                this.resultsComponent.highlightNext();
                break;
            case 'ArrowUp':
                this.resultsComponent.highlightPrev();
                break;
            case 'Enter':
                this.resultsComponent.selectHighlighted();
                break;
            default:
                preventionNeeded = false;
        }
        if (preventionNeeded) {
            eventProxy.preventDefault();
        }
    };

    render() {
        return (
            <div className="employer-search-select">
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
                    ref={ref => (this.resultsComponent = ref)}
                    onSelect={this.handleSelection}
                    data={this.state.results}
                />
            </div>
        );
    }
}

export default EmployerSearchSelect;
