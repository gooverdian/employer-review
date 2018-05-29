import React from 'react';
import StarIcon from '@material-ui/icons/Star';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import IconButton from 'material-ui/IconButton';
import classNames from 'classnames';

class RatingStarButton extends React.Component {
    handleActivation = () => {
        this.props.onActivate(this.props.value);
    };

    handleDeactivation = () => {
        this.props.onDeactivate();
    };

    handleClick = () => {
        this.props.onStarClick(this.props.value);
    };

    getClassName() {
        let isHovering = this.props.value <= this.props.activeIndex;
        return classNames('rating-star rating-input__star', {
            'rating-star_hover': isHovering,
            'rating-star_active': this.props.activeIndex === undefined && this.props.value <= this.props.currentRating
        });
    }

    renderStarIcon() {
        return this.props.value <= this.props.currentRating
            ? <StarIcon className="rating-star__icon" />
            : <StarBorderIcon className="rating-star__icon" />;
    }

    render() {
        return (
            <IconButton
                className={this.getClassName()}
                onMouseOver={this.handleActivation}
                onFocus={this.handleActivation}
                onMouseLeave={this.handleDeactivation}
                onBlur={this.handleDeactivation}
                onClick={this.handleClick}
            >
                {this.renderStarIcon()}
            </IconButton>
        );
    }
}

export default RatingStarButton;
