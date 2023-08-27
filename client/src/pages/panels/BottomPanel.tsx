import React from 'react';
import { Typography, SwipeableDrawer } from '@mui/material';
import { usePanelManager } from '../../slices/bottomPanelSlice';

function BottomPanel() {
  const panelManager = usePanelManager();

  console.log(panelManager);

  return (
    <>
      <SwipeableDrawer {...panelManager} anchor="bottom" disableSwipeToOpen={false}>
        <div className="drawer">
          <div className="drawer__text">
            <strong className="drawer__text--bold">서울</strong>
            진행중인 공고 33건
          </div>
        </div>
      </SwipeableDrawer>
    </>
  );
}

export default React.memo(BottomPanel);
