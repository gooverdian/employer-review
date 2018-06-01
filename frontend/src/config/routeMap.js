import EmployerSearchView from 'containers/EmployerSearchView';
import ReviewAddView from 'containers/ReviewAddView';
import EmployerView from 'containers/EmployerView';
import HomeView from 'containers/HomeView';

const routeMap = [
    {
        path: '/',
        exact: true,
        component: HomeView,
    },
    {
        path: '/search/:search?/:page?',
        exact: true,
        component: EmployerSearchView
    },
    {
        path: '/employer/:employerId/:reviewTypeUrl?/:reviewId?',
        component: EmployerView
    },
    {
        path: '/review/add/:employerId?',
        component: ReviewAddView
    },
 ];

export default routeMap;
