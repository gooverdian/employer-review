import settings from 'config/settings';
import ExchangeRequest from './ExchangeRequest';

const ExchangeInterface = {
    employerSearch: function (text, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.employer,
            params: {
                text: text,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    getReviews: function (employerId, page = 0, perPage = settings.defaultPageSize) {
        return new ExchangeRequest({
            url: settings.apiUrls.review,
            params: {
                employerId: employerId,
                page: page,
                per_page: perPage,
            }
        }).perform();
    },
    getEmployer: function (employerId) {
        return new ExchangeRequest({
            url: settings.apiUrls.employer + employerId
        }).perform();
    },
    addReview: function (reviewFormData) {
        console.log(reviewFormData);
        return new ExchangeRequest({
            method: 'post',
            url: settings.apiUrls.review,
            params: reviewFormData
        }).perform();
    }
};

export default ExchangeInterface;
