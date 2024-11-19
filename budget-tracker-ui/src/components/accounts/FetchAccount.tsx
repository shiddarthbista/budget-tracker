import React, { useState } from 'react';
import { getAccountById } from '../../services/accountService';

const FetchAccount: React.FC = () => {
  const [accountId, setAccountId] = useState('');
  const [accountInfo, setAccountInfo] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);

  const handleFetchAccount = async () => {
    try {
      setError(null); // Clear any previous errors
      const account = await getAccountById(accountId);
      setAccountInfo(account);
    } catch (err) {
      setError('Failed to fetch account information. Please try again.');
      setAccountInfo(null);
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

      {error && (
        <p style={{ color: 'red', marginTop: '10px' }}>
          <strong>{error}</strong>
        </p>
      )}

      {accountInfo && (
        <div style={{ marginTop: '20px', border: '1px solid #ccc', padding: '10px' }}>
          <h3>Account Details:</h3>
          <p>
            <strong>ID:</strong> {accountInfo.id}
          </p>
          <p>
            <strong>Name:</strong> {accountInfo.name}
          </p>
          <p>
            <strong>Email:</strong> {accountInfo.email}
          </p>
          {/* Add additional account fields as needed */}
        </div>
      )}
    </div>
  );
};

export default FetchAccount;