import React from 'react';
import './EmployerAvatar.css';

class EmployerAvatar extends React.Component {
    render () {
        return (
            <img alt={this.props.alt || ''} src={this.props.src} className="employer-avatar" />
        );
    }
}

export default EmployerAvatar;
