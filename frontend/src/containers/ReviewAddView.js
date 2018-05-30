import React from 'react';
import AddReviewForm from 'components/add-review-form/AddReviewForm';

const ReviewAddView = function({match, history}) {
    return (
        <div>
            <h1>Оставить отзыв</h1>
            <AddReviewForm
                employerId={match.params.employerId}
                history={history}
            />
        </div>
    );
};

export default ReviewAddView;