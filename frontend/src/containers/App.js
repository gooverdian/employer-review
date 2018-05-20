import React from 'react';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
import { Route, Switch } from 'react-router-dom';
import routeMap from 'config/routeMap';
import NavigationBar from 'components/layout/NavigationBar';
import ErrorSnackbar from 'components/layout/ErrorSnackbar';
import 'assets/css/App.css';

const theme = createMuiTheme();

class App extends React.Component {
    render() {
        return (
            <MuiThemeProvider theme={theme}>
                <NavigationBar />
                <div className="container">
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
        );
    }
}

export default App;
