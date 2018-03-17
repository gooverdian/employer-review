import React from 'react';
import EmployerSearch from 'components/employer-search/EmployerSearch';
import { withRouter } from 'react-router-dom';

const ViewHome = withRouter(function({match, history}) {
    return (
        <div className="page-home">
            <EmployerSearch
                history={history}
                search={match.params.search}
                page={match.params.page}
            />
        </div>
    );
});

export default ViewHome;
