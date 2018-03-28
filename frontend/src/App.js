import React from 'react';
import logo from 'assets/images/logo.svg';
import 'assets/css/App.css';
import 'assets/react-toolbox/theme.css';
import theme from 'assets/react-toolbox/theme';
import ThemeProvider from 'react-toolbox/lib/ThemeProvider';
import AppBar from 'react-toolbox/lib/app_bar/AppBar';
import Navigation from "react-toolbox/lib/navigation/Navigation";
import ViewHome from 'routing/default/home';
import ViewAddReview from 'routing/review/add';
import ViewEmployerIndex from "routing/employer";
import {BrowserRouter as Router, Route, Link} from 'react-router-dom';

const LogoIcon = () => (
    <img src={logo} alt="logo" />
);

const NavigationBar = () => (
    <AppBar leftIcon={<LogoIcon/>}>
        <Link to="/" className="brand-link">
            HH Employer Review
        </Link>
        <Navigation className="main-nav" type="horizontal">
            <NavigationLink to="/review/add" label="Оставить отзыв" />
        </Navigation>
    </AppBar>
);

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

class App extends React.Component {
    render() {
        return (
            <Router>
                <ThemeProvider theme={theme}>
                    <div className="layout">
                        <div className="page-header">
                            <NavigationBar />
                        </div>
                        <div className="page-wrap">
                            <Route exact path="(/|/search)/:search?/:page?" component={ViewHome} />
                            <Route path="/review/add/:employerId?" component={ViewAddReview} />
                            <Route path="/employer/:employerId/:reviewId?" component={ViewEmployerIndex} />
                        </div>
                    </div>
                </ThemeProvider>
            </Router>
        );
    }
}

export default App;
