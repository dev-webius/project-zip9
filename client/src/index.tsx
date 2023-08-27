import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { ThemeProvider, createTheme } from '@mui/material';

import store from './store';
import './styles/index.scss';
import MainPage from './pages/MainPage';
import LocationPage from './pages/LocationPage';
import AnnounceListPage from './pages/AnnounceListPage';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);

const theme = createTheme({
  palette: {
    info: {
      main: '#333D4B', // gray-90
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <Routes>
          <Route path="" element={ <MainPage /> } />
          <Route path="location" element={ <LocationPage /> } />
          <Route path="announcements" element={ <AnnounceListPage /> } />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
