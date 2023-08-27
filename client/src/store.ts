import { configureStore } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';

import { AppDispatch, RootState } from './types/store.type';

import themeSlice from './slices/themeSlice';
import mapSlice from './slices/mapSlice';
import bottomPanelSlice from './slices/bottomPanelSlice';

const store = configureStore({
  reducer: {
    theme: themeSlice,
    map: mapSlice,
    bottomPanel: bottomPanelSlice,
  },
});

export default store;

export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
