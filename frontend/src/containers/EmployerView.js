import React from 'react';
import Grid from 'material-ui/Grid';
import EmployerCard from 'components/employer/EmployerCard';
import EmployerTabs from 'components/employer/EmployerTabs';

const EmployerView = function({match, history}) {
    return (
        <div className="container container_content">
            <Grid container>
                <Grid item xs>
                    <EmployerCard
                        employerId={match.params.employerId}
                    />
                </Grid>
            </Grid>
            <EmployerTabs
                match={match}
                history={history}
            />
        </div>
    );
};

export default EmployerView;
