import React from 'react';
import EmployerSearch from 'components/employer-search/EmployerSearch';

const ViewHome = function({match, history}) {
    return (
        <div className="page-home">
            <EmployerSearch
                history={history}
                search={match.params.search}
                page={match.params.page}
            />
        </div>
    );
};

export default ViewHome;
