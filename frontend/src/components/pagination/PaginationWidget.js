import React from 'react';
import PaginationButton from './PaginationButton';

const paginationMaxButtonsCount = 9;
const paginationMaxPagesCount = 15;

const FIRST_PAGE = 0;

export default class PaginationWidget extends React.Component {
    pagination = {};
    widgetCache = null;

    handlePageChange = (newPage) => {
        if (newPage === this.props.page) {
            return;
        }

        if (this.props.onPageChange) {
            this.props.onPageChange(newPage);
        }
    };

    regenerateCache() {
        if (!this.props.pages || this.props.pages < 2) {
            this.widgetCache = null;
            this.pagination = {};
            return;
        }

        let linkList = [];
        let lastPage = Math.min(paginationMaxPagesCount, this.props.pages) - 1;
        let currentPage = this.props.page;
        linkList.push(
            <PaginationButton
                key={FIRST_PAGE}
                page={FIRST_PAGE}
                active={currentPage === FIRST_PAGE}
                onClick={this.handlePageChange}
            />
        );

        let nearestPagesCountLow =  Math.floor((paginationMaxButtonsCount - 3) / 2);
        let nearestPagesCountHigh = Math.ceil((paginationMaxButtonsCount - 3) / 2);

        if (currentPage - nearestPagesCountLow > 1) {
            linkList.push(
                <PaginationButton key="ellipsis-low" disabled />
            );
        } else {
            linkList.push(
                <PaginationButton
                    key={FIRST_PAGE + 1}
                    page={FIRST_PAGE + 1}
                    active={currentPage === FIRST_PAGE + 1}
                    onClick={this.handlePageChange}
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
                <PaginationButton
                    key={currentLink}
                    page={currentLink}
                    active={currentPage === currentLink}
                    onClick={this.handlePageChange}
                />
            );
        }

        if (lastPage > 2) {
            if (currentPage + nearestPagesCountHigh < lastPage - 1) {
                linkList.push(
                    <PaginationButton key="ellipsis-high" disabled />
                );
            } else {
                linkList.push(
                    <PaginationButton
                        key={lastPage - 1}
                        page={lastPage - 1}
                        active={currentPage === lastPage - 1}
                        onClick={this.handlePageChange}
                    />
                );
            }
        }

        if (lastPage > 1) {
            linkList.push(
                <PaginationButton
                    key={lastPage}
                    page={lastPage}
                    active={currentPage === lastPage}
                    onClick={this.handlePageChange}
                />
            );
        }

        this.pagination = {
            pages: this.props.pages,
            page: this.props.page
        };

        this.widgetCache = (
            <div className="pagination">{linkList}</div>
        );
    }

    render() {
        if (this.pagination.page !== this.props.page
            || this.pagination.pages !== this.props.pages) {
            this.regenerateCache();
        }
        return this.widgetCache;
    }
}
