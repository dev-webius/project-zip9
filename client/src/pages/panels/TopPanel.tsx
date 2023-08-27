import React from 'react';

import SearchBar from '../../components/SearchBar';

function TopPanel() {
  return (
    <>
      <SearchBar />
    </>
  );
}

export default React.memo(TopPanel);
