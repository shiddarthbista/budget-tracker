import React, { useState } from 'react';
import { getAccountById, deleteAccount  } from '../../services/accountService';
import { useNavigate } from 'react-router-dom';

const FetchAccount: React.FC = () => {
  const [accountId, setAccountId] = useState('');
  const [accountInfo, setAccountInfo] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate(); // Initialize useNavigate hook

  const handleFetchAccount = async () => {
    try {
      setError(null); // Clear any previous errors
      const account = await getAccountById(accountId);
      setAccountInfo(account);
    } catch (err) {

      if(accountId === ''){
        setError('Enter account id.');
      }
      else{
        setError('Failed to fetch account information. Please try again.');
      }
      
      setAccountInfo(null);
    }
  };

  const handleCreateAccount = () => {
    navigate('/create'); // Navigate to the /createAccount page
  };

  const handleDeleteAccount = async () => {

    console.log(accountId);
    try {
      setError(null); // Clear any previous errors
      const account = await deleteAccount(accountId);

      window.location.reload()
    
    } catch (err) {

      if(accountId === ''){
        setError('Enter account id.');
      }
      else{
        setError('Failed to fetch account information. Please try again.');
      }
      
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h2>Fetch Account Information</h2>
      <div style={{ marginBottom: '10px' }}>
        <label htmlFor="accountId">Account ID:</label>
        <input
          type="text"
          id="accountId"
          value={accountId}
          onChange={(e) => setAccountId(e.target.value)}
          style={{
            width: '100%',
            padding: '8px',
            margin: '5px 0',
            border: '1px solid #ccc',
            borderRadius: '4px',
          }}
          placeholder="Enter account ID"
        />
      </div>
      <button
        onClick={handleFetchAccount}
        style={{
          padding: '10px 20px',
          backgroundColor: '#007BFF',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
        }}
      >
        Fetch Account
      </button>

      <button
        onClick={handleCreateAccount}
        style={{
          padding: '10px 30px',
          marginLeft: '20px',
          backgroundColor: '#007BFF',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
        }}
      >
        Create Account
      </button>

      {error && (
        <p style={{ color: 'red', marginTop: '10px' }}>
          <strong>{error}</strong>
        </p>
      )}

      {accountInfo && (
        <div style={{ marginTop: '20px', border: '1px solid #ccc', padding: '10px' }}>
          <h3>Account Details:</h3>
          <p>
            <strong>ID:</strong> {accountInfo.accountId}
          </p>
          <p>
            <strong>Name:</strong> {accountInfo.accountHolderName}
          </p>
          <p>
            <strong>Email:</strong> {accountInfo.email}
          </p>
          <p>
            <strong>Balance:</strong> ${accountInfo.accountBalance}
          </p>
          <p>
            <strong>Budget:</strong> ${accountInfo.budget}
          </p>
          <p>
            <strong>goals:</strong> {accountInfo.goals}
          </p>

          <button
        onClick={handleDeleteAccount}
        style={{
          padding: '10px 20px',
          backgroundColor: '#007BFF',
          color: 'white',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer',
        }}
      >
        Delete Account
      </button>
          {/* Add additional account fields as needed */}
        </div>
      )}
    </div>
  );
};

export default FetchAccount;
