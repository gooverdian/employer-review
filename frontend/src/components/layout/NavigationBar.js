import React from 'react';
import AppBar from 'material-ui/AppBar';
import ToolBar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import Button from 'material-ui/Button';
import { Link } from 'react-router-dom';
import logo from './logo.svg';
import './NavigationBar.css';

const LogoIcon = (props) => (
    <img className={props.className} src={logo} alt="logo" />
);

const NavigationBar = () => {
    return (
        <div className="nav">
            <AppBar position="static">
                <ToolBar disableGutters className="container">
                    <LogoIcon className="nav__logo" />
                    <Typography variant="title" color="inherit" className="flex flex_max">
                        <Link to="/" className="nav__brand-link">
                            HH Employer Review
                        </Link>
                    </Typography>
                    <Button component={Link} to="/review/add" color="inherit">
                        Оставить отзыв
                    </Button>
                </ToolBar>
            </AppBar>
        </div>
    );
};

export default NavigationBar;
