import React from 'react';
import IconButton from 'material-ui/IconButton';

export default class PaginationButton extends React.Component {
    handleClick = () => {
        this.props.onClick(this.props.page)
    };

    render() {
        return (
            <IconButton
                color={this.props.active ? "primary" : "default"}
                disabled={this.props.disabled}
                onClick={this.handleClick}
            >
                {typeof this.props.page === 'undefined' ? '..' : this.props.page + 1}
            </IconButton>
        );
    }
}
