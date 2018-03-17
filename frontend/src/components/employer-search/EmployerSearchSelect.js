import React from 'react';
import Input from 'react-toolbox/lib/input/Input';
import Exchange from 'helpers/exchange/Exchange';
import EmployerSearchSelectResults from './EmployerSearchSelectResults';
import './EmployerSearchSelect.css';

const requestThreshold = 300;

class EmployerSearchSelect extends React.Component {
    state = {
        searchValue: '',
        selectedItem: null
    };

    requestThresholdTimer = null;

    constructor(params) {
        super();
        if (params.employerId) {
            let instance = this;
            Exchange.getEmployer(params.employerId).then(function(data) {
                instance.handleSelection(data);
            }, function(error) {
                console.log(error);
            });
        }
    }

    handleTextChange(value) {
        this.setState({searchValue: value});
        if (this.requestThresholdTimer) {
            clearTimeout(this.requestThresholdTimer);
        }

        if (this.state.selectedItem) {
            this.invalidateSelection();
        }

        let instance = this;
        this.requestThresholdTimer = setTimeout(
            function () {
                if (value === '') {
                    instance.resultsComponent.setState({data: {}});
                } else {
                    Exchange.employerSearch(value, 0, 50).then(function (data) {
                        instance.resultsComponent.setState({data: data});
                    }, function (error) {
                        console.log(error);
                    });
                }

            },
            requestThreshold
        );
    };

    invalidateSelection() {
        if (this.props.onChange) {
            this.props.onChange(null);
        }
        this.setState({selectedItem: null});
        this.resultsComponent.invalidateSelection();
        this.resultsComponent.show();
    }

    handleSelection(item) {
        if (this.props.onChange) {
            this.props.onChange(item.id);
        }
        this.setState({searchValue: item.name, selectedItem: item});
    }

    handleFocus() {
        if (!this.state.selectedItem) {
            this.resultsComponent.show();
        }
    }

    handleBlur() {
        this.resultsComponent.hide();
    }

    render() {
        return (
            <div className="employer-search-select">
                <Input
                    className="employer-search-select-input"
                    type="text"
                    label="Выберите компанию"
                    value={this.state.searchValue}
                    onChange={this.handleTextChange.bind(this)}
                    onFocus={this.handleFocus.bind(this)}
                    onBlur={this.handleBlur.bind(this)}
                    error={this.props.error}
                />
                <EmployerSearchSelectResults
                    onSelect={this.handleSelection.bind(this)}
                    onReference={ref => (this.resultsComponent = ref)}
                />
            </div>
        );
    }
}

export default EmployerSearchSelect;
