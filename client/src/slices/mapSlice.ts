import { createSlice } from '@reduxjs/toolkit';

const name = 'map';
const initialState = {
  loaded: false,
};

const mapSlice = createSlice({
  name,
  initialState,
  reducers: {
    mapLoad: (state) => {
      state.loaded = true;
    },
  },
  extraReducers: (builder) => {},
});

export default mapSlice.reducer;

export const {
  mapLoad,
} = mapSlice.actions;
