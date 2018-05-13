import React from 'react';
import { withStyles } from 'material-ui/styles';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import { InputLabel } from 'material-ui/Input';
import { FormControl, FormHelperText } from 'material-ui/Form';
import classNames from 'classnames';
import 'components/rating-input/RatingInput.css';

const styles = () => ({
    largeIcon: {
        fontSize: 48,
    },
});

const valuesDescription = [
    undefined,
    '1 — Ужасно',
    '2 — Плохо',
    '3 — Средне',
    '4 — Хорошо',
    '5 — Отлично'
];

class RatingInput extends React.Component {
    state = {
        hovering: 0,
        value: 0
    };

    handleMouseOver(key) {
        this.setState({hovering: Number(key + 1)});
    }

    handleMouseLeave() {
        this.setState({hovering: 0});
    }

    handleClick(key) {
        if (this.props.onChange) {
            this.props.onChange(key + 1);
        }
        this.setState({value: Number(key + 1)});
    }

    getClassName(key) {
        let isHovering = key < this.state.hovering;
        return classNames({
            'rating-input_star-hover': isHovering,
            'rating-input_star-idle': !isHovering,
            'rating-input_star-active': key < this.state.value && this.state.hovering === 0
        });
    }

    getRatingIcon(key) {
        return key < this.state.value ? 'star' : 'star_border';
    }

    render () {
        let ratingButtons = [];
        for (let i = 0; i < 5; i++) {
            ratingButtons.push({key: i});
        }
        const { classes } = this.props;

        return (
            <FormControl
                error={Boolean(this.props.error)}
                fullWidth
            >
                <InputLabel htmlFor="rating-input" shrink={true}>Ваша оценка</InputLabel>
                <div className="rating-input__selector">
                    <span className="rating-input__stars">
                        {ratingButtons.map(button => (
                            <IconButton
                                className={this.getClassName(button.key)}
                                key={button.key}
                                onMouseOver={this.handleMouseOver.bind(this, button.key)}
                                onFocus={this.handleMouseOver.bind(this, button.key)}
                                onMouseLeave={this.handleMouseLeave.bind(this, button.key)}
                                onBlur={this.handleMouseLeave.bind(this, button.key)}
                                onClick={this.handleClick.bind(this, button.key)}
                            >
                                <Icon className={classes.largeIcon}>{this.getRatingIcon(button.key)}</Icon>
                            </IconButton>
                        ))}
                    </span>
                    <span className="rating-input__value">
                        {valuesDescription[this.state.hovering] || valuesDescription[this.state.value]}
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

export default withStyles(styles)(RatingInput);
