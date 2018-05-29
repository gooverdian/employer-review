import React from 'react';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import Typography from 'material-ui/Typography';
import classNames from 'classnames';
import './EmployerReview.css';
import 'assets/css/RatingPlate.css';

class EmployerReview extends React.Component {
    renderReviewText = () => {
        if (!this.props.data.text) {
            return (<span className="review__empty-text">Отзыв без текста</span>);
        }

        let formattedText = this.props.data.text.replace(/\n\s*\n/g, '\n');

        return formattedText.split("\n").map(function(item, index) {
            return (<span key={index}>{item}<br/></span>)
        });
    };

    render () {
        console.log(this.props.highlight);
        return (
            <div className={classNames('review', {review_highlighted: this.props.highlight})}>
                <Typography
                    className="review__rating rating-plate"
                    variant="display1"
                    gutterBottom={false}
                >
                    <span className="rating-plate__rating">
                        <StarBorderIcon classes={{root: 'rating-plate__rating-icon'}}/>
                        {this.props.data.rating}
                    </span>
                </Typography>
                <Typography variant="body2" className="review__time">
                    {new Date(this.props.data.created_on.substr(0, this.props.data.created_on.length - 3)).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" className="review__text">
                    {this.renderReviewText()}
                </Typography>
            </div>
        );
    }
}

export default EmployerReview;
