import React from 'react';
import {Grid, Row, Col} from 'react-flexbox-grid';
import Exchange from 'helpers/exchange/Exchange';

class EmployerReviews extends React.Component {
    state = {
        employer: null
    };

    stopListeningHistory = undefined;
    requestThresholdTimer = null;

    constructor(params) {
        super();
        if (params.employerId) {
            let instance = this;
            Exchange.getEmployer(params.employerId).then(function(data) {
                instance.setState({employer: data});
            }, function(error) {
                console.log(error);
            });
        }
    }

    render () {
        return (
            <Grid>
                Reviews
            </Grid>
        );
    }
}

export default EmployerReviews;
