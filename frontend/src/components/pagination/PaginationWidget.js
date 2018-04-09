import React from 'react';
import {Button} from 'components/router-button/RouterButton';

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
            <Button
                key={0} label="1"
                raised mini
                accent={currentPage === 0}
                onClick={() => this.handlePageChange(0)}
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
                    onClick={() => this.handlePageChange(1)}
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
                    onClick={() => this.handlePageChange(currentLink)}
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
                        onClick={() => this.handlePageChange(lastPage - 1)}
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
                    onClick={() => this.handlePageChange(lastPage)}
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