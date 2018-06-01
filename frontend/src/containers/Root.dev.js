import React from 'react';
import { Provider } from 'react-redux';
import DevTools from './DevTools';
import App from './App';

const Root = ({ store }) => (
    <Provider store={store}>
        <div className="page">
            <App />
            <DevTools />
        </div>
    </Provider>
);

export default Root;
