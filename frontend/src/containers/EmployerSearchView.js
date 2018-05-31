import React from 'react';
import EmployerSearchResults from 'components/employer-search/EmployerSearchResults';

const EmployerSearchView = function({match, history}) {
    return (
        <div className="container container_content">
            <EmployerSearchResults
                history={history}
                search={match.params.search}
                page={match.params.page}
            />
        </div>
    );
};

export default EmployerSearchView;
