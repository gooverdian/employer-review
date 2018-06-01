import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import { connect } from 'react-redux';
import { getGeneralStatistics } from 'modules/mainStatistics';
import { dispatchError } from 'modules/errorMessage';
import './AboutUs.css';

class AboutUs extends React.Component {
    state = {
        bugCount: 0,
    };

    static getDerivedStateFromProps(nextProps) {
        if (typeof nextProps.REVIEW_COUNT === 'undefined') {
            nextProps.getGeneralStatistics();
            return null;
        }

        return {
            reviewsCount: nextProps.REVIEW_COUNT,
            employerWithReviews: nextProps.EMPLOYER_WITH_REVIEW_COUNT,
        };
    }

    handleBugCounterClick = () => {
        this.props.dispatchError(`${this.state.bugCount + 1}? ¯\\_(ツ)_/¯`);
        this.setState({bugCount: this.state.bugCount + 1});
    };

    render() {
        return (
            <div className="about">
                <div className="container">
                    <Grid container>
                        <Grid className="about__container" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">оставлено отзывов</div>
                                <Typography className="about__value" variant="display3">
                                    {this.state.reviewsCount}
                                </Typography>
                            </div>
                        </Grid>
                        <Grid className="about__container" item xs={12} md={6}>
                            <div className="about__wrapper">
                                <div className="about__description">компаний с отзывами</div>
                                <Typography className="about__value" variant="display3">
                                    {this.state.employerWithReviews}
                                </Typography>
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
                                <Typography
                                    className="about__value about__bug-counter"
                                    variant="display3"
                                    ripple
                                    onClick={this.handleBugCounterClick}
                                >
                                    {this.state.bugCount}
                                </Typography>
                                <div className="about__suffix">(но это не точно)</div>
                            </div>
                        </Grid>
                    </Grid>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => state.mainStatistics.general;

export default connect(mapStateToProps, { getGeneralStatistics, dispatchError })(AboutUs);
