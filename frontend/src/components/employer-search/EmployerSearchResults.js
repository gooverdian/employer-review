import React from 'react';
import {Col} from 'react-flexbox-grid';
import List from 'react-toolbox/lib/list/List';
import ListItem from 'react-toolbox/lib/list/ListItem';
import {Button} from 'components/router-button/RouterButton';
import FontAwesome from 'react-fontawesome';

const paginationMaxButtonsCount = 9;
const paginationMaxPagesCount = 15;

class EmployerSearchResults extends React.Component {
    state = {
        data: {}
    };

    pagination = {};
    pagerWidget = null;

    constructor(params) {
        super();
        if (params.onReference) {
            this.referenceCallback = params.onReference;
        }
        if (params.onPageChange) {
            this.pageChangeCallback = params.onPageChange;
        }

        this.state.data = params.data || {};
    }

    setState(state, props) {
        if (state.data.pages && state.data.pages > 1) {
            this.pagination = {
                page: state.data.page,
                pages: state.data.pages
            }
        } else {
            this.pagination = {
                page: null,
                pages: null
            }
        }
        this.generatePagination();

        return super.setState(state, props);
    }

    componentDidMount() {
        if (this.props.onReference) {
            this.props.onReference(this);
        }
    }

    componentWillUnmount() {
        if (this.props.onReference) {
            this.props.onReference(undefined);
        }
    }

    generateResultsList() {
        if (!this.state.data.items) {
            return '';
        }

        if (this.state.data.items.length === 0) {
            return (
                <List>
                    <ListItem
                        className="nothing-found"
                        caption="По вашему запросу компаний не найдено"
                    />
                </List>
            );
        }

        return (
            <List ripple>
                {this.state.data.items.map((item) => (
                    <ListItem
                        key={item.id}
                        avatar={item.logo_urls[90]}
                        caption={item.name}
                        rightActions={[
                            <Button
                                floating mini
                                key={item.id + 'button'}
                                icon={<FontAwesome name="plus" />}
                                href={"/review/add/" + item.id}
                                title="Оставить отзыв о компании"
                            />
                        ]}
                    >
                    </ListItem>
                ))}
            </List>
        );
    }

    handlePageChange(page) {
        if (page === this.state.data.page) {
            return;
        }

        if (this.props.onPageChange) {
            this.props.onPageChange(page);
        }
    }

    generatePagination() {
        this.pagerWidget = '';
        if (!this.pagination.pages || this.pagination.pages === 1) {
            return '';
        }

        let linkList = [];
        let lastPage = Math.min(paginationMaxPagesCount, this.pagination.pages) - 1;
        let currentPage = this.pagination.page;
        linkList.push(
            <Button
                key={0} label="1"
                raised mini
                accent={currentPage === 0}
                onClick={this.handlePageChange.bind(this, 0)}
            />
        );

        let nearestPagesCountLow =  Math.floor((paginationMaxButtonsCount - 3) / 2);
        let nearestPagesCountHigh = Math.ceil((paginationMaxButtonsCount - 3) / 2);

        if (currentPage - nearestPagesCountLow > 1) {
            linkList.push(
                <Button
                    key="ellipsis-low" label="..."
                    flat mini disabled
                />
            );
        } else {
            linkList.push(
                <Button
                    key={1} label="2"
                    raised mini
                    accent={currentPage === 1}
                    onClick={this.handlePageChange.bind(this, 1)}
                />
            );
        }
        let maxVisibleButtons = Math.min(lastPage, paginationMaxButtonsCount - 4);
        let firstVisiblePage = Math.max(2, currentPage - nearestPagesCountLow + 1);

        if (lastPage - currentPage < nearestPagesCountHigh + 1) {
            firstVisiblePage = Math.max(2, firstVisiblePage - (nearestPagesCountHigh - (lastPage - currentPage)) - 1);
        }

        for(let i = 0; i < maxVisibleButtons; i++) {
            let currentLink = i + firstVisiblePage;
            if (currentLink > lastPage - 2) {
                break;
            }
            linkList.push(
                <Button
                    key={currentLink} label={String(currentLink + 1)}
                    raised mini
                    accent={currentPage === currentLink}
                    onClick={this.handlePageChange.bind(this, currentLink)}
                />
            );
        }

        if (lastPage > 2) {
            if (currentPage + nearestPagesCountHigh < lastPage - 1) {
                linkList.push(
                    <Button
                        key="ellipsis-high" label="..."
                        flat mini disabled
                    />
                );
            } else {
                linkList.push(
                    <Button
                        key={lastPage - 1} label={String(lastPage)}
                        raised mini
                        accent={currentPage === lastPage - 1}
                        onClick={this.handlePageChange.bind(this, lastPage - 1)}
                    />
                );
            }
        }

        if (lastPage > 1) {
            linkList.push(
                <Button
                    key={lastPage} label={String(lastPage + 1)}
                    raised mini floating
                    accent={currentPage === lastPage}
                    onClick={this.handlePageChange.bind(this, lastPage)}
                />
            );
        }

        this.pagerWidget = (
            <div className="pagination">{linkList}</div>
        );
    }

    render() {
        return (
            <Col md={9} className="employer-search-results">
                {this.pagerWidget}
                {this.generateResultsList()}
            </Col>
        );
    }
}

export default EmployerSearchResults;
