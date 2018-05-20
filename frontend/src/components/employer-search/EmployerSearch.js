import React from 'react';
import TextField from 'material-ui/TextField';
import Grid from 'material-ui/Grid';
import EmployerSearchResults from './EmployerSearchResults';
import settings from 'config/settings';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { searchEmployers } from 'modules/employerSearch';

class EmployerSearch extends React.Component {
    requestThresholdTimer = null;
    state = {
        searchValue: this.props.search || '',
    };

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.search !== prevState.derivedSearch || nextProps.page !== prevState.derivedPage) {
            nextProps.searchEmployers(nextProps.search, nextProps.page);
            return {
                searchValue: nextProps.search || '',
                derivedSearch: nextProps.search,
                derivedPage: nextProps.page,
            };
        }

        return null;
    }

    pushLocationState(locationState) {
        let url = '/';
        if (locationState.search) {
            url += 'search/' + encodeURIComponent(locationState.search);
        }
        if (locationState.page) {
            url += '/' + locationState.page;
        }

        this.props.history.push(url);
    }

    handleTextChange = (event) => {
        const value = event.target.value;
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
            <Grid container justify="center">
                <Grid item md={9}>
                    <form className="form-employer-search">
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    type="text"
                                    label="Поиск по компаниям"
                                    placeholder="Начните вводить название компании"
                                    value={this.state.searchValue}
                                    onChange={this.handleTextChange}
                                />
                            </Grid>
                            <EmployerSearchResults
                                onPageChange={this.handlePageChange}
                                history={this.props.history}
                                items={this.props.data.items}
                                pages={this.props.data.pages}
                                page={this.props.data.page}
                            />
                    </form>
                </Grid>
            </Grid>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        search: ownProps.match.params.search,
        page: ownProps.match.params.page,
        data: state.employerSearch.data
    }
};

export default withRouter(connect(mapStateToProps, {searchEmployers})(EmployerSearch));
