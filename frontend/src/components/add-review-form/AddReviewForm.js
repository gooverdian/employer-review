import React from 'react';
import Grid from 'material-ui/Grid';
import TextField from 'material-ui/TextField';
import RatingInput from 'components/rating-input/RatingInput';
import SearchSelect from 'components/search-select/SearchSelect';
import Button from 'material-ui/Button';
import ExchangeInterface from 'components/exchange/ExchangeInterface';
import SendIcon from '@material-ui/icons/Send';
import AddIcon from '@material-ui/icons/Add';
import settings from 'config/settings';
import AddEmployerDialog from 'components/add-employer-dialog/AddEmployerDialog';
import Validator from 'helpers/validator/Validator';
import 'assets/css/Form.css';
import './AddReviewForm.css';

class AddReviewForm extends React.Component {
    state = {
        employerInfo: undefined,
        attributes: {
            employerId: {
                value: this.props.employerId || null,
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
        const { name, value } = event.target;
        this.updateAttribute(name, value);
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

        let { valid, attributes, } = Validator.validateForm(this.state.attributes, this.validationRules);
        this.setState({attributes: attributes});

        if(!valid) {
            return;
        }

        let formData = Validator.getFormData(attributes);
        // TODO: delete this when input implemented (backend require this field)
        formData.reviewType = 'EMPLOYEE';
        ExchangeInterface.addReview(formData).then(
            (data) => this.props.history.push(`/employer/${formData.employer_id}/${data.review_id}`),
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

    render () {
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
                                        <AddIcon className="button-icon button-icon_left" /> Новая компания
                                    </Button>
                                }
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
                                Отправить <SendIcon className="button-icon button-icon_right" />
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </div>
        );
    }
}

export default AddReviewForm;
