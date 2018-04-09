import React from 'react';
import Input from 'react-toolbox/lib/input/Input';
import {Col, Row} from 'react-flexbox-grid';
import RatingInput from 'components/rating-input/RatingInput';
import EmployerSearchSelect from 'components/employer-search/EmployerSearchSelect';
import './AddReviewForm.css';
import Button from "react-toolbox/lib/button/Button";
import FontAwesome from 'react-fontawesome';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import Validator from 'helpers/validator/Validator';

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
            {rule: 'required', message: 'Необходимо указать рейтинг'}
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

        return hasErrors;
    }

    submit = () => {
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
            <form className="page-add-review">
                <Row className="form-group">
                    <Col md={8}>
                        <EmployerSearchSelect
                            value={this.state.attributes.employerId.value}
                            onChange={this.updateAttribute.bind(this, 'employerId')}
                            employerId={this.props.employerId}
                            error={this.state.attributes.employerId.error}
                        />
                    </Col>
                    <Col md={4} className="form-group__description">
                        Начните вводить название компании
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <RatingInput
                            value={this.state.attributes.rating.value}
                            onChange={this.updateAttribute.bind(this, 'rating')}
                            error={this.state.attributes.rating.error}
                        />
                    </Col>
                    <Col md={4} className="form-group__description">
                        1 - очень плохо, 5 - отлично
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <Input
                            type="text" multiline
                            label="Текст отзыва" maxLength={1000}
                            value={this.state.attributes.reviewText.value}
                            onChange={this.updateAttribute.bind(this, 'reviewText')}
                            error={this.state.attributes.reviewText.error}
                        />
                    </Col>
                    <Col md={4} className="form-group__description">
                        Напишите Ваше мнение о компании
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <Button
                            raised primary
                            className="pull-right"
                            icon={<FontAwesome name="send" />}
                            label="Отправить"
                            onClick={this.submit}
                        />
                    </Col>
                </Row>
            </form>
        );
    }
}

export default AddReviewForm;
