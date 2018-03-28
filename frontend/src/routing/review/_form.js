import React from 'react';
import Input from 'react-toolbox/lib/input/Input';
import {Col, Row} from 'react-flexbox-grid';
import RatingInput from 'components/rating-input/RatingInput';
import EmployerSearchSelect from 'components/employer-search/EmployerSearchSelect';
import 'assets/css/forms.css';
import Button from "react-toolbox/lib/button/Button";
import FontAwesome from 'react-fontawesome';
import ExchangeInterface from 'helpers/exchange/Exchange';
import Validator from 'helpers/validator/Validator';

class FormAddReview extends React.Component {
    state = {
        attributes: {
            employerId: null,
            rating: null,
            reviewText: ''
        },
        errors: {}
    };

    validationRules = {
        employerId: [
            {rule: 'required', message: 'Необходимо выбрать компанию'}
        ],
        rating: [
            {rule: 'required', message: 'Необходимо указать рейтинг'}
        ]
    };

    handleChange(name, value) {
        this.setState({attributes: {
            ...this.state.attributes,
            [name]: value
        }});
        if (this.state.errors[name]) {
            if (this.validateAttribute(name)) {
                let errors = this.state.errors;
                delete errors[name];
                this.setState({errors: errors});
            }
        }
    }

    validateAttribute(name) {
        if (!this.validationRules[name]) {
            return true;
        }
        let ruleSet = this.validationRules[name];
        let attributeResult = true;
        for (let i = 0; i < ruleSet.length; i++) {
            let rule = ruleSet[i];
            attributeResult = Validator.validate(rule.rule, this.state.attributes[name]) && attributeResult;
            if (!attributeResult) {
                return rule.message || 'Неправильное значение поля ' + name;
            }
        }

        return attributeResult;
    }

    validate() {
        let errors = {};
        for (let key in this.state.attributes) {
            if (!this.state.attributes.hasOwnProperty(key)) {
                continue;
            }
            let attributeResult = this.validateAttribute(key);
            if (attributeResult !== true) {
                errors[key] = attributeResult;
            }
        }

        this.setState({errors: errors});

        return Object.keys(errors).length === 0;
    }

    submit() {
        if (!this.validate()) {
            return;
        }

        let formData = {
            employerId: 2,
            rating: this.state.rating,
            reviewText: this.state.reviewText
        };

        ExchangeInterface.addReview(formData).then(function(data) {
            console.log(data);
            // Review saved, gonna redirect to employer and show it
        }, function(error) {
            console.log(error);
        });
    }

    render () {
        return (
            <form className="page-add-review">
                <Row className="form-group">
                    <Col md={8}>
                        <EmployerSearchSelect
                            value={this.state.attributes.employerId}
                            onChange={this.handleChange.bind(this, 'employerId')}
                            employerId={this.props.employerId}
                            error={this.state.errors['employerId']}
                        />
                    </Col>
                    <Col md={4} className="field-description">
                        Описание для выбора компании
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <RatingInput
                            value={this.state.attributes.rating}
                            onChange={this.handleChange.bind(this, 'rating')}
                            error={this.state.errors['rating']}
                        />
                    </Col>
                    <Col md={4} className="field-description">
                        Описание для рейтинга
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <Input
                            type="text" multiline
                            label="Текст отзыва" maxLength={1000}
                            value={this.state.attributes.reviewText}
                            onChange={this.handleChange.bind(this, 'reviewText')}
                            error={this.state.errors['reviewText']}
                        />
                    </Col>
                    <Col md={4} className="field-description">
                        Описание для отзыва
                    </Col>
                </Row>
                <Row className="form-group">
                    <Col md={8}>
                        <Button
                            raised primary
                            className="pull-right"
                            icon={<FontAwesome name="send" />}
                            label="Отправить"
                            onClick={this.submit.bind(this)}
                        />
                    </Col>
                </Row>
            </form>
        );
    }
}

export default FormAddReview;
