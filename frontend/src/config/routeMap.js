import ViewHome from 'routing/default/home';
import ViewReviewAdd from 'routing/review/add';
import ViewEmployerIndex from 'routing/employer';

const routeMap = [
    {
        path: '(/|/search)/:search?/:page?',
        exact: true,
        component: ViewHome
    },
    {
        path: '/review/add/:employerId?',
        component: ViewReviewAdd
    },
    {
        path: '/employer/:employerId/:reviewId?',
        component: ViewEmployerIndex
    }
];

export default routeMap;
