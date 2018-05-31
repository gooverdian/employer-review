import React from 'react';
import Grid from 'material-ui/Grid';
import TopEmployers from 'components/top-employers/TopEmployers';
import Typography from 'material-ui/Typography';
import AboutUs from 'components/about-us/AboutUs';

const HomeView = function() {
    return (
        <div>
            <div className="container container_content">
                <Grid container spacing={16}>
                    <Grid item xs={12} md={6}>
                        <Typography variant="display1">
                            Лучшие компании
                        </Typography>
                        <TopEmployers />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Typography variant="display1">Средняя зарплата по регионам</Typography>
                    </Grid>
                </Grid>
            </div>
            <div className="container">
                <Typography variant="display2">О нас</Typography>
            </div>
            <AboutUs />
        </div>
    );
};

export default HomeView;
