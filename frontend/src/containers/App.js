import React from 'react';
import JssProvider from 'react-jss/lib/JssProvider';
import { createGenerateClassName } from '@material-ui/core/styles';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
import { Route, Switch } from 'react-router-dom';
import routeMap from 'config/routeMap';
import NavigationBar from 'components/layout/NavigationBar';
import ErrorSnackbar from 'components/layout/ErrorSnackbar';
import green from '@material-ui/core/colors/green';
import 'assets/css/App.css';

const theme = createMuiTheme({
    palette: {
        secondary: {
            light: green[500],
            main: green[600],
            dark: green[700],
            contrastText: '#fff',
        },
    },
});

const generateClassName = createGenerateClassName();

class App extends React.Component {
    render() {
        return (
            <JssProvider generateClassName={generateClassName}>
                <MuiThemeProvider theme={theme}>
                    <NavigationBar />
                    <div className="container container_content">
                        <Switch>
                            {routeMap.map((item, index) => (
                                <Route
                                    key={index}
                                    exact={item.exact}
                                    path={item.path}
                                    component={item.component}
                                />
                            ))}
                        </Switch>
                    </div>
                    <ErrorSnackbar />
                </MuiThemeProvider>
            </JssProvider>
        );
    }
}

export default App;
