import React from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage: React.FC = () => {
  const navigate = useNavigate();

  const buttonStyles = {
    padding: '15px 25px',
    margin: '10px',
    backgroundColor: '#007BFF',
    color: 'white',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
    fontSize: '1rem',
  };

  const containerStyles = {
    textAlign: 'center' as React.CSSProperties['textAlign'],
    marginTop: '50px',
  };

  return (
    <div style={containerStyles}>
      <h1>Welcome to your Personal Finance Tracker</h1>
      <button
        style={buttonStyles}
        onClick={() => navigate('/create')}
      >
        Create Account
      </button>
      <button
        style={buttonStyles}
        onClick={() => navigate('/fetch-account')}
      >
        Fetch Account
      </button>
      <button
        style={buttonStyles}
        onClick={() => navigate('/set-goals')}
      >
        Set Goals to Account
      </button>
      <button
        style={buttonStyles}
        onClick={() => navigate('/add-transactions')}
      >
        Add Transactions to Account
      </button>
    </div>
  );
};

export default HomePage;
