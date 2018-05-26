import React from 'react';
import Typography from 'material-ui/Typography';
import { Link, Route } from 'react-router-dom';
import EmployerSearchFormView from 'containers/EmployerSearchFormView';
import ReviewButtonView from 'containers/ReviewButtonView';
import logo from './logo.svg';
import './NavigationBar.css';

const LogoIcon = (props) => (
    <img className={props.className} src={logo} alt="logo" />
);

const NavigationBar = () => {
    return (
        <div className="nav">
            <div className="nav__header">
                <div className="nav__wrapper container container_header">
                    <div className="breadcrumbs">
                        <Typography variant="title" color="inherit">
                            <Link to="/" className="nav__brand-link">
                                <LogoIcon className="nav__logo"/>
                                HH Employer Review
                            </Link>
                        </Typography>
                    </div>
                    <Route exact path="(/|/search)/:search?/:page?" component={EmployerSearchFormView} />
                    <Route exact path="/" component={ReviewButtonView} />
                </div>
            </div>
        </div>
    );
};

export default NavigationBar;
