import React from 'react';
import {Route, Link} from 'react-router-dom';

const NavigationLink = ({ label, to, activeOnExact }) => (
    <Route
        path={to}
        exact={activeOnExact}
        children={({match}) => (
            <Link
                className={"main-nav__link" + (match ? " main-nav_active" : "")}
                to={to}
            >
                {label}
            </Link>
        )}
    />
);

export default NavigationLink;

