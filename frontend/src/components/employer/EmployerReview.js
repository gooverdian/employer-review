import React from 'react';
import Grid from 'material-ui/Grid';
import Icon from "material-ui/Icon";
import './EmployerReview.css';

class EmployerReview extends React.Component {
    render () {
        return (
            <Grid container className={'review' + (this.props.highlight ? ' review_highlighted' : '')}>
                <Grid item xs={2}>
                    <h1 className="review__header">
                        <Icon className="review__star">star</Icon> {this.props.data.rating}
                    </h1>
                </Grid>
                <Grid item xs={10} className="review__text">
                    {this.props.data.text.split("\n").map(function(item) {
                        return (
                            <span>
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
