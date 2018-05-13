import React from 'react';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import routeMap from 'config/routeMap';
import NavigationBar from 'components/layout/NavigationBar';
import 'assets/css/App.css';
import 'assets/material-design-icons/material-icons.css';

const theme = createMuiTheme();

class App extends React.Component {
    render() {
        return (
            <Router>
                <MuiThemeProvider theme={theme}>
                    <div className="layout">
                        <div className="page-header">
                           <NavigationBar />
                        </div>
                        <div className="container">
                            {routeMap.map((item, index) => (
                                <Route
                                    key={index}
                                    exact={item.exact}
                                    path={item.path}
                                    component={item.component}
                                />
                            ))}
                        </div>
                    </div>
                </MuiThemeProvider>
            </Router>
        );
    }
}

export default App;
