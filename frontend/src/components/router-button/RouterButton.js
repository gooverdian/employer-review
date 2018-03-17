import React from 'react';
import RtButton from 'react-toolbox/lib/button/Button';
import RtIconButton from 'react-toolbox/lib/button/IconButton';
import {Link} from 'react-router-dom';

export class Button extends React.Component {
    render() {
        const {href, ...otherProps} = this.props;

        if (href === undefined) {
            return <RtButton {...otherProps}/>;
        }

        return (
            <Link to={href}>
                <RtButton {...otherProps}/>
            </Link>
        );
    }
}

export class IconButton extends React.Component {
    render() {
        const {href, ...otherProps} = this.props;

        if (href === undefined) {
            return <RtIconButton {...otherProps}/>;
        }

        return (
            <Link to={href}>
                <RtIconButton {...otherProps}/>
            </Link>
        );
    }
}

export default Button;
