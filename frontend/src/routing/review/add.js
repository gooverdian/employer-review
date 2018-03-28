import React from 'react';
import AddReviewForm from 'components/add-review-form/AddReviewForm';
import {Grid} from 'react-flexbox-grid';

export default function ViewAddReview({match, history}) {
    return (
        <Grid className="page-add-review">
            <h1>Оставить отзыв</h1>
            <AddReviewForm
                employerId={match.params.employerId}
                history={history}
            />
        </Grid>
    );
}
