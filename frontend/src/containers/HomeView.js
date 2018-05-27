import React from 'react';
import Grid from 'material-ui/Grid';
import TopEmployers from 'components/top-employers/TopEmployers';
import Typography from 'material-ui/Typography';

const HomeView = function() {
    return (
        <Grid container spacing={16}>
            <Grid item xs={12} md={6}>
                <TopEmployers />
            </Grid>
            <Grid item xs={12} md={6}>
                <Typography variant="title">Средняя зарплата по регионам</Typography>
            </Grid>
            <Grid item xs={12}>
                О нас
            </Grid>
        </Grid>
    );
};

export default HomeView;
