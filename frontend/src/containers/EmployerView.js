import React from 'react';
import Grid from 'material-ui/Grid';
import EmployerCard from 'components/employer/EmployerCard';
import EmployerReviews from 'components/employer/EmployerReviews';

const EmployerView = function({match, history}) {
    return (
        <div>
            <Grid container>
                <Grid item xs>
                    <EmployerCard
                        employerId={match.params.employerId}
                    />
                </Grid>
            </Grid>
            <Grid container>
                <Grid item md={9} className="employer-reviews">
                    <EmployerReviews
                        employerId={match.params.employerId}
                        reviewId={match.params.reviewId}
                        history={history}
                    />
                </Grid>
            </Grid>
        </div>
    );
};

export default EmployerView;
