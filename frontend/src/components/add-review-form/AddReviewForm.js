import React from 'react';
import Grid from 'material-ui/Grid';
import TextField from 'material-ui/TextField';
import RatingInput from 'components/rating-input/RatingInput';
import EmployerSearchSelect from 'components/employer-search/EmployerSearchSelect';
import Button from 'material-ui/Button';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import Validator from 'helpers/validator/Validator';
import SendIcon from '@material-ui/icons/Send';
import './AddReviewForm.css';

class AddReviewForm extends React.Component {
    state = {
        attributes: {
            employerId: {
                value: null,
                valid: undefined,
                error: undefined
            },
            rating: {
                value: null,
                valid: undefined,
                error: undefined
            },
            reviewText: {
                value: '',
                valid: undefined,
                error: undefined
            },
        }
    };

    validationRules = {
        employerId: [
            {rule: 'required', message: 'Необходимо выбрать компанию'}
        ],
        rating: [
            {rule: 'required', message: 'Необходимо выбрать оценку'}
        ]
    };

    updateAttribute = (name, value) => {
        let validationResult = this.validateAttribute(name, value);
        this.setState({attributes: {
            ...this.state.attributes,
            [name]: {
                value: value,
                valid: validationResult.valid,
                error: validationResult.error
            }
        }});
    };

    updateText = (name, event) => {
        const value = event.target.value;
        this.updateAttribute(name, value);
    };

    validateAttribute(name, value) {
        if (!this.validationRules[name]) {
            return true;
        }
        let ruleSet = this.validationRules[name],
            attributeResult = {
                valid: true,
                error: undefined
            };
        for (let i = 0; i < ruleSet.length; i++) {
            let rule = ruleSet[i];
            if (!Validator.validate(rule.rule, value)) {
                attributeResult.valid = false;
                // Будет показано только сообщение от последнего нарушенного правила
                attributeResult.error = rule.message || 'Неправильное значение поля ' + name;
            }
        }

        return attributeResult;
    }

    validate() {
        let validatedAttributes = {},
            hasErrors = false,
            attributeResult;
        Object.keys(this.state.attributes).forEach((key) => {
             attributeResult = this.validateAttribute(key, this.state.attributes[key].value);
             if (!attributeResult.valid) {
                 hasErrors = true;
             }
             validatedAttributes[key] = {
                value: this.state.attributes[key].value,
                valid: attributeResult.valid,
                error: attributeResult.error
             };
        });

        this.setState({attributes: validatedAttributes});

        return !hasErrors;
    }

    submit = (event) => {
        event.preventDefault();

        if (!this.validate()) {
            return;
        }

        let formData = {
            employerId: this.state.attributes.employerId.value,
            rating: this.state.attributes.rating.value,
            text: this.state.attributes.reviewText.value
        };

        ExchangeInterface.addReview(formData).then((data) => {
            this.props.history.push(`/employer/${this.state.attributes.employerId}/${data.reviewId}`);
        }, (error) => {
            console.log(error);
        });
    };

    render () {
        return (
            <form onSubmit={this.submit} className="form-add-review">
                <Grid container className="form-group">
                    <Grid item md={8}>
                        <EmployerSearchSelect
                            value={this.state.attributes.employerId.value}
                            onChange={this.updateAttribute.bind(this, 'employerId')}
                            employerId={this.props.employerId}
                            error={this.state.attributes.employerId.error}
                        />
                    </Grid>
                </Grid>
                <Grid container className="form-group">
                    <Grid item md={8}>
                        <RatingInput
                            value={this.state.attributes.rating.value}
                            onChange={this.updateAttribute.bind(this, 'rating')}
                            error={this.state.attributes.rating.error}
                        />
                    </Grid>
                </Grid>
                <Grid container className="form-group">
                    <Grid item md={8}>
                        <TextField
                            multiline fullWidth
                            label="Ваше мнение о компании"
                            maxLength={1000}
                            value={this.state.attributes.reviewText.value}
                            onChange={this.updateText.bind(this, 'reviewText')}
                            error={this.state.attributes.reviewText.error}
                        />
                    </Grid>
                </Grid>
                <Grid container className="form-group">
                    <Grid item md={8}>
                        <Button variant="raised" color="primary" type="submit">
                            Отправить <SendIcon className="button-icon button-icon_right" />
                        </Button>
                    </Grid>
                </Grid>
            </form>
        );
    }
}

export default AddReviewForm;
