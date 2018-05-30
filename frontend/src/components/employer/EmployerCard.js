import React from 'react';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import { getEmployer } from 'modules/employers';
import { connect } from 'react-redux';
import './EmployerCard.css';
import 'components/top-employers/TopEmployers.css';

class EmployerCard extends React.Component {
    state = {
        employer: {}
    };

    static getDerivedStateFromProps(nextProps) {
        if (!nextProps.employer) {
            nextProps.getEmployer(nextProps.employerId);
            return null;
        }

        return { employer: nextProps.employer };
    }

    render () {
        const employer = this.state.employer;
        return (
            <Grid container className="employer">
                <Grid className="employer__info" item xs={12} sm={6} md={4} lg={3}>
                    <div className="employer__info-row">
                        <img className="employer__logo" alt="" src={employer.logoUrl} />
                    </div>
                    {
                        employer.rating ? (
                            <div>
                                <Typography variant="subheading" className="employer__info-row">
                                    <span className="employer__info-row-name">Рейтинг</span>
                                    <span className="employer__info-row-value">
                                        <Typography
                                            className="rating-plate"
                                            variant="display1"
                                            gutterBottom={false}
                                        >
                                            <span className="rating-plate__rating">
                                                <StarBorderIcon classes={{root: 'rating-plate__rating-icon'}}/>
                                                {employer.rating ? employer.rating.toFixed(1) : ''}
                                            </span>
                                        </Typography>
                                    </span>
                                </Typography>
                                <Typography variant="subheading" className="employer__info-row">
                                    <span className="employer__info-row-name">Отзывов</span>
                                    <span className="employer__info-row-value">{employer.peopleRated}</span>
                                </Typography>
                            </div>
                        ) : ''
                    }
                    <Typography variant="subheading" className="employer__info-row employer__info-row_last">
                        <a href={employer.url} className="employer__info-row-item">
                            Веб-сайт компании
                        </a>
                    </Typography>
                </Grid>
                <Grid item xs={12} sm={6} md={8} lg={9} className="employer__name">
                    <Typography variant="display1" className="section-title">
                        {employer.name}
                    </Typography>
                    <Typography variant="subheading">
                        {employer.areaName}
                    </Typography>
                    <Button
                        className="employer__name-button"
                        variant="raised"
                        color="secondary"
                        component={Link}
                        to={"/review/add/" + employer.id}
                    >
                        Оставить отзыв о компании
                    </Button>
                </Grid>
            </Grid>
        );
    }
}

const mapStateToProps = (state, ownProps) => ({ employer: state.employers[ownProps.employerId] });

export default connect(mapStateToProps, { getEmployer })(EmployerCard);
