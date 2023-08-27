import React from 'react';

import NaverMap from '../components/NaverMap';
import { useAppSelector } from '../store';
import TopPanel from './panels/TopPanel';
import BottomPanel from './panels/BottomPanel';

function MainPage() {
  const theme = useAppSelector((state) => state.theme);

  console.log(theme);

  return (
    <>
      <TopPanel />
      <NaverMap />
      <BottomPanel />
    </>
  );
}

export default React.memo(MainPage);
