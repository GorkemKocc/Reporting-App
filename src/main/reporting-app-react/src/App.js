import React, { useEffect } from 'react';
import './App.css';
import 'semantic-ui-css/semantic.min.css';
import { BrowserRouter as Router, Routes, Route, useParams, useLocation, useNavigate } from 'react-router-dom';
import Login from './pages/login/Login.jsx';
import Dashboard from './layouts/Dashboard';
import LaborantRegistration from './pages/LaborantRegistration';
import { useDispatch } from 'react-redux';
import { setActivePage } from './store/features/reportList/reportsSlice';

function App() {

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    if (parseInt(localStorage.getItem('userId'), 10) !== 0 && localStorage.getItem('userId') !== location.pathname.split('/')[2]) {
      localStorage.setItem('userId', 0);
      navigate('/');
      dispatch(setActivePage(1))
    }
  }, [location, navigate]);

  return (
    <div className="App">
      <Routes>
        {parseInt(localStorage.getItem('userId'), 10) === 0 ? (
          <>
            <Route path="/*" element={<Login />} />
            <Route path="/new laborant" element={<LaborantRegistration />} />
          </>
        ) : (
          localStorage.getItem('userId') === location.pathname.split(`/`)[2] ? (
            <>
              <Route path="/*" element={<Login />} />
              <Route path="/new laborant" element={<LaborantRegistration />} />
              <Route path="/laborant/:laborantId/*" element={<Dashboard />} />
              <Route path="/admin/:laborantId/*" element={<Dashboard />} />
            </>
          ) : null

        )}
      </Routes>
    </div>
  );
}

export default App;
