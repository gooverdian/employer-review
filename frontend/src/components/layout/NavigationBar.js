import React from 'react';
import AppBar from 'react-toolbox/lib/app_bar/AppBar';
import Navigation from 'react-toolbox/lib/navigation/Navigation';
import {Link} from 'react-router-dom';
import NavigationLink from 'components/layout/NavigationLink';
import logo from './logo.svg';
import './NavigationBar.css';

const LogoIcon = () => (
    <img src={logo} alt="logo" />
);

const NavigationBar = () => (
    <AppBar leftIcon={<LogoIcon/>}>
        <Link to="/" className="main-nav__brand-link">
            HH Employer Review
        </Link>
        <Navigation className="main-nav" type="horizontal">
            <NavigationLink to="/review/add" label="Оставить отзыв" />
        </Navigation>
    </AppBar>
);

export default NavigationBar;