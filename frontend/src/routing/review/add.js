import React from 'react';
import AddReviewForm from 'components/add-review-form/AddReviewForm';

const ViewReviewAdd = function({match, history}) {
    return (
        <div className="page-add-review">
            <h1>Оставить отзыв</h1>
            <AddReviewForm
                employerId={match.params.employerId}
                history={history}
            />
        </div>
    );
};

export default ViewReviewAdd;
