import React, { useEffect, useState } from 'react';
import { Checkbox, Message } from 'semantic-ui-react';
import './Login.css';
import { useNavigate } from 'react-router-dom';
import LaborantService from '../../services/laborantService';

export default function Login() {

  const navigate = useNavigate();

  const [error, setError] = useState(null);

  const [laborants, setLaborants] = useState([]);

  useEffect(() => {
    let laborantService = new LaborantService();
    laborantService.getLaborants().then(result => {
      const activeLaborants = result.data.filter(laborant => laborant.isActive === true);
      setLaborants(activeLaborants);
    });
  }, []);

  const [loginData, setLoginData] = useState({
    username: '',
    password: '',
  });

  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setLoginData({ ...loginData, [name]: value });
    setError(null)
  };

  const handleRegister = () => {
    navigate('/new laborant')
  };

  const handleLogin = (e) => {
    e.preventDefault();

    const isLaborant = laborants.some(laborant => laborant.username === loginData.username && laborant.password === loginData.password);

    if (loginData.username === "admin" && loginData.password === "admin") {
      localStorage.setItem('userId', -1)
      localStorage.setItem('name', "Admin")
      localStorage.setItem('surname', "")
      const admin = {
        id: -1,
        firstName: "Admin",
        lastName: ""
      };
      localStorage.setItem("loggedInClient", JSON.stringify(admin));
      navigate('/admin/-1')

    } else if (isLaborant) {
      const loggedInClient = laborants.find(laborant => laborant.username === loginData.username && laborant.password === loginData.password);
      localStorage.setItem('userId', loggedInClient.id)
      localStorage.setItem('name', loggedInClient.firstName)
      localStorage.setItem('surname', loggedInClient.lastName)
      localStorage.setItem('loggedInClient', JSON.stringify(loggedInClient));

      navigate(`/laborant/${loggedInClient.id}`)

    } else {
      setError('Invalid login credentials')
    }
  };

  return (
    <div className="login-container">
      <form >
        <label>
          Username
          <input
            name="username"
            value={loginData.email}
            onChange={handleChange}
          />
        </label>
        <label>
          Password
          <input
            type={showPassword ? 'text' : 'password'}
            name="password"
            value={loginData.password}
            onChange={handleChange}
          />
        </label>
        <label>
          Show Password
          <Checkbox
            checked={showPassword}
            onChange={() => setShowPassword(!showPassword)}
          />
        </label>
        <div className="tab-container">
          <button type="submit" onClick={handleLogin}>
            Login
          </button>
          <button onClick={handleRegister} >Register</button>
        </div>

        {error && (
          <Message negative>
            <Message.Header>Error</Message.Header>
            <p>{error}</p>
          </Message>
        )}
      </form>
    </div>
  );
}
