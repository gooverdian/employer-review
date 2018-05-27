import React from 'react';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import './EmployerCard.css';
import 'components/top-employers/TopEmployers.css';

class EmployerCard extends React.Component {
    state = {
        employer: {}
    };

    componentDidMount() {
        if (this.props.employerId) {
            ExchangeInterface.getEmployer(this.props.employerId).then(
                (data) => {
                    this.setState({employer: data});
                },
                function(error) {
                    console.log(error);
                }
            )
        }
    }

    render () {
        const employer = this.state.employer;
        return (
            <Grid container className="employer">
                <Grid item xs={12} sm={6} md={4} lg={3}>
                    <div className="employer__info">
                        <div className="employer__info-row">
                            <img className="employer__logo" alt="" src={employer.logo_url} />
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
                                        <span className="employer__info-row-value">{employer.people_rated}</span>
                                    </Typography>
                                </div>
                            ) : ''
                        }
                        <Typography variant="subheading" className="employer__info-row">
                            <a href={employer.url} className="employer__info-row-item">
                                Веб-сайт компании
                            </a>
                        </Typography>
                    </div>
                </Grid>
                <Grid item xs={12} sm={6} md={8} lg={9} className="employer__name">
                    <Typography variant="display1" className="section-title">
                        {employer.name}
                    </Typography>
                    <Typography variant="subheading">
                        {employer.area_name}
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

export default EmployerCard;
