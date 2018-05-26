import React from 'react';
import EmployerSearchForm from 'components/employer-search/EmployerSearchForm';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';

const EmployerSearchFormView = function() {
    return (
        <Grid className="header-block" container justify="center">
            <Grid item xs={12} md={9} className="header-block__item">
                <Typography variant="headline" color="inherit">Найдите работу, которую полюбите!</Typography>
                <div className="header-block__action">
                    <EmployerSearchForm />
                </div>
            </Grid>
        </Grid>
    );
};

export default EmployerSearchFormView;
