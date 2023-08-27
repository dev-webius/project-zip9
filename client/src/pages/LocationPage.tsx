import React from 'react';

import TopPanel from './panels/TopPanel';
import { LocationList, LocationListItem } from '../components/list/LocationList';

function LocationPage() {
  const locationList = [
    { key: 'seoul', label: '서울', count: 2 },
    { key: 'busan', label: '부산', count: 2 },
    { key: 'daegu', label: '대구', count: 2 },
    { key: 'incheon', label: '인천', count: 2 },
    { key: 'gwangju', label: '광주', count: 2 },
  ];

  return (
    <>
      <TopPanel />
      <LocationList>
        {locationList.map(({ key, label, count }) => <LocationListItem key={key} id={key} count={count}>{label}</LocationListItem>)}
      </LocationList>
    </>
  );
}

export default React.memo(LocationPage);
