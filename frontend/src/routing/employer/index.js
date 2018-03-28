import React from 'react';
import EmployerCard from 'components/employer/EmployerCard';
import EmployerReviews from 'components/employer/EmployerReviews';
import {Col, Grid, Row} from 'react-flexbox-grid';

const ViewEmployerIndex = function({match, history}) {
    return (
        <div className="page-employer-index">
            <Grid>
                <Row>
                    <Col>
                        <EmployerCard
                            employerId={match.params.employerId}
                        />
                    </Col>
                </Row>
                <Row>
                    <EmployerReviews
                        employerId={match.params.employerId}
                        reviewId={match.params.reviewId}
                        history={history}
                    />
                </Row>
            </Grid>
        </div>
    );
};

export default ViewEmployerIndex;
