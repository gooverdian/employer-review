import React from 'react';
import { InputLabel } from 'material-ui/Input';
import { FormControl, FormHelperText } from 'material-ui/Form';
import RatingStarButton from './RatingStarButton';
import classNames from 'classnames';
import './RatingInput.css';

const valuesDescription = [
    '1 — Ужасно',
    '2 — Плохо',
    '3 — Средне',
    '4 — Хорошо',
    '5 — Отлично'
];

class RatingInput extends React.Component {
    state = {
        active: undefined,
        value: undefined
    };

    starActivated = (index) => {
        this.setState({active: index});
    };

    starDeactivated = () => {
        this.setState({active: undefined});
    };

    starClicked = (index) => {
        if (this.props.onChange) {
            this.props.onChange(index + 1);
        }
        this.setState({value: index});
    };

    render () {
        let ratingButtons = [];
        for (let i = 0; i < valuesDescription.length; i++) {
            ratingButtons.push(
                <RatingStarButton
                    key={i}
                    value={i}
                    activeIndex={this.state.active}
                    currentRating={this.state.value}
                    onActivate={this.starActivated}
                    onDeactivate={this.starDeactivated}
                    onStarClick={this.starClicked}
                />
            );
        }

        return (
            <FormControl fullWidth error={Boolean(this.props.error)}>
                <InputLabel htmlFor="rating-input" shrink={true}>{this.props.label || "Ваша оценка"}</InputLabel>
                <div className="rating-input__selector">
                    <span
                        className={classNames(
                            'rating-input__stars',
                            {'rating-input_error': Boolean(this.props.error)}
                        )}
                    >
                        {ratingButtons}
                    </span>
                    <span className="rating-input__value">
                        {valuesDescription[this.state.active] || valuesDescription[this.state.value]}
                    </span>
                </div>
                {
                    this.props.error
                        ? <FormHelperText id="rating-helper-text">{this.props.error}</FormHelperText>
                        : ''
                }
            </FormControl>
        );
    }
}

export default RatingInput;
