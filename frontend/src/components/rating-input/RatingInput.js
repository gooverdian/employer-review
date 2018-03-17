import React from 'react';
import IconButton from 'react-toolbox/lib/button/IconButton';
import FontAwesome from 'react-fontawesome';
import 'assets/font-awesome/css/font-awesome.min.css';
import 'components/rating-input/RatingInput.css';

class RatingInput extends React.Component {
    state = {
        statuses: ['idle', 'idle', 'idle', 'idle', 'idle'],
        value: 0
    };

    handleMouseOver(key) {
        let statuses = this.state.statuses;
        for (let i = 0; i <= key; i++) {
            statuses[i] = 'hover';
        }
        this.setState({statuses: statuses});
    }

    handleMouseLeave(key) {
        let statuses = this.state.statuses;
        for (let i = 0; i <= key; i++) {
            statuses[i] = 'idle';
        }
        this.setState({statuses: statuses});
    }

    handleClick(key) {
        if (this.props.onChange) {
            this.props.onChange((key + 1));
        }
        this.setState({value: (key + 1)});
    }

    getClassName(key) {
        return 'rating-star-' + this.state.statuses[key];
    }

    getRatingIcon(key) {
        return key < this.state.value ? 'star' : 'star-o';
    }

    render () {
        let ratingButtons = [];
        for (let i = 0; i < 5; i++) {
            ratingButtons.push({key: i});
        }

        return (
            <div className={(this.props.error ? ' has-error' : '')}>
                <label className="rating-label">
                    Рейтинг
                </label>
                <div className="rating-input-selector">
                    <span className="rating-stars">
                        {ratingButtons.map(button => (
                            <IconButton
                                className={this.getClassName(button.key)}
                                icon={<FontAwesome name={this.getRatingIcon(button.key)} size="2x" />}
                                key={button.key}
                                onMouseOver={this.handleMouseOver.bind(this, button.key)}
                                onMouseLeave={this.handleMouseLeave.bind(this, button.key)}
                                onClick={this.handleClick.bind(this, button.key)}
                                accent
                            />
                        ))}
                    </span>
                    <span className="rating-value">
                        {this.state.value > 0 ? this.state.value : ''}
                    </span>
                </div>
                <span className="help-block">
                    {this.props.error ? this.props.error : ''}
                </span>
            </div>
        );
    }
}

export default RatingInput;
