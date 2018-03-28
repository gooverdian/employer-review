import React from 'react';
import FormAddReview from './_form';
import {Grid} from 'react-flexbox-grid';

export default function ViewAddReview({match, history}) {
    return (
        <Grid className="page-add-review">
            <h1>Оставить отзыв</h1>
            <FormAddReview
                employerId={match.params.employerId}
                history={history}
            />
        </Grid>
    );
}
