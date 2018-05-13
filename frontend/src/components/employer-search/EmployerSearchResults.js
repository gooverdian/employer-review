import React from 'react';
import { withStyles } from 'material-ui/styles';
import List, { ListItem, ListItemSecondaryAction, ListItemText } from 'material-ui/List';
import Avatar from 'material-ui/Avatar';
import PaginationWidget from 'components/pagination/PaginationWidget';
import IconButton from 'material-ui/IconButton';
import CommentIcon from '@material-ui/icons/Comment';
import './EmployerSearchResults.css';

const styles = theme => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
});

class EmployerSearchResults extends React.Component {
    handlePageChange(page) {
        if (this.props.onPageChange) {
            this.props.onPageChange(page);
        }
    }

    renderResultsList() {
        if (!this.props.data || !this.props.data.items) {
            return null;
        }

        if (this.props.data.items.length === 0) {
            return (
                <List>
                    <ListItem className="employer-search-select_nothing-found">
                        <ListItemText primary="По вашему запросу компаний не найдено" />
                    </ListItem>
                </List>
            );
        }

        return (
            <List>
                {this.props.data.items.map((item) => (
                    <ListItem
                        button
                        key={item.id}
                        onClick={() => {this.props.history.push('/employer/' + item.id)}}
                    >
                    <Avatar alt="Лого" src={item.logo_url} />
                    <ListItemText primary={item.name} />
                    <ListItemSecondaryAction>
                        <IconButton title="Оставить отзыв о компании" onClick={() => {this.props.history.push('/review/add/' + item.id)}}>
                            <CommentIcon />
                        </IconButton>
                    </ListItemSecondaryAction>
                    </ListItem>
                ))}
            </List>
        );
    }

    render() {
        const { classes } = this.props;
        return (
            <div className="employer-search-results">
                {this.renderResultsList(classes)}
                <PaginationWidget
                    pages={this.props.data ? this.props.data.pages : undefined}
                    page={this.props.data ? this.props.data.page : undefined}
                    onPageChange={page => this.handlePageChange(page)}
                />
            </div>
        );
    }
}

export default withStyles(styles)(EmployerSearchResults);
