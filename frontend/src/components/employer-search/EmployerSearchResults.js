import React from 'react';
import List, { ListItem, ListItemSecondaryAction, ListItemText } from 'material-ui/List';
import { Link } from "react-router-dom";
import Avatar from 'material-ui/Avatar';
import PaginationWidget from 'components/pagination/PaginationWidget';
import IconButton from 'material-ui/IconButton';
import CommentIcon from '@material-ui/icons/Comment';
import './EmployerSearchResults.css';
import { searchEmployers } from 'modules/employerSearch';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

class EmployerSearchResults extends React.Component {
    state = {};

    handlePageChange = (page) => {
        let url = '/';
        if (this.props.search) {
            url += 'search/' + encodeURIComponent(this.props.search);
        }

        if (page) {
            url += '/' + encodeURIComponent(page);
        }

        this.props.history.push(url);
    };

    static getDerivedStateFromProps(nextProps, prevState) {
        if (nextProps.search !== prevState.derivedSearch || nextProps.page !== prevState.derivedPage) {
            nextProps.searchEmployers(nextProps.search, nextProps.page);
            return {
                derivedSearch: nextProps.search,
                derivedPage: nextProps.page,
            };
        }

        return null;
    }

    renderResultsList() {
        if (typeof this.props.items === 'undefined') {
            return null;
        }

        if (!this.props.items || this.props.items.length === 0) {
            return (
                <List>
                    <ListItem className="search-select-item search-select-item_nothing-found">
                        <ListItemText
                            classes={{primary: "search-select-item__caption"}}
                            primary="По вашему запросу компаний не найдено"
                        />
                    </ListItem>
                </List>
            );
        }

        return (
            <List>
                {this.props.items.map((item) => (
                    <ListItem
                        key={item.id}
                        button
                        component={Link}
                        to={"/employer/" + item.id}
                    >
                    <Avatar alt="Лого" src={item.logoUrl} />
                    <ListItemText primary={item.name} />
                    <ListItemSecondaryAction>
                        <IconButton component={Link} title="Оставить отзыв о компании" to={"/review/add/" + item.id}>
                            <CommentIcon />
                        </IconButton>
                    </ListItemSecondaryAction>
                    </ListItem>
                ))}
            </List>
        );
    }

    render() {
        return (
            <div className="employer-search-results">
                {this.renderResultsList()}
                <PaginationWidget
                    pages={this.props.pages}
                    page={this.props.page}
                    onPageChange={this.handlePageChange}
                />
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        search: ownProps.search,
        page: ownProps.page,
        pages: state.employerSearch.data.pages,
        items: state.employerSearch.data.items
    }
};

export default withRouter(connect(mapStateToProps, { searchEmployers })(EmployerSearchResults));
