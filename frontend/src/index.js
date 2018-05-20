import React from 'react';
import { render } from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import Root from 'containers/Root';
import registerServiceWorker from 'registerServiceWorker';
import configureStore from 'store/configureStore'
import 'assets/css/reset.css';

const store = configureStore();

render(
    <Router>
        <Root store={store} />
    </Router>,
    document.getElementById('root')
);
registerServiceWorker();
