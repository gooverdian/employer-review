const DEFAULT_API_HOST = 'http://localhost:8081/';

const settings = {
    unknownErrorMessage: 'Произошла неизвестная и непредвиденная ошибка',
    searchRequestThreshold: 400,
    defaultPageSize: 10,
    selectPageSize: 50,
    paginationMaxButtonsCount: 9,
    paginationMaxPagesCount: 15,
    defaultTopSize: 10,
    reviewTypes: {
        employee: 'EMPLOYEE',
        interviewee: 'INTERVIEWEE',
    },
    apiUrls: {
        employer: DEFAULT_API_HOST + 'employers/',
        bestEmployers: DEFAULT_API_HOST + 'employers/best/',
        area: DEFAULT_API_HOST + 'areas/',
        review: DEFAULT_API_HOST + 'review/',
        generalStatistics: DEFAULT_API_HOST + 'statistic/',
        salaryByProfFields: DEFAULT_API_HOST + 'statistic/salary/by_proff_field/',
        employmentDurationByProfField: DEFAULT_API_HOST + '/statistic/employment_duration/by_proff_field/'
    },
};

export default settings;
