import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';

const ReviewButtonView = function() {
    return (
        <Grid className="header-block" container justify="center">
            <Grid item xs={9} md={9} className="header-block__item">
                <Typography variant="headline" color="inherit">Есть что рассказать?</Typography>
                <div className="header-block__action">
                    <Button
                        className="header-block__action-button"
                        component={Link}
                        to="/review/add"
                        variant="raised"
                        color="secondary"
                    >
                        Оставить отзыв о компании
                    </Button>
                </div>
            </Grid>
        </Grid>
    );
};

export default ReviewButtonView;
