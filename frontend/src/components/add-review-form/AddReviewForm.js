import React from 'react';
import Grid from 'material-ui/Grid';
import TextField from 'material-ui/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import RatingInput from 'components/add-review-form/RatingInput';
import SearchSelect from 'components/search-select/SearchSelect';
import Button from 'material-ui/Button';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import SendIcon from '@material-ui/icons/Send';
import AddIcon from '@material-ui/icons/Add';
import settings from 'config/settings';
import AddEmployerDialog from 'components/add-employer-dialog/AddEmployerDialog';
import Validator from 'helpers/validator/Validator';
import RadioGroupInput from './RadioGroupInput';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import { resetTopEmployers } from 'modules/topEmployers';
import { resetEmployerReviews } from 'modules/employerReviews';
import { resetEmployer } from 'modules/employers';
import { connect } from 'react-redux';
import classNames from 'classnames';
import { reviewTypesUrls } from 'components/employer/EmployerTabs';
import 'assets/css/Form.css';
import './AddReviewForm.css';

const reviewTypes = {
    [settings.reviewTypes.employee] : 'О работе в компании',
    [settings.reviewTypes.interviewee] : 'Об интервью в компании'
};
const defaultReviewType = settings.reviewTypes.employee;

class AddReviewForm extends React.Component {
    state = {
        employerInfo: undefined,
        attributes: {
            employerId: {
                value: this.props.employerId || null,
                valid: undefined,
                error: undefined
            },
            reviewType: {
                value: defaultReviewType,
                valid: undefined,
                error: undefined
            },
            salary: {
                value: '',
                valid: undefined,
                error: undefined
            },
            employmentDuration: {
                value: '',
                valid: undefined,
                error: undefined
            },
            employmentOngoing: {
                value: undefined,
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
        salary: [
            {rule: 'integer', min: 0, message: 'Введите целое положительное число'}
        ],
        employmentDuration: [
            {rule: 'integer', min: 0, message: 'Введите целое положительное число'}
        ],
        rating: [
            {rule: 'required', message: 'Необходимо выбрать оценку'}
        ]
    };

    employerName;

    componentDidMount() {
        if (this.props.employerId) {
            ExchangeInterface.getEmployer(this.props.employerId).then(
                (data) => this.updateSelectedEmployer(data),
                () => this.updateAttribute('employerId', undefined)
            );
        }
    }

    handleTextFieldChange = (event) => {
        const {name, value} = event.target;
        this.updateAttribute(name, value);
    };

    handleSwitchFieldChange = (event) => {
        const {name, checked} = event.target;
        this.updateAttribute(name, checked);
    };

    updateAttribute = (name, value) => {
        this.setState({
            attributes: {
                ...this.state.attributes,
                [name]: Validator.validateAttribute(name, value, this.validationRules)
            }
        });
    };

    handleSubmission = (event) => {
        event.preventDefault();

        let {valid, attributes,} = Validator.validateForm(this.state.attributes, this.validationRules);
        this.setState({attributes: attributes});

        if (!valid) {
            return;
        }

        let formData = Validator.getFormData(
            attributes,
            {
                employerId: 'employer_id',
                reviewType: 'review_type',
                employmentDuration: 'employment_duration',
                employmentOngoing: 'employment_terminated',
                reviewText: 'text',
            }
        );

        if (formData.review_type === settings.reviewTypes.interviewee) {
            formData.salary = '';
            formData.employment_duration = '';
        } else {
            if (formData.employment_terminated !== undefined) {
                formData.employment_terminated = !formData.employment_terminated;
            }
        }
        ExchangeInterface.addReview(formData).then(
            (data) => {
                this.props.resetTopEmployers();
                this.props.resetEmployerReviews();
                this.props.resetEmployer(formData.employer_id);
                this.props.history.push(
                    `/employer/${formData.employer_id}/${reviewTypesUrls[formData.review_type]}/${data.review_id}/`
                );
            },
            (error) => console.log(error)
        );
    };

    updateSelectedEmployer = (data) => {
        this.setState({
            employerInfo: data,
            attributes: {
                ...this.state.attributes,
                employerId: Validator.validateAttribute('employerId', data.id, this.validationRules),
            }
        });
    };

    getSelectSearchResults = (search, success, failure) => {
        ExchangeInterface.employerSearch(search, 0, settings.selectPageSize).then(
            (data) => success(data),
            (error) => failure(error)
        );
    };

    handleAddEmployerClick = () => {
        if (this.addEmployerDialog) {
            this.addEmployerDialog.show(this.employerName);
        }
    };

    render() {
        return (
            <div>
                <AddEmployerDialog
                    ref={component => this.addEmployerDialog = component}
                    onEmployerCreated={this.updateSelectedEmployer}
                />
                <form onSubmit={this.handleSubmission} className="form-add-review">
                    <Grid container className="form-group">
                        <Grid item md={8}>
                            <SearchSelect
                                label="Выберите компанию"
                                selectedItem={this.state.employerInfo}
                                getSearchResults={this.getSelectSearchResults}
                                onChange={this.updateAttribute.bind(this, 'employerId')}
                                onTextChange={(newText) => this.employerName = newText}
                                error={this.state.attributes.employerId.error}
                                controls={
                                    <Button
                                        variant="raised"
                                        color="primary"
                                        onMouseDown={this.handleAddEmployerClick}
                                    >
                                        <AddIcon className="button-icon button-icon_left"/> Новая компания
                                    </Button>
                                }
                            />
                        </Grid>
                    </Grid>
                    <div>
                        <RadioGroupInput
                            label="Тип отзыва"
                            name="reviewType"
                            value={this.state.attributes.reviewType.value}
                            items={reviewTypes}
                            onChange={this.handleTextFieldChange}
                        />
                    </div>
                    <Grid container className={
                        classNames(
                            'form-group',
                            {'form-group_hidden': this.state.attributes.reviewType.value === 'INTERVIEWEE'}
                        )
                    }>
                        <Grid item md={6} xs={12}>
                            <FormControlLabel
                                className="form-group__field"
                                control={
                                    <Checkbox
                                        name="employmentOngoing"
                                        checked={this.state.attributes.employmentOngoing.value}
                                        onChange={this.handleSwitchFieldChange}
                                        value="checked"
                                    />
                                }
                                label="Я работаю в этой компании в настоящее время"
                            />
                            <TextField
                                className="form-group__field"
                                label="Заработная плата"
                                name="salary"
                                value={this.state.attributes.salary.value}
                                onChange={this.handleTextFieldChange}
                                error={Boolean(this.state.attributes.salary.error)}
                                helperText={this.state.attributes.salary.error}
                                InputProps={{
                                    endAdornment: <InputAdornment position="end">руб.</InputAdornment>,
                                }}
                            />
                            <TextField
                                className="form-group__field"
                                label="Опыт работы"
                                name="employmentDuration"
                                value={this.state.attributes.employmentDuration.value}
                                onChange={this.handleTextFieldChange}
                                error={Boolean(this.state.attributes.employmentDuration.error)}
                                helperText={this.state.attributes.employmentDuration.error}
                                InputProps={{
                                    endAdornment: <InputAdornment position="end">мес.</InputAdornment>,
                                }}
                            />
                        </Grid>
                    </Grid>
                    <Grid container className="form-group">
                        <Grid item md={8}>
                            <RatingInput
                                label="Ваша оценка компании"
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
                                name="reviewText"
                                value={this.state.attributes.reviewText.value}
                                onChange={this.handleTextFieldChange}
                                error={this.state.attributes.reviewText.error}
                            />
                        </Grid>
                    </Grid>
                    <Grid container className="form-group">
                        <Grid item md={8}>
                            <Button variant="raised" color="primary" type="submit">
                                Отправить отзыв <SendIcon className="button-icon button-icon_right"/>
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </div>
        );
    }
}

export default connect(() => {return {};}, {
    resetTopEmployers,
    resetEmployerReviews,
    resetEmployer
})(AddReviewForm);
