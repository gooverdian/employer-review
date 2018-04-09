import React from 'react';
import Input from 'react-toolbox/lib/input/Input';
import {Grid, Row, Col} from 'react-flexbox-grid';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import EmployerSearchResults from './EmployerSearchResults';
import settings from 'config/settings';

class EmployerSearch extends React.Component {
    stopListeningHistory = undefined;
    requestThresholdTimer = null;

    constructor(props, context) {
        super(props, context);
        let locationState = props.history.location.state;
        if (!locationState) {
            locationState = {
                search: this.props.search || '',
                page: this.props.page || 0
            };
            this.props.history.replace(
                this.props.history.pathname,
                locationState
            );
        }

        this.state = this.getStateFromLocation(locationState);
    }

    fetchEmployers(state) {
        ExchangeInterface.employerSearch(state.searchValue, state.page).then((data) => {
            this.setState({...state, results: data});
        }, (error) => {
            console.log(error);
        });
    }

    componentDidMount() {
        this.stopListeningHistory = this.props.history.listen((location) => {
            this.popLocationState(location.state);
        });

        if (String(this.state.searchValue).length > 0) {
            this.fetchEmployers(this.state);
        }
    }

    componentWillUnmount() {
        this.stopListeningHistory();
        this.stopListeningHistory = undefined;
    }

    pushLocationState(locationState) {
        let url = '/';
        if (locationState.search) {
            url += 'search/' + encodeURIComponent(locationState.search);
        }
        if (locationState.page) {
            url += '/' + locationState.page;
        }

        this.props.history.push(url, locationState);
    }

    getStateFromLocation(locationState) {
        locationState = locationState || {};
        let state = {
            searchValue: locationState.search || '',
            page: locationState.page || 0,
        };

        if (String(state.searchValue).length === 0) {
            state.results = {};
        }

        return state;
    }

    popLocationState(locationState) {
        let state = this.getStateFromLocation(locationState);

        if(!state.results) {
            this.fetchEmployers(state);
        } else {
            this.setState(state);
        }
    }

    handleTextChange = (value) => {
        this.setState({searchValue: value});

        if (this.requestThresholdTimer) {
            clearTimeout(this.requestThresholdTimer);
        }

        this.requestThresholdTimer = setTimeout(
            () => {
                this.pushLocationState({search: value});
            },
            settings.searchRequestThreshold
        );
    };

    handlePageChange = (page) => {
        this.pushLocationState({
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
                                onChange={value => this.handleTextChange(value)}
                            />
                        </Col>
                        <EmployerSearchResults
                            onPageChange={page => (this.handlePageChange(page))}
                            history={this.props.history}
                            data={this.state.results}
                        />
                    </Row>
                </form>
            </Grid>
        );
    }
}

export default EmployerSearch;
