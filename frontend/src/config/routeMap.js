import EmployerSearchView from 'containers/EmployerSearchView';
import ReviewAddView from 'containers/ReviewAddView';
import EmployerView from 'containers/EmployerView';

const routeMap = [
    {
        path: '/search/:search?/:page?',
        exact: true,
        component: EmployerSearchView
    },
    {
        path: '/employer/:employerId/:reviewId?',
        component: EmployerView
    },
    {
        path: '/review/add/:employerId?',
        component: ReviewAddView
    },
 ];

export default routeMap;
