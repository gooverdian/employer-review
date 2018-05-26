import React from 'react';
import EmployerSearchResults from 'components/employer-search/EmployerSearchResults';

const EmployerSearchView = function({match, history}) {
    return (
        <EmployerSearchResults
            history={history}
            search={match.params.search}
            page={match.params.page}
        />
    );
};

export default EmployerSearchView;
