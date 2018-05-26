import React from 'react';
import TextField from 'material-ui/TextField';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import Button from 'material-ui/Button';
import SearchIcon from '@material-ui/icons/Search';
import './EmployerSearchForm.css'

class EmployerSearchForm extends React.Component {
    state = {
        searchValue: this.props.search || '',
    };

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.search !== prevState.derivedSearch) {
            return {
                searchValue: nextProps.search || '',
                derivedSearch: nextProps.search,
            };
        }

        return null;
    }

    performSearch(search) {
        let url = '/';
        if (search) {
            url += 'search/' + encodeURIComponent(search);
        }

        this.props.history.push(url);
    }

    handleTextChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    };

    handleSubmission = (event) => {
        event.preventDefault();

        this.performSearch(this.state.searchValue);
    };

    render () {
        return (
            <form className="header-form" onSubmit={this.handleSubmission}>
                <TextField
                    key="search"
                    size="large"
                    className="flex flex_max"
                    InputProps={{
                        disableUnderline: true,
                        classes: {
                            root: "header-form__search",
                            input: "header-form__input",
                        },
                    }}
                    type="text"
                    placeholder="Название компании"
                    name="searchValue"
                    value={this.state.searchValue}
                    onChange={this.handleTextChange}
                />
                <TextField
                    key="region"
                    size="large"
                    InputProps={{
                        disableUnderline: true,
                        classes: {
                            root: "header-form__search",
                            input: "header-form__input header-form_middle",
                        },
                    }}
                    type="text"
                    placeholder="регион"
                    name="searchValue"
                    value="Москва"
                />
                <Button className="header-form__button" variant="raised" color="secondary" size="large" type="submit">
                    <SearchIcon className="header-form__button-icon" />
                </Button>
            </form>
        );
    }
}

const mapStateToProps = (state, ownProps) => ({ search: ownProps.match.params.search });

export default withRouter(connect(mapStateToProps)(EmployerSearchForm));
