import { createSlice } from '@reduxjs/toolkit';
import { useAppDispatch, useAppSelector } from '../store';
import { useCallback } from 'react';
import { IPanelManager } from '../types/slices/bottomPanelSlice.type';

const name = 'bottomPanel';
const initialState = {
  open: false,
};

const bottomPanelSlice = createSlice({
  name,
  initialState,
  reducers: {
    openPanel: (state) => {
      state.open = true;
    },
    closePanel: (state) => {
      state.open = false;
    },
  },
  extraReducers: (builder) => {},
});

export default bottomPanelSlice.reducer;

export const {
  openPanel,
  closePanel,
} = bottomPanelSlice.actions;

export function usePanelManager(): IPanelManager {
  const dispatch = useAppDispatch();
  const open = useAppSelector((state) => state.bottomPanel.open);

  const handleOpenPanel = useCallback(() => {
    dispatch(openPanel());
  }, [dispatch]);

  const handleClosePanel = useCallback(() => {
    dispatch(openPanel());
  }, [dispatch]);

  return {
    open,
    onOpen: handleOpenPanel,
    onClose: handleClosePanel,
  };
}
