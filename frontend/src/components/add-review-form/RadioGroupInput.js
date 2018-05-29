import React from 'react';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import { FormControl, FormControlLabel } from 'material-ui/Form';
import { InputLabel } from 'material-ui/Input';

class RadioGroupInput extends React.Component {
    handleChange = event => {
        this.props.onChange(event);
    };

    render() {
        let radioItems = [];
        for(let itemKey in this.props.items) {
            radioItems.push((
                <FormControlLabel
                    key={itemKey}
                    value={itemKey}
                    control={<Radio />}
                    label={this.props.items[itemKey]}
                />
            ));
        }
        return (
            <FormControl component="fieldset">
                <InputLabel component="legend" shrink>{this.props.label}</InputLabel>
                <RadioGroup
                    name={this.props.name}
                    value={this.props.value}
                    onChange={this.handleChange}
                >
                    {radioItems}
                </RadioGroup>
            </FormControl>
        );
    }
}

export default RadioGroupInput;
