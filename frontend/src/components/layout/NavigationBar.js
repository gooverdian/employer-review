import React from 'react';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import ToolBar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import Button from "material-ui/Button";
import { Link } from 'react-router-dom';
import logo from './logo.svg';

const styles = {
    root: {
        flexGrow: 1,
    },
    flex: {
        flex: 1,
    },
    logo: {
        height: 32,
        width: 32,
    },
    brandLink: {
        color: 'inherit',
        textDecoration: 'none',
    },
};

const LogoIcon = (props) => (
    <img className={props.className} src={logo} alt="logo" />
);

const NavigationBar = (props) => {
    const { classes } = props;
    return (
        <div className={classes.root}>
            <AppBar position="static">
                <ToolBar disableGutters={true} className="container">
                    <LogoIcon className={classes.logo} />
                    <Typography variant="title" color="inherit" className={classes.flex}>
                        <Link to="/" className={classes.brandLink}>
                            HH Employer Review
                        </Link>
                    </Typography>
                    <Button component={Link} to="/review/add" color="inherit" >
                        Оставить отзыв
                    </Button>
                </ToolBar>
            </AppBar>
        </div>
    );
};

export default withStyles(styles)(NavigationBar);