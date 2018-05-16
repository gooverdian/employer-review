import React from 'react';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import routeMap from 'config/routeMap';
import NavigationBar from 'components/layout/NavigationBar';
import 'assets/css/App.css';

const theme = createMuiTheme();

class App extends React.Component {
    render() {
        return (
            <Router>
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
                </MuiThemeProvider>
            </Router>
        );
    }
}

export default App;
