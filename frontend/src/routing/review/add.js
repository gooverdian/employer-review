import React from 'react';
import FormAddReview from './_form';
import {Grid} from 'react-flexbox-grid';

export default function ViewAddReview(props) {
    return (
        <Grid className="page-add-review">
            <h1>Оставить отзыв</h1>
            <FormAddReview
                employerId={props.match.params.employerId}
            />
        </Grid>
    );
}
