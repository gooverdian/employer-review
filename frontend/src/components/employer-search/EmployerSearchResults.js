import React from 'react';
import {Col} from 'react-flexbox-grid';
import List from 'react-toolbox/lib/list/List';
import ListItem from 'react-toolbox/lib/list/ListItem';
import {Button} from 'components/router-button/RouterButton';
import FontAwesome from 'react-fontawesome';
import PaginationWidget from 'components/pagination/PaginationWidget';
import './EmployerSearchResults.css';

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
                    <ListItem
                        className="employer-search-select_nothing-found"
                        caption="По вашему запросу компаний не найдено"
                    />
                </List>
            );
        }

        return (
            <List className="employer-search-results__list" ripple>
                {this.props.data.items.map((item) => (
                    <ListItem
                        selectable
                        key={item.id}
                        avatar={item.logo_url}
                        caption={item.name}
                        onClick={() => {this.props.history.push('/employer/' + item.id)}}
                        rightActions={[
                            <Button
                                floating mini
                                key={item.id + 'button'}
                                icon={<FontAwesome name="plus" />}
                                onClick={(event) => {
                                    event.stopPropagation();
                                    this.props.history.push('/review/add/' + item.id)
                                }}
                                title="Оставить отзыв о компании"
                            />
                        ]}
                    >
                    </ListItem>
                ))}
            </List>
        );
    }

    render() {
        return (
            <Col md={9} className="employer-search-results">
                <PaginationWidget
                    pages={this.props.data ? this.props.data.pages : undefined}
                    page={this.props.data ? this.props.data.page : undefined}
                    onPageChange={page => this.handlePageChange(page)}
                />
                {this.renderResultsList()}
            </Col>
        );
    }
}

export default EmployerSearchResults;
