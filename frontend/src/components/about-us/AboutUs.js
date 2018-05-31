import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import './AboutUs.css';

class AboutUs extends React.Component {

    render() {
        return (
            <div className="about">
                <div className="container">
                    <Grid container>
                        <Grid className="about__container" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">оставлено отзывов</div>
                                <Typography className="about__value" variant="display3">2422</Typography>
                            </div>
                        </Grid>
                        <Grid className="about__container" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">компаний с отзывами</div>
                                <Typography className="about__value" variant="display3">57</Typography>
                            </div>
                        </Grid>
                        <Grid className="about__container" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">программистов</div>
                                <Typography className="about__value" variant="display3">3</Typography>
                            </div>
                        </Grid>
                        <Grid className="about__container about_suffix" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">багов</div>
                                <Typography className="about__value" variant="display3">0</Typography>
                                <div className="about__suffix">(но это не точно)</div>
                            </div>
                        </Grid>
                    </Grid>
                </div>
            </div>
        );
    }
}

export default AboutUs;
