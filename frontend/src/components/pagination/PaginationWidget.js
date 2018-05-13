import React from 'react';
import IconButton from 'material-ui/IconButton';

const paginationMaxButtonsCount = 9;
const paginationMaxPagesCount = 15;

export default class PaginationWidget extends React.Component {
    pagination = {};
    widgetCache = null;

    handlePageChange(newPage) {
        if (newPage === this.props.page) {
            return;
        }

        if (this.props.onPageChange) {
            this.props.onPageChange(newPage);
        }
    }

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
            <IconButton
                key={0}
                color={currentPage === 0 ? "primary" : "default"}
                onClick={() => this.handlePageChange(0)}
            >
                {"1"}
            </IconButton>
        );

        let nearestPagesCountLow =  Math.floor((paginationMaxButtonsCount - 3) / 2);
        let nearestPagesCountHigh = Math.ceil((paginationMaxButtonsCount - 3) / 2);

        if (currentPage - nearestPagesCountLow > 1) {
            linkList.push(
                <IconButton key="ellipsis-low" disabled>
                    {".."}
                </IconButton>
            );
        } else {
            linkList.push(
                <IconButton
                    key={1}
                    color={currentPage === 1 ? "primary" : "default"}
                    onClick={() => this.handlePageChange(1)}
                >
                    {"2"}
                </IconButton>
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
                <IconButton
                    key={currentLink}
                    color={currentPage === currentLink ? "primary" : "default"}
                    onClick={() => this.handlePageChange(currentLink)}
                >
                    {String(currentLink + 1)}
                </IconButton>
            );
        }

        if (lastPage > 2) {
            if (currentPage + nearestPagesCountHigh < lastPage - 1) {
                linkList.push(
                    <IconButton key="ellipsis-high" disabled>
                        {".."}
                    </IconButton>
                );
            } else {
                linkList.push(
                    <IconButton
                        key={lastPage - 1}
                        color={currentPage === lastPage - 1 ? "primary" : "default"}
                        onClick={() => this.handlePageChange(lastPage - 1)}
                    >
                        {String(lastPage)}
                    </IconButton>
                );
            }
        }

        if (lastPage > 1) {
            linkList.push(
                <IconButton
                    key={lastPage}
                    color={currentPage === lastPage ? "primary" : "default"}
                    onClick={() => this.handlePageChange(lastPage)}
                >
                    {String(lastPage + 1)}
                </IconButton>
            );
        }

        this.pagination = {
            pages: this.props.pages,
            page: this.props.page
        };

        this.widgetCache = (
            <div className="paginati1on">{linkList}</div>
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