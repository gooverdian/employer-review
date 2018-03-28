import React from 'react';
import Input from 'react-toolbox/lib/input/Input';
import {Grid, Row, Col} from 'react-flexbox-grid';
import Exchange from 'helpers/exchange/Exchange';
import EmployerSearchResults from './EmployerSearchResults';
import settings from "config/settings";

class EmployerSearch extends React.Component {
    state = {
        searchValue: '',
        page: null
    };

    stopListeningHistory = undefined;
    requestThresholdTimer = null;

    componentDidMount() {
        let state = {
            search: this.props.search || '',
            page: this.props.page || null
        };
        this.props.history.replace(
            this.props.history.pathname,
            state
        );
        let instance = this;
        this.stopListeningHistory = this.props.history.listen((location, action) => {
            if (action === 'POP') {
                instance.onHistoryPop(location.state);
            }
            if (action === 'PUSH' && !location.state) {
                instance.onHistoryPop();
            }
        });
        this.onHistoryPop(state);
    }

    componentWillUnmount() {
        this.stopListeningHistory();
    }

    historyPush(state) {
        let url = '/';
        if (state.search) {
            url += 'search/' + encodeURIComponent(state.search);
        }
        if (state.page) {
            url += '/' + state.page;
        }

        this.props.history.push(
            url,
            state
        );
    }

    performSearch(value, page) {
        if (!value || value === '') {
            this.resultsComponent.setState({
                data: {}
            });
        } else {
            let instance = this;
            Exchange.employerSearch(value, page).then(function (data) {
                instance.resultsComponent.setState({
                    data: data
                });
            }, function (error) {
                console.log(error);
            });
        }
    }

    onHistoryPop(state) {
        state = state || {};
        this.setState({
            searchValue: state.search || '',
            page: state.page || null
        });
        this.performSearch(state.search, state.page);
    }

    handleTextChange = (value) => {
        this.setState({...this.state, searchValue: value});

        if (this.requestThresholdTimer) {
            clearTimeout(this.requestThresholdTimer);
        }

        let instance = this;
        this.requestThresholdTimer = setTimeout(
            function () {
                instance.performSearch(value);
                instance.historyPush({search: value});
            },
            settings.searchRequestThreshold
        );
    };

    handlePageChange = (page) => {
        this.performSearch(this.state.searchValue, page);
        this.historyPush({
            search: this.state.searchValue,
            page: page
        });
    };

    render () {
        return (
            <Grid>
                <form className="form-employer-search">
                    <Row center="xs">
                        <Col md={9}>
                            <Input
                                type="text"
                                label="Поиск по компаниям"
                                value={this.state.searchValue}
                                onChange={this.handleTextChange.bind(this)}
                            />
                        </Col>
                        <EmployerSearchResults
                            onReference={ref => (this.resultsComponent = ref)}
                            onPageChange={page => (this.handlePageChange(page))}
                            history={this.props.history}
                        />
                    </Row>
                </form>
            </Grid>
        );
    }
}

export default EmployerSearch;
