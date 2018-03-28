import React from 'react';
import { withRouter } from 'react-router-dom';
import EmployerCard from "components/employer/EmployerCard";
import EmployerReviews from "components/employer/EmployerReviews";

const ViewEmployerIndex = withRouter(function({match, history}) {
    return (
        <div className="page-employer-index">
            <EmployerCard
                employerId={match.params.employerId}
            />
            <EmployerReviews
                employerId={match.params.employerId}
                history={history}
            />
        </div>
    );
});

export default ViewEmployerIndex;
