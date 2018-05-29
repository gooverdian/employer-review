import React from 'react';
import Typography from 'material-ui/Typography';
import './ReviewInfoRow.css';

class ReviewInfoRow extends React.Component {
    render () {
        const { title, value, valueSuffix } = this.props;
        if (!value) {
            return null;
        }

        return (
            <Typography variant="body2" className="review-row">
                {title
                    ? <span className="review-row__title">{title}: </span>
                    : ''
                }
                <span className="review-row__value">
                    {value}
                    <span className="review-row__value-suffix">
                        {valueSuffix}
                    </span>
                </span>
            </Typography>
        );
    }
}

export default ReviewInfoRow;
