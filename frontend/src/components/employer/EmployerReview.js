import React from 'react';
import Grid from 'material-ui/Grid';
import StarIcon from '@material-ui/icons/Star';
import classNames from 'classnames';
import './EmployerReview.css';

class EmployerReview extends React.Component {
    render () {
        return (
            <Grid container className={classNames('review', {review_highlighted: this.props.highlight})}>
                <Grid item xs={2}>
                    <h1 className="review__header">
                        <StarIcon className="review__star" /> {this.props.data.rating}
                    </h1>
                </Grid>
                <Grid item xs={10} className="review__text">
                    {this.props.data.text.split("\n").map(function(item, index) {
                        return (
                            <span key={index}>
                                {item}
                                <br/>
                            </span>
                        )
                    })}
                </Grid>
            </Grid>
        );
    }
}

export default EmployerReview;
