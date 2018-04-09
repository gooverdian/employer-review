import React from 'react';
import 'assets/css/App.css';
import 'assets/react-toolbox/theme.css';
import theme from 'assets/react-toolbox/theme';
import ThemeProvider from 'react-toolbox/lib/ThemeProvider';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import routeMap from 'config/routeMap';
import NavigationBar from 'components/layout/NavigationBar';

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
                </ThemeProvider>
            </Router>
        );
    }
}

export default App;
