import { createSlice } from '@reduxjs/toolkit';
import { ThemeSliceState } from '../types/slices/themeSlice.type';

const name = 'theme';
const initialState: ThemeSliceState = {
  mode: 'main',
};

const themeSlice = createSlice({
  name,
  initialState,
  reducers: {},
  extraReducers: (builder) => {},
});

export default themeSlice.reducer;
